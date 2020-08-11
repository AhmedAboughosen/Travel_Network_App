package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.postManagementComponents;


import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.CompressImageUtilModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.SaveImageModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.SaveRawDataModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.postManagementModules.BasePostModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.postManagementModules.EditPostModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.postManagementActivities.EditPostActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ContextModule.class, EditPostModule.class, CompressImageUtilModule.class, SaveImageModule.class, SaveRawDataModule.class, BasePostModule.class})
public interface EditPostComponent {

    void inject(EditPostActivity editPostActivity);
}
