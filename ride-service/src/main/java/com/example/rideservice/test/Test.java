package com.example.rideservice.test;

import java.time.Duration;
import java.time.LocalDateTime;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        LocalDateTime one = getCurrentDateTime();
        Thread.sleep(2000);
        LocalDateTime two = getCurrentDateTime();
        System.out.println(getDuration(one,two).getSeconds());

    }

    private static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }
    public static Duration getDuration(LocalDateTime endTime,LocalDateTime startTime) {
        if (endTime != null) {
            return Duration.between(startTime, endTime);
        } else {
            throw new IllegalStateException("Поездка еще не завершена");
        }
    }
}
