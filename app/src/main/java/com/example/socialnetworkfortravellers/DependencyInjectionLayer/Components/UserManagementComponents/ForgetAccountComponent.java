package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.BaseValidatorModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.UserManagementModules.ForgetAccountModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.forgetAccountActivity.ForgetAccountActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ForgetAccountModule.class, BaseValidatorModule.class, ContextModule.class})
public interface ForgetAccountComponent {
    void inject(ForgetAccountActivity forgetAccountActivity);
}
