package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.UserManagementInjectors;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents.BioComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents.DaggerBioComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.userRegistrationActivities.BioActivity;

public class BioInjector {


    private static BioInjector mInjector;

    private BioInjector() {
    }

    public static BioInjector getSharedInjector() {
        if (mInjector == null) {
            mInjector = new BioInjector();
        }
        return mInjector;
    }


    public void injectIn(BioActivity bioActivity) {
        BioComponent mBioComponent = DaggerBioComponent.builder()
                .contextModule(new ContextModule(bioActivity))
                .build();

        mBioComponent.inject(bioActivity);

    }
}
