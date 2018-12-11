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
    public boolean lock(String key,String value){

        //setIfAbsent对应redis中的setnx，key存在的话返回false，不存在返回true
        if ( jedis.setnx(key,value) == 1){
            return true;
        }
        //两个问题，Q1超时时间
        String currentValue = jedis.get(key);
        // 判断字符串不为空并且锁的时间已经过期
        if (currentValue != null && !currentValue.equals("") && !currentValue.isEmpty() && Long.parseLong(currentValue) < System.currentTimeMillis()){
            //Q2 在线程超时的时候，多个线程争抢锁的问题
            // 抢到资源立马改锁值（时间戳）
            String oldValue = jedis.getSet(key, value);
            // 如果之前设定的值不为空，并且和当前的锁值（时间）相同，说明是当前线程设置的值
            if (oldValue != null && !oldValue.equals("") && !oldValue.isEmpty() && oldValue.equals(currentValue)){
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
