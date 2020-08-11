package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.UserManagementModules;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.forgetAccountPresenters.ForgetAccountPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.forgetAccountPresenters.IForgetAccountPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.forgetAccountServices.ForgetAccountService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.forgetAccountServices.IForgetAccountService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Validators.baseValidators.IBaseValidator;

import dagger.Module;
import dagger.Provides;

@Module
public class ForgetAccountModule {

    @Provides
    IForgetAccountPresenter ProvidesUpdateEmailPresenter(IBaseValidator baseValidator, IForgetAccountService forgetAccountService) {
        return new ForgetAccountPresenter(baseValidator , forgetAccountService);
    }

    @Provides
    IForgetAccountService ProvidesForgetAccountService() {
        return new ForgetAccountService();
    }
}
