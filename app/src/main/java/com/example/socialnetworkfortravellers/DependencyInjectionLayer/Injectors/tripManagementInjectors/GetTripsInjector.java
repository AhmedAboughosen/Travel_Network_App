package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.tripManagementInjectors;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.tripManagementComponents.DaggerGetTripsComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.tripManagementComponents.GetTripsComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.tripManagementFragments.TripsFragment;

public class GetTripsInjector {

    private static GetTripsInjector mInjector;

    private GetTripsInjector() {
    }

    public static GetTripsInjector getSharedInjector() {
        if (mInjector == null) {
            mInjector = new GetTripsInjector();
        }
        return mInjector;
    }


    public void injectIn(TripsFragment tripsFragment) {
        GetTripsComponent mComponent = DaggerGetTripsComponent.builder()
                .contextModule(new ContextModule(tripsFragment.getActivity()))
                .build();

        mComponent.inject(tripsFragment);

    }
}
