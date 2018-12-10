package com.mark.common.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface JedisClient {
    JedisPool getJedisPool();

    void setJedisPool(JedisPool jedisPool);

    String set(String key, String value);

    Long setnx(String key, String value);

    String setex(String key, Integer seconds, String value);

    String get(String key);

    Long zadd(String key, Double score, String member);

    Double zscore(String key, String member);

    Double zIncrBy(String key, Double increment, String member);

    Boolean exists(String key);

    Long expire(String key, int seconds);

    Long ttl(String key);

    Long incr(String key);

    Long hset(String key, String field, String value);

    String hget(String key, String field);

    Map<String, String> hgetAll(String key);

    Long hdel(String key, String... field);

    Boolean hexists(String key, String field);

    List<String> hvals(String key);

    Long del(String key);

    Set<String> keys();

    Set<String> keys(String pattern);

    String lpop(String str);

    Long rpush(String key, String... values);

    Long llen(String key);

    String watch(String... keys);

    Transaction multi();
}
