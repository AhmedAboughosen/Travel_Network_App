package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.UserManagementInjectors;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents.DaggerRegisterComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents.RegisterComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.userRegistrationActivities.RegisterActivity;

public class RegisterInjector {

    private static RegisterInjector mSignUpInjector;

    private RegisterInjector() {
    }

    public static RegisterInjector getSharedInjector() {
        if (mSignUpInjector == null) {
            mSignUpInjector = new RegisterInjector();
        }
        return mSignUpInjector;
    }


    public void injectIn(RegisterActivity registerActivity) {
        RegisterComponent mRegisterComponent = DaggerRegisterComponent.builder()
                .contextModule(new ContextModule(registerActivity))
                .build();

        mRegisterComponent.inject(registerActivity);

    }

}
