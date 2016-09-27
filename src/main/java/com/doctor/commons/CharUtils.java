package com.doctor.commons;

import com.doctor.beaver.annotation.ThreadSafe;

/**
 * @author sdcuike
 *         <p>
 *         Created on 2016年9月27日<br/>
 */
@ThreadSafe
public final class CharUtils {

    /**
     * whitespace的定义来源{@link String#trim()} 的实现判断
     * 
     * @param c
     * @return {@code boolean}
     */
    public static boolean isWhitespace(char c) {
        return c <= ' ';
    }
}
