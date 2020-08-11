package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.tripManagementInjectors;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.tripManagementComponents.CountyListComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.tripManagementComponents.DaggerCountyListComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.tripManagementActivities.FriendTripActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.tripManagementFragments.CountryListFragment;

public class CountryListInjector {

    private static CountryListInjector mInjector;

    private CountryListInjector() {
    }

    public static CountryListInjector getSharedInjector() {
        if (mInjector == null) {
            mInjector = new CountryListInjector();
        }
        return mInjector;
    }


    public void injectIn(CountryListFragment countryListFragment) {
        CountyListComponent mCountyListComponent = DaggerCountyListComponent.builder()
                .contextModule(new ContextModule(countryListFragment.getActivity()))
                .build();

        mCountyListComponent.inject(countryListFragment);

    }

    public void injectIn(FriendTripActivity friendTripActivity) {
        CountyListComponent mCountyListComponent = DaggerCountyListComponent.builder()
                .contextModule(new ContextModule(friendTripActivity))
                .build();

        mCountyListComponent.inject(friendTripActivity);

    }
}
