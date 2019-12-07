package com.ysxsoft.deliverylocker.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private static Calendar calendar;

    private static final long TIME_DAY = 24* 60* 60* 1000;

    private static final String DAYTIME = "06:00:00";
    private static final String UNDAYTIME = "20:00:00";

    private static Calendar getCalendar() {
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }
        return calendar;
    }

    /**
     * 是否是白天
     *
     * @return true 是白天 false 晚上
     */
    public static boolean isDayTime() {
        long dayTime = getDayTime();
        long unDayTime = getUnDayTime();
        long time = System.currentTimeMillis();
        if (time > dayTime && time < unDayTime) {//白天
            return true;
        }
        return false;
    }

    /**
     * 当天早上的时间
     * @return
     */
    public static long getDayTime() {
        String time = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
        String getlong = time + " " + DAYTIME;
        Log.e("getDayTime", getlong);
        return DateTimeUtil.parseLong(getlong)*1000;
    }
    /**
     * 获取第二天早上的时间
     * @return
     */
    public static long getTomorrowDayTime() {
        String time = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
        String getlong = time + " " + DAYTIME;
        Log.e("getDayTime", getlong);
        return DateTimeUtil.parseLong(getlong)*1000+ TIME_DAY;
    }
    public static long getUnDayTime() {
        String time = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
        String getlong = time + " " + UNDAYTIME;
        Log.e("getUnDayTime", getlong);
        return DateTimeUtil.parseLong(getlong)*1000;
    }

}
