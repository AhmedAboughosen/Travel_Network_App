package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.UserManagementInjectors;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents.DaggerDeactivateAccountComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents.DeactivateAccountComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.deactivateAccountActivity.DeactivateAccountActivity;

public class DeactivateAccountInjector  {




    private static DeactivateAccountInjector mInjector;

    private DeactivateAccountInjector() {
    }

    public static DeactivateAccountInjector getSharedInjector() {
        if (mInjector == null) {
            mInjector = new DeactivateAccountInjector();
        }
        return mInjector;
    }




    public void injectIn(DeactivateAccountActivity deactivateAccountActivity) {
        DeactivateAccountComponent mDeactivateAccountComponent = DaggerDeactivateAccountComponent.builder()
                .contextModule(new ContextModule(deactivateAccountActivity))
                .build();

        mDeactivateAccountComponent.inject(deactivateAccountActivity);

    }
}
