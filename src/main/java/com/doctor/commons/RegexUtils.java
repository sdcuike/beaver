package com.doctor.commons;

import java.util.regex.Pattern;

/**
 * 常见正则表达式工具类
 * 
 * @author sdcuike
 *         <p>
 *         Created on 2016.11.22<br/>
 */
public final class RegexUtils {

    private static Pattern Pattern_Digit_6_Same = Pattern.compile("^(\\d)\\1{5}$");
    private static Pattern Pattern_Email        = Pattern.compile("^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$");
    private static Pattern Pattern_IPv4         = Pattern.compile("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");

    /**
     * 六个相同数字的字符串
     * 
     * @param input
     * @return true :六个相同数字的字符串
     */
    public static boolean isSameDigit6(String input) {
        return Pattern_Digit_6_Same.matcher(input).find();
    }

    /**
     * 邮箱格式校验
     * 
     * @param email
     * @return
     */
    public static boolean isValidEmail(String email) {
        return Pattern_Email.matcher(email).find();
    }

    public static boolean isValidIPv4(String ipV4) {
        return Pattern_IPv4.matcher(ipV4).find();
    }
}
