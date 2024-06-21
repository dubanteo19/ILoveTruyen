package com.example.ilovetruyen.util;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeDifference {
    public static String getTimeDifference(LocalDateTime start) {
        Duration duration = Duration.between(start, LocalDateTime.now());
        long seconds = duration.getSeconds();

        long days = seconds / (24 * 3600);
        long hours = (seconds % (24 * 3600)) / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;

        if (days > 0) {
            return days + " ngày trước";
        } else if (hours > 0) {
            return hours + " giờ trước";
        } else if (minutes > 0) {
            return minutes + " phút trước";
        } else {
            return secs + "giây trước";
        }
    }
}
