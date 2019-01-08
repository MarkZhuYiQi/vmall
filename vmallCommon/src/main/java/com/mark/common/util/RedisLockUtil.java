package com.mark.common.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisLockUtil {
    private Jedis jedis;

    public RedisLockUtil(Jedis jedis) {
        this.jedis = jedis;
    }
    /*
    加锁
     */
    public boolean lock(String key,String value) {
        //setnx，成功设置直接返回true，否则继续
        if ( jedis.setnx(key,value) == 1){
            return true;
        }
        // 两个问题，Q1超时时间
        // 先尝试获取锁的时间戳
        String currentValue = jedis.get(key);
        // 判断时间戳不为空并且是否已经过期，如果锁的时间戳小于当前时间说明已过期，锁已经失效了，可以重设
        if (currentValue != null && !currentValue.equals("") && !currentValue.isEmpty() && Long.parseLong(currentValue) < System.currentTimeMillis()){
            //Q2 在线程超时的时候，多个线程争抢锁的问题
            // 尝试争抢锁
            String oldValue = jedis.getSet(key, value);
            // 拿到时间戳如果仍然是超时的，说明拿到锁了，说明在设置这个锁之前没有其他线程争抢到
            if (oldValue != null && !oldValue.equals("") && !oldValue.isEmpty() && Long.parseLong(oldValue) < System.currentTimeMillis()){
                return true;
            }
        }
        return false;
    }

    public void unlock(String key ,String value){
        try{
            String currentValue = jedis.get(key);
            if (currentValue != null && !currentValue.equals("") && !currentValue.isEmpty() && currentValue.equals(value)){
                jedis.del(key);
            }
        }catch(Exception e){
            System.out.println("redis分布上锁解锁异常, {}");
        }
    }
}
