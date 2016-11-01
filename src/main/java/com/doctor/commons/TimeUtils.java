package com.doctor.commons;

import com.doctor.beaver.annotation.ThreadSafe;

/**
 * @author sdcuike
 *         <p>
 *         Created on 2016年10月9日<br/>
 */
@ThreadSafe
public final class TimeUtils {
    public static final int  Second_In_Day      = 24 * 60 * 60;
    public static final long Millisecond_In_Day = Second_In_Day * 1000L;

    /**
     * 判断年份是否是闰年<br>
     * 四年一闰，百年不闰，四百年又一闰.
     * 
     * @param y
     * @return {@code true} 如果是闰年
     */
    public static boolean isLeapYear(int y) {
        boolean result = false;

        if (((y % 4) == 0) && // 年份能被4整除...
                ((y < 1582) || //  
                        ((y % 100) != 0) ||
                        ((y % 400) == 0))) {
            result = true;
        }
        return result;
    }
}
