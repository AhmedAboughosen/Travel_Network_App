package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.tripManagementComponents;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.tripManagementModules.CountryListModules;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.tripManagementActivities.FriendTripActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.tripManagementFragments.CountryListFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ContextModule.class, CountryListModules.class})
public interface CountyListComponent {
    void inject(CountryListFragment countryListFragment);
    void inject(FriendTripActivity countryListFragment);

}
