package com.doctor.commons;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import com.doctor.beaver.annotation.ThreadSafe;

/**
 * @author sdcuike
 *         <p>
 *         Created on 2016年10月9日<br/>
 */
@ThreadSafe
public final class TimeUtils {
    static GregorianCalendar   cldr        = new GregorianCalendar();

    /** the milli second of a day */
    public static final long   DAYMILLI    = 24 * 60 * 60 * 1000;

    /** the milli seconds of an hour */
    public static final long   HOURMILLI   = 60 * 60 * 1000;

    /** the milli seconds of a minute */
    public static final long   MINUTEMILLI = 60 * 1000;

    /** the milli seconds of a second */
    public static final long   SECONDMILLI = 1000;

    /** added time */
    public static final String TIMETO      = " 23:59:59";

    /**
     * set the default time zone
     */
    static {
        cldr.setTimeZone(java.util.TimeZone.getTimeZone("GMT+9:00"));
    }

    /** flag before */
    public static final transient int BEFORE               = 1;

    /** flag after */
    public static final transient int AFTER                = 2;

    /** flag equal */
    public static final transient int EQUAL                = 3;

    /** date format dd/MMM/yyyy:HH:mm:ss +0900 */
    public static final String        TIME_PATTERN_LONG    = "dd/MMM/yyyy:HH:mm:ss +0900";

    /** date format dd/MM/yyyy:HH:mm:ss +0900 */
    public static final String        TIME_PATTERN_LONG2   = "dd/MM/yyyy:HH:mm:ss +0900";

    /** date format yyyy-MM-dd HH:mm:ss */
    public static final String        TIME_PATTERN         = "yyyy-MM-dd HH:mm:ss";

    /** date format dd/MM/yy HH:mm:ss */
    public static final String        TIME_PATTERN_SHORT   = "dd/MM/yy HH:mm:ss";

    /** date format dd/MM/yy HH:mm */
    public static final String        TIME_PATTERN_SHORT_1 = "yyyy/MM/dd HH:mm";

    /** date format yyyyMMddHHmmss */
    public static final String        TIME_PATTERN_SESSION = "yyyyMMddHHmmss";

    /** date format yyyyMMdd */
    public static final String        DATE_FMT_0           = "yyyyMMdd";

    /** date format yyyy/MM/dd */
    public static final String        DATE_FMT_1           = "yyyy/MM/dd";

    /** date format yyyy-MM-dd */
    public static final String        DATE_FMT_3           = "yyyy-MM-dd";

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
        return millisecondsTo(durationMills, timeUnit);
    }

    public static long getInterval(Date startDate, Date endDate, TimeUnit timeUnit) {
        long durationMills = endDate.getTime() - startDate.getTime();
        return millisecondsTo(durationMills, timeUnit);
    }

    public static long millisecondsTo(long milliseconds, TimeUnit timeUnit) {
        switch (timeUnit) {
            case MILLISECONDS:
                return TimeUnit.MILLISECONDS.toMillis(milliseconds);
            case NANOSECONDS:
                return TimeUnit.MILLISECONDS.toNanos(milliseconds);
            case MICROSECONDS:
                return TimeUnit.MILLISECONDS.toMicros(milliseconds);
            case SECONDS:
                return TimeUnit.MILLISECONDS.toSeconds(milliseconds);
            case MINUTES:
                return TimeUnit.MILLISECONDS.toMinutes(milliseconds);
            case HOURS:
                return TimeUnit.MILLISECONDS.toHours(milliseconds);
            case DAYS:
                return TimeUnit.MILLISECONDS.toDays(milliseconds);
            default:
                throw new IllegalArgumentException();
        }
    }

    public static Date toDate(String date, String format) throws ParseException {
        if (StringUtils.isBlank(date) || StringUtils.isBlank(format)) {
            throw new IllegalArgumentException("日期或日期格式不能为空");
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(date);
    }

    public static String toString(Date date) {
        return toString(date, TIME_PATTERN);
    }

    public static String toString(Date date, String format) {
        if (StringUtils.isBlank(format) || date == null) {
            throw new IllegalArgumentException();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    /**
     * 此日期当月的最后时间
     * 
     * @param date
     * @return
     */
    public static Date getMonthLastDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1, 23, 59, 59);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }

    public static String getMonthLastDate(Date date, String format) {
        Date lastDate = getMonthLastDate(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(lastDate);
    }

    public static Date getMonthFirstDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1, 0, 0, 0);
        return calendar.getTime();
    }

    public static String getMonthFirstDate(Date date, String format) {
        Date monthFirstDate = getMonthFirstDate(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(monthFirstDate);
    }

    public static boolean isBetween(Date date, Date startDateNotInclude, Date endDateNotInclude) {
        if (endDateNotInclude.before(startDateNotInclude)) {
            throw new IllegalArgumentException("");
        }
        if (date.after(startDateNotInclude) && date.before(endDateNotInclude)) {
            return true;
        }
        return false;
    }

    /**
     * 日期时间部分清零(yyyy-MM-dd 00:00:00:000)
     * 
     * @param date Date
     * @return Date
     */
    public static final Date zerolizedTime(Date fullDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fullDate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
