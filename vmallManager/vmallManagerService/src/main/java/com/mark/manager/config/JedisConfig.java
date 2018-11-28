package com.mark.manager.config;

import com.mark.common.jedis.JedisClient;
import com.mark.common.jedis.JedisClientPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class JedisConfig {

    @Value("${ip}")
    private String ip;
    @Value("${port}")
    private int port;
    @Value("${password}")
    private String password;
    @Value("${timeout}")
    private int timeout;
    @Value("${database}")
    private int database;

    @Bean
    public JedisPool genJedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
        //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
        jedisPoolConfig.setMaxTotal(50);
        //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
        jedisPoolConfig.setMaxIdle(5);
        //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；单位毫秒
        //小于零:阻塞不确定的时间,  默认-1
        jedisPoolConfig.setMaxWaitMillis(1000);
        //在borrow(引入)一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
        jedisPoolConfig.setTestOnBorrow(true);
        //return 一个jedis实例给pool时，是否检查连接可用性（ping()）
        jedisPoolConfig.setTestOnReturn(true);
        return new JedisPool(jedisPoolConfig, ip, port, timeout, password, database);
    }

    @Bean
    public JedisClient genJedisClient() {
        JedisClient jedisClient = new JedisClientPool();
        jedisClient.setJedisPool(genJedisPool());
        return jedisClient;
    }
//    public static Jedis getJedis() {
//        return jedisPool.getResource();
//    }
//    public static void returnResource(Jedis jedis) {
//        if(jedis != null)
//        {
//            jedis.close();
//        }
//    }
}
