package com.mark.common.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.util.List;
import java.util.Set;

public class JedisClientPool implements JedisClient{
    private JedisPool jedisPool;

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public String set(String key, String value)
    {
        Jedis jedis = jedisPool.getResource();
        String result = jedis.set(key, value);
        jedis.close();
        return result;
    }
    @Override
    public String get(String key)
    {
        Jedis jedis = jedisPool.getResource();
        String value = jedis.get(key);
        jedis.close();
        return value;
    }
    @Override
    public Long zadd(String key, Double score, String member)
    {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.zadd(key, score, member);
        jedis.close();
        return result;
    }
    @Override
    public Double zscore(String key, String member)
    {
        Jedis jedis = jedisPool.getResource();
        Double score = jedis.zscore(key, member);
        jedis.close();
        return score;
    }

    @Override
    public Boolean exists(String key) {
        Jedis jedis = jedisPool.getResource();
        Boolean result = jedis.exists(key);
        jedis.close();
        return result;
    }

    @Override
    public Long expire(String key, int seconds) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.expire(key, seconds);
        jedis.close();
        return result;
    }

    @Override
    public Long ttl(String key) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.ttl(key);
        jedis.close();
        return result;
    }

    @Override
    public Long incr(String key) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.incr(key);
        jedis.close();
        return result;
    }

    @Override
    public Long hset(String key, String field, String value) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.hset(key, field, value);
        jedis.close();
        return result;
    }

    @Override
    public String hget(String key, String field) {
        Jedis jedis = jedisPool.getResource();
        String result = jedis.hget(key, field);
        jedis.close();
        return result;
    }

    @Override
    public Long hdel(String key, String... field) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.hdel(key, field);
        jedis.close();
        return result;
    }

    @Override
    public Boolean hexists(String key, String field) {
        Jedis jedis = jedisPool.getResource();
        Boolean result = jedis.hexists(key, field);
        jedis.close();
        return result;
    }

    @Override
    public List<String> hvals(String key) {
        Jedis jedis = jedisPool.getResource();
        List<String> result = jedis.hvals(key);
        jedis.close();
        return result;
    }

    @Override
    public Long del(String key) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.del(key);
        jedis.close();
        return result;
    }

    @Override
    public Set<String> keys() {
        return keys("");
    }

    @Override
    public Set<String> keys(String pattern) {
        Jedis jedis = jedisPool.getResource();
        Set<String> result = jedis.keys(pattern);
        jedis.close();
        return result;
    }

}
