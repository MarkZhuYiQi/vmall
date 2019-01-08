package com.mark.manager.task;

import com.mark.common.jedis.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.TimeZone;

@Component
public class RoutineTask {
    @Autowired
    JedisClient jedisClient;
    @Scheduled(cron = "0 0 0 * * ?")
    public void resetUid() {
        Long current = System.currentTimeMillis();
        long todayAtZero = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();
        jedisClient.set("uid", String.valueOf(todayAtZero / 1000));
    }
}
