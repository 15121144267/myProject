package com.dispatching.feima.utils;

import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class TimeUtil {

    public static final String TIME_HHMMSS = "HH:mm:ss";
    public static final String TIME_HHMM = "HH:mm";
    public static final String TIME_YYMMDD = "yyyy-MM-dd";
    public static final String TIME_MMDD = "MM-dd";
    public static final String TIME_MMDD_CH = "M月d日";
    public static final String TIME_YYMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_YYMMDD_HHMM = "yyyy-MM-dd HH:mm";
    public static final String TIME_MMDD_HHMMSS1 = "MM/dd HH:mm";
    public static final String TIME_YYMMDD_HHMMSS1 = "yyyy/MM/dd HH:mm";
    public static final String TIME_MMDD_HHMMSS = "M月d日 HH时mm分";
    public static final String TIME_YYMMDD_CH = "yyyy年MM月dd日";
    public static final String TIME_YYMM_CH = "yyyy年MM月";
    public static final String TIME_WEEK = "EEEE";
    public static final String TIME_YMMDD = "yy-MM-dd";
    public static final String TIME_YYMMDD_HHMMSS_S = "yyyy-MM-dd HH:mm:ss.S";
    public static final String TIME_YY = "yyyy";
    public static final String TIME_MM = "M";
    public static final String TIME_MM_CH = "MM月";
    public static final String TIME_dd = "d";

    public static String getFullTime(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    /*
     *
     */
    public static String transferLongToDate(String dateFormat, Long millSec) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date= new Date(millSec);
        return sdf.format(date);
    }

    /**
     * 把long类型的时间 转换为format类型的时间
     *
     * @param time   long
     * @param format 2014-12-20 15:33:20
     * @return
     */
    public static String longToTime(long time, String format) {
        return new SimpleDateFormat(format).format(new Date(time));
    }

    /**
     * 把String类型的时间 转换为long
     *
     * @param time   2014-12-20 15:33:20
     * @param format mm:ss
     * @return
     */
    public static long timeToLong(String time, String format) {
        long min = 0;
        try {
            min = new SimpleDateFormat(format).parse(time).getTime();
        } catch (ParseException e) {

            e.printStackTrace();
        }
        return min;
    }

    /**
     * 把String类型的时间 转换为format类型的时间
     *
     * @param time 2014-12-20 15:33
     * @return
     */
    public static String stringFormatToFormat(String time, String fromFormat, String toFormat) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        long time1 = timeToLong(time, fromFormat);

        return longToTime(time1, toFormat);
    }

    /**
     * 把String类型的时间 转换为format类型的时间
     *
     * @param time   2014-12-20 15:33:20
     * @param format mm:ss
     * @return
     */
    public static String stringTimeToFormat(String time, String format) {
        long time1 = timeToLong(time, TIME_YYMMDD_HHMMSS);

        return longToTime(time1, format);
    }

    //将秒数转换为  hh:mm:ss 00:20:30
    public static String secToTime(long timeString) {

        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (timeString <= 0)
            return "00:00:00";
        else {
            minute = (int) (timeString / 60);
            if (minute < 60) {
                second = (int) (timeString % 60);
                timeStr = "00:" + unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = (int) (timeString - hour * 3600 - minute * 60);
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":"
                        + unitFormat(second);
            }
        }
        return timeStr;
    }

    //将秒数转换为  mm:ss 20:30
    public static String secToTime2(long timeString) {

        String timeStr = null;
        int minute = 0;
        int second = 0;
        if (timeString <= 0)
            return "00:00";
        else {
            minute = (int) (timeString / 60);
            second = (int) (timeString % 60);
            timeStr = unitFormat(minute) + ":" + unitFormat(second);
        }
        return timeStr;
    }
    //将秒数转换为  mm:ss 20:30
    public static String secToTime2(String timeString) {
        if (TextUtils.isEmpty(timeString) || "0".equals(timeString)){
            return "00:00";
        }
        long longTime = Long.parseLong(timeString);
        String timeStr = null;
        int minute = 0;
        int second = 0;
        if (longTime <= 0){
            return "00:00";
        }else if(longTime>3599){
            return "59：:59";
        }else {
            minute = (int) (longTime / 60);
            second = (int) (longTime % 60);
            timeStr = unitFormat(minute) + ":" + unitFormat(second);
        }
        return timeStr;
    }
    /**
     * 将秒数转换为 1小时20分的格式
     *
     * @param timeString
     * @return
     */
    public static String secToTime1(long timeString) {

        String timeStr = null;
        int hour = 0;
        int minute = 0;
        if (timeString <= 0) {
            return "数据有误";
        } else if (timeString < 60) {
            return "不到1分钟";
        } else {
            minute = (int) (timeString / 60);

            if (minute < 60) {
                timeStr = minute + "分钟";
            } else {
                hour = minute / 60;
                if (hour > 99) {
                    return "数据有误";
                }
                minute = minute % 60;
                if (minute == 0) {
                    timeStr = hour + "小时";
                } else {
                    timeStr = hour + "小时" + minute + "分钟";
                }

            }
        }
        return timeStr;
    }

    public static Date stringToDate(String dateString) {
        ParsePosition position = new ParsePosition(0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_YYMMDD_HHMMSS);
        Date dateValue = simpleDateFormat.parse(dateString, position);
        return dateValue;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    /**
     * 获取用车时间加上用车时长的时间 return hhmmss
     */
    public static String getUseCarTime(String time, long useTime) {

        long timeStart = timeToLong(time, TIME_YYMMDD_HHMMSS);

        timeStart += (useTime * 1000);
        String s = longToTime(timeStart, TIME_HHMM);

        return s;
    }

    /**
     * 得到 今天 明天 后天
     *
     * @return
     */
    public static String getTimeChinString(String time, String format) {
        long s = timeToLong(stringTimeToFormat(time, TIME_YYMMDD), TIME_YYMMDD);
        long now = timeToLong(getFullTime(TIME_YYMMDD), TIME_YYMMDD);
        if (s - now == 0) {
            return "今天";
        } else if (s - now == 1000 * 60 * 60 * 24) {
            return "明天";
        } else if (s - now == 1000 * 60 * 60 * 24 * 2) {
            return "后天";
        } else {
            return stringTimeToFormat(time, format);
        }
    }

    public static String getNumMonth(long time, int addNum, boolean isHavaYear) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(time * 1000));
        c.add(Calendar.MONTH, addNum);
        int month = c.get(Calendar.MONTH) + 1;
        if (isHavaYear) {
            return c.get(Calendar.YEAR) + "/" + month;
        }
        return (month > 9 ? month : "0" + month) + "月";
    }

    public static Calendar getCalendar(){
        Calendar calendar = Calendar.getInstance();
        Calendar todayCalendar = new GregorianCalendar(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),0,0,0);
        return todayCalendar;
    }

    public static Date formatDate(String time){
        Date date = new Date();
        try {
            String tmp = time.replace("T"," ").split("\\.")[0];
            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = df.parse(tmp);
        }catch (Exception ex){
            Log.e("tag", ex.getMessage());
        }
        return date;
    }

    public static String getTimeNow(Date date){
        if (null == date){
            Calendar calendar = Calendar.getInstance();
            date = calendar.getTime();
        }
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dayStr = df.format(date);
        return dayStr;
    }
}
