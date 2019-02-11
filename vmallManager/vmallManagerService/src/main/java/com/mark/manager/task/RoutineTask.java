package com.mark.manager.task;

import com.mark.common.jedis.JedisClient;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

@Component
public class RoutineTask {
    @Autowired
    JedisClient jedisClient;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Scheduled(cron = "0 0 0 * * ?")
    public void resetUid() {
        Long current = System.currentTimeMillis();
        long todayAtZero = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();
        jedisClient.set("uid", String.valueOf(todayAtZero / 1000));
    }
//    @Scheduled(cron = "0 0/1 0/1 * * ?")
    @Scheduled(cron = "0/10 * * * * ?")
    public void ExpiredListening() {
        Message message1 = MessageBuilder.withBody("test".getBytes()).setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
        rabbitTemplate.convertAndSend("vmall.expired.exchange", "vmall.expired.bindingKey", message1);
    }
    @Scheduled(cron = "0/12 * * * * ?")
    public void topicSending() {
        Message message = MessageBuilder.withBody("topicSend!".getBytes()).setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
        rabbitTemplate.convertAndSend("vmall.expired.topic.exchange", "vmall.topic.routingKey", message);
    }
}
