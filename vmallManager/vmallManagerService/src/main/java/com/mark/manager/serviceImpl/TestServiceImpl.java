package com.mark.manager.serviceImpl;

import com.mark.common.jedis.JedisClient;
import com.mark.manager.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    @Override
    public Long test() {
        Long time = System.currentTimeMillis();
        System.out.println(time);
        return time;
    }
}
