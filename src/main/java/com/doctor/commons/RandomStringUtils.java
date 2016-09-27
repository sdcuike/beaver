package com.doctor.commons;

import java.util.concurrent.ThreadLocalRandom;

import com.doctor.beaver.annotation.ThreadSafe;

/**
 * @author sdcuike
 *         <p>
 *         Created on 2016年9月27日<br/>
 */
@ThreadSafe
public final class RandomStringUtils {

    /**
     * Refer:{@link http://t1174779123.iteye.com/blog/2037719} <br>
     * 要考虑：随机数的(多线程)安全、不可猜测破解、性能
     */
    public static String random(int count, String str) {
        if (StringUtils.isBlank(str)) {
            return StringConstants.Empty;
        }

        StringBuilder sb = new StringBuilder(count);
        int length = str.length();
        while (count-- > 0) {
            sb.append(str.charAt(ThreadLocalRandom.current().nextInt(length)));
        }
        return sb.toString();
    }

    public static String randomNumeric(int count) {
        return random(count, StringConstants.Digits);
    }

    public static String randomLowerCaseLetters(int count) {
        return random(count, StringConstants.Lower_Case_Letters);
    }

    public static String randomUpperCaseLetters(int count) {
        return random(count, StringConstants.Upper_Case_Letters);
    }

    public static String randomAlpha(int count) {
        return random(count, StringConstants.Alphabets);
    }

    public static String randomAlphaNumeric(int count) {
        return random(count, StringConstants.Alpha_Numeric);
    }

}
