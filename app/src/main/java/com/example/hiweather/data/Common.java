package com.example.hiweather.data;

import android.annotation.SuppressLint;
import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Common {

    public static final String APP_ID = "5ce936c78d0f7badeb527bb0104e1d2d";

    public static Location current_location = null;

    public static String convertUnixToDate(long dt) {
        Date date = Calendar.getInstance().getTime();
//        Date date = new Date(dt*1000L);
        @SuppressLint("SimpleDateFormat")
        java.text.SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd EEE-MM-yyyy");
        return simpleDateFormat.format(date);
    }

    public static String convertUnixToHour(long dt) {
        Date date = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat")
        java.text.SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:");
        return simpleDateFormat.format(date);
    }

//    public static String convertUnixToDate (long dt)
//    {
//        Date date = new Date(dt*1000L);
//        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm EEE MM YYY");
//        String formatted = sdf.format(date);
//        return formatted;
//    }
//
//    public static String convertUnixToHour (long dt)
//    {
//        Date date = new Date(dt*1000L);
//        SimpleDateFormat sdf = new SimpleDateFormat("HH:MM");
//        String formatted = sdf.format(date);
//        return formatted;
//    }
}
