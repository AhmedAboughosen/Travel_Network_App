package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.postManagementComponents;


import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.UpdatePathUsingTransactionModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.postManagementModules.DeletePostModule;
import com.example.socialnetworkfortravellers.ViewLayer.Dialog.PostMenuSheetDialog;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {DeletePostModule.class, UpdatePathUsingTransactionModule.class})
public interface DeletePostComponent {

    void setUpInject(PostMenuSheetDialog postMenuSheetDialog);
}
