package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents;


import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.BaseValidatorModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.UserManagementModules.DeactivateAccountModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.deactivateAccountActivity.DeactivateAccountActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ContextModule.class, BaseValidatorModule.class, DeactivateAccountModule.class})
public interface DeactivateAccountComponent {

    void inject(DeactivateAccountActivity deactivateAccountActivity);
}
