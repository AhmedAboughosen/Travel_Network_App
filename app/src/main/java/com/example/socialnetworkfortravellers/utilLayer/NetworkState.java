package com.example.socialnetworkfortravellers.utilLayer;


import android.content.Context;
import android.net.ConnectivityManager;

/**
 * responsibility of this class is to check if network is active or not if not display Message.
 */
public class NetworkState {

    public static boolean isNetworkConnected(Context context) {
        try {
            return NetworkUtil.isNetworkConnected((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }
}
