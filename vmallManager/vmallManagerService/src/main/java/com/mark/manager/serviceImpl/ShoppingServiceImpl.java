package com.mark.manager.serviceImpl;

import com.mark.common.exception.RedisException;
import com.mark.common.jedis.JedisClient;
import com.mark.common.util.RedisLockUtil;
import com.mark.manager.service.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisException;

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

    /**
     * 使用lock锁并配置过期时间1秒
     * watch监控lock键，如果发现lock被改变，本次减库存失效
     *
     * @return
     */
    public Map<String, String> decreaseStock1() {
        Boolean delay = false;
        Map<String, String> data = new HashMap<String, String>();
        Jedis jedis = null;
        try {
            while(!jedisClient.exists(lock)) {
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
                } else {
                    // 库存还有
                    Transaction transaction = jedis.multi();
                    transaction.zincrby(key, Double.valueOf(-1), member);
                    List<Object> list = transaction.exec();
                    transaction.close();
                    jedis.del(lock);
                    if (list.size() > 0) {
                        System.out.println(list);
                        Double stockNow = jedis.zscore(key, member);
                        jedis.close();
                        data.put("stock", String.valueOf(stockNow));
                    } else {
                        data.put("stock", "抢购失败，lock被改变，库存：" + String.valueOf(currentStock));
                        jedis.close();
                    }
                }
                return data;
            }
            data.put("stock",  "太火爆了！请稍后再试！");
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            if (jedis.exists(lock)) jedis.del(lock);
            if (jedis != null) jedis.close();
        } catch (RedisException e) {
            data.put("stock", e.getMsg());
            return data;
        }
        return data;
    }

    public Map<String, String> decreaseStock2() {
        Map<String, String> data = new HashMap<String, String>();
        Long now = System.currentTimeMillis();
        // 给lock设置锁，如果设置失败就需要判断是不是死锁
        while(jedisClient.setnx(lock, String.valueOf(now + 1000)) == 0) {
            if(now > Long.valueOf(jedisClient.get(lock)) && now > Long.valueOf(jedisClient.getSet(lock, String.valueOf(now + 1000)))) {
                // 锁已经超时，然后再去获取现在lock中的内容是否依然超时，并设置新的超时时间；超时说明锁过期了失效了可以操作了，否则休眠30ms
                break;
            } else {
                // 锁还没超时，继续等待
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
    public Map<String, String> decreaseStock3(String name) {
        Map<String, String> map = new HashMap<String, String>();
        JedisPool jedisPool = jedisClient.getJedisPool();
        RedisLockUtil redisLockUtil = null;
        Jedis jedis = null;
        Long time = System.currentTimeMillis() + 1000;
        try {
            jedis = jedisPool.getResource();
            redisLockUtil = new RedisLockUtil(jedis);
            Double stock = jedis.zscore(key, member);
            if (stock <= 0) {
                map.put("stock", "已经被抢完啦！早没了");
                System.out.println("已经被抢完啦！早没了");
                return map;
            }
            Boolean lockNow = redisLockUtil.lock(lock, String.valueOf(time));
            if (!lockNow) {
                map.put("stock", "活动火爆，稍后再试（锁没放开）");
                System.out.println("活动火爆，稍后再试（锁没放开）");
                return map;
            }
            if (jedis.zscore(key, member) > 0) {
                jedis.zincrby(key, Double.valueOf("-1"), member);
            } else {
                map.put("stock", "已经抢完了");
                System.out.println("已经抢完了");
                return map;
            }
            Long affect = jedis.sadd("member", name);
            if (affect == 0) {
                jedis.zincrby(key, Double.valueOf("1"), member);
                map.put("stock", "不可以重复抢购");
                System.out.println("不可以重复抢购");
                return map;
            }
            map.put("stock", "抢购成功，剩余库存：" + jedis.zscore(key, member));
            System.out.println("抢购成功，剩余库存：" + jedis.zscore(key, member));
            return map;
        } catch (JedisException e) {
            System.out.println("获取连接超时");
            map.put("stock", "获取连接超时");
            return map;
        } finally {
            System.out.println("关闭jedis句柄");
            if (redisLockUtil != null) redisLockUtil.unlock(lock, String.valueOf(time));
            if (jedis != null) jedis.close();
        }
    }
}
