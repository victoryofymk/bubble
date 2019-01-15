package com.it.ymk.bubble.core.utils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yanmingkun
 * @version 1.0
 * @since  2016-12-26 11:28.34
 */
public class DateUtil {
    private static final Logger            LOGGER                 = LoggerFactory.getLogger(DateUtil.class);

    /** 日期格式(yyyy-MM-dd) */
    public static final String             YYYY_MM_DD_EN          = "yyyy-MM-dd";

    /** 日期格式(yyyyMMdd) */
    public static final String             YYYYMMDD_EN            = "yyyyMMdd";

    /** 日期格式(yyyy-MM) */
    public static final String             YYYY_MM_EN             = "yyyy-MM";

    /** 日期格式(yyyyMM) */
    public static final String             YYYYMM_EN              = "yyyyMM";

    /** 日期格式(yyyy-MM-dd HH:mm:ss) */
    public static final String             YYYY_MM_DD_HH_MM_SS_EN = "yyyy-MM-dd HH:mm:ss";

    /** 日期格式(yyyyMMddHHmmss) */
    public static final String             YYYYMMDDHHMMSS_EN      = "yyyyMMddHHmmss";

    /** 日期格式(yyyy年MM月dd日) */
    public static final String             YYYY_MM_DD_CN          = "yyyy年MM月dd日";

    /** 日期格式(yyyy年MM月dd日HH时mm分ss秒) */
    public static final String             YYYY_MM_DD_HH_MM_SS_CN = "yyyy年MM月dd日HH时mm分ss秒";

    /** 日期格式(yyyy年MM月dd日HH时mm分) */
    public static final String             YYYY_MM_DD_HH_MM_CN    = "yyyy年MM月dd日HH时mm分";

    /** DateFormat缓存 */
    private static Map<String, DateFormat> dateFormatMap          = new HashMap<String, DateFormat>();

    /**
     * 一天开始时间
     */
    public static final String             TIME_START_STR         = " 00:00:00";

    /**
     * 一天结束时间
     */
    public static final String             TIME_END_STR           = " 23:59:59";

    private DateUtil() {

    }

    /**
     * 获取DateFormat
     *
     * @param formatStr
     * @return
     */
    public static DateFormat getDateFormat(String formatStr) {
        DateFormat df = dateFormatMap.get(formatStr);
        if (df == null) {
            df = new SimpleDateFormat(formatStr);
            dateFormatMap.put(formatStr, df);
        }
        return df;
    }

    /**
     * 按照默认formatStr的格式，转化dateTimeStr为Date类型 dateTimeStr必须是formatStr的形式
     *
     * @param dateTimeStr
     * @param formatStr
     * @return
     */
    public static Date getDate(String dateTimeStr, String formatStr)
        throws Exception {
        if (dateTimeStr == null || "".equals(dateTimeStr)) {
            return null;
        }
        DateFormat sdf = DateUtil.getDateFormat(formatStr);
        return sdf.parse(dateTimeStr);
    }

    /**
     * 转化dateTimeStr为Date类型
     *
     * @param dateTimeStr
     * @return
     */
    public static Date convertDate(String dateTimeStr) throws Exception {
        if (dateTimeStr == null || "".equals(dateTimeStr)) {
            return null;
        }
        DateFormat sdf = DateUtil.getDateFormat(YYYY_MM_DD_EN);
        return sdf.parse(dateTimeStr);
    }

    /**
     * 按照默认显示日期时间的格式"yyyy-MM-dd"，转化dateTimeStr为Date类型
     * dateTimeStr必须是"yyyy-MM-dd"的形式
     *
     * @param dateTimeStr
     * @return
     */
    public static Date getDate(String dateTimeStr) throws Exception {
        return getDate(dateTimeStr, YYYY_MM_DD_EN);
    }

