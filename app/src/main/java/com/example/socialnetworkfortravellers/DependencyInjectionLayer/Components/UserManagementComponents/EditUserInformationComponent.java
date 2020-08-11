package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents;


import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.BaseValidatorModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.UserManagementModules.EditUserInformationModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.CompressImageUtilModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.SaveImageModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.SaveRawDataModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.editUserInformationActivity.EditUserInformationActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {EditUserInformationModule.class, ContextModule.class, BaseValidatorModule.class,
        SaveRawDataModule.class, SaveImageModule.class, CompressImageUtilModule.class})
public interface EditUserInformationComponent {
    void inject(EditUserInformationActivity editUserInformationActivity);
}
