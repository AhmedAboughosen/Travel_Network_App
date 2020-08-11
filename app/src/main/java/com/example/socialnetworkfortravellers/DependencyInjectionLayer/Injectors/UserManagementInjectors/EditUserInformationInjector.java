package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.UserManagementInjectors;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents.DaggerEditUserInformationComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents.EditUserInformationComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.editUserInformationActivity.EditUserInformationActivity;

public class EditUserInformationInjector {


    private static EditUserInformationInjector mInjector;

    private EditUserInformationInjector() {
    }

    public static EditUserInformationInjector getSharedInjector() {
        if (mInjector == null) {
            mInjector = new EditUserInformationInjector();
        }
        return mInjector;
    }


    public void injectIn(EditUserInformationActivity editUserInformationActivity) {
        EditUserInformationComponent mEditUserInformationComponent = DaggerEditUserInformationComponent.builder()
                .contextModule(new ContextModule(editUserInformationActivity))
                .build();

        mEditUserInformationComponent.inject(editUserInformationActivity);

    }
}
