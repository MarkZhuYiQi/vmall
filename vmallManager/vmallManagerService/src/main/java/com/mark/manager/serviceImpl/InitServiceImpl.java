package com.mark.manager.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mark.common.elasticsearch.ElasticsearchUtil;
import com.mark.common.jedis.JedisClient;
import com.mark.common.jedis.JedisClientPool;
import com.mark.common.pojo.ESQuery;
import com.mark.manager.config.ElasticRestClientPool;
import com.mark.manager.config.ElasticsearchPool;
import com.mark.manager.dao.CourseDao;
import com.mark.manager.daoImpl.CourseDaoByRedisImpl;
import com.mark.manager.dto.Courses;
import com.mark.manager.mapper.*;
import com.mark.manager.pojo.*;
import com.mark.manager.service.CategoryService;
import com.mark.manager.service.CourseService;
import com.mark.manager.service.InitService;
import com.mark.manager.task.RoutineTask;
import com.mark.manager.threads.CourseOutput;
import com.mark.manager.threads.ElasticSearchImport;
import com.mark.manager.threads.NameThreadFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Tuple;

import javax.annotation.PostConstruct;
import java.beans.IntrospectionException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

import static com.mark.common.util.BeanUtil.bean2map;

@Service
public class InitServiceImpl implements InitService {

    private static final Logger logger = LoggerFactory.getLogger(InitServiceImpl.class);

    @Value("${navbarPrefix}")
    private String navbarPrefix;
    @Value("${rolePrefix}")
    private String rolePrefix;
    @Value("${authPrefix}")
    private String authPrefix;
    @Value("${coursePrefix}")
    private String coursePrefix;
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private VproRolesMapper vproRolesMapper;
    @Autowired
    private VproAuthMapper vproAuthMapper;
    @Autowired
    private CoursesMapper coursesMapper;
    @Autowired
    private VproCoursesMapper vproCoursesMapper;
    @Autowired
    private JedisClient jedisClient;

    @Override
//    @PostConstruct
    public void genCategoryCache() {
        List<VproNavbar> categories = categoryService.getCategories();
        logger.info("开始管道插入" + navbarPrefix);
        Jedis jedis = jedisPool.getResource();
        Pipeline p = jedis.pipelined();
//        categories.stream().forEach(item -> System.out.println(item));
        for(VproNavbar vproNavbar : categories) {
            String categoryName = navbarPrefix + vproNavbar.getNavId();
            Map<String, String> map = null;
            try {
                map = bean2map(vproNavbar);
            } catch (IntrospectionException e) {
                logger.warn("map转换失败，nav记录id：" + rolePrefix);
                e.printStackTrace();
            }
            p.hmset(categoryName, map);
        }
        p.sync();
        jedis.close();
        logger.info("插入完成" + navbarPrefix);
    }

    @Override
//    @PostConstruct
    public void genUserRoleCache() {
        List<VproRoles> vproRoles = vproRolesMapper.selectByExample(new VproRolesExample());
        logger.info("开始管道插入" + rolePrefix);
        Jedis jedis = jedisPool.getResource();
        Pipeline p = jedis.pipelined();
        for(VproRoles rs : vproRoles) {
            String roleName = rolePrefix + String.valueOf(rs.getRolesId());
            // 这个方法限制很多
//            Map<String, Object> rsm = BeanUtil.beanToMap(rs);
            Map<String, String> map = null;
            try {
                map = bean2map(rs);
            } catch (IntrospectionException e) {
                logger.warn("map转换失败，role记录id：" + rolePrefix);
                e.printStackTrace();
            }
            p.hmset(roleName, map);
        }
        p.sync();
        jedis.close();
        logger.info("插入完成" + rolePrefix);
    }

//    @PostConstruct
    @Override
    public void genUserCache() {
        List<VproAuth> vproAuths = vproAuthMapper.selectByExample(new VproAuthExample());
        Jedis jedis = jedisPool.getResource();
        Pipeline p = jedis.pipelined();
        for(VproAuth va : vproAuths) {
            String authName = authPrefix + String.valueOf(va.getAuthAppid());
            // 这个方法限制很多
//            Map<String, Object> rsm = BeanUtil.beanToMap(rs);
            Map<String, String> map = null;
            try {
                map = bean2map(va);
            } catch (IntrospectionException e) {
                logger.warn("map转换失败，auth记录id：" + rolePrefix);
                e.printStackTrace();
            }
            p.hmset(authName, map);
        }
        p.sync();
        jedis.close();
        logger.info("插入完成" + rolePrefix);
    }

