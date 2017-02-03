package com.viethoa.lazadaretailer.utilities;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Build;

import com.viethoa.lazadaretailer.R;

import java.util.Locale;

/**
 * Created by datnguyen on 6/10/16.
 */

public class ResourceUtils {
    @SuppressWarnings("deprecation")
    public static Locale getSystemLocaleLegacy(Configuration config) {
        return config.locale;
    }

    @TargetApi(Build.VERSION_CODES.N)
    public static Locale getSystemLocale(Configuration config) {
        return config.getLocales().get(0);
    }

    @SuppressWarnings("deprecation")
    public static void setSystemLocaleLegacy(Configuration config, Locale locale) {
        config.locale = locale;
    }

    @TargetApi(Build.VERSION_CODES.N)
    public static void setSystemLocale(Configuration config, Locale locale) {
        config.setLocale(locale);
    }

    public static void setLanguage(Context context, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            setSystemLocale(config, locale);
        } else {
            setSystemLocaleLegacy(config, locale);
        }

        context.getApplicationContext().getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());
    }

    public static Drawable getDrawableResource(Context context, int resId) {
        if (Build.VERSION.SDK_INT < 21) {
            return context.getResources().getDrawable(resId);
        }
        return context.getDrawable(resId);
    }

    public static int getColorResource(Context context, int resId) {
        if (Build.VERSION.SDK_INT < 23) {
            return context.getResources().getColor(resId);
        }
        return context.getColor(resId);
    }

    public static String getStringResourceByName(Context context, String aString) throws NoSuchFieldException, IllegalAccessException, NullPointerException {
        //String packageName = context.getPackageName();
        //int resId = context.getResources().getIdentifier(aString, "string", packageName);
        int stringID = R.string.class.getField(aString).getInt(null);
        return context.getString(stringID);
    }

    public static float getDimenResource(Context context, int resId) {
        return context.getResources().getDimension(resId);
    }
}
