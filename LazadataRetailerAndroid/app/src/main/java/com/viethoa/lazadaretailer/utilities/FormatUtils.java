package com.viethoa.lazadaretailer.utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by VietHoa on 10/08/2016.
 */

public class FormatUtils {

    private static final String DATE_FORMAT = "dd-MM-yyyy";
    private static final String DATE_TIME_FORMAT = "dd-MM-yyyy hh:mm:ss";

    public static String date(long millisecond) {
        DateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);
        return dateFormatter.format(millisecond);
    }

    public static String dateTime(long millisecond) {
        DateFormat dateFormatter = new SimpleDateFormat(DATE_TIME_FORMAT);
        return dateFormatter.format(millisecond);
    }
}
