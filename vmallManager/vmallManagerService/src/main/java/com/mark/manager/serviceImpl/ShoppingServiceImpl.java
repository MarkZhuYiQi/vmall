package com.mark.manager.serviceImpl;

import com.mark.common.jedis.JedisClient;
import com.mark.manager.service.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
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
    private static final String lock = "lock";
    private static final String key = "stock";
    private static final String member = "panicBuying";

    @PostConstruct
    public void test() {
        Integer stock = 100;
        jedisClient.zadd("stock", Double.valueOf(String.valueOf(stock)), "panicBuying");
        String[] values = new String[stock];
        for(Integer i = 0; i < stock; i++) {
            values[i] = String.valueOf(stock);
        }
        jedisClient.rpush(listName, values);
    }

    public Map<String, String> decreaseStock() throws InterruptedException {
        Boolean delay = false;
        Map<String, String> data = new HashMap<String, String>();
        Jedis jedis = null;
        while(!jedisClient.exists(lock)) {
            try {
                JedisPool jedisPool = jedisClient.getJedisPool();
                jedis = jedisPool.getResource();
                jedis.setex(lock, 1, String.valueOf(1));
                String status = jedis.watch(lock);
                Double currentStock = jedis.zscore(key, member);
                if (currentStock <= 0) {
                    // 库存没了
                    jedis.del(lock);
                    jedis.close();
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
                    Transaction transaction = jedis.multi();
                    transaction.zincrby(key, Double.valueOf(-1), member);
                    List<Object> list = transaction.exec();
                    transaction.close();
                    jedis.del(lock);
                    if (list.size() > 0) {
                        Double stockNow = jedis.zscore(key, member);
                        jedis.close();
                        data.put("stock", String.valueOf(stockNow));
                        return data;
                    } else {
                        data.put("stock", String.valueOf(currentStock));
                        jedis.close();
                        return data;
                    }
                }
            }catch (RuntimeException | IOException e) {
                e.printStackTrace();
                if (jedis.exists(lock)) jedis.del(lock);
                if (jedis != null) jedis.close();
                break;
            }
        }
        data.put("stock",  "太火爆了！请稍后再试！");
        return data;
    }

    public Map<String, String> decreaseStock2() {
        Map<String, String> data = new HashMap<String, String>();
        Long now = System.currentTimeMillis();
        // 给lock设置锁，如果设置失败就需要判断是不是死锁
        while(jedisClient.setnx(lock, String.valueOf(now + 1000)) == 0) {
            System.out.println(jedisClient.get(lock));
            System.out.println(jedisClient.getSet(lock, String.valueOf(now + 1000)));
            if(now > Long.valueOf(jedisClient.get(lock)) && now > Long.valueOf(jedisClient.getSet(lock, String.valueOf(now + 1000)))) {
                // 锁已经超时，然后再去获取现在lock中的内容是否依然超时，并设置新的超时时间；超时说明锁过期了失效了可以操作了，否则休眠30ms
                break;
            }else{
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        Jedis jedis = null;
        try {
            JedisPool jedisPool = jedisClient.getJedisPool();
            jedis = jedisPool.getResource();
            String status = jedis.watch(lock);
            Double currentStock = jedis.zscore(key, member);
            if (currentStock <= 0) {
                // 库存没了
                jedis.del(lock);
                jedis.close();
                data.put("stock", String.valueOf(currentStock));
                return data;
            } else {
                // 库存还有
                Transaction transaction = jedis.multi();
                transaction.zincrby(key, Double.valueOf(-1), member);
                List<Object> list = transaction.exec();
                transaction.close();
                jedis.del(lock);
                if (list.size() > 0) {
                    Double stockNow = jedis.zscore(key, member);
                    jedis.close();
                    data.put("stock", String.valueOf(stockNow));
                    return data;
                } else {
                    data.put("stock", String.valueOf(currentStock));
                    jedis.close();
                    return data;
                }
            }
        }catch (RuntimeException | IOException e) {
            e.printStackTrace();
            if (jedis.exists(lock)) jedis.del(lock);
            if (jedis != null) jedis.close();
        }
        return data;
    }
}
