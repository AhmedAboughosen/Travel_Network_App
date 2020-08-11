package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.BaseValidatorModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.UserManagementModules.UpdateEmailModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.editUserInformationActivity.UpdateEmailActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {UpdateEmailModule.class, BaseValidatorModule.class, ContextModule.class})
public interface UpdateEmailComponent {

    void inject(UpdateEmailActivity updateEmailActivity);
}
