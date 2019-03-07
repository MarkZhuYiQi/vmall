package com.mark.manager.task;

import com.mark.common.exception.CourseException;
import com.mark.common.jedis.JedisClient;
import com.mark.manager.dao.CourseDao;
import com.mark.manager.mapper.CoursesMapper;
import com.mark.manager.mapper.SupportRateMapper;
import com.mark.manager.mapper.VproCommentSupportRateMapper;
import com.mark.manager.pojo.VproCommentSupportRate;
import com.mark.manager.pojo.VproCommentSupportRateExample;
import com.mark.manager.threads.ImportClicksConcurrent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Tuple;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

@Component
public class RoutineTask {
    private final static Logger logger = LoggerFactory.getLogger(RoutineTask.class);
    @Autowired
    JedisClient jedisClient;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    private CoursesMapper coursesMapper;

    @Autowired
    @Qualifier("courseByRedis")
    CourseDao courseDao;

    @Scheduled(cron = "0 0 0 * * ?")
    public void resetUid() {
        Long current = System.currentTimeMillis();
        long todayAtZero = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();
        jedisClient.set("uid", String.valueOf(todayAtZero / 1000));
    }
//    @Scheduled(cron = "0/10 * * * * ?")
//    public void ExpiredListening() {
//        Message message1 = MessageBuilder.withBody("test".getBytes()).setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
//        rabbitTemplate.convertAndSend("vmall.expired.exchange", "vmall.expired.bindingKey", message1);
//    }
//    @Scheduled(cron = "0/12 * * * * ?")
//    public void topicSending() {
//        Message message = MessageBuilder.withBody("topicSend!".getBytes()).setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
//        rabbitTemplate.convertAndSend("vmall.expired.topic.exchange", "vmall.topic.routingKey", message);
//    }

    @Value("${rabbitmq.topicQueue}")
    String topicQueue;

    @Value("${rabbitmq.topickey}")
    String topickey;

    @Value("${rabbitmq.topicExchange}")
    String topicExchange;

    @Value("${coursesClicksSummary}")
    String courseClicksSummary;

    @Scheduled(cron = "0 0 0/2 * * ?")
    public void expiredChecking() {
        try {
            Set<String> expiredSet = courseDao.checkIndexCourseCache();
            Message message;
            for(String expiredKey : expiredSet) {
                message = MessageBuilder.withBody(expiredKey.getBytes()).setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
                rabbitTemplate.convertAndSend(topicExchange, topickey, message);
            }
        } catch (CourseException e) {
            logger.info(e.getMsg());
        }
    }

    /**
     * 从redis导出clickNum到数据库
     */
    @Scheduled(cron = "0 0 2 * * ? ")
    public void updateClickNumFromRedis() {
        Integer splitSize = 5000;
        Set<Tuple> set = jedisClient.zrangeWithScores(courseClicksSummary, 0L, -1L);
        List<List<Map<String, Integer>>> list = new ArrayList<>();
        // 切分成几个list
        Integer t = set.size() / splitSize;
        Double threadNum = Math.floor(t.doubleValue());
        // 如果t个模块正好等于总数，那么就不需要再多一个模块放余下的了，否则需要再加一个模块
        t = threadNum * splitSize == set.size() ? t : t + 1;
        // 转换set到list
        List<Map<String, Integer>> update = new ArrayList<>();
        for (Tuple tuple : set) {
            Map<String, Integer> map = new HashMap<>();
            map.put("courseId", Integer.parseInt(tuple.getElement()));
            map.put("click", new Double(tuple.getScore()).intValue());
            update.add(map);
        }
        for (int i = 0; i < t; i++) {
            // 最后一个位置
            Integer total = ((i + 1) * splitSize - 1) >= set.size() ? set.size() - 1 : (i + 1) * splitSize - 1;
            list.add(new ArrayList<Map<String, Integer>>(update.subList(i * splitSize, total)));
        }
        logger.info("start update clicks info");
        // 多线程执行
        final Semaphore semaphore = new Semaphore(t);
        ExecutorService threadPool = Executors.newCachedThreadPool();
        for (int j = 0; j < list.size(); j++) {
            threadPool.execute(new ImportClicksConcurrent(semaphore, update, coursesMapper));
        }
        threadPool.shutdown();
        logger.info("update finished");
    }

