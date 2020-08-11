package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents;


import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.BaseValidatorModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.UserManagementModules.UpdatePasswordModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.editUserInformationActivity.UpdatePasswordActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {UpdatePasswordModule.class, BaseValidatorModule.class, ContextModule.class})
public interface UpdatePasswordComponent {
    void inject(UpdatePasswordActivity updatePasswordActivity);

}
