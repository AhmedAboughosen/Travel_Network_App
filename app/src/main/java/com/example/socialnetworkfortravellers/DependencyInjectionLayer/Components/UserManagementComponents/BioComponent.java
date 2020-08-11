package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.BaseValidatorModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.UserManagementModules.userRegistrationModules.BioModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.SaveRawDataModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.userRegistrationActivities.BioActivity;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {ContextModule.class, BaseValidatorModule.class, BioModule.class, SaveRawDataModule.class})
public interface BioComponent {
    void inject(BioActivity bioActivity);
}
