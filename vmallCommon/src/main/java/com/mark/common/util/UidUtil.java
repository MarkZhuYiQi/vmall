package com.mark.common.util;

import com.mark.common.jedis.JedisClient;

public class UidUtil {
    public static Long getUid(JedisClient jedisClient) {
        return jedisClient.incr("uid");
    }
}
