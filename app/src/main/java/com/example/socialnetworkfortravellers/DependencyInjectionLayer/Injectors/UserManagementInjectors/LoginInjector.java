package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.UserManagementInjectors;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents.DaggerLoginComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents.LoginComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.loginActivity.LoginActivity;

public class LoginInjector {


    private static LoginInjector mInjector;
    private LoginComponent mLoginComponent;

    private LoginInjector() {
    }

    public static LoginInjector getSharedInjector() {
        if (mInjector == null) {
            mInjector = new LoginInjector();
        }
        return mInjector;
    }


    public void injectIn(LoginActivity loginActivity) {
        mLoginComponent = DaggerLoginComponent.builder()
                .contextModule(new ContextModule(loginActivity))
                .build();

        mLoginComponent.inject(loginActivity);

    }
}
