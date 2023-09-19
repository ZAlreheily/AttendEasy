package com.example.AttendEasy.utilities;

import com.example.AttendEasy.Times.Times;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Report {


//    String generateMonthlyReport(List<Times> timesList, int month, int year) {
//        final Duration[] totalDuration = {Duration.ZERO};
//        final String[] report = {""};
//        final Times[] tempTime = {null};
//        timesList.forEach(time -> {
//            if (time.getTime().getMonth() == month && time.getTime().getYear() == year) {
//                report[0] += time.toString() + "\n";
//                if (tempTime[0] == null) {
//                    tempTime[0] = time;
//                } else {
//                    Duration duration = getTimeBetweenChecks(tempTime[0], time);
//                    totalDuration[0] = totalDuration[0].plus(duration);
//                    report[0] += "Hours: " + duration.toHours() + "Minutes: " + duration.toMinutes() + "\n\n";
//                    tempTime[0] = null;
//                }
//            }
//        });
//        report[0] += "Total Work Time: " + "Hours: " + totalDuration[0].toHours() + "Minutes " + totalDuration[0].toMinutes();
//        return report[0];
//    }

//    Duration getTimeBetweenChecks(Times time) {
//        Instant startInstant = time.getInTime().toInstant();
//        Instant endInstant = time.getOutTime().toInstant();
//
//        return Duration.between(startInstant, endInstant);
//    }
}
