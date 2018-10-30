package com.example.devrathrathee.legal.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import java.util.Calendar;

public class Utilities {
    public static RequestQueue getRequestQueue(Context context) {
        return VolleySingleton.getInstance(context).getRequestQueue();
    }

    public static void serverError(Context context) {
        Toast.makeText(context, "Server Error.Try again later.", Toast.LENGTH_SHORT).show();
    }

    public static void internetConnectionError(Context context) {
        Toast.makeText(context, "Check Internet Connectivity.", Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static String getTodayDate() {
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;

        return "TODAY (" + day + "-" + getMonth(month) + ")";
    }

    public static String getTomorrowDate() {
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + 1;
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;

        return "TOMORROW (" + day + "-" + getMonth(month) + ")";
    }

    public static String getWeeklyDates() {
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;

        return "WEEKLY (" + getWeekDate(2,day,month) + " TO " + getWeekDate(7, day, month) + ")";
    }

    private static int getDaysInMonth(int month) {

        switch (month) {
            case 1:
                return 31;
            case 2:
                return 28;
            case 3:
                return 31;
            case 4:
                return 30;
            case 5:
                return 31;
            case 6:
                return 30;
            case 7:
                return 31;
            case 8:
                return 31;
            case 9:
                return 30;
            case 10:
                return 31;
            case 11:
                return 30;
            case 12:
                return 31;
        }
        return 0;
    }

    private static String getWeekDate(int dayToCompare, int day, int month) {

        if (day + dayToCompare > getDaysInMonth(month)) {
            return (day + dayToCompare) - getDaysInMonth(month) + "-" + getMonth(month + 1);
        } else {
            return (day + dayToCompare) + "-" + getMonth(month);
        }
    }

    private static String getMonth(int month) {
        switch (month) {
            case 1:
                return "JAN";
            case 2:
                return "FEB";
            case 3:
                return "MAR";
            case 4:
                return "APR";
            case 5:
                return "MAY";
            case 6:
                return "JUNE";
            case 7:
                return "JULY";
            case 8:
                return "AUG";
            case 9:
                return "SEPT";
            case 10:
                return "OCT";
            case 11:
                return "NOV";
            case 12:
                return "DEC";
        }
        return "";
    }
}

