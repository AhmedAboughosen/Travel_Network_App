package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.UserManagementModules.findFriendModules.FindFriendModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.GetDataByUseAddValueEventModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.GetDataByUseSingleValueModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.findFriendsActivities.FindFriendActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {FindFriendModule.class, GetDataByUseSingleValueModule.class, ContextModule.class})
public interface FindFriendComponent {

    void inject(FindFriendActivity findFriendActivity);
}
