package com.mark.common.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisLockUtil {
    private Jedis jedis;

    public RedisLockUtil(Jedis jedis) {
        this.jedis = jedis;
    }

    /**
     * 加锁
     * 第一个问题：超时
     * 获取锁的时间戳，判断锁（时间戳）不为空并且是否已经过期，如果过期就说明失效了，立即重设
     * 第二个问题，争抢
     * 设置完毕再次去获得锁时间戳，如果拿到的锁时间戳是超时的，说明抢到了
     * 否则说明这个锁被别的线程抢走了。
     * @param key
     * @param value
     * @return
     */
    public boolean lock(String key,String value) {
        try {
            //setnx，成功设置直接返回true，否则继续
            if ( jedis.setnx(key,value) == 1) {
                jedis.pexpireAt(key, Long.parseLong(value));
                return true;
            }
            // 两个问题，Q1: 超时时间
            // 先尝试获取锁的时间戳
            String currentValue = jedis.get(key);
            // 判断时间戳不为空并且是否已经过期，如果锁的时间戳小于当前时间说明已过期，锁已经失效了，可以重设
            if (currentValue != null && !currentValue.equals("") && !currentValue.isEmpty() && Long.parseLong(currentValue) < System.currentTimeMillis()){
                //Q2: 在线程超时的时候，多个线程争抢锁的问题
                // 尝试争抢锁
                String oldValue = jedis.getSet(key, value);
                // 拿到时间戳如果仍然是超时的，说明拿到锁了，说明在设置这个锁之前没有其他线程争抢到
                if (oldValue != null && !oldValue.equals("") && !oldValue.isEmpty() && Long.parseLong(oldValue) < System.currentTimeMillis()){
                    jedis.pexpireAt(key, Long.parseLong(value));
                    return true;
                }
            }
            return false;
        } finally {
            jedis.close();
        }
    }

    public void unlock(String key) {
        try {
            String currentValue = jedis.get(key);
            if (currentValue != null && !currentValue.equals("") && !currentValue.isEmpty()) {
                jedis.del(key);
            }
        }catch(Exception e) {
            System.out.println("redis分布上锁解锁异常, {}");
        } finally {
            jedis.close();
        }
    }
}
