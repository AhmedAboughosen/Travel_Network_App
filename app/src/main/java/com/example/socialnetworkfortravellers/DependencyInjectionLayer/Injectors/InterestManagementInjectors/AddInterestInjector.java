package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.InterestManagementInjectors;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.InterestManagementComponents.AddInterestComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.InterestManagementComponents.DaggerAddInterestComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.InterestManagementAcitivites.AddInterestActivity;

public class AddInterestInjector {

    private static AddInterestInjector mInjector;

    private AddInterestInjector() {
    }

    public static AddInterestInjector getSharedInjector() {
        if (mInjector == null) {
            mInjector = new AddInterestInjector();
        }
        return mInjector;
    }


    public void injectIn(AddInterestActivity addInterestActivity) {
        AddInterestComponent mAddInterestComponent = DaggerAddInterestComponent.builder()
                .contextModule(new ContextModule(addInterestActivity))
                .build();

        mAddInterestComponent.inject(addInterestActivity);

    }
}