    @Autowired
    VproCommentSupportRateMapper vproCommentSupportRateMapper;

    @Autowired
    SupportRateMapper supportRateMapper;

    @Value("${commentAgreePrefix}")
    String commentAgreePrefix;

    @Value("${commentOpposePrefix}")
    String commentOpposePrefix;
    @Scheduled(cron = "0 30 0/2 * * ?")
    public void importSupportRateFromDB() {
        if (jedisClient.exists(commentAgreePrefix) && jedisClient.exists(commentOpposePrefix)) return;
        Integer size = 1000;
        VproCommentSupportRateExample vproCommentSupportRateExample = new VproCommentSupportRateExample();
        vproCommentSupportRateExample.createCriteria();
        Long count = vproCommentSupportRateMapper.countByExample(vproCommentSupportRateExample);
        Integer split = (int)Math.ceil(count / size);
        JedisPool jedisPool = jedisClient.getJedisPool();
        Jedis jedis = jedisPool.getResource();
        Pipeline p = jedis.pipelined();
        for (int i = 0; i < split; i++) {
            Map<String, Integer> splitCriteria = new HashMap<String, Integer>();
            splitCriteria.put("offset", i * size);
            splitCriteria.put("limit", size);
            List<VproCommentSupportRate> rate = supportRateMapper.getSupportRateByLimit(splitCriteria);
            Map<String, String> agree = new HashMap<String, String>();
            Map<String, String> oppose = new HashMap<String, String>();
            for (VproCommentSupportRate vproCommentSupportRate : rate) {
                agree.put(String.valueOf(vproCommentSupportRate.getCommentId()), String.valueOf(vproCommentSupportRate.getCommentAgree()));
                oppose.put(String.valueOf(vproCommentSupportRate.getCommentId()), String.valueOf(vproCommentSupportRate.getCommentOppose()));
            }
            p.hmset(commentAgreePrefix, agree);
            p.hmset(commentOpposePrefix, oppose);
        }
        p.sync();
        jedis.close();
    }

    @Value("${commentAgreeSetPrefix}")
    String commentAgreeSetPrefix;

    @Value("${commentOpposeSetPrefix}")
    String commentOpposeSetPrefix;

    public void transferSupportRateFromRedisToDB() {
        if (!jedisClient.exists(commentAgreeSetPrefix) && !jedisClient.exists(commentOpposeSetPrefix)) return;
        Set<String> agreeKeyNeedUpdate = jedisClient.smembers(commentAgreeSetPrefix);
        Set<String> opposeKeyNeedUpdate = jedisClient.smembers(commentOpposeSetPrefix);
        List<Map<String, Integer>> agreeMap = genCommentSupportRateMapping(agreeKeyNeedUpdate);
        List<Map<String, Integer>> opposeMap = genCommentSupportRateMapping(opposeKeyNeedUpdate);
        supportRateMapper.batchUpdateSupportRate(agreeMap, "agree");
        supportRateMapper.batchUpdateSupportRate(opposeMap, "oppose");

    }
    private List<Map<String, Integer>> genCommentSupportRateMapping(Set<String> keysNeedUpdate) {
        List<Map<String, Integer>> rateList = new ArrayList<>();
        if (keysNeedUpdate.size() == 0) return rateList;
        String[] keys = keysNeedUpdate.toArray(new String[keysNeedUpdate.size()]);
        List<String> num = jedisClient.hmget(commentAgreeSetPrefix, keys);
        for (int i = 0; i < keys.length; i++) {
            Map<String, Integer> supportRateMapping = new HashMap<>();
            supportRateMapping.put("key", Integer.parseInt(keys[i]));
            supportRateMapping.put("rate", Integer.parseInt(num.get(i)));
            rateList.add(supportRateMapping);
        }
        return rateList;
    }
}
