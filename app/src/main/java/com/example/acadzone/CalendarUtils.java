package com.example.acadzone;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


import static com.example.acadzone.CalendarUtils.daysInMonthArray;
import static com.example.acadzone.CalendarUtils.monthYearFromDate;

import android.os.Build;

public class CalendarUtils{

    public static LocalDate selectedDate;

    public static String formattedDate(LocalDate date) {
        DateTimeFormatter formatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return date.format(formatter);
        } else {
            return ""; // add a default return statement
        }
    }

    public static String formattedTime(LocalTime time) {
        DateTimeFormatter formatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return time.format(formatter);
        } else {
            return ""; // add a default return statement
        }
    }

    public static String monthYearFromDate(LocalDate date) {
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        Date dateObj = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dateObj = java.sql.Date.valueOf(String.valueOf(date));
        }
        return formatter.format(dateObj);
    }

    public static ArrayList<LocalDate> daysInMonthArray(LocalDate date) {
        ArrayList<LocalDate> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            yearMonth = YearMonth.from(date);
        }

        LocalDate firstOfMonth = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            firstOfMonth = selectedDate.withDayOfMonth(1);
        }
        int dayOfWeek = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dayOfWeek = firstOfMonth.getDayOfWeek().getValue();
        }
        int daysInMonth;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            daysInMonth = yearMonth.lengthOfMonth();
        } else {
            Calendar calendar = Calendar.getInstance();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                calendar.setTime(Date.from(yearMonth.atDay(1).atStartOfDay().toInstant(ZoneOffset.UTC)));
            }
            daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        }

        for (int i = 1; i <= 42; i++) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add(null);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    daysInMonthArray.add(LocalDate.of(selectedDate.getYear(), selectedDate.getMonth(), i - dayOfWeek));
                }
            }
        }
        return daysInMonthArray;
    }

    public static ArrayList<LocalDate> daysInWeekArray(LocalDate selectedDate) {
        ArrayList<LocalDate> days = new ArrayList<>();
        LocalDate current = sundayForDate(selectedDate);
        LocalDate endDate = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            endDate = current.plusWeeks(1);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            while (current.compareTo(endDate) < 0) {
                days.add(current);
                current = current.plusDays(1);
            }
        }
        return days;
    }

    private static LocalDate sundayForDate(LocalDate current) {
        LocalDate oneWeekAgo = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            oneWeekAgo = current.minusWeeks(1);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            while (current.isAfter(oneWeekAgo)) {
                if(current.getDayOfWeek() == DayOfWeek.SUNDAY)
                    return current;

                current = current.minusDays(1);
            }
        }

        return null;
    }
}