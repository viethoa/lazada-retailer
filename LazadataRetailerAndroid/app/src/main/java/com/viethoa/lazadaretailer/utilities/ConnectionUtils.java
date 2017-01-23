package com.viethoa.lazadaretailer.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;

import com.viethoa.lazadaretailer.Application;

/**
 * Created by admin on 8/5/16.
 */
public class ConnectionUtils {

    public static final int WIFI_CONNECTION = 1;
    public static final int MOBILE_DATA_CONNECTION = 0;
    public static final int NO_NETWORK = -1;

    public static int getInternetConnection() {
        final ConnectivityManager connMgr = (ConnectivityManager) Application.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifi.isConnectedOrConnecting()) {
            return WIFI_CONNECTION;
        } else if (mobile.isConnectedOrConnecting()) {
            return MOBILE_DATA_CONNECTION;
        } else {
            return NO_NETWORK;
        }
    }

    public static int getWifiSignalStrength() {
        WifiManager wifiManager = (WifiManager)Application.getInstance().getSystemService(Context.WIFI_SERVICE);

        int rssi = wifiManager.getConnectionInfo().getRssi();
        return WifiManager.calculateSignalLevel(rssi, 5);
    }

}
