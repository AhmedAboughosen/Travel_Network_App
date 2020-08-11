package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents;


import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.UserManagementModules.TravelersNearModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.GetDataByUseAddValueEventModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.GetDataByUseSingleValueModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.GetUserInfoModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.SaveRawDataModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.postManagementModules.NewsFeedModule;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.postManagementFragments.NewsFeedFragment;

import javax.inject.Singleton;

import dagger.Component;


@Component(modules = {TravelersNearModule.class, ContextModule.class, GetUserInfoModule.class, SaveRawDataModule.class ,  GetDataByUseSingleValueModule.class, GetDataByUseAddValueEventModule.class , NewsFeedModule.class})
public interface NewsFeedComponent {
    void inject(NewsFeedFragment newsFeedFragment);
}
