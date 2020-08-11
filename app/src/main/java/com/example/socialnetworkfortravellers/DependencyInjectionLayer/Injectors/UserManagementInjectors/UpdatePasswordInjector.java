package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.UserManagementInjectors;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents.DaggerUpdatePasswordComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents.UpdatePasswordComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.editUserInformationActivity.UpdatePasswordActivity;

public class UpdatePasswordInjector {


    private static UpdatePasswordInjector mUpdatePasswordInjector;

    private UpdatePasswordInjector() {
    }

    public static UpdatePasswordInjector getSharedInjector() {
        if (mUpdatePasswordInjector == null) {
            mUpdatePasswordInjector = new UpdatePasswordInjector();
        }
        return mUpdatePasswordInjector;
    }


    public void injectIn(UpdatePasswordActivity updatePasswordActivity) {
        UpdatePasswordComponent mUpdatePasswordComponent = DaggerUpdatePasswordComponent.builder()
                .contextModule(new ContextModule(updatePasswordActivity))
                .build();

        mUpdatePasswordComponent.inject(updatePasswordActivity);

    }

}
