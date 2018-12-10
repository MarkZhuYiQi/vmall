package com.mark.manager.serviceImpl;

import com.mark.common.jedis.JedisClient;
import com.mark.manager.service.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShoppingServiceImpl implements ShoppingService {

    @Autowired
    JedisClient jedisClient;

    private static final String listName = "stockList";

    @PostConstruct
    public void test() {
        Integer stock = 100;
        jedisClient.zadd("stock", Double.valueOf(String.valueOf(stock)), "panicBuying");
        String[] values = new String[stock];
        for(Integer i = 0; i < stock; i++) {
            values[i] = String.valueOf(stock);
        }
//        jedisClient.rpush(listName, values);
    }

    public Map<String, String> decreaseStock() {
        Boolean delay = true;
        String lock = "lock";
        String key = "stock";
        String member = "panicBuying";
        Map<String, String> data = new HashMap<String, String>();
        while(!jedisClient.exists(lock)) {
            System.out.println("lock" + lock);
            jedisClient.setex(lock, 1, String.valueOf(1));
            System.out.println("setex finished.");
            String status = jedisClient.watch(lock);
            System.out.println("begin watch");
            Double currentStock = jedisClient.zscore(key, member);
            System.out.println("currentStock");
            if (currentStock <= 0) {
                // 库存没了
                jedisClient.del(lock);
                data.put("stock", String.valueOf(currentStock));
                return data;
            } else {
                // 库存还有
                // 模拟卡顿
                if (delay) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Transaction transaction = jedisClient.multi();
                System.out.println("tranaction");
                Double score = jedisClient.zIncrBy(key, Double.valueOf(-1), member);
                System.out.println("decrease stock important!");
                jedisClient.del(lock);
                List<Object> list = transaction.exec();
                System.out.println("exec");
                try {
                    transaction.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (list.size() > 0) {
                    Double stockNow = jedisClient.zscore(key, member);
                    data.put("stock", String.valueOf(stockNow));
                    return data;
                } else {
                    data.put("stock", String.valueOf(currentStock));
                    return data;
                }
            }
        }
        return data;
    }
}
