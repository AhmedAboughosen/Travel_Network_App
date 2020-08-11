package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.UserManagementModules.userRegistrationModules;


import android.content.Context;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.RegistrationPresenter.IRegisterPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.RegistrationPresenter.RegisterPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.createUserService.CreateUserService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.createUserService.ICreateUserService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Validators.baseValidators.IBaseValidator;

import dagger.Module;
import dagger.Provides;

@Module
public class RegisterModules {


    @Provides
    IRegisterPresenter ProvidesRegisterPresenter(IBaseValidator baseValidator, ICreateUserService createUserPresenter) {
        return new RegisterPresenter(baseValidator, createUserPresenter);
    }

    @Provides
    ICreateUserService ProvidesCreateUserPresenter(Context context) {
        return new CreateUserService(context);
    }

}
