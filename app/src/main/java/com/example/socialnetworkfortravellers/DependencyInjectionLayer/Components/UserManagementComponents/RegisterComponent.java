package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.BaseValidatorModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.SendActivityModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.UserManagementModules.userRegistrationModules.RegisterModules;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.userRegistrationActivities.RegisterActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={ContextModule.class, BaseValidatorModule.class , RegisterModules.class , SendActivityModule.class})
public interface RegisterComponent {
    void inject(RegisterActivity registerActivity);
}
