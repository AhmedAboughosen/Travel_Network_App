package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.UserManagementInjectors;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents.DaggerActiveUserComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents.ActiveUserComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.displayListOfUserActivity.DisplayListOfUserActivity;

public class ActiveUserInjector {
    private static ActiveUserInjector mInjector;

    private ActiveUserInjector() {
    }

    public static ActiveUserInjector getSharedInjector() {
        if (mInjector == null) {
            mInjector = new ActiveUserInjector();
        }
        return mInjector;
    }


    public void injectIn(DisplayListOfUserActivity displayListOfUserActivity) {
        ActiveUserComponent activeUserComponent = DaggerActiveUserComponent.builder().contextModule(new ContextModule(displayListOfUserActivity)).build();
        activeUserComponent.inject(displayListOfUserActivity);

    }
}
