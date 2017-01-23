package com.viethoa.lazadaretailer.loggers;

import android.support.compat.BuildConfig;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by VietHoa on 22/05/16.
 */
public class Logger {

    private static void sendException(String message) {
        //FirebaseCrash.log(message);
    }

    public static void i(String tag, String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        if (BuildConfig.DEBUG) {
            Log.i(tag, message);
        }
    }

    public static void e(String tag, Exception ex) {
        if (ex == null || TextUtils.isEmpty(ex.getMessage())) {
            return;
        }
        if (BuildConfig.DEBUG)
            Log.e(tag, ex.getMessage());
        else {
            sendException(ex.getMessage());
        }
    }

    public static void e(String tag, Throwable throwable) {
        if (throwable == null || TextUtils.isEmpty(throwable.getMessage())) {
            return;
        }
        if (BuildConfig.DEBUG)
            Log.e(tag, throwable.getMessage());
        else {
            sendException(throwable.getMessage());
        }
    }

    public static void e(String tag, String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        if (BuildConfig.DEBUG)
            Log.e(tag, message + " |error|");
        else {
            sendException(message);
        }
    }

    public static void d(String tag, String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        if (BuildConfig.DEBUG) {
            Log.d(tag, message);
        }
    }

    public static void v(String tag, String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        if (BuildConfig.DEBUG) {
            Log.v(tag, message);
        }
    }
}
