package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.UserManagementModules.MutualFriendModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.GetDataByUseAddValueEventModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.GetDataByUseSingleValueModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.GetUserInfoModule;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.userManagementFragments.findFriendFragments.MutualFriendFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MutualFriendModule.class, GetDataByUseAddValueEventModule.class, ContextModule.class, GetUserInfoModule.class, GetDataByUseSingleValueModule.class})
public interface MutualFriendComponent {
    void inject(MutualFriendFragment mutualFriendFragment);
}
