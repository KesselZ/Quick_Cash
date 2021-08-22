package com.example.project1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateProcessor {
    /**
     *
     * @param d Date to be processed
     * @return return a String in "yyyy-MM-dd HH:mm:ss zzz" format
     */


    public static String dateToString(Date d){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zzz");
        return simpleDateFormat.format(d);
    }

    /**
     *
     * @param s String to be processed "yyyy-MM-dd HH:mm:ss zzz"
     * @return return a Date according to input String
     */

    public static Date stringToDate(String s) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zzz");
        return simpleDateFormat.parse(s);
    }

}
