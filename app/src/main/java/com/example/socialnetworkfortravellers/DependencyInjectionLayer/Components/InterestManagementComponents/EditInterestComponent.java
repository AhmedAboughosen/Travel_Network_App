package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.InterestManagementComponents;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.InterestManagementModules.EditInterestModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.GetDataByUseSingleValueModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.SaveRawDataModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.InterestManagementAcitivites.EditInterestActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ContextModule.class, EditInterestModule.class, SaveRawDataModule.class, GetDataByUseSingleValueModule.class})
public interface EditInterestComponent {
    void inject(EditInterestActivity editInterestActivity);

}
