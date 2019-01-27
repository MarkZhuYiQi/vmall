package com.mark.common.util;

public class LogUtil {
    public static String getObjectName() {
        // 2
        return Thread.currentThread().getStackTrace()[2].getClassName();
    }
    public static String funcName() {
        return new Throwable().getStackTrace()[1].getMethodName();
    }
}
