package com.mark.common.jedis;

import com.mark.common.exception.RedisException;
import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class JedisClientPool implements JedisClient{
    private JedisPool jedisPool;

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    // 获得redis操作句柄
    public Jedis getResource() throws RedisException {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis;
        } catch(JedisException e) {
            throw new RedisException("未能得到redis操作句柄", e);
        }
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
    public Long setnx(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        Long res = jedis.setnx(key, value);
        jedis.close();
        return res;
    }

    @Override
    public String setex(String key, Integer seconds, String value) {
        Jedis jedis = jedisPool.getResource();
        String res = jedis.setex(key, seconds, value);
        jedis.close();
        return res;
    }

    @Override
    public String getSet(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        String res = jedis.getSet(key, value);
        jedis.close();
        return res;
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
    public Double zIncrBy(String key, Double increment, String member) {
        Jedis jedis = jedisPool.getResource();
        Double score = jedis.zincrby(key, increment, member);
        jedis.close();
        return score;
    }

    @Override
    public Set<String> zRange(String key, long start, long end) {
        Jedis jedis = getResource();
        Set<String> members = jedis.zrange(key, start, end);
        jedis.close();
        return members;
    }
    @Override
    /**
     * 根据分数范围取值
     */
    public Set<String> zRangeByScore(String key, Double min, Double max) {
        Jedis jedis = getResource();
        Set<String> members = jedis.zrangeByScore(key, min, max);
        jedis.close();
        return members;
    }
    @Override
    public Set<Tuple> zrangeByScoreWithScores(String key, Double min, Double max) {
        Jedis jedis = getResource();
        Set<Tuple> members = jedis.zrangeByScoreWithScores(key, min, max);
//        Set<Tuple> members = jedis.zrangeByScoreWithScores(key, min, max, offset, count);
        jedis.close();
        return members;
    }

    @Override
    public Boolean exists(String key) throws RedisException{
        Jedis jedis = getResource();
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
    public Map<String, String> hgetAll(String key) {
        Jedis jedis = jedisPool.getResource();
        Map<String, String> result = jedis.hgetAll(key);
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

    @Override
    public String lpop(String str) {
        Jedis jedis = jedisPool.getResource();
        String result = jedis.lpop(str);
        jedis.close();
        return result;
    }

    @Override
    public Long rpush(String key, String... values) {
        Jedis jedis = jedisPool.getResource();
        Long res = jedis.rpush(key, values);
        jedis.close();
        return res;
    }

    @Override
    public Long llen(String key) {
        Jedis jedis = jedisPool.getResource();
        Long res = jedis.llen(key);
        jedis.close();
        return res;
    }

    @Override
    public String watch(String... keys) {
        Jedis jedis = jedisPool.getResource();
        String status = jedis.watch(keys);
        jedis.close();
        return status;
    }

    @Override
    public Transaction multi() {
        Jedis jedis = jedisPool.getResource();
        Transaction transaction = jedis.multi();
        return transaction;
    }

}
