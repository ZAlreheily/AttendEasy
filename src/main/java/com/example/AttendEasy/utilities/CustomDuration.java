package com.example.AttendEasy.utilities;

import lombok.Getter;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;


public class CustomDuration {

    public static String formatSecondsToHHMMSS(long totalSeconds) {
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public static String formatTime(Duration duration) {
        return formatSecondsToHHMMSS(duration.toSeconds());
    }

    public static String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/M/d - HH:mm:ss");
        return dateFormat.format(date);
    }

}
