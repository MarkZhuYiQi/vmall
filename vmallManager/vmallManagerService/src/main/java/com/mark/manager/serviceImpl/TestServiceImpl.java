package com.mark.manager.serviceImpl;

import com.mark.common.jedis.JedisClient;
import com.mark.manager.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    JedisClient jedisClient;

    @Override
    public String test() {
//        System.out.println(jedisClient.zscore("test", "test"));
        String str = jedisClient.get("test1");
        return str;
    }
}
