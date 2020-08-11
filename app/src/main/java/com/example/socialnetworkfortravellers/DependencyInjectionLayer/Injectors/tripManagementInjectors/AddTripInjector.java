package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.tripManagementInjectors;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.tripManagementComponents.AddTripComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.tripManagementComponents.CountyListComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.tripManagementComponents.DaggerAddTripComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.tripManagementComponents.DaggerCountyListComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.tripManagementActivities.AddTripActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.tripManagementFragments.CountryListFragment;

public class AddTripInjector {

    private static AddTripInjector mInjector;

    private AddTripInjector() {
    }

    public static AddTripInjector getSharedInjector() {
        if (mInjector == null) {
            mInjector = new AddTripInjector();
        }
        return mInjector;
    }


    public void injectIn(AddTripActivity addTripActivity) {
        AddTripComponent mComponent = DaggerAddTripComponent.builder()
                .contextModule(new ContextModule(addTripActivity))
                .build();

        mComponent.inject(addTripActivity);

    }
}
