package com.mark.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    private Utils () {}
    public static String getDigits(String str) {
        String regex = "[^0-9]+";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }
}
