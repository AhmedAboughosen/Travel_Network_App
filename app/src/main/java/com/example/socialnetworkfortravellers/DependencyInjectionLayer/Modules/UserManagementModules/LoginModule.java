package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.UserManagementModules;

import android.content.Context;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.loginPresenters.ILoginPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.loginPresenters.LoginPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.loginServices.ILoginService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.loginServices.LoginService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getUserInfoServices.IGetUserInfoService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Validators.baseValidators.IBaseValidator;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

    @Provides
    ILoginPresenter ProvidesLoginPresenter(IBaseValidator baseValidator, ILoginService loginService, IGetUserInfoService getUserInfoService) {
        return new LoginPresenter(baseValidator, loginService, getUserInfoService);
    }

    @Provides
    ILoginService ProvidesLoginService(Context context) {
        return new LoginService(context);
    }
}
