package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.BaseValidatorModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.SendActivityModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.UserManagementModules.LoginModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.GetDataByUseSingleValueModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.GetUserInfoModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.loginActivity.LoginActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ContextModule.class, BaseValidatorModule.class, LoginModule.class, SendActivityModule.class, GetUserInfoModule.class, GetDataByUseSingleValueModule.class})
public interface LoginComponent {
    void inject(LoginActivity loginActivity);
}
