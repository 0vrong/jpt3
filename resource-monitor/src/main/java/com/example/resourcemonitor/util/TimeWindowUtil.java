package com.example.resourcemonitor.util;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeWindowUtil {

    private TimeWindowUtil() {
    }

    public static boolean isAllowedTimeNow() {
        LocalDateTime now = LocalDateTime.now();

        DayOfWeek day = now.getDayOfWeek();
        LocalTime time = now.toLocalTime();

        boolean isWeekday =
                day != DayOfWeek.SATURDAY &&
                day != DayOfWeek.SUNDAY;

        boolean isAllowedTime =
                !time.isBefore(LocalTime.of(7, 0)) &&
                !time.isAfter(LocalTime.of(20, 0));

        return isWeekday && isAllowedTime;
    }
}