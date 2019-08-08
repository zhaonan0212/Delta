package com.delta;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DateUtils {
    public static SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy/MM/dd");
    public static SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("yyyy年MM月dd");

    /**
     * 日期轉化字符串的方法
     * @param date
     * @param simpleDateFormat
     * @return
     */
     public static String dateToString(Date date,SimpleDateFormat simpleDateFormat){
        return  simpleDateFormat1.format(date);

     }

    public static void main(String[] args) {
        System.out.println(dateToString(new Date(), simpleDateFormat1));
    }
}
