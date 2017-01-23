package com.viethoa.lazadaretailer.utilities;

import android.util.Log;

import com.orhanobut.hawk.LogInterceptor;

public class HawkLogger implements LogInterceptor {

    private static final String TAG = "Hawk";

    @Override
    public void onLog(String message) {
        Log.d(TAG, message);
    }
}
