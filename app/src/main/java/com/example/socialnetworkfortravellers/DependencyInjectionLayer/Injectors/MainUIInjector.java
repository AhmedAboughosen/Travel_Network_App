package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.ActivityModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.DaggerMainUIComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.MainUIComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.mainActivity.MainActivity;

public class MainUIInjector {

    private static MainUIInjector mInjector;
    private MainUIComponent mMainUIComponent;

    private MainUIInjector() {
    }

    public static MainUIInjector getSharedInjector() {
        if (mInjector == null) {
            mInjector = new MainUIInjector();
        }
        return mInjector;
    }


    public void injectIn(MainActivity mainActivity) {
        mMainUIComponent = DaggerMainUIComponent.builder()
                .contextModule(new ContextModule(mainActivity))
                .activityModule(new ActivityModule(mainActivity))
                .build();

        mMainUIComponent.inject(mainActivity);

    }
}