    /**
     * 将Timestamp时间转为指定格式的字符串
     *
     * @param timestamp
     * @param pattern
     * @return
     */
    public static String getTimestampStr(Timestamp timestamp, String pattern) {
        if (null == timestamp) {
            return "";
        }

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp.getTime());
        return new SimpleDateFormat(pattern).format(c.getTime());
    }

    /**
     * 将YYYYMMDD转换成Date日期
     *
     * @param date
     * @return Date
     * @throws Exception
     */
    public static Date transferDate(String date) throws Exception {
        if (StringUtils.isEmpty(date) || date.length() != 8) {
            throw new RuntimeException("日期格式错误");
        }
        String con = "-";
        String yyyy = date.substring(0, 4);
        String mm = date.substring(4, 6);
        String dd = date.substring(6, 8);
        int month = Integer.parseInt(mm);
        int day = Integer.parseInt(dd);
        if (month < 1 || month > 12 || day < 1 || day > 31) {
            throw new RuntimeException("日期格式错误");
        }
        String str = yyyy + con + mm + con + dd;
        return DateUtil.getDate(str, DateUtil.YYYY_MM_DD_EN);
    }

    /**
     * 将Date转换成字符串“yyyy-mm-dd hh:mm:ss”的字符串
     *
     * @param date
     * @return
     */
    public static String dateToDateString(Date date) {
        if (date == null) {
            return "";
        }
        else {
            return dateToDateString(date, YYYY_MM_DD_HH_MM_SS_EN);
        }

    }

    /**
     * 将Date转换成formatStr格式的字符串
     *
     * @param date
     * @param formatStr
     * @return
     */
    public static String dateToDateString(Date date, String formatStr) {
        if (date == null) {
            return "";
        }
        else {
            DateFormat df = getDateFormat(formatStr);
            return df.format(date);
        }

    }

    /**
     * 将String转换成formatStr格式的字符串
     *
     * @param date
     * @param formatStr1
     * @param formatStr2
     * @return
     */
    public static String stringToDateString(String date, String formatStr1,
        String formatStr2) throws Exception {
        Date d = getDate(date, formatStr1);
        DateFormat df = getDateFormat(formatStr2);

        return df.format(d);
    }

    /**
     * 获取当前日期yyyy-MM-dd的形式
     *
     * @return
     */
    public static String getCurDate() {
        return dateToDateString(new Date(), YYYY_MM_DD_EN);
    }

    /**
     * 获取当前日期yyyy-MM-dd的形式
     *
     * @return
     */
    public static String getCurDateByPattern(String pattern) {
        return dateToDateString(new Date(), pattern);
    }

    /***
     * 获取昨天的日期
     *
     * @return
     */
    public static String getYestdayDate() {
        Calendar currentDate = Calendar.getInstance();
        currentDate.add(Calendar.DATE, -1);
        return dateToDateString(currentDate.getTime(), YYYY_MM_DD_EN);
    }

    /**
     * 获取昨天日期时间yyyy-MM-dd HH:mm:ss的形式
     *
     * @return
     */
    public static String getYestDayDateTime() {
        Calendar currentDate = Calendar.getInstance();
        currentDate.add(Calendar.DATE, -1);
        return dateToDateString(currentDate.getTime(), YYYY_MM_DD_HH_MM_SS_EN);
    }

    /***
     * 获取当天的开始时间
     *
     * @return
     */
    public static Date getCurBeginDate() {
        Calendar currentDate = Calendar.getInstance();
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        return currentDate.getTime();
    }

    /***
     * 获取当天的结束时间
     *
     * @return
     */
    public static String getCurEndDate() {
        Calendar currentDate = Calendar.getInstance();
        currentDate.set(Calendar.HOUR_OF_DAY, 23);
        currentDate.set(Calendar.MINUTE, 59);
        currentDate.set(Calendar.SECOND, 59);
        return dateToDateString((Date) currentDate.getTime().clone());
    }

    /***
     * 获取昨天的开始时间
     *
     * @return
     */
    public static String getYestDayBeginDate() {
        Calendar currentDate = Calendar.getInstance();
        currentDate.add(Calendar.DATE, -1);
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        return dateToDateString((Date) currentDate.getTime().clone());
    }

    /***
     * 获取昨天的结束时间
     *
     * @return
     */
    public static String getYestDayEndDate() {
        Calendar currentDate = Calendar.getInstance();
        currentDate.add(Calendar.DATE, -1);
        currentDate.set(Calendar.HOUR_OF_DAY, 23);
        currentDate.set(Calendar.MINUTE, 59);
        currentDate.set(Calendar.SECOND, 59);
        return dateToDateString((Date) currentDate.getTime().clone());
    }

    /***
     * 获取当前月的第一天
     *
     * @return
     */
    public static String getCurMonthFirstDay() {
        Calendar currentDate = Calendar.getInstance();
        currentDate.set(Calendar.DAY_OF_MONTH, 1);
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        return dateToDateString((Date) currentDate.getTime().clone());
    }

    // 获取7天前日期
    public static String getWeekBeforeDay() {
        DateFormat df = new SimpleDateFormat("yyyy-M-d");
        String time = df.format(new Date().getTime() - 7 * 24 * 60 * 60 * 1000);
        return time;
    }

    /***
     * 获取上一个月的第一天
     *
     * @return
     */
    public static String getLastMonthFirstDay() {
        Calendar currentDate = Calendar.getInstance();
        currentDate.add(Calendar.MONTH, -1);
        currentDate.set(Calendar.DAY_OF_MONTH, 1);
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        return dateToDateString((Date) currentDate.getTime().clone());
    }

    /**
     * 获取上个月的最后一天
     *
     * @return
     */
    public static String getLastMonthLastDay() {
        Calendar currentDate = Calendar.getInstance();
        currentDate.set(Calendar.DATE, 0);
        currentDate.set(Calendar.HOUR_OF_DAY, 23);
        currentDate.set(Calendar.MINUTE, 59);
        currentDate.set(Calendar.SECOND, 59);
        return dateToDateString((Date) currentDate.getTime().clone());
    }

    /**
     * 获取当前月的最后一天
     *
     * @return
     */
    public static String getCurMonthLastDay() {
        Calendar currentDate = Calendar.getInstance();
        currentDate.set(Calendar.DATE, 1);
        currentDate.roll(Calendar.DATE, -1);
        currentDate.set(Calendar.HOUR_OF_DAY, 23);
        currentDate.set(Calendar.MINUTE, 59);
        currentDate.set(Calendar.SECOND, 59);
        return dateToDateString((Date) currentDate.getTime().clone());
    }

    /**
     * 获取当前日期yyyy年MM月dd日的形式
     *
     * @return
     */
    public static String getCurCNDate() {
        return dateToDateString(new Date(), YYYY_MM_DD_CN);
    }

    /**
     * 获取当前日期时间yyyy-MM-dd HH:mm:ss的形式
     *
     * @return
     */
    public static String getCurDateTime() {
        return dateToDateString(new Date(), YYYY_MM_DD_HH_MM_SS_EN);
    }

    /**
     * 获取当前日期时间yyyyMMddHHmmss的形式
     *
     * @return
     */
    public static String getCurDateTimes() {
        return dateToDateString(new Date(), YYYYMMDDHHMMSS_EN);
    }

    /**
     * 获取当前日期时间yyyy年MM月dd日HH时mm分ss秒的形式
     *
     * @return
     */
    public static String getCurZhCNDateTime() {
        return dateToDateString(new Date(), YYYY_MM_DD_HH_MM_SS_CN);
    }

    /**
     * 比较两个"yyyy-MM-dd HH:mm:ss"格式的日期，之间相差多少毫秒,time2-time1
     *
     * @param time1
     * @param time2
     * @return
     */
    public static long compareDateStr(String time1, String time2)
        throws Exception {
        Date d1 = getDate(time1);
        Date d2 = getDate(time2);
        return d2.getTime() - d1.getTime();
    }

    /**
     * 将小时数换算成返回以毫秒为单位的时间
     *
     * @param hours
     * @return
     */
    public static long getMicroSec(BigDecimal hours) {
        BigDecimal bd;
        bd = hours.multiply(new BigDecimal(3600 * 1000));
        return bd.longValue();
    }

    /**
     * 将String(yyyy-MM-dd HH:mm:ss)转换成timestamp
     *
     * @param formatStr
     * @return
     */
    public static Timestamp stringToTimestamp(String formatStr) {
        Timestamp ts = new Timestamp(System.currentTimeMillis());

        SimpleDateFormat df1 = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_EN);
        try {
            Date date = df1.parse(formatStr);
            String time = df1.format(date);
            ts = Timestamp.valueOf(time);

        } catch (ParseException e) {
            LOGGER.error("DateUtil.stringToTimestamp异常", e);
        }
        return ts;
    }

    /**
     * 获取当前日期years年后的一个(formatStr)的字符串
     *
     * @param years
     * @param formatStr
     * @return
     */
    public static String getDateStringOfYear(int years, String formatStr) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(new Date());
        now.add(Calendar.YEAR, years);
        return dateToDateString(now.getTime(), formatStr);
    }

    /**
     * 获取当前日期mon月后的一个(formatStr)的字符串
     *
     * @param months
     * @param formatStr
     * @return
     */
    public static String getDateStringOfMon(int months, String formatStr) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(new Date());
        now.add(Calendar.MONTH, months);
        return dateToDateString(now.getTime(), formatStr);
    }

    /**
     * 获取当前日期days天后的一个(formatStr)的字符串
     *
     * @param days
     * @param formatStr
     * @return
     */
    public static String getDateStringOfDay(int days, String formatStr) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(new Date());
        now.add(Calendar.DATE, days);
        return dateToDateString(now.getTime(), formatStr);
    }

    /**
     * 获取当前日期hours小时后的一个(formatStr)的字符串
     *
     * @param hours
     * @param formatStr
     * @return
     */
    public static String getDateStringOfHour(int hours, String formatStr) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(new Date());
        now.add(Calendar.HOUR_OF_DAY, hours);
        return dateToDateString(now.getTime(), formatStr);
    }

    /**
     * 获取指定日期mon月后的一个(formatStr)的字符串
     *
     * @param date
     * @param mon
     * @param formatStr
     * @return
     */
    public static String getDateOfMon(String date, int mon, String formatStr)
        throws Exception {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(DateUtil.getDate(date, formatStr));
        now.add(Calendar.MONTH, mon);
        return dateToDateString(now.getTime(), formatStr);
    }

    /**
     * 获取指定日期day天后的一个(formatStr)的字符串
     *
     * @param date
     * @param day
     * @param formatStr
     * @return
     */
    public static String getDateOfDay(String date, int day, String formatStr)
        throws Exception {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(DateUtil.getDate(date, formatStr));
        now.add(Calendar.DATE, day);
        return dateToDateString(now.getTime(), formatStr);
    }

    /**
     * 获取指定日期mins分钟后的一个(formatStr)的字符串
     *
     * @param date
     * @param mins
     * @param formatStr
     * @return
     */
    public static String getDateOfMin(String date, int mins, String formatStr)
        throws Exception {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(DateUtil.getDate(date, formatStr));
        now.add(Calendar.SECOND, mins * 60);
        return dateToDateString(now.getTime(), formatStr);
    }

    /**
     * 获取指定日期mins分钟后的一个日期
     *
     * @param date
     * @param mins
     * @return
     */
    public static Date getDateOfMin(Date date, int mins) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(date);
        now.add(Calendar.SECOND, mins * 60);
        return now.getTime();
    }

    /**
     * 获取当前日期mins分钟后的一个(formatStr)的字符串
     *
     * @param mins
     * @param formatStr
     * @return
     */
    public static String getDateStringOfMin(int mins, String formatStr) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(new Date());
        now.add(Calendar.MINUTE, mins);
        return dateToDateString(now.getTime(), formatStr);
    }

    /**
     * 获取当前日期mins分钟后的一个日期
     *
     * @param mins
     * @return
     */
    public static Date getDateOfMin(int mins) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(new Date());
        now.add(Calendar.MINUTE, mins);
        return now.getTime();
    }

    /**
     * 获取当前日期sec秒后的一个(formatStr)的字符串
     *
     * @param sec
     * @param formatStr
     * @return
     */
    public static String getDateStringOfSec(int sec, String formatStr) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(new Date());
        now.add(Calendar.SECOND, sec);
        return dateToDateString(now.getTime(), formatStr);
    }

    /**
     * 获得指定日期月份的天数
     *
     * @return
     */
    public static int getMonthDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);

    }

    /**
     * 获得系统当前月份的天数
     *
     * @return
     */
    public static int getCurentMonthDay() {
        Date date = Calendar.getInstance().getTime();
        return getMonthDay(date);
    }

    /**
     * 获得指定日期月份的天数 yyyy-mm-dd
     *
     * @return
     */
    public static int getMonthDay(String date) throws Exception {
        Date strDate = getDate(date, YYYY_MM_DD_EN);
        return getMonthDay(strDate);
    }

    /**
     * 获取19xx,20xx形式的年
     *
     * @param d
     * @return
     */
    public static int getYear(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.YEAR);
    }

    /**
     * 获取月份，1-12月
     *
     * @param d
     * @return
     */
    public static int getMonth(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取xxxx-xx-xx的日
     *
     * @param d
     * @return
     */
    public static int getDay(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取Date中的小时(24小时)
     *
     * @param d
     * @return
     */
    public static int getHour(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取Date中的分钟
     *
     * @param d
     * @return
     */
    public static int getMin(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.MINUTE);
    }

    /**
     * 获取Date中的秒
     *
     * @param d
     * @return
     */
    public static int getSecond(Date d) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.SECOND);
    }

    /**
     * 得到本周周一
     *
     * @return yyyy-MM-dd
     */
    public static String getMondayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }
        c.add(Calendar.DATE, -dayOfWeek + 1);
        return dateToDateString(c.getTime(), YYYY_MM_DD_EN);
    }

    /**
     * 得到本周周日
     *
     * @return yyyy-MM-dd
     */
    public static String getSundayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }
        c.add(Calendar.DATE, -dayOfWeek + 7);
        return dateToDateString(c.getTime());
    }

    /**
     * 得到本周周(*)
     *
     * @return yyyy-MM-dd
     */
    public static String getDayOfThisWeek(int num) {
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }
        c.add(Calendar.DATE, -dayOfWeek + num);
        return dateToDateString(c.getTime(), YYYY_MM_DD_EN);
    }

    /**
     * 得到本月指定天
     *
     * @return yyyy-MM-dd
     */
    public static String getDayOfThisMoon(String num) {
        String date = dateToDateString(new Date(), YYYY_MM_EN);
        return date + "-" + num;
    }

    /***
     * 得到本年本月
     *
     * @return
     */
    public static String getCurYearMonth() {
        String date = dateToDateString(new Date(), YYYY_MM_EN);
        return date;
    }

    /***
     * 得到上年本月
     *
     * @return
     */
    public static String getCurYearLastMonth() {
        Calendar currentDate = Calendar.getInstance();
        currentDate.add(Calendar.MONTH, -1);
        return dateToDateString(currentDate.getTime(), YYYY_MM_EN);
    }

    /**
     * 获取两个日期相差的天数
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static long getQuotByDays(String beginDate, String endDate)
        throws Exception {
        long quot = 0;
        DateFormat df = getDateFormat(YYYY_MM_DD_EN);
        Date d1 = df.parse(beginDate);
        Date d2 = df.parse(endDate);
        quot = d2.getTime() - d1.getTime();
        quot = quot / 1000 / 60 / 60 / 24;
        return quot;
    }

    /**
     * 根据日期追加的天数，得到一个新日期
     *
     * @param date
     * @param days
     * @return
     */
    public static String getDateAddDay(String date, int days) throws Exception {
        DateFormat df = getDateFormat(YYYY_MM_DD_EN);
        Calendar cal = Calendar.getInstance();
        cal.setTime(df.parse(date));
        cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + days);

        return df.format(cal.getTime());
    }

    public static String getDateAddDay(String date, int days, String pattern)
        throws Exception {
        DateFormat df = getDateFormat(pattern);
        Calendar cal = Calendar.getInstance();
        cal.setTime(df.parse(date));
        cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + days);

        return df.format(cal.getTime());
    }

    /**
     * 时间长度描述
     *
     * @param secondDistance
     *            　时间差（秒）
     * @return
     */
    public static String getTimeLengthName(long secondDistance) {
        String str = "";
        long a = 60L * 60 * 24 * 30;
        long monCount = (long) Math.floor(secondDistance / a);
        long b = a / 31;
        long c = secondDistance % a;
        long dayCount = b > 0 ? (long) Math.floor(c / b) : 0;
        long e = b / 24;
        long f = c % b;
        long hourCount = e > 0 ? (long) Math.floor(f / e) : 0;
        long g = e / 60;
        long h = f % e;
        long minCount = g > 0 ? (long) Math.floor(h / g) : 0;
        long i = g / 60;
        long j = h % g;
        long secCount = i > 0 ? (long) Math.floor(j / i) : 0;
        if (monCount > 0) {
            str += monCount + "个月";
        }
        if (dayCount > 0) {
            str += dayCount + "天";
        }
        if (hourCount > 0) {
            str += hourCount + "小时";
        }
        if (minCount > 0) {
            str += minCount + "分钟";
        }
        if (secCount > 0) {
            str += secCount + "秒";
        }
        return str;
    }

    /**
     * 获取自然月的最大天数
     *
     * @param year
     *            年
     * @param month
     *            月
     * @return
     */
    public static int getMaxDay(String year, String month) {
        // 计算某一月份的最大天数

        Calendar time = Calendar.getInstance();
        // 若不clear，很多信息会继承自系统当前时间
        time.clear();
        time.set(Calendar.YEAR, Integer.valueOf(year));
        time.set(Calendar.MONTH, Integer.valueOf(month) - 1);
        int day = time.getActualMaximum(Calendar.DAY_OF_MONTH); // 本月总天数
        return day;
    }

    /**
     * 按指定的格式格式化日期字符串
     *
     * @param strDate
     * @param formatStr 原格式
     * @param formatStrTo 目标格式
     * @return
     */
    public static String formatStrDate(String strDate, String formatStr, String formatStrTo)
        throws Exception {
        DateFormat df = getDateFormat(formatStrTo);
        Date date = getDate(strDate, formatStr);
        return df.format(date);
    }

    public static String timeStamp2String(Timestamp ts) {

        String tsStr = "";
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            // 方法一
            tsStr = sdf.format(ts);
            // 方法二
        } catch (Exception e) {
            LOGGER.error("DateUtil.timeStamp2String异常", e);
        }

        return tsStr;
    }

    /**
     * @param args
     * @throws ParseException
     *             public static void main(String[] args) throws ParseException
     *             { SimpleDateFormat sdf = new
     *             SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); Date d1 =
     *             sdf.parse("2012-09-19 10:10:10"); Date d2 =
     *             sdf.parse("2012-09-18 10:10:00");
     *             System.out.println(daysBetween(d1, d2));
     *             System.out.println(daysBetween("2012-09-08 10:10:10",
     *             "2012-09-15 00:00:00")); }
     */

    /**
     * 计算两个日期之间相差的天数
     *
     * @param sdate
     *            较小的时间
     * @param edate
     *            较大的时间
     * @return 相差天数 正数为天数，0为不相差，负数为开始时间大于结束时间
     * @throws ParseException
     */
    public static int daysBetween(Date sdate, Date edate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date smdate = sdf.parse(sdf.format(sdate));
        Date bdate = sdf.parse(sdf.format(edate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long betweenDays = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(betweenDays));
    }

    /**
     * 字符串的日期格式的计算
     *
     * @return 正数为天数，0为不相差，负数为开始时间大于结束时间
     */
    public static int daysBetween(String smdate, String bdate)
        throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long betweenDays = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(betweenDays));
    }

    public static String getDaysInfo(Date startDate, Date endDate) {

        // milliseconds
        long different = endDate.getTime() - startDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        return elapsedDays + "天" + elapsedHours + "小时" + elapsedMinutes + "分";

    }

    /**
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static String compareToDate(Date startDate, Date endDate) {
        // milliseconds
        long different = endDate.getTime() - startDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        if (elapsedDays > 3) {
            return "";
        }
        else if (elapsedDays == 3 && elapsedHours > 0) {
            return "";
        }
        else if (elapsedDays == 3 && elapsedHours == 0 && elapsedMinutes > 0) {
            return "";
        }
        else {
            return elapsedDays + "天" + elapsedHours + "小时" + elapsedMinutes
                   + "分";
        }
    }

    public static Date addMonth(String dateStr, String pattern, int value)
        throws Exception {
        DateFormat df = getDateFormat(pattern);
        Calendar cal = Calendar.getInstance();
        cal.setTime(df.parse(dateStr));

        cal.add(Calendar.MONTH, value);
        return cal.getTime();
    }

    // 获得本周的周一日期 格式：yyyyMMdd
    public static String getMondayDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String mondayDate = sdf.format(cal.getTime());
        return mondayDate;
    }

    // 获得上周的周一
    public static String getLastWeekMonday() {
        Calendar cal = Calendar.getInstance();
        // n为推迟的周数，1本周，-1向前推迟一周，2下周，依次类推
        int n = -2;
        String monday;
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        monday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        System.out.println(monday);
        return monday;
    }

    /**
     * 获取日期
     * @param startTime   开始时间
     * @param endTime 结束时间
     * @return String[] 周列表
     */
    public static String[] getDays(String startTime, String endTime) {
        ArrayList al = new ArrayList<String>();
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = df1.parse(startTime);
            date2 = df1.parse(endTime);
        } catch (ParseException e) {
        }

        while (!date2.before(date1)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            al.add(df1.format(date1));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            date1 = calendar.getTime();
        }
        String[] weeks = new String[al.size()];
        return (String[]) al.toArray(weeks);
    }

    /**
     * 获取周列表
     * @param startTime   开始时间
     * @param endTime 结束时间
     * @return String[] 周列表
     */
    public static String[] getWeeks(String startTime, String endTime) {
        ArrayList al = new ArrayList<String>();
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMdd");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = df1.parse(startTime);
            date1 = convertWeekDate(date1);
            date2 = df1.parse(endTime);
        } catch (ParseException e) {
        }

        while (date2.after(date1)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 6);
            String week = df2.format(date1) + "-" + df2.format(calendar.getTime());
            al.add(week);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
            date1 = calendar.getTime();
        }
        String[] weeks = new String[al.size()];
        return (String[]) al.toArray(weeks);
    }

    /**
     * 根据时间范围获取月份段
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return String[] 月列表
     */
    public static String[] getMonths(String startTime, String endTime) {
        ArrayList al = new ArrayList<String>();
        String year1 = startTime.split("-")[0];
        String month1 = startTime.split("-")[1];
        String year2 = endTime.split("-")[0];
        String month2 = endTime.split("-")[1];

        while (Integer.parseInt(year2 + month2) >= Integer.parseInt(year1 + month1)) {
            al.add(year1 + "-" + month1);
            if (Integer.parseInt(month1) + 1 < 10) {
                month1 = "0" + (Integer.parseInt(month1) + 1);
            }
            else if (Integer.parseInt(month1) + 1 <= 12) {
                month1 = (Integer.parseInt(month1) + 1) + "";
            }
            else {
                year1 = (Integer.parseInt(year1) + 1) + "";
                month1 = "01";
            }
        }
        String[] months = new String[al.size()];
        return (String[]) al.toArray(months);
    }

    /**
     * 获取周一的时间
     * @param time 时间
     * @return Date 周一的时间
     */
    private static Date convertWeekDate(Date time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //设置时间格式
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        //  System.out.println("要计算日期为:" + sdf.format(cal.getTime()));
        int day = cal.get(Calendar.DAY_OF_WEEK);
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day + 1);
        //System.out.print(sdf.format(cal.getTime()));
        return cal.getTime();
    }

    /**
     * 生成ISO-8601 规范的时间格式
     * @param date
     * @return
     */
    public static String formatISO8601DateString(Date date) {
        String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
        return DateFormatUtils.format(date, pattern);
    }

    /**
     * 获取反时间戳
     * @return
     */
    public static Long getCurrentReverseTime() {
        long longTime = System.currentTimeMillis() * 1000000 + CalculateUtil.getNext(999999);
        return Long.MAX_VALUE - longTime;
    }

    /**
     * 获取原时间戳
     * @param reverseTime
     * @return
     */
    public static Long recoverReverseTime(Long reverseTime) {
        long longTime = Long.MAX_VALUE - reverseTime;
        return longTime / 1000000;
    }

    /**
     * 生成页面普通展示时间
     * @param date
     * @return
     */
    public static String formatNormalDateString(Date date) {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        return DateFormatUtils.format(date, pattern);
    }

    public static void main(String[] args) {
        try {
            System.out.println(formatStrDate("20161111", "yyyyMMdd", "yyyy-MM-dd"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
