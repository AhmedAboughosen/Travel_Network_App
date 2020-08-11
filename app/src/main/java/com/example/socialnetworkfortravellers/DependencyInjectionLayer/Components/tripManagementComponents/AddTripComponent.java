package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.tripManagementComponents;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.SaveRawDataModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.tripManagementModules.AddTripModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.tripManagementActivities.AddTripActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ContextModule.class, AddTripModule.class, SaveRawDataModule.class})
public interface AddTripComponent {
    void inject(AddTripActivity addTripActivity);
}
