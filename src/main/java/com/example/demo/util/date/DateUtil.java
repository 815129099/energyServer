package com.example.demo.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {

    public static List<String> getWeekDay(){
        List<String> list = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("M-dd");
        Calendar c = Calendar.getInstance();
        //过去七天
        c.setTime(new Date());
        c.add(Calendar.DATE, - 7);
        Date d = c.getTime();
        String day = format.format(d);
        list.add(day);

        c.add(Calendar.DATE, 1);
        d = c.getTime();
        day = format.format(d);
        list.add(day);

        c.add(Calendar.DATE, 1);
        d = c.getTime();
        day = format.format(d);
        list.add(day);

        c.add(Calendar.DATE, 1);
        d = c.getTime();
        day = format.format(d);
        list.add(day);

        c.add(Calendar.DATE, 1);
        d = c.getTime();
        day = format.format(d);
        list.add(day);

        c.add(Calendar.DATE, 1);
        d = c.getTime();
        day = format.format(d);
        list.add(day);

        c.add(Calendar.DATE, 1);
        d = c.getTime();
        day = format.format(d);
        list.add(day);
        c.add(Calendar.DATE, 1);
        d = c.getTime();
        day = format.format(d);
        list.add(day);
        return list;
    }

    /**
     * 格林威治时间（GMT） 字符串转Date
     * 目前只有这种方法可行
     * @param strDate Thu May 18 2017 00:00:00 GMT+0800 (中国标准时间)
     */
    public static Date parseGMT(String strDate) {
        if (strDate != null && strDate.trim().length() > 0) {
            strDate = strDate.substring(4,24).replace(" ","/");
            strDate = strDate.replace("Jan","01");
            strDate = strDate.replace("Feb","02");
            strDate = strDate.replace("Mar","03");
            strDate = strDate.replace("Apr","04");
            strDate = strDate.replace("May","05");
            strDate = strDate.replace("Jun","06");
            strDate = strDate.replace("Jul","07");
            strDate = strDate.replace("Aug","08");
            strDate = strDate.replace("Sep","09");
            strDate = strDate.replace("Oct","10");
            strDate = strDate.replace("Nov","11");
            strDate = strDate.replace("Dec","12");
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy/HH:mm:ss");
            Date date = null;
            try {
                date = sdf.parse(strDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date;
        }
        return null;
    }

    public static String formatCSTTime(String date, String format) throws ParseException {
        Date d = StringToDateByPattern(date,format);
        return DateUtil.DateToStringByPattern(d, format);
    }

    public static Date GMTStringtoDateByPattern(String Time,String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss z",Locale.ENGLISH);
        Date date = null;
        try {
            date = sdf.parse(Time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Time = DateToStringByPattern(date,pattern);
        System.out.println(Time);
        return StringToDateByPattern(Time,pattern);
    }

    public static String DateToString(Date date){
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss ");
        return ft.format(date);
    }

    public static String DateToStringByPattern(Date date,String pattern){
        SimpleDateFormat ft = new SimpleDateFormat (pattern);
        return ft.format(date);
    }
    public static Date StringToDate(String date) {
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        Date date1 = null;
        try {
            date1 =  ft.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  date1;
    }


    public static Date StringToDateByPattern(String date,String pattern){
        //  SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss ");
        SimpleDateFormat ft = new SimpleDateFormat (pattern);
        Date date1 = null;
        try {
            date1 =  ft.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    //十五分获取一次
    public static byte[] getTmByFifteen(){
        byte[] tm = new byte[9];
        Calendar c = Calendar.getInstance();
        tm[0] = (byte)(c.get(Calendar.YEAR)-2000);
        tm[1] = (byte)(c.get(Calendar.MONTH)+1);
        tm[2] = (byte)(c.get(Calendar.DATE));

        if(c.get(Calendar.HOUR_OF_DAY)<=15){
            tm[3] = (byte)(c.get(Calendar.HOUR_OF_DAY)-1);
            tm[4] = 45;
            tm[5] = 1;
            tm[6] = (byte)(c.get(Calendar.HOUR_OF_DAY));
            tm[7] = 0;
            tm[8] = 0;
        }else if(c.get(Calendar.HOUR_OF_DAY)<=30){
            tm[3] = (byte)(c.get(Calendar.HOUR_OF_DAY));
            tm[4] = 0;
            tm[5] = 1;
            tm[6] = (byte)(c.get(Calendar.HOUR_OF_DAY));
            tm[7] = 15;
            tm[8] = 0;
        }else if(c.get(Calendar.HOUR_OF_DAY)<=45){
            tm[3] = (byte)(c.get(Calendar.HOUR_OF_DAY));
            tm[4] = 15;
            tm[5] = 1;
            tm[6] = (byte)(c.get(Calendar.HOUR_OF_DAY));
            tm[7] = 30;
            tm[8] = 0;
        }else {
            tm[3] = (byte)(c.get(Calendar.HOUR_OF_DAY)-1);
            tm[4] = 30;
            tm[5] = 1;
            tm[6] = (byte)(c.get(Calendar.HOUR_OF_DAY)-1);
            tm[7] = 45;
            tm[8] = 0;
        }
        System.out.println(Arrays.toString(tm));
        return tm;
    }

    //十分钟获取一次
    public static byte[] getTmByTen(){
        byte[] tm = new byte[9];
        Calendar c = Calendar.getInstance();
        tm[0] = (byte)(c.get(Calendar.YEAR)-2000);
        tm[1] = (byte)(c.get(Calendar.MONTH)+1);
        tm[2] = (byte)(c.get(Calendar.DATE));
        if(c.get(Calendar.HOUR_OF_DAY)<=10){
            tm[3] = (byte)(c.get(Calendar.HOUR_OF_DAY)-1);
            tm[4] = 50;
            tm[5] = 1;
            tm[6] = (byte)(c.get(Calendar.HOUR_OF_DAY));
            tm[7] = 0;
            tm[8] = 0;
        }else if(c.get(Calendar.HOUR_OF_DAY)<=20){
            tm[3] = (byte)(c.get(Calendar.HOUR_OF_DAY));
            tm[4] = 0;
            tm[5] = 1;
            tm[6] = (byte)(c.get(Calendar.HOUR_OF_DAY));
            tm[7] = 10;
            tm[8] = 0;
        }else if(c.get(Calendar.HOUR_OF_DAY)<=30){
            tm[3] = (byte)(c.get(Calendar.HOUR_OF_DAY));
            tm[4] = 10;
            tm[5] = 1;
            tm[6] = (byte)(c.get(Calendar.HOUR_OF_DAY));
            tm[7] = 20;
            tm[8] = 0;
        }else if(c.get(Calendar.HOUR_OF_DAY)<=40){
            tm[3] = (byte)(c.get(Calendar.HOUR_OF_DAY));
            tm[4] = 20;
            tm[5] = 1;
            tm[6] = (byte)(c.get(Calendar.HOUR_OF_DAY));
            tm[7] = 30;
            tm[8] = 0;
        }else if(c.get(Calendar.HOUR_OF_DAY)<=50){
            tm[3] = (byte)(c.get(Calendar.HOUR_OF_DAY));
            tm[4] = 30;
            tm[5] = 1;
            tm[6] = (byte)(c.get(Calendar.HOUR_OF_DAY));
            tm[7] = 40;
            tm[8] = 0;
        }else {
            tm[3] = (byte)(c.get(Calendar.HOUR_OF_DAY));
            tm[4] = 40;
            tm[5] = 1;
            tm[6] = (byte)(c.get(Calendar.HOUR_OF_DAY));
            tm[7] = 50;
            tm[8] = 0;
        }
        System.out.println(Arrays.toString(tm));
        return tm;
    }

    //一小时获取一次
    public static byte[] getTmByHour(){
        byte[] tm = new byte[9];
        Calendar c = Calendar.getInstance();
        //时间往前推一小时
        c.add(Calendar.HOUR_OF_DAY, -1);
        tm[0] = (byte)(c.get(Calendar.YEAR)-2000);
        tm[1] = (byte)(c.get(Calendar.MONTH)+1);
        tm[2] = (byte)(c.get(Calendar.DATE));
        tm[3] = (byte)(c.get(Calendar.HOUR_OF_DAY));
        tm[4] = 0;
        tm[5] = 0;
        tm[6] = (byte)(c.get(Calendar.HOUR_OF_DAY));
        tm[7] = 59;
        tm[8] = 59;
        System.out.println(Arrays.toString(tm));
        return tm;
    }

    //获取当前的月-日
    public static String getBeforeTime(){
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat ("M-dd");
        return ft.format(date);
    }

    //获取当前时间前一天 年月日00:00:00
    public static Date getBeforeDay(){
        Calendar c = Calendar.getInstance();
        //时间往前推一小时
        c.add(Calendar.DATE, -1);
        // 获得年份
        int year = c.get(Calendar.YEAR);
        // 获得月份
        int month = c.get(Calendar.MONTH);
        // 获得日期
        int day = c.get(Calendar.DATE);
        System.out.println("year:"+year+",month:"+month+",day:"+day);
        c.set(year,month,day,0,0,0);
        Date date = c.getTime();
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        return StringToDate(ft.format(date));
    }

    //获取当前时间前一天 年-月-日
    public static String getBeforeDay1(){
        Calendar c = Calendar.getInstance();
        //时间往前推一小时
        c.add(Calendar.DATE, -1);
        Date date = c.getTime();
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-M-d");
        return ft.format(date);
    }

    public static String getNow(){
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        return ft.format(new Date());
    }
    public static void main(String[] args) throws ParseException {
        /*
        List<String> list= getWeekDay();
        for(String s:list){
            System.out.println(s);
        }*/
        //System.out.print(StringToDateByPattern("2019-4-4 00:00:00","HH:mm"));
        //getTmByHour();
        //System.out.println(getBeforeTime());
       // System.out.println(DateUtil.StringToDate("2019-07-" + 9 + " 00:00:00"));
       // System.out.println(getBeforeDay());
       // System.out.println(getBeforeDay1());
        System.out.println(getWeekDay());
    }
}
