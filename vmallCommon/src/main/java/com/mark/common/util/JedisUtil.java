package com.mark.common.util;

public class JedisUtil {
    private JedisUtil() {}
    public static Double expiredTimeStamp(Integer min, Integer max) {
        // int num = min + (int)(Math.random() * (max-min+1));
        double num = min + (double)(Math.random() * (max - min + 1));
        return (System.currentTimeMillis() / 1000) + num;
    }
    public static Double expiredTimeStamp() {
        return JedisUtil.expiredTimeStamp(60*60*24, 60*60*48);
    }
    public static boolean isExpired(Double timeStamp) {
        // 当前时间戳大于timestamp说明过期了，返回true
        return timeStamp < (System.currentTimeMillis() / 1000);
    }
    public static Long cookieExpireTime() {
        return System.currentTimeMillis() + (long) (3600 * 24 * 30) * (long)1000;
    }

    /**
     * 默认锁定半小时，时间以毫秒记
     * @return
     */
    public static String payLockExpiredTimeStamp() {
        long timeStamp = System.currentTimeMillis();
        return String.valueOf(timeStamp + (long)(1800 * 1000));
    }
}
