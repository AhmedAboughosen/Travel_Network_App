package com.example.socialnetworkfortravellers.utilLayer;

import android.app.Application;
import android.content.Context;

import com.jakewharton.threetenabp.AndroidThreeTen;

public class StartApplication extends Application {


    private Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        AndroidThreeTen.init(this);

    }
}
