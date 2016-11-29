package com.doctor.commons;

import com.doctor.beaver.annotation.ThreadSafe;

/**
 * @author sdcuike
 *         <p>
 *         Created on 2016.11.30
 *         <p>
 */
@ThreadSafe
public final class NumberUtils {

    /**
     * 偶数判断
     * 
     * @param number
     * @return
     */
    public static boolean isEvenNumber(int number) {
        return number % 2 == 0;
    }

    /**
     * 奇数判断
     * 
     * @param number
     * @return
     */
    public static boolean isOddNumber(int number) {
        return !isEvenNumber(number);
    }
}
