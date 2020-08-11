package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents;


import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.UserManagementModules.FriendSuggestionModules;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.GetDataByUseAddValueEventModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.GetDataByUseSingleValueModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.GetUserInfoModule;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.userManagementFragments.suggestionFragments.FriendSuggestionFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ContextModule.class, FriendSuggestionModules.class, GetDataByUseSingleValueModule.class, GetUserInfoModule.class, GetDataByUseAddValueEventModule.class})

public interface FriendSuggestionComponent {

    void inject(FriendSuggestionFragment friendSuggestionFragment);
}
