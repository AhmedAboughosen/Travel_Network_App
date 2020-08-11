package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.UserManagementInjectors;


import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents.DaggerSetUpComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents.SetUpComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.userRegistrationActivities.SetUpActivity;

public class SetUpInjector {

    private static SetUpInjector mSetUpInjector;

    private SetUpInjector() {
    }

    public static SetUpInjector getSharedInjector() {
        if (mSetUpInjector == null) {
            mSetUpInjector = new SetUpInjector();
        }
        return mSetUpInjector;
    }


    public void injectIn(SetUpActivity setUpActivity) {
        SetUpComponent mSetUpComponent = DaggerSetUpComponent.builder()
                .contextModule(new ContextModule(setUpActivity))
                .build();

        mSetUpComponent.inject(setUpActivity);

    }

}
