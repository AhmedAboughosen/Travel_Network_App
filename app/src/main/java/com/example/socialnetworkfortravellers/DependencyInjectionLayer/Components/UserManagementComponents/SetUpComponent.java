package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.BaseValidatorModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.SendActivityModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.UserManagementModules.userRegistrationModules.SetUpModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.SaveRawDataModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.userRegistrationActivities.SetUpActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
    @Component(modules = {ContextModule.class, BaseValidatorModule.class, SetUpModule.class, SendActivityModule.class , SaveRawDataModule.class})
public interface SetUpComponent {

    void inject(SetUpActivity setUpActivity);
}
