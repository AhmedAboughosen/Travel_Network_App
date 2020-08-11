package com.example.socialnetworkfortravellers.utilLayer;


import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

public class NetworkUtil {


    /**
     *  Check for network connectivity.
     */

    public static boolean isNetworkConnected(ConnectivityManager manager) {

        try {
            ConnectivityManager cm = manager;

            if (cm != null) {
                if (Build.VERSION.SDK_INT < 23) {
                    final NetworkInfo ni = cm.getActiveNetworkInfo();

                    if (ni != null) {
                        return (ni.isConnected() && (ni.getType() == ConnectivityManager.TYPE_WIFI || ni.getType() == ConnectivityManager.TYPE_MOBILE));
                    }
                } else {
                    final Network n = cm.getActiveNetwork();

                    if (n != null) {
                        final NetworkCapabilities nc = cm.getNetworkCapabilities(n);

                        if (nc != null)
                        return (nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)  || nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)|| nc.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)|| nc.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)|| nc.hasTransport(NetworkCapabilities.TRANSPORT_LOWPAN)|| nc.hasTransport(NetworkCapabilities.TRANSPORT_VPN)|| nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI_AWARE));
                    }
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }


        return false;
    }

}
