package com.dydl.socketlib.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

@SuppressLint("SimpleDateFormat")
public class DateFormatUtils {
    public static final String FORMAT_YM = "yyyyMM";
    private static final SimpleDateFormat datetimeFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
            "yyyy-MM-dd");
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat(
            "HH:mm:ss");

    public static Date str2Date(String str) {
        return str2Date(str, null);
    }

    public static final String PATTERN_STANDARD08W = "yyyyMMdd";
    public static final String PATTERN_STANDARD12W = "yyyyMMddHHmm";
    public static final String PATTERN_STANDARD14W = "yyyyMMddHHmmss";
    public static final String PATTERN_STANDARD17W = "yyyyMMddHHmmssSSS";

    public static final String PATTERN_STANDARD10H = "yyyy-MM-dd";
    public static final String PATTERN_STANDARD16H = "yyyy-MM-dd HH:mm";
    public static final String PATTERN_STANDARD19H = "yyyy-MM-dd HH:mm:ss";

    public static final String PATTERN_STANDARD10X = "yyyy/MM/dd";
    public static final String PATTERN_STANDARD16X = "yyyy/MM/dd HH:mm";
    public static final String PATTERN_STANDARD19X = "yyyy/MM/dd HH:mm:ss";

    /**
     * @param date
     * @param pattern
     * @Title: date2String
     * @Description: 日期格式的时间转化成字符串格式的时间
     */
    public static String date2String(Date date, String pattern) {
        if (date == null) {
            throw new IllegalArgumentException("timestamp null illegal");
        }
        pattern = (pattern == null || pattern.equals("")) ? PATTERN_STANDARD19H : pattern;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * @param strDate
     * @param pattern
     * @Title: string2Date
     * @Description: 字符串格式的时间转化成日期格式的时间
     */
    public static Date string2Date(String strDate, String pattern) {
        if (strDate == null || strDate.equals("")) {
            throw new RuntimeException("strDate is null");
        }
        pattern = (pattern == null || pattern.equals("")) ? PATTERN_STANDARD19H : pattern;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return date;
    }

    /**
     * 字符串格式的时间转化成特定日期格式的时间
     *
     * @param time   字符串格式的时间
     * @param oldFmt 原日期格式
     * @param newFmt 要转化的日期格式
     */
    public static String getWantDateStr(String time, String oldFmt, String newFmt) {
        if (StringUtil.isEmpty(time)) {
            return "";
        }
        SimpleDateFormat oldFormat = new SimpleDateFormat(oldFmt);
        SimpleDateFormat newFormat = new SimpleDateFormat(newFmt);
        Date date = null;
        try {
            date = oldFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newFormat.format(date);
    }

    /**
     * @param format 格式 17位(yyyyMMddHHmmssSSS) (14位:yyyyMMddHHmmss) (12位:yyyyMMddHHmm) (8位:yyyyMMdd)
     * @Title: getCurrentTime
     * @Description: 取得当前系统时间
     */
    public static String getCurrentTime(String format) {
        SimpleDateFormat formatDate = new SimpleDateFormat(format);
        String date = formatDate.format(new Date());
        return date;
    }

    /**
     * @param dateStr
     * @param wantFormat
     * @Title: getWantDate
     * @Description: 获取想要的时间格式
     */
    public static String getWantDate(String dateStr, String wantFormat) {
        if (!"".equals(dateStr) && dateStr != null) {
            String pattern = PATTERN_STANDARD14W;
            int len = dateStr.length();
            switch (len) {
                case 8:
                    pattern = PATTERN_STANDARD08W;
                    break;
                case 12:
                    pattern = PATTERN_STANDARD12W;
                    break;
                case 14:
                    pattern = PATTERN_STANDARD14W;
                    break;
                case 17:
                    pattern = PATTERN_STANDARD17W;
                    break;
                case 10:
                    pattern = (dateStr.contains("-")) ? PATTERN_STANDARD10H : PATTERN_STANDARD10X;
                    break;
                case 16:
                    pattern = (dateStr.contains("-")) ? PATTERN_STANDARD16H : PATTERN_STANDARD16X;
                    break;
                case 19:
                    pattern = (dateStr.contains("-")) ? PATTERN_STANDARD19H : PATTERN_STANDARD19X;
                    break;
                default:
                    pattern = PATTERN_STANDARD14W;
                    break;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(wantFormat);
            try {
                SimpleDateFormat sdfStr = new SimpleDateFormat(pattern);
                Date date = sdfStr.parse(dateStr);
                dateStr = sdf.format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dateStr;
    }

    /**
     * @param dateStr
     * @param minute
     * @Title: getAfterTime
     * @Description: 获取该时间的几分钟之后的时间
     */
    public static String getAfterTime(String dateStr, int minute) {
        String returnStr = "";
        try {
            String pattern = PATTERN_STANDARD14W;
            int len = dateStr.length();
            switch (len) {
                case 8:
                    pattern = PATTERN_STANDARD08W;
                    break;
                case 10:
                    pattern = PATTERN_STANDARD10H;
                    break;
                case 12:
                    pattern = PATTERN_STANDARD12W;
                    break;
                case 14:
                    pattern = PATTERN_STANDARD14W;
                    break;
                case 16:
                    pattern = PATTERN_STANDARD16H;
                    break;
                case 17:
                    pattern = PATTERN_STANDARD17W;
                    break;
                case 19:
                    pattern = PATTERN_STANDARD19H;
                    break;
                default:
                    pattern = PATTERN_STANDARD14W;
                    break;
            }
            SimpleDateFormat formatDate = new SimpleDateFormat(pattern);
            Date date = null;
            date = formatDate.parse(dateStr);
            Date afterDate = new Date(date.getTime() + (60000 * minute));
            returnStr = formatDate.format(afterDate);
        } catch (Exception e) {
            returnStr = dateStr;
            e.printStackTrace();
        }
        return returnStr;
    }

    /**
     * @param dateStr
     * @param minute
     * @Title: getBeforeTime
     * @Description: 获取该时间的几分钟之前的时间
     */
    public static String getBeforeTime(String dateStr, int minute) {
        String returnStr = "";
        try {
            String pattern = PATTERN_STANDARD14W;
            int len = dateStr.length();
            switch (len) {
                case 8:
                    pattern = PATTERN_STANDARD08W;
                    break;
                case 10:
                    pattern = PATTERN_STANDARD10H;
                    break;
                case 12:
                    pattern = PATTERN_STANDARD12W;
                    break;
                case 14:
                    pattern = PATTERN_STANDARD14W;
                    break;
                case 16:
                    pattern = PATTERN_STANDARD16H;
                    break;
                case 17:
                    pattern = PATTERN_STANDARD17W;
                    break;
                case 19:
                    pattern = PATTERN_STANDARD19H;
                    break;
                default:
                    pattern = PATTERN_STANDARD14W;
                    break;
            }
            SimpleDateFormat formatDate = new SimpleDateFormat(pattern);
            Date date = null;
            date = formatDate.parse(dateStr);
            Date afterDate = new Date(date.getTime() - (60000 * minute));
            returnStr = formatDate.format(afterDate);
        } catch (Exception e) {
            returnStr = dateStr;
            e.printStackTrace();
        }
        return returnStr;
    }

    /**
     * 字符串转时间
     *
     * @param str
     * @param format
     */
    public static Date str2Date(String str, String format) {
        if (str == null || str.length() == 0) {
            return null;
        }
        if (format == null || format.length() == 0) {
            format = PATTERN_STANDARD19H;
        }
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(str);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;

    }

    public static Calendar str2Calendar(String str) {
        return str2Calendar(str, null);

    }

    public static Calendar str2Calendar(String str, String format) {
        Date date = str2Date(str, format);
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        return c;

    }

    public static String date2Str(Calendar c) {// yyyy-MM-dd HH:mm:ss
        return date2Str(c, null);
    }

    public static String date2Str(Calendar c, String format) {
        if (c == null) {
            return null;
        }
        return date2Str(c.getTime(), format);
    }

    public static String date2Str(Date d) {// yyyy-MM-dd HH:mm:ss
        return date2Str(d, null);
    }

    public static String date2Str(Date d, String format) {// yyyy-MM-dd HH:mm:ss
        if (d == null) {
            return null;
        }
        if (format == null || format.length() == 0) {
            format = PATTERN_STANDARD19H;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String s = sdf.format(d);
        return s;
    }

    public static String getCurDateStr() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        return c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-"
                + c.get(Calendar.DAY_OF_MONTH) + "-"
                + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE)
                + ":" + c.get(Calendar.SECOND);
    }

    /**
     * 获得当前日期的字符串格式
     *
     * @param format
     * @return
     */
    public static String getCurDateStr(String format) {
        Calendar c = Calendar.getInstance();
        return date2Str(c, format);
    }

    /**
     * 格式到秒
     *
     * @param time
     * @return
     */
    public static String getMillon(long time) {
        return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(time);

    }

    /**
     * 格式到天
     *
     * @param time
     * @return
     */
    public static String getDay(long time) {
        return new SimpleDateFormat("yyyy-MM-dd").format(time);

    }

    /**
     * 格式到毫秒
     *
     * @param time
     * @return
     */
    public static String getSMillon(long time) {
        return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS").format(time);

    }

    /**
     * 字符串转换到时间格式
     *
     * @param dateStr   需要转换的字符串
     * @param formatStr 需要格式的目标字符串 举例 yyyy-MM-dd
     * @return Date 返回转换后的时间
     * @throws ParseException 转换异常
     */
    public static Date StringToDate(String dateStr, String formatStr) {
        DateFormat sdf = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 转化时间输入时间与当前时间的间隔
     *
     * @param timestamp
     * @return
     */
    public static String converTime(long timestamp) {
        long currentSeconds = System.currentTimeMillis() / 1000;
        long timeGap = currentSeconds - timestamp;// 与现在时间相差秒数
        String timeStr = null;
        if (timeGap > 24 * 60 * 60) {// 1天以上
            timeStr = timeGap / (24 * 60 * 60) + "天前";
        } else if (timeGap > 60 * 60) {// 1小时-24小时
            timeStr = timeGap / (60 * 60) + "小时前";
        } else if (timeGap > 60) {// 1分钟-59分钟
            timeStr = timeGap / 60 + "分钟前";
        } else {// 1秒钟-59秒钟
            timeStr = "刚刚";
        }
        return timeStr;
    }

    /**
     * 把字符串转化为时间格式
     *
     * @param timestamp
     * @return
     */
    public static String getStandardTime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm");
        Date date = new Date(timestamp * 1000);
        sdf.format(date);
        return sdf.format(date);
    }

    /**
     * 获得当前日期时间 日期时间格式yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String currentDatetime() {
        return datetimeFormat.format(now());
    }

    /**
     * 格式化日期时间 日期时间格式yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String formatDatetime(Date date) {
        return datetimeFormat.format(date);
    }

    /**
     * 获得当前时间 时间格式HH:mm:ss
     *
     * @return
     */
    public static String currentTime() {
        return timeFormat.format(now());
    }

    /**
     * 格式化时间 时间格式HH:mm:ss
     *
     * @return
     */
    public static String formatTime(Date date) {
        return timeFormat.format(date);
    }

    /**
     * 获得当前时间的<code>java.util.Date</code>对象
     *
     * @return
     */
    public static Date now() {
        return new Date();
    }

    public static Calendar calendar() {
        Calendar cal = GregorianCalendar.getInstance(Locale.CHINESE);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        return cal;
    }

    /**
     * 获得当前时间的毫秒数
     * <p/>
     * 详见{@link System#currentTimeMillis()}
     *
     * @return
     */
    public static long millis() {
        return System.currentTimeMillis();
    }

    /**
     * 获得当前Chinese月份
     *
     * @return
     */
    public static int month() {
        return calendar().get(Calendar.MONTH) + 1;
    }

    /**
     * 获得月份中的第几天
     *
     * @return
     */
    public static int dayOfMonth() {
        return calendar().get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 今天是星期的第几天
     *
     * @return
     */
    public static int dayOfWeek() {
        return calendar().get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 今天是年中的第几天
     *
     * @return
     */
    public static int dayOfYear() {
        return calendar().get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 判断原日期是否在目标日期之前
     *
     * @param src
     * @param dst
     * @return
     */
    public static boolean isBefore(Date src, Date dst) {
        return src.before(dst);
    }

    /**
     * 判断原日期是否在目标日期之后
     *
     * @param src
     * @param dst
     * @return
     */
    public static boolean isAfter(Date src, Date dst) {
        return src.after(dst);
    }

    /**
     * 判断两日期是否相同
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isEqual(Date date1, Date date2) {
        return date1.compareTo(date2) == 0;
    }

    /**
     * 判断某个日期是否在某个日期范围
     *
     * @param beginDate 日期范围开始
     * @param endDate   日期范围结束
     * @param src       需要判断的日期
     * @return
     */
    public static boolean between(Date beginDate, Date endDate, Date src) {
        return beginDate.before(src) && endDate.after(src);
    }

    /**
     * 获得当前月的最后一天
     * <p/>
     * HH:mm:ss为0，毫秒为999
     *
     * @return
     */
    public static Date lastDayOfMonth() {
        Calendar cal = calendar();
        cal.set(Calendar.DAY_OF_MONTH, 0); // M月置零
        cal.set(Calendar.HOUR_OF_DAY, 0);// H置零
        cal.set(Calendar.MINUTE, 0);// m置零
        cal.set(Calendar.SECOND, 0);// s置零
        cal.set(Calendar.MILLISECOND, 0);// S置零
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);// 月份+1
        cal.set(Calendar.MILLISECOND, -1);// 毫秒-1
        return cal.getTime();
    }

    /**
     * 获得当前月的第一天
     * <p/>
     * HH:mm:ss SS为零
     *
     * @return
     */
    public static Date firstDayOfMonth() {
        Calendar cal = calendar();
        cal.set(Calendar.DAY_OF_MONTH, 1); // M月置1
        cal.set(Calendar.HOUR_OF_DAY, 0);// H置零
        cal.set(Calendar.MINUTE, 0);// m置零
        cal.set(Calendar.SECOND, 0);// s置零
        cal.set(Calendar.MILLISECOND, 0);// S置零
        return cal.getTime();
    }

    private static Date weekDay(int week) {
        Calendar cal = calendar();
        cal.set(Calendar.DAY_OF_WEEK, week);
        return cal.getTime();
    }

    /**
     * 获得周五日期
     * <p/>
     * 注：日历工厂方法{@link #calendar()}设置类每个星期的第一天为Monday，US等每星期第一天为sunday
     *
     * @return
     */
    public static Date friday() {
        return weekDay(Calendar.FRIDAY);
    }

    /**
     * 获得周六日期
     * <p/>
     * 注：日历工厂方法{@link #calendar()}设置类每个星期的第一天为Monday，US等每星期第一天为sunday
     *
     * @return
     */
    public static Date saturday() {
        return weekDay(Calendar.SATURDAY);
    }

    /**
     * 获得周日日期 注：日历工厂方法{@link #calendar()}设置类每个星期的第一天为Monday，US等每星期第一天为sunday
     *
     * @return
     */
    public static Date sunday() {
        return weekDay(Calendar.SUNDAY);
    }

    /**
     * 将字符串日期时间转换成java.util.Date类型 日期时间格式yyyy-MM-dd HH:mm:ss
     *
     * @param datetime
     * @return
     */
    public static Date parseDatetime(String datetime) throws ParseException {
        return datetimeFormat.parse(datetime);
    }

    /**
     * 将字符串日期转换成java.util.Date类型 日期时间格式yyyy-MM-dd
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String date) throws ParseException {
        return dateFormat.parse(date);
    }

    /**
     * 将字符串日期转换成java.util.Date类型 时间格式 HH:mm:ss
     *
     * @param time
     * @return
     * @throws ParseException
     */
    public static Date parseTime(String time) throws ParseException {
        return timeFormat.parse(time);
    }

    /**
     * 根据自定义pattern将字符串日期转换成java.util.Date类型
     *
     * @param datetime
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Date parseDatetime(String datetime, String pattern)
            throws ParseException {
        SimpleDateFormat format = (SimpleDateFormat) datetimeFormat.clone();
        format.applyPattern(pattern);
        return format.parse(datetime);
    }

    /**
     * 把秒格式化为分钟小时
     *
     * @param second
     * @return
     */
    public static String parseSecond(int second) {
        if (second >= 60 * 60 * 24) {
            return second / (60 * 60 * 24) + "天";
        } else if (second >= 60 * 60) {
            return second / (60 * 60) + "时";
        } else if (second >= 60) {
            return second / 60 + "分";
        } else {
            return second + "秒";
        }
    }

    /**
     * 将秒数转换为日时分秒，
     *
     * @param secondStr
     * @return
     */
    public static String secondToTime(String secondStr) {
        long second = 0l;
        if (!StringUtil.isEmpty(secondStr)){
            second = Integer.parseInt(secondStr);
        }
        long days = second / 86400;            //转换天数
        second = second % 86400;            //剩余秒数
        long hours = second / 3600;            //转换小时
        second = second % 3600;                //剩余秒数
        long minutes = second / 60;            //转换分钟
        second = second % 60;                //剩余秒数
        if (days > 0) {
            return days + "天" + hours + "小时" + minutes + "分" + second + "秒";
        } else if (hours > 0) {
            return hours + "小时" + minutes + "分" + second + "秒";
        } else if (minutes > 0) {
            return minutes + "分" + second + "秒";
        } else {
            return second + "秒";
        }
    }

    /**
     * 将日期转换为日时分秒
     *
     * @param date
     * @return
     */
    public static String dateToTime(String date, String dateStyle) {
        SimpleDateFormat format = new SimpleDateFormat(dateStyle);
        try {
            Date oldDate = format.parse(date);
            long time = oldDate.getTime();                    //输入日期转换为毫秒数
            long nowTime = System.currentTimeMillis();        //当前时间毫秒数
            long second = nowTime - time;                    //二者相差多少毫秒
            second = second / 1000;                            //毫秒转换为妙
            long days = second / 86400;
            second = second % 86400;
            long hours = second / 3600;
            second = second % 3600;
            long minutes = second / 60;
            second = second % 60;
            if (days > 0) {
                return days + "天" + hours + "小时" + minutes + "分" + second + "秒";
            } else if (hours > 0) {
                return hours + "小时" + minutes + "分" + second + "秒";
            } else if (minutes > 0) {
                return minutes + "分" + second + "秒";
            } else {
                return second + "秒";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 比较时间大小
     *
     * @param begin
     * @param end
     * @return
     */
    public static int compareDate(String begin, String end) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            Date beginDate = df.parse(begin);
            Date endDate = df.parse(end);
            if (beginDate.getTime() < endDate.getTime()) {
                return 1;
            } else if (beginDate.getTime() > endDate.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 获得年份
     *
     * @param date
     * @return
     */
    public int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    /**
     * 获得月份
     *
     * @param date
     * @return
     */
    public int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * 获得星期几
     *
     * @param date
     * @return
     */
    public int getWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获得日期
     *
     * @param date
     * @return
     */
    public int getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DATE);
    }

    /**
     * 获得天数差
     *
     * @param begin
     * @param end
     * @return
     */
    public long getDayDiff(Date begin, Date end) {
        long day = 1;
        if (end.getTime() < begin.getTime()) {
            day = -1;
        } else if (end.getTime() == begin.getTime()) {
            day = 1;
        } else {
            day += (end.getTime() - begin.getTime()) / (24 * 60 * 60 * 1000);
        }
        return day;
    }

    /**
     * "yyyyMMddHHmmss"
     */
    public static String getDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date dateCurrent = new Date();

        String strCurrentDate = formatter.format(dateCurrent);

        return strCurrentDate;
    }

    /**
     * 获取yyyyMMdd格式的日期
     *
     * @return
     */
    public static String getDateYYMMDD() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd");
        Date dateCurrent = new Date();

        String strCurrentDate = formatter.format(dateCurrent);

        return strCurrentDate;

    }

    /**
     * 获取yyyyMMdd格式的日期
     *
     * @return
     */
    public static String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date dateCurrent = new Date();

        String strCurrentDate = formatter.format(dateCurrent);

        return strCurrentDate;

    }

    /**
     * 获取HHmmss格式的时间
     *
     * @return
     */
    public static String getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("HHmmss");
        Date dateCurrent = new Date();

        String strCurrentTime = formatter.format(dateCurrent);

        return strCurrentTime;
    }

    /**
     * 将yyyyMMddHHmmss格式的日期时间改成yyyy-MM-dd HH:mm:ss
     *
     * @param dateStr
     * @return
     */
    public static String addBarAndColonToDateString(String dateStr) {
        String retDateStr = "";

        if (dateStr == null || dateStr.length() != 14) {
            return dateStr;
        }

        retDateStr = dateStr.substring(0, 4) + "-" + dateStr.substring(4, 6)
                + "-" + dateStr.substring(6, 8) + " "
                + dateStr.substring(8, 10) + ":" + dateStr.substring(10, 12)
                + ":" + dateStr.substring(12);

        return retDateStr;
    }

    public static final String FORMATTER_HOUR_MIN_SECONDS = "%02d:%02d:%02d";

    /**
     * 将秒格式化为易读时间 最大到小时
     *
     * @param seconds
     * @return
     */
    public static String getTimeBySeconds(int seconds) {
        String time = "";
        if (seconds < 60) {
            time = String.format(FORMATTER_HOUR_MIN_SECONDS, 0, 0, seconds);
        } else if (seconds >= 60 && seconds < 3600) {
            int min = seconds / 60;
            int second = seconds % 60;
            time = String.format(FORMATTER_HOUR_MIN_SECONDS, 0, min, second);
        } else {
            int hour = seconds / 3600;
            int last = seconds % 3600;
            int min = last / 60;
            int second = last % 60;
            time = String.format(FORMATTER_HOUR_MIN_SECONDS, hour, min, second);
        }
        return time;
    }

    /**
     * 计算星期几(基姆拉尔森公式)
     *
     * @param y 年
     * @param m 月
     * @param d 日
     * @return
     */
    public static int calcWeekByDate(int y, int m, int d) {
        if (m == 1) {
            m = 13;
            y--;
        }
        if (m == 2) {
            m = 14;
            y--;
        }
        int week = (d + 2 * m + 3 * (m + 1) / 5 + y + y / 4 - y / 100 + y / 400) % 7;

        return week;
    }

    /**
     * 获取系统日期时间
     *
     * @return 系统时间：年-月-日 时：分：秒
     */
    public static String getDateTimeNormal() {
        long time = System.currentTimeMillis();//long now = android.os.SystemClock.uptimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = new Date(time);
        String t1 = format.format(d1);
        Log.e("msg", t1);
        return t1;
    }

    public static String leftPad(String str, int len, char ch) {
        StringBuffer nstr = new StringBuffer(len);

        int p = len - str.length();

        for (int i = 0; i < len; i++) {
            if (i < p)
                nstr.append(ch);
            else
                nstr.append(str.charAt(i - p));
        }
        return new String(nstr);
    }

    /**
     * 判断字符窜是否全是数字组合
     *
     * @return true, 是 false,否
     */
    static public boolean isNumber(String s) {
        StringBuffer sb = new StringBuffer(s);
        for (int i = 0; i < s.length(); i++) {
            char c = sb.charAt(i);
            if (c < '0' || c > '9')
                return false;
        }
        return true;
    }

    /**
     * 取得oldDate的相隔sizeOfParamValue的日期，可以带符号
     *
     * @param oldDate          初始日期
     * @param sizeOfParamValue 时间间隔（天）
     * @return String
     */
    public static String getNextDate(String oldDate, int sizeOfParamValue) {
        try {
            if (oldDate.length() != 8 || !isNumber(oldDate)) {
                System.out.println("oldDate 必须是YYYYMMDD格式");
                return null;
            }
            int year = new Integer(oldDate.substring(0, 4)).intValue();
            int month = new Integer(oldDate.substring(4, 6)).intValue() - 1;
            int date = new Integer(oldDate.substring(6, 8)).intValue();
            // System.out.println("year[" + year + "]month[" + month + "]date["
            // + date + "]ParamValue[" + ParamValue + "]");
            Calendar calender = Calendar.getInstance();
            calender.set(year, month, date);
            calender.add(Calendar.DATE, sizeOfParamValue);
            year = calender.get(Calendar.YEAR);
            month = calender.get(Calendar.MONTH) + 1;
            date = calender.get(Calendar.DATE);
            // System.out.println("year[" + year + "]month[" + month + "]date["
            // + date + "]ParamValue[" + ParamValue + "]");
            String NextDate = leftPad("" + year, 4, '0')
                    + leftPad("" + month, 2, '0') + leftPad("" + date, 2, '0');
            return NextDate;
        } catch (Exception e) {
            System.out.println("Exception in getNextDate[" + e.getMessage()
                    + "]");
            return null;
        }
    }

    /**
     * 将yyyyMMdd格式的日期改变为yyyy-MM-dd的格式返回
     */
    public static String addBarToDateString(String dateStr) {
        String retDateStr = "";

        if (dateStr == null || dateStr.trim().length() != 8) {
            return dateStr;
        }

        retDateStr = dateStr.substring(0, 4) + "-" + dateStr.substring(4, 6)
                + "-" + dateStr.substring(6);

        return retDateStr;

    }

    /**
     * 将HHmmss格式的时间改变成HH：mm：ss的格式返回
     */
    public static String addColonToTimeString(String timeStr) {
        String retDateStr = "";

        if (timeStr == null || timeStr.trim().length() != 6) {
            return timeStr;
        }

        retDateStr = timeStr.substring(0, 2) + ":" + timeStr.substring(2, 4)
                + ":" + timeStr.substring(4);
        return retDateStr;

    }

    /**
     * 将HHmmss格式的时间改变成HH时mm分ss秒的格式返回
     */
    public static String addChineseToTimeString(String timeStr) {
        String retDateStr = "";

        if (timeStr == null || timeStr.length() != 6) {
            return timeStr;
        }

        retDateStr = timeStr.substring(0, 2) + "时" + timeStr.substring(2, 4)
                + "分" + timeStr.substring(4) + "秒";
        return retDateStr;
    }

    /**
     * 获取间隔的年月
     *
     * @param type  Calendar.MONTH,Calendar.DAY,Calendar.YEAR
     * @param space 正数当前月份以后，负数当前月份以前
     */
    public static String getSpaceDate(String dateFormat, int type, int space) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(type, -space);
        Date m = c.getTime();
        String mon = format.format(m);
        return mon;
    }
}