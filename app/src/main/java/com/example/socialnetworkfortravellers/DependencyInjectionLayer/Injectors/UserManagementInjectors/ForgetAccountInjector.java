package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.UserManagementInjectors;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents.DaggerForgetAccountComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents.ForgetAccountComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.forgetAccountActivity.ForgetAccountActivity;

public class ForgetAccountInjector {


    private static ForgetAccountInjector mInjector;

    private ForgetAccountInjector() {
    }


    public static ForgetAccountInjector getSharedInjector() {
        if (mInjector == null) {
            mInjector = new ForgetAccountInjector();
        }
        return mInjector;
    }


    public void injectIn(ForgetAccountActivity forgetAccountActivity) {
        ForgetAccountComponent mForgetAccountModule = DaggerForgetAccountComponent.builder().contextModule(new ContextModule(forgetAccountActivity)).build();
        mForgetAccountModule.inject(forgetAccountActivity);

    }

}
