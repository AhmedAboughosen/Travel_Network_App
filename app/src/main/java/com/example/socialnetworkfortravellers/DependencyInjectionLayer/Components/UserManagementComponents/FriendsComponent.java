package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.UserManagementModules.findFriendModules.FriendsModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.GetDataByUseAddValueEventModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.GetDataByUseSingleValueModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.GetUserInfoModule;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.userManagementFragments.findFriendFragments.FriendsFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {FriendsModule.class,  ContextModule.class, GetUserInfoModule.class, GetDataByUseSingleValueModule.class})
public interface FriendsComponent {

    void inject(FriendsFragment friendsFragment);
}
