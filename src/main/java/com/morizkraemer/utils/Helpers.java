package com.morizkraemer.utils;

public class Helpers {

    public static String formatSecondsToTime(int totalSeconds) {
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;

        if (hours > 0) {
            return String.format("%d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format("%02d:%02d", minutes, seconds);
        }
    }

    public static String formatMsToTime(long ms) {
        long s = ms / 1000;
        return formatSecondsToTime((int) s);
    }

    public static String formatIntToFloatString(int number) {
        float result = number / 1000f;
        return String.format("%,.1f", result);
    }
}
