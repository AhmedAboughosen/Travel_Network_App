package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.InterestManagementInjectors;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.InterestManagementComponents.DaggerEditInterestComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.InterestManagementComponents.EditInterestComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.InterestManagementAcitivites.EditInterestActivity;

public class EditInterestInjector {
    private static EditInterestInjector mInjector;

    private EditInterestInjector() {
    }

    public static EditInterestInjector getSharedInjector() {
        if (mInjector == null) {
            mInjector = new EditInterestInjector();
        }
        return mInjector;
    }


    public void injectIn(EditInterestActivity editInterestActivity) {
        EditInterestComponent mEditInterestComponent = DaggerEditInterestComponent.builder()
                .contextModule(new ContextModule(editInterestActivity))
                .build();

        mEditInterestComponent.inject(editInterestActivity);

    }
}
