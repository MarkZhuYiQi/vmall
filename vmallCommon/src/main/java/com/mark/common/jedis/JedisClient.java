package com.mark.common.jedis;

import com.mark.common.exception.RedisException;
import redis.clients.jedis.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface JedisClient {
    JedisPool getJedisPool();

    void setJedisPool(JedisPool jedisPool);

    String set(String key, String value);

    Long setnx(String key, String value);

    String setex(String key, Integer seconds, String value);

    Long expire(String key, Integer seconds);

    Long pexpire(String key, Long milliseconds);

    Long expireAt(String key, Long unixTime);

    Long pexpireAt(String key, Long millisecondsTimestamp);

    String getSet(String key, String value);

    String get(String key);

    Long zunionstore(String destKey, String... sets);

    Long zadd(String key, Double score, String member);

    Long zadd(String key, Map<String, Double> scoreMembers);

    Long zcount(String key, Double min, Double max);

    Long zcount(String key, String min, String max);

    Double zscore(String key, String member);

    Double zIncrBy(String key, Double increment, String member);

    Set<String> zRange(String key, long start, long end);

    Set<String> zRangeByScore(String key, Double min, Double max);

    Set<Tuple> zrangeByScoreWithScores(String key, Double min, Double max);

    Set<Tuple> zrangeWithScores(String key, Long start, Long end);

    Set<String> zrevrangeByScore(String key, String max, String min);

    Set<String> zrevrange(String key, Long max, Long min);

    Long zremrangeByScore(String key, Double start, Double end);

    Boolean exists(String key) throws RedisException;

    Long expire(String key, int seconds);

    Long ttl(String key);

    Long incr(String key);

    Long hset(String key, String field, String value);

    String hmset(String key, Map<String, String> hash);

    String hget(String key, String field);

    List<String> hmget(String key, String... fields);

    Map<String, String> hgetAll(String key);

    Long hdel(String key, String... field);

    Long hincrBy(String key, String field, Long value);

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

    Long sadd(String key, String... members);

    Long scard(String key);

    Set<String> smembers(String key);

    Set<String> sdiff(String... keys);

    Long sdiffstore(String destination, String... keys);

    Set<String> sinter(String... keys);

    Long sinterstore(String destination, String... keys);

    Long srem(String key, String... members);

    List<String> srandmember(String key, int count);

    Long lpush(String key, String... strings);

    List<String> lrange(String key, long start, long end);
}
