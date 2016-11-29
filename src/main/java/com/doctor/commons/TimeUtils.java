package com.doctor.commons;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

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

    /**
     * 获取今天剩余时间
     * 
     * @param timeUnit 时间单位
     * @return
     */
    public static long getRemainingTimeToday(TimeUnit timeUnit) {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        long durationMills = todayEnd.getTimeInMillis() - System.currentTimeMillis();
        switch (timeUnit) {
            case MILLISECONDS:
                return TimeUnit.MILLISECONDS.toMillis(durationMills);
            case NANOSECONDS:
                return TimeUnit.MILLISECONDS.toNanos(durationMills);
            case MICROSECONDS:
                return TimeUnit.MILLISECONDS.toMicros(durationMills);
            case SECONDS:
                return TimeUnit.MILLISECONDS.toSeconds(durationMills);
            case MINUTES:
                return TimeUnit.MILLISECONDS.toMinutes(durationMills);
            case HOURS:
                return TimeUnit.MILLISECONDS.toHours(durationMills);
            case DAYS:
                return TimeUnit.MILLISECONDS.toDays(durationMills);
            default:
                throw new IllegalArgumentException();
        }
    }
}
