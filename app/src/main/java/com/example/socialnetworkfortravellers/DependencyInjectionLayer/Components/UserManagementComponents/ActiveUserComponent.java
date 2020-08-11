package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents;


import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.UserManagementModules.ActiveUserModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.GetDataByUseSingleValueModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.GetUserInfoModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.displayListOfUserActivity.DisplayListOfUserActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ActiveUserModule.class, ContextModule.class, GetUserInfoModule.class, GetDataByUseSingleValueModule.class})
public interface ActiveUserComponent {

    void inject(DisplayListOfUserActivity displayListOfUserActivity);
}
