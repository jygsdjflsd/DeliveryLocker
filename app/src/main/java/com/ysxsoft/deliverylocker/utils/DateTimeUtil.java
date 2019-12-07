package com.ysxsoft.deliverylocker.utils;

import android.annotation.SuppressLint;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class
DateTimeUtil {

    static SimpleDateFormat format;

    /**
     * 日期格式：yyyy-MM-dd HH:mm:ss
     **/
    public static final String DF_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式：yyyy-MM-dd HH:mm
     **/
    public static final String DF_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    /**
     * 日期格式：yyyy-MM-dd
     **/
    public static final String DF_YYYY_MM_DD = "yyyy-MM-dd";
    /**
     * 日期格式：MM-dd
     **/
    public static final String DF_MM_DD = "MM-dd";

    /**
     * 日期格式：yyyy-MM-dd
     **/
    public static final String DF_MM_DD_HH_MM = "MM-dd HH:mm";

    /**
     * 日期格式：HH:mm:ss
     **/
    public static final String DF_HH_MM_SS = "HH:mm:ss";

    /**
     * 日期格式：HH:mm
     **/
    public static final String DF_HH_MM = "HH:mm";

    private static final String[] FORMATS = {DF_YYYY_MM_DD_HH_MM_SS, DF_YYYY_MM_DD_HH_MM, DF_YYYY_MM_DD, DF_MM_DD_HH_MM, DF_HH_MM_SS, DF_HH_MM};

    private final static long minute = 60 * 1000;// 1分钟
    private final static long hour = 60 * minute;// 1小时
    private final static long day = 24 * hour;// 1天
    private final static long week = 7 * day;// 1周
    private final static long month = 31 * day;// 月
    private final static long year = 12 * month;// 年


    public DateTimeUtil() {

    }

    public static int getDayforYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_YEAR);
    }
    public static int getYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    public static int getDayforMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int getDayforMonth(int month) {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int day;
        switch (month) {
            case 1:
                day = 0;
                break;
            case 2:
                day = 31;
                break;
            case 3:
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                    day = 60;
                } else {
                    day = 59;
                }
                break;
            case 4:
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                    day = 91;
                } else {
                    day = 90;
                }
                break;
            case 5:
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                    day = 121;
                } else {
                    day = 120;
                }
                break;
            case 6:
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                    day = 152;
                } else {
                    day = 151;
                }
                break;
            case 7:
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                    day = 182;
                } else {
                    day = 181;
                }
                break;
            case 8:
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                    day = 213;
                } else {
                    day = 212;
                }
                break;
            case 9:
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                    day = 244;
                } else {
                    day = 243;
                }
                break;
            case 10:
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                    day = 274;
                } else {
                    day = 273;
                }
                break;
            case 11:
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                    day = 305;
                } else {
                    day = 304;
                }
                break;
            case 12:
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                    day = 336;
                } else {
                    day = 335;
                }
                break;
            default:
                day = 0;
                break;
        }
        return day;
    }

    /**
     * 根据时间戳获取当前年龄
     *
     * @param time
     * @return
     */
    public static String formatFriendlyBirthday(long time) {
        Date date = currentTimeToDateDrop(time);
        if (date == null) {
            return null;
        }
        long diff = new Date().getTime() - date.getTime();
        long r = 0;
        if (diff > year) {
            r = (diff / year);
            return String.valueOf(r + 1) + "岁";
        }
        return "0岁";
    }

    /**
     * 倒计时
     */
    public static String countDown(long time) {
        long day = time / (3600 * 24);
        long second = time % (3600 * 24);
        long hour = second / 3600;
        second = second % 3600;
        long minute = second / 60;
        second = second % 60;

        return day + "天" + hour + "时" + minute + "分" + second + "秒";
    }


    /**
     * 将日期格式化成友好的字符串：今天，昨天，几月几日
     *
     * @param time
     * @return
     */
    public static String formatDay(long time) {
        long diff = new Date().getTime() - time * 1000;
        long r = 0;
        String timeDay = "";
        if (diff > day) {
            r = (diff / day);
        }
        if (isToday(time * 1000)) {
            timeDay = "今天";
        } else if (r == 1) {
            timeDay = "昨天";
        } else {
            timeDay = formatDateTime(time * 1000, DF_MM_DD);
        }
        return timeDay;
    }

    /**
     * 将日期格式化成友好的字符串：今天，昨天，几月几日
     *
     * @param time
     * @return
     */
    public static SpannableString formatDayForThisApp(long time) {
        long diff = new Date().getTime() - time * 1000;
        long r = 0;
        SpannableString spannableString;
        if (diff > day) {
            r = (diff / day);
        }
        if (isToday(time * 1000)) {
            spannableString = new SpannableString("今天");
            spannableString.setSpan(new AbsoluteSizeSpan(28, true), 0, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        } else if (r == 1 || r == 0) {
            spannableString = new SpannableString("昨天");
            spannableString.setSpan(new AbsoluteSizeSpan(28, true), 0, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        } else {
            Date date = currentTimeToDateDrop(time * 1000);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            String timeDay = "" + (calendar.get(Calendar.DAY_OF_MONTH) >= 10 ? calendar.get(Calendar.DAY_OF_MONTH) : "0" + calendar.get(Calendar.DAY_OF_MONTH)) + (calendar.get(Calendar.MONTH) + 1) + "月";

            spannableString = new SpannableString(timeDay);
            if (timeDay.length() > 2) {
                spannableString.setSpan(new AbsoluteSizeSpan(28, true), 0, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        }
        return spannableString;
    }

    /**
     * 将日期格式化成友好的字符串：几分钟前、几小时前、几天前、几月前、几年前、刚刚
     *
     * @param date
     * @return
     */
    public static String formatFriendly(Date date) {
        if (date == null) {
            return null;
        }
        long diff = new Date().getTime() - date.getTime();
        return formatFriendly(diff);
    }

    public static String formatFriendly(long diff) {
        diff = System.currentTimeMillis() - diff;
        long r = 0;
        if (diff > year) {
            r = (diff / year);
            return r + "年前";
        }
        if (diff > month) {
            r = (diff / month);
            return r + "个月前";
        }
        if (diff > day) {
            r = (diff / day);
            return r + "天前";
        }
        if (diff > hour) {
            r = (diff / hour);
            return r + "个小时前";
        }
        if (diff > minute) {
            r = (diff / minute);
            return r + "分钟前";
        }
        return "刚刚";
    }

    /**
     * 将日期以yyyy-MM-dd HH:mm:ss格式化
     *
     * @param dateL 日期
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String formatDateTime(long dateL) {
        SimpleDateFormat sdf = new SimpleDateFormat(DF_YYYY_MM_DD_HH_MM_SS);
        Date date = new Date(dateL);
        return sdf.format(date);
    }

    /**
     * 将日期以HH:mm:ss格式化
     *
     * @param dateL 日期
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String formatHourTime(long dateL) {
//        SimpleDateFormat sdf = new SimpleDateFormat(DF_HH_MM_SS);
//        Date date = new Date(dateL);
//        return sdf.format(date);
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateL);
        SimpleDateFormat fmat = new SimpleDateFormat("HH:mm:ss");
        String time = fmat.format(calendar.getTime());
        return time;
    }

    /**
     * 将日期以yyyy-MM-dd HH:mm:ss格式化
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String formatChatDateTime(long diff) {
        if (String.valueOf(diff).length() == 10) {
            diff = diff * 1000;
        }
        long surplus = System.currentTimeMillis() - diff;
        if (surplus > year || surplus > month || surplus > week) {
            return formatDateTime(diff, DF_MM_DD);
        }
        if (surplus <week && surplus > day*2){//显示周几
            return formatDate(diff);
        }
        if (surplus > day && surplus < day*2) {
            return "昨天";
        }
        return formatDateTime(diff, DF_HH_MM);
    }
    public static String formatDateTime(long dateL, String formater) {
        if (String.valueOf(dateL).length() == 10)
            dateL = dateL * 1000;
        SimpleDateFormat sdf = new SimpleDateFormat(formater);
        return sdf.format(new Date(dateL));
    }


    /**
     * 将日期以yyyy-MM-dd HH:mm:ss格式化
     *
     * @param date     日期
     * @param formater
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String formatDateTime(Date date, String formater) {
        SimpleDateFormat sdf = new SimpleDateFormat(formater);
        return sdf.format(date);
    }

    /**
     * 将日期字符串转成日期
     *
     * @param strDate 字符串日期
     * @return java.util.date日期类型
     */
    @SuppressLint("SimpleDateFormat")
    public static Date parseDate(String strDate) {
        DateFormat dateFormat = new SimpleDateFormat(DF_YYYY_MM_DD_HH_MM_SS);
        Date returnDate = null;
        try {
            returnDate = dateFormat.parse(strDate);
        } catch (ParseException e) {

        }
        return returnDate;

    }

    /**
     * 将日期字符串转成日期
     *
     * @param strDate 字符串日期
     * @return long 日期类型
     */
    @SuppressLint("SimpleDateFormat")
    public static long parseLong(String strDate) {
        DateFormat dateFormat = new SimpleDateFormat(DF_YYYY_MM_DD_HH_MM);
        long returnTiem = 0L;
        try {
            returnTiem = dateFormat.parse(strDate).getTime() / 1000;
        } catch (ParseException e) {

        }
        return returnTiem;

    }

    /**
     * 将日期字符串转成日期
     *
     * @param strDate 字符串日期
     * @return java.util.date日期类型
     */
    @SuppressLint("SimpleDateFormat")
    public static Date parseDate(String strDate, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date returnDate = null;
        try {
            returnDate = dateFormat.parse(strDate);
        } catch (ParseException e) {

        }
        return returnDate;

    }

    /**
     * 获取系统当前日期
     *
     * @return
     */
    public static Date gainCurrentDate() {
        return new Date();
    }

    /**
     * 判断是否是当天
     *
     * @param current 时间戳类型
     * @return true 当天 false 其他时间
     */
    public static boolean isToday(long current) {
        long zero = System.currentTimeMillis() / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
        long twelve = zero + 24 * 60 * 60 * 1000 - 1;//今天23点59分59秒的毫秒数
        long yesterday = System.currentTimeMillis() - 24 * 60 * 60 * 1000;//昨天的这一时间的毫秒数
//        System.out.println(new Timestamp(current));//当前时间
////        System.out.println(new Timestamp(yesterday));//昨天这一时间点
////        System.out.println(new Timestamp(zero));//今天零点零分零秒
////        System.out.println(new Timestamp(twelve));//今天23点59分59秒
        return current >= zero;
    }

    /**
     * 验证日期是否比当前日期早
     *
     * @param target1 比较时间1
     * @param target2 比较时间2
     * @return true 则代表target1比target2晚或等于target2，否则比target2早
     */
    public static boolean compareDate(Date target1, Date target2) {
        boolean flag = false;
        try {
            String target1DateTime = formatDateTime(target1, DF_YYYY_MM_DD_HH_MM_SS);
            String target2DateTime = formatDateTime(target2, DF_YYYY_MM_DD_HH_MM_SS);
            if (target1DateTime.compareTo(target2DateTime) <= 0) {
                flag = true;
            }
        } catch (Exception e) {
            System.out.println("比较失败，原因：" + e.getMessage());
        }
        return flag;
    }

    /**
     * 对日期进行增加操作
     *
     * @param target 需要进行运算的日期
     * @param hour   小时
     * @return
     */
    public static Date addDateTime(Date target, double hour) {
        if (null == target || hour < 0) {
            return target;
        }

        return new Date(target.getTime() + (long) (hour * 60 * 60 * 1000));
    }

    /**
     * 对日期进行相减操作
     *
     * @param target 需要进行运算的日期
     * @param hour   小时
     * @return
     */
    public static Date subDateTime(Date target, double hour) {
        if (null == target || hour < 0) {
            return target;
        }

        return new Date(target.getTime() - (long) (hour * 60 * 60 * 1000));
    }

    /**
     * 获取系统时间的方法:月/日 时:分:秒
     */
    public static String getFormateDate() {
        Calendar calendar = Calendar.getInstance();
        int month = (calendar.get(Calendar.MONTH) + 1);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        String systemTime = (month < 10 ? "0" + month : month) + "/" + (day < 10 ? "0" + day : day) + "  " + (hour < 10 ? "0" + hour : hour) + ":" + (minute < 10 ? "0" + minute : minute) + ":" + (second < 10 ? "0" + second : second);
        return systemTime;
    }

    /**
     * 获取系统时间的方法:时:分:秒
     */
    public static String getHourAndMinute() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return (hour < 10 ? "0" + hour : hour) + ":" + (minute < 10 ? "0" + minute : minute);
    }

    /**
     * 获取系统时间的方法:时
     */
    public static String getHour() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return ((hour < 10 ? "0" + hour : hour) + "");
    }

    /**
     * 将2017-07-10 00:00:00 换2017-07-10
     *
     * @param strDate
     * @return
     */
    public static String strFormatStr(String strDate) {
        if (strDate.equals("")) {
            return "";
        }
        return dateToStr(strToDate(strDate));
    }

    /**
     * 2015-01-07 15:05:34
     *
     * @param strDate
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static Date strToDateHHMMSS(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 2015-01-07
     *
     * @param strDate
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 2015.01.07
     *
     * @param strDate
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static Date strToDateDorp(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    @SuppressLint("SimpleDateFormat")
    public static String dateToStr(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 传入一个String转化为long
     */
    @SuppressLint("SimpleDateFormat")
    public static Long stringParserLong(String param) throws ParseException {
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.parse(param).getTime();
    }

    /**
     * 传入一个String转化为long
     */
    @SuppressLint("SimpleDateFormat")
    public static Long stringParserLong(String param, String aformat, long defaultValue) {
        format = new SimpleDateFormat(aformat);
        try {
            return format.parse(param).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    /**
     * 传入一个String转化为long
     */
    @SuppressLint("SimpleDateFormat")
    public static Long stringParserLongShort(String param) {
        format = new SimpleDateFormat("yyyy-MM-dd");
        long time = 0;
        try {
            time = format.parse(param).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 当前时间转换为long
     */
    @SuppressLint("SimpleDateFormat")
    public static Long currentDateParserLong() throws ParseException {
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.parse(format.format(Calendar.getInstance().getTime())).getTime();
    }

    /**
     * 当前时间 如: 2013-04-22 10:37:00
     */
    @SuppressLint("SimpleDateFormat")
    public static String getCurrentDate() {
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(Calendar.getInstance().getTime());
    }

    /**
     * 当前时间 如: 10:37
     */
    @SuppressLint("SimpleDateFormat")
    public static String getCurrentDateHHMM() {
        format = new SimpleDateFormat("HH:mm");
        return format.format(Calendar.getInstance().getTime());
    }

    /**
     * 当前时间 如: 10:37
     *
     * @throws ParseException
     */
    @SuppressLint("SimpleDateFormat")
    public static String getCurrentDateHHMMSS() {
        format = new SimpleDateFormat("HH:mm:ss");
        return format.format(Calendar.getInstance().getTime());
    }

    /**
     * 当前时间 如: 20130422
     */
    @SuppressLint("SimpleDateFormat")
    public static String getCurrentDateString() {
        format = new SimpleDateFormat("yyyyMMddHHmm");
        return format.format(Calendar.getInstance().getTime());
    }

    /**
     * 当前时间 如: 2013-04-22
     */
    @SuppressLint("SimpleDateFormat")
    public static String getCurrentTime() {
        format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(Calendar.getInstance().getTime());
    }

    @SuppressLint("SimpleDateFormat")
    public static String getSWAHDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(Calendar.getInstance().getTime());
    }

    @SuppressLint("SimpleDateFormat")
    public static Long stringToLongD(String param) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.parse(param.substring(0, param.length() - 4)).getTime();
    }

    @SuppressLint("SimpleDateFormat")
    public static Long stringToLong(String param) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
        return format.parse(param).getTime();
    }

    /**
     * 获取两个日期之间的间隔天数
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static int getGapCount(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
    }

    /**
     * 日期转换成Java字符串
     *
     * @param date
     * @return str
     */
    @SuppressLint("SimpleDateFormat")
    public static String DateToStr(Date date) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(date);
        return str;
    }

    /**
     * 字符串转换成日期
     *
     * @param str
     * @return date
     */
    @SuppressLint("SimpleDateFormat")
    public static Date StrToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * long转date
     * 时间戳转换成日期
     *
     * @param time
     * @return date
     */
    @SuppressLint("SimpleDateFormat")
    public static Date currentTimeToDateDrop(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d = format.format(time);
        Date date = null;
        try {
            date = format.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 字符串转换成日期
     *
     * @param str
     * @return date
     */
    @SuppressLint("SimpleDateFormat")
    public static Date StrToDateDrop(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * @param time
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static long getLongTime(String time) {
        long ct = 0;
        try {
            format = new SimpleDateFormat("HH:mm:ss");
            ct = format.parse(time).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ct;
    }

    /**
     * 判断两日期是否同一天
     *
     * @param str1
     * @param str2
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static boolean isSameDay(String str1, String str2) {

        Date day1 = null, day2 = null;
        day1 = DateTimeUtil.strToDate(str1);
        day2 = DateTimeUtil.strToDate(str2);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String ds1 = sdf.format(day1);

        String ds2 = sdf.format(day2);

        if (ds1.equals(ds2)) {
            return true;
        } else {
            return false;
        }

    }
    @SuppressLint("SimpleDateFormat")
    public static boolean isSameDay(long str1, long str2) {

        String ds1 = formatDateTime(str1,"yyyy-MM-dd");

        String ds2 = formatDateTime(str2,"yyyy-MM-dd");

        if (ds1.equals(ds2)) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 获取两个日期的时间差
     */
    @SuppressLint("SimpleDateFormat")
    public static int getTimeInterval(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int interval = 0;
        try {
            Date currentTime = new Date();// 获取现在的时间
            Date beginTime = dateFormat.parse(date);
            interval = (int) ((beginTime.getTime() - currentTime.getTime()) / (1000));// 时间差
            // 单位秒
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return interval;
    }

    /**
     * 获取两个日期的时间差 yyyy.MM.dd HH.mm.ss
     */
    @SuppressLint("SimpleDateFormat")
    public static int getInterval(String bDate, String eDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        int interval = 0;
        try {
            Date currentTime = dateFormat.parse(eDate);// 获取现在的时间
            Date beginTime = dateFormat.parse(bDate);
            interval = (int) ((beginTime.getTime() - currentTime.getTime()));// 时间差
            // 单位秒
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return interval;
    }

    @SuppressLint("SimpleDateFormat")
    public static long getTimeFormat(String date, String format) {

        DateFormat df = new SimpleDateFormat(format);
        long diff = 0;
        try {
            Date getdate = df.parse(date);
            diff = getdate.getTime();
        } catch (Exception e) {
        }
        return diff;
    }

    /**
     * 两个时间之差 求出一个long Time
     *
     * @param date
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static long getTime(String date) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long diff = 0;
        try {
            Date currentTime = new Date();// 获取现在的时间
            Date getdate = df.parse(date);
            diff = getdate.getTime() - currentTime.getTime();

        } catch (Exception e) {
        }
        return diff;
    }

    @SuppressLint("SimpleDateFormat")
    public static long getTime(String date, String format) {

        DateFormat df = new SimpleDateFormat(format);
        long diff = 0;
        try {
            Date currentTime = new Date();// 获取现在的时间
            Date getdate = df.parse(date);
            diff = getdate.getTime() - currentTime.getTime();

        } catch (Exception e) {
        }
        return diff;
    }

    public static String getFormat(String date, int time) {
        String format = FORMATS[time];
        DateFormat df = new SimpleDateFormat(format);
        try {
            Date getdate = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return getFormat(date, ++time);
        }
        return format;
    }


    /**
     * 日期转换成Java字符串
     *
     * @param DATE1
     * @param DATE2
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static int compare_date(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() >= dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
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
     * 传入时间 算出星期几
     *
     * @param str  2014年1月3日
     * @param days 1:2014年1月4日 类推
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String formatDate(String str, int days) {

        String dateStr = "";
        try {
            DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
            Date date = df.parse(str);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            Date d = dateFormat.parse(dateFormat.format(date));
            c.setTime(d);
            c.add(Calendar.DAY_OF_MONTH, days);
            switch (c.get(Calendar.DAY_OF_WEEK) - 1) {
                case 0:
                    dateStr = "周日";
                    break;
                case 1:
                    dateStr = "周一";
                    break;
                case 2:
                    dateStr = "周二";
                    break;
                case 3:
                    dateStr = "周三";
                    break;
                case 4:
                    dateStr = "周四";
                    break;
                case 5:
                    dateStr = "周五";
                    break;
                case 6:
                    dateStr = "周六";
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateStr;
    }
    @SuppressLint("SimpleDateFormat")
    public static String formatDate(long str) {

        String dateStr = "";
        try {
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(str);
            switch (c.get(Calendar.DAY_OF_WEEK) - 1) {
                case 0:
                    dateStr = "周日";
                    break;
                case 1:
                    dateStr = "周一";
                    break;
                case 2:
                    dateStr = "周二";
                    break;
                case 3:
                    dateStr = "周三";
                    break;
                case 4:
                    dateStr = "周四";
                    break;
                case 5:
                    dateStr = "周五";
                    break;
                case 6:
                    dateStr = "周六";
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateStr;
    }


    /**
     * @param day
     * @return
     */
    private static String getDayOfWeek(int day) {
        System.out.println(day);
        String dayOfWeek = "周";
        switch (day) {
            case 1:
                dayOfWeek += "日";
                break;
            case 2:
                dayOfWeek += "一";
                break;
            case 3:
                dayOfWeek += "二";
                break;
            case 4:
                dayOfWeek += "三";
                break;
            case 5:
                dayOfWeek += "四";
                break;
            case 6:
                dayOfWeek += "五";
                break;
            case 7:
                dayOfWeek += "六";
                break;
        }
        return dayOfWeek;
    }
}
