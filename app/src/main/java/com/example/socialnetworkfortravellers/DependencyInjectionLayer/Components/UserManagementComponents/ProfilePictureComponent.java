package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.UserManagementModules.userRegistrationModules.ProfilePictureModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.CompressImageUtilModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.SaveImageModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.SaveRawDataModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.userRegistrationActivities.ProfilePictureActivity;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {ContextModule.class, SaveRawDataModule.class, ProfilePictureModule.class, CompressImageUtilModule.class , SaveImageModule.class})
public interface ProfilePictureComponent {
    void inject(ProfilePictureActivity setUpActivity);
}
