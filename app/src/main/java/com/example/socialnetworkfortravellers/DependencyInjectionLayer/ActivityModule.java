package com.example.socialnetworkfortravellers.DependencyInjectionLayer;

import android.app.Activity;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    Activity mContext;

    public ActivityModule(Activity context) {
        mContext = context;
    }

    @Provides
    Activity provideActivity() {
        return mContext;
    }

}
