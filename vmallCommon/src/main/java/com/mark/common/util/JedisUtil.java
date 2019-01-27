package com.mark.common.util;

public class JedisUtil {
    public static Double expiredTimeStamp(Integer min, Integer max) {
        // int num = min + (int)(Math.random() * (max-min+1));
        double num = min + (double)(Math.random() * (max - min + 1));
        return (System.currentTimeMillis() / 1000) + num;
    }
    public static Double expiredTimeStamp() {
        return JedisUtil.expiredTimeStamp(3600, 10800);
    }
    public static boolean isExpired(Double timeStamp) {
        // 当前时间戳大于timestamp说明过期了，返回true
        return timeStamp < (System.currentTimeMillis() / 1000);
    }
}
