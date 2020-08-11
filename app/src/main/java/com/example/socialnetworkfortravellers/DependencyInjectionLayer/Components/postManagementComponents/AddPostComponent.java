package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.postManagementComponents;


import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.CompressImageUtilModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.GetDataByUseSingleValueModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.SaveImageModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.SaveRawDataModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.UpdatePathUsingTransactionModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.postManagementModules.AddPostModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.postManagementModules.BasePostModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.postManagementActivities.AddPostActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ContextModule.class, AddPostModule.class, CompressImageUtilModule.class, SaveImageModule.class, SaveRawDataModule.class, UpdatePathUsingTransactionModule.class, BasePostModule.class})
public interface AddPostComponent {

    void inject(AddPostActivity addPostActivity);

}
