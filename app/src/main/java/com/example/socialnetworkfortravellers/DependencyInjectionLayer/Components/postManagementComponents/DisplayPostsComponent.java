package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.postManagementComponents;


import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.FragmentActivityModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.GetDataByUseAddValueEventModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.GetDataByUseSingleValueModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.GetUserInfoModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.SaveRawDataModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.postManagementModules.DisplayPostsModule;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.postManagementFragments.PostsFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ContextModule.class, DisplayPostsModule.class, GetDataByUseSingleValueModule.class, GetDataByUseAddValueEventModule.class, SaveRawDataModule.class, FragmentActivityModule.class, GetUserInfoModule.class,GetDataByUseSingleValueModule.class})
public interface DisplayPostsComponent {
    void inject(PostsFragment postsFragment);
}