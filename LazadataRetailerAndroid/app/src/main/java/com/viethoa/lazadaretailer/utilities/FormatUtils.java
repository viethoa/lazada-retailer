package com.viethoa.lazadaretailer.utilities;

import java.text.DecimalFormat;

/**
 * Created by VietHoa on 10/08/2016.
 */

public class FormatUtils {

    private static final String CURRENCY_FORMATTER = "%s IDR";
    private static final String KM_DISTANCE_FORMATTER = "%s km distance";
    private static final String KM_TRAVELLED_FORMATTER = "%s km travelled";
    private static final String TOTAL_TRIP_COUNTER = "%s total trip";
    private static final String TOTAL_TRIPS_COUNTER = "%s total trips";
    private static final String TRIP_COUNTER_FORMATTER = "%s successful, %s unsuccessful trips";
    private static final String KM_FORMATTER = "%s KM";

    public static String formatCurrency(double number) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
        return String.format(CURRENCY_FORMATTER, decimalFormat.format(number));
    }

    public static String formatKmDistance(float distance) {
        return String.format(KM_DISTANCE_FORMATTER, distance);
    }

    public static String tripCounter(int successful, int unsuccessful) {
        return String.format(TRIP_COUNTER_FORMATTER, successful, unsuccessful);
    }

    public static String totalTripCounter(int total) {
        if (total == 1 || total == 0) {
            return String.format(TOTAL_TRIP_COUNTER, total);
        } else {
            return String.format(TOTAL_TRIPS_COUNTER, total);
        }
    }

    public static String travelDistanceKm(float kmTravelled) {
        return String.format(KM_TRAVELLED_FORMATTER, kmTravelled);
    }

    public static String formatKm(float km) {
        return String.format(KM_FORMATTER, km);
    }
}
