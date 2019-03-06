package com.mark.manager.task;

import com.mark.common.exception.CourseException;
import com.mark.common.jedis.JedisClient;
import com.mark.manager.dao.CourseDao;
import com.mark.manager.mapper.CoursesMapper;
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
}