    private final Integer fragment = 3000;

//    @PostConstruct
    @Override
    public void coursesCache() {
        Long count = coursesMapper.getCoursesCount();
        // 创建索引
        try {
            ElasticsearchUtil.createIndexAndMapping();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Integer sharding = (int)(Math.ceil(count / fragment));
        if (sharding > 0)
        {
            List<Map<String, String>> idList = new ArrayList<Map<String, String>>();
            String lastCourseId = null;
            for (int i = 0; i <= sharding; i++)
            {
                Map<String, String> idRange = new HashMap<String, String>();
                if (lastCourseId != null) idRange.put("courseIdStart", lastCourseId);
                lastCourseId = coursesMapper.getCourseId(lastCourseId == null ? null : Long.parseLong(lastCourseId), fragment);
                idRange.put("courseIdEnd", lastCourseId);
                idRange.put("sharding", String.valueOf(i));
                idList.add(idRange);
            }
            // 创建一个定长的线程池， 自定义参数
            ExecutorService exec = Executors.newFixedThreadPool (idList.size(), new NameThreadFactory());
            List<Callable<String>> taskList = new ArrayList<Callable<String>>();
            for(Map<String, String> idRange : idList)
            {
                // 批量添加创建json文件任务
                taskList.add(new CourseOutput(idRange, coursesMapper, jedisClient, coursePrefix));
            }
            // 结果存放列表
            List<Future<String>> resultList = new ArrayList<Future<String>>();
            // 执行所有任务
            try{
                resultList = exec.invokeAll(taskList);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
            try {
                taskList.clear();
                /*从future中输出每个任务的返回值*/
                for (Future<String> future : resultList) {
                    //get方法会阻塞直到结果返回
                    String filename = "D:/wamp/www/vmall/" + future.get() + ".txt";
                    taskList.add(new ElasticSearchImport(filename));
                }
                exec.invokeAll(taskList);
                for (Future<String> future : resultList) {
                    System.out.println(future.get());
                }
                exec.shutdown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

//        List<Courses> list = coursesMapper.getCourses();
//        System.out.println(JSON.toJSONString(idList));
    }

//    @PostConstruct
    public void testThread() {
        ExecutorService exec = Executors.newFixedThreadPool (8, new NameThreadFactory());
        for(int i = 0; i < 5; i++) {
            exec.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + " doing task");
                }
            });
        }
        exec.shutdown();
    }

    @PostConstruct
    @Bean
    public GenericObjectPool<TransportClient> elasticsearchConfig() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();//对象池配置类，不写也可以，采用默认配置
        poolConfig.setMaxTotal(20);
        poolConfig.setMinIdle(5);
        ElasticsearchPool elasticsearchPool = new ElasticsearchPool();
        GenericObjectPool<TransportClient> clientPool = new GenericObjectPool<TransportClient>(elasticsearchPool, poolConfig);
        return clientPool;
    }
    @PostConstruct
    @Bean
    public GenericObjectPool<RestHighLevelClient> elasticRestClientPool() {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();//对象池配置类，不写也可以，采用默认配置
        poolConfig.setMaxTotal(20);
        poolConfig.setMinIdle(5);
        ElasticRestClientPool elasticRestClientPool = new ElasticRestClientPool();
        GenericObjectPool<RestHighLevelClient> clientPool = new GenericObjectPool<RestHighLevelClient>(elasticRestClientPool, poolConfig);
        return clientPool;
    }
    @PostConstruct
    public void setUid() {
        Long current = System.currentTimeMillis();
        long todayAtZero = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();
        String dataInRedis = jedisClient.get("uid");
        if (dataInRedis == null || (Long.parseLong(dataInRedis) < todayAtZero)) {
            jedisClient.set("uid", String.valueOf(todayAtZero / 1000));
        }
    }

    @Value("${coursesClicksSummary}")
    String courseClicksSummary;

    @Autowired
    VproCoursesTempDetailMapper vproCoursesTempDetailMapper;

    /**
     * 点击set存储格式：key: courses.clicks.set; member: courseId; score: clickNumber
     */
//    @PostConstruct
    public void transferClickNum() {
        System.out.println(courseClicksSummary);
        VproCoursesTempDetailExample vproCoursesTempDetailExample = new VproCoursesTempDetailExample();
        vproCoursesTempDetailExample.createCriteria();
        List<VproCoursesTempDetail> list = vproCoursesTempDetailMapper.selectByExample(vproCoursesTempDetailExample);
        logger.info("开始管道插入" + navbarPrefix);
        Jedis jedis = jedisPool.getResource();
        Pipeline p = jedis.pipelined();
        for(VproCoursesTempDetail vproCoursesTempDetail : list) {
            Integer courseClickNum  = vproCoursesTempDetail.getCourseClickNum();
            Integer courseId = vproCoursesTempDetail.getCourseId();
            // key, score, member
            p.zadd(courseClicksSummary, courseClickNum, String.valueOf(courseId));
        }
        p.sync();
        jedis.close();
        logger.info("插入完成" + navbarPrefix);
    }

}
