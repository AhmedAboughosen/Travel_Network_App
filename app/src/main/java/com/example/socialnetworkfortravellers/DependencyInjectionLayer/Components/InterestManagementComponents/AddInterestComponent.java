package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.InterestManagementComponents;


import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.InterestManagementModules.AddInterestModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.SaveRawDataModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.InterestManagementAcitivites.AddInterestActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ContextModule.class, AddInterestModule.class, SaveRawDataModule.class})
public interface AddInterestComponent {

    void inject(AddInterestActivity addInterestActivity);
}
