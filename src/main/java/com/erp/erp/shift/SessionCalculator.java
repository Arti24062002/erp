package com.erp.erp.shift;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SessionCalculator {
    public static String formatDuration(String startTime, String endTime) {
        // Parse start and end times
        LocalTime start = LocalTime.parse(startTime, DateTimeFormatter.ofPattern("HH:mm:ss"));
        LocalTime end = LocalTime.parse(endTime, DateTimeFormatter.ofPattern("HH:mm:ss"));

        // Calculate total seconds for start and end times
        int startSeconds = start.toSecondOfDay();
        int endSeconds = end.toSecondOfDay();

        // If end time is before start time, adjust for next day
        if (endSeconds < startSeconds) {
            endSeconds += 24 * 3600; // Add 24 hours (in seconds) for night shift
        }

        // Calculate total seconds difference
        int totalSeconds = endSeconds - startSeconds;

        // Calculate hours, minutes, seconds
        int hours = totalSeconds / 3600;
        totalSeconds %= 3600;
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;

        // Format and return the duration
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public static List<ShiftSession> sessionCalucaute(String startTime, String endTime, int session, int extraInHours) {

        // Fixed in time and out time
        LocalTime inTime = LocalTime.parse(startTime, DateTimeFormatter.ofPattern("HH:mm:ss"));
        LocalTime outTime = LocalTime.parse(endTime, DateTimeFormatter.ofPattern("HH:mm:ss"));
        LocalTime outTime2 = LocalTime.parse(endTime, DateTimeFormatter.ofPattern("HH:mm:ss"));
        LocalTime sessionDurationLocalTime = LocalTime.parse(formatDuration(startTime, endTime),
                DateTimeFormatter.ofPattern("HH:mm:ss"));

        // Calculate session durations in minutes
        int totalMinutes = sessionDurationLocalTime.toSecondOfDay() / 60; // Calculate total
                                                                          // minutes between inTime
                                                                          // and outTime
        System.out.println("total mInutes " + totalMinutes);
        int noOfSessions = session;
        double sessionDuration = Math.ceil(totalMinutes / (double) (noOfSessions));
        System.out.println(noOfSessions);
        System.out.println("session Duration " + sessionDuration);
        int j = 0;
        int extratTimeinMinute = (extraInHours * 60) / noOfSessions;
        List<ShiftSession> shiftSessions = new ArrayList<>();
        if (noOfSessions == 1) {
            // Calculate and print session times

            LocalTime sessionInTime = inTime;
            LocalTime sessionOutTime = outTime;
            ShiftSession sh1 = new ShiftSession();
            sh1.setName("Session 1");
            sh1.setInTime(sessionInTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            sh1.setOutTime(sessionOutTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            shiftSessions.add(sh1);
            // Output session details
            System.out.println(
                    "Session\t" + sessionInTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
                            + "\t" + sessionOutTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));

        } else {
            // Calculate and print session times
            for (int i = 0; i < noOfSessions; i++) {

                if (i < noOfSessions / 2)
                    j++;
                else
                    j--;
                LocalTime sessionInTime = inTime.plusMinutes((long) (i * sessionDuration));
                LocalTime sessionOutTime = sessionInTime.plusMinutes((long) sessionDuration);

                sessionInTime = sessionInTime.minusMinutes(extratTimeinMinute * i);
                sessionOutTime = sessionOutTime.plusMinutes(j * extratTimeinMinute);

                ShiftSession sh1 = new ShiftSession();
                sh1.setName("Session " + (i + 1));
                sh1.setInTime(sessionInTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                sh1.setOutTime(sessionOutTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                shiftSessions.add(sh1);
                // Output session details
                System.out.println(
                        "Session" + (i + 1) + "\t" + sessionInTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
                                + "\t" + sessionOutTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            }
        }
        shiftSessions.get(shiftSessions.size() - 1)
                .setOutTime(outTime2.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        return shiftSessions;

    }

    public static List<ShiftSession> sessionCalucaute(String startTime, String endTime, int session, int extraInHours,
            Shift shift) {
        // Fixed in time and out time
        LocalTime inTime = LocalTime.parse(startTime, DateTimeFormatter.ofPattern("HH:mm:ss"));
        LocalTime outTime = LocalTime.parse(endTime, DateTimeFormatter.ofPattern("HH:mm:ss"));
        LocalTime outTime2 = LocalTime.parse(endTime, DateTimeFormatter.ofPattern("HH:mm:ss"));
        LocalTime sessionDurationLocalTime = LocalTime.parse(formatDuration(startTime, endTime),
                DateTimeFormatter.ofPattern("HH:mm:ss"));

        // Calculate session durations in minutes
        int totalMinutes = sessionDurationLocalTime.toSecondOfDay() / 60; // Calculate total
                                                                          // minutes between inTime
                                                                          // and outTime
        System.out.println("total mInutes " + totalMinutes);
        int noOfSessions = session;
        double sessionDuration = Math.ceil(totalMinutes / (double) (noOfSessions));
        System.out.println(noOfSessions);
        System.out.println("session Duration " + sessionDuration);
        int j = 0;
        int extratTimeinMinute = (extraInHours * 60) / noOfSessions;
        List<ShiftSession> shiftSessions = new ArrayList<>();
        if (noOfSessions == 1) {
            // Calculate and print session times

            LocalTime sessionInTime = inTime;
            LocalTime sessionOutTime = outTime;
            ShiftSession sh1 = new ShiftSession();
            sh1.setName("Session 1");
            sh1.setInTime(sessionInTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            sh1.setOutTime(sessionOutTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            shiftSessions.add(sh1);
            sh1.setShift(shift);
            // Output session details
            System.out.println(
                    "Session\t" + sessionInTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
                            + "\t" + sessionOutTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));

        } else {
            // Calculate and print session times
            for (int i = 0; i < noOfSessions; i++) {

                if (i < noOfSessions / 2)
                    j++;
                else
                    j--;
                LocalTime sessionInTime = inTime.plusMinutes((long) (i * sessionDuration));
                LocalTime sessionOutTime = sessionInTime.plusMinutes((long) sessionDuration);
                sessionInTime = sessionInTime.minusMinutes(extratTimeinMinute * i);
                sessionOutTime = sessionOutTime.plusMinutes(j * extratTimeinMinute);

                ShiftSession sh1 = new ShiftSession();
                sh1.setName("Session " + (i + 1));
                sh1.setInTime(sessionInTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                sh1.setOutTime(sessionOutTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                shiftSessions.add(sh1);
                sh1.setShift(shift);
                // Output session details
                System.out.println(
                        "Session" + (i + 1) + "\t" + sessionInTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
                                + "\t" + sessionOutTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            }
        }
        shiftSessions.get(shiftSessions.size() - 1)
                .setOutTime(outTime2.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        return shiftSessions;

    }

    public static void main(String[] args) {
        // System.out.println(sessionCalucaute("20:00:00", "05:45:00", 4, 0));
        System.out.println(sessionCalucaute("01:30:00", "09:30:00", 2, 1));

        // String formattedDuration = formatDuration("01:00:00", "05:45:00");
        // System.out.println("Formatted Duration: " + formattedDuration);
    }
}
