package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.tripManagementComponents;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.GetDataByUseSingleValueModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.tripManagementModules.RelatedTripModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.tripManagementActivities.MatchesTripActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ContextModule.class, RelatedTripModule.class, GetDataByUseSingleValueModule.class})
public interface RelatedTripComponent {
    void inject(MatchesTripActivity matchesTripActivity);
}
