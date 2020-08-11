package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.tripManagementComponents;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.GetDataByUseSingleValueModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.RemoveDataModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.tripManagementModules.GetTripsModule;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.tripManagementFragments.TripsFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ContextModule.class, GetTripsModule.class, GetDataByUseSingleValueModule.class , RemoveDataModule.class})
public interface GetTripsComponent {
    void inject(TripsFragment tripsFragment);
}
