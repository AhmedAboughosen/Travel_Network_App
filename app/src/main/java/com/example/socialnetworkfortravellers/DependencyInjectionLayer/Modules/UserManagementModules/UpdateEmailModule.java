package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.UserManagementModules;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.UpdateEmailPresenters.IUpdateEmailPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.UpdateEmailPresenters.UpdateEmailPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.reauthenticateAccountServices.IReauthenticateAccountService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.reauthenticateAccountServices.ReauthenticateAccountAccountService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Validators.baseValidators.IBaseValidator;

import dagger.Module;
import dagger.Provides;

@Module
public class UpdateEmailModule {


    @Provides
    IUpdateEmailPresenter ProvidesUpdateEmailPresenter(IBaseValidator baseValidator, IReauthenticateAccountService reauthenticateAccountService) {
        return new UpdateEmailPresenter(baseValidator, reauthenticateAccountService);
    }


    @Provides
    IReauthenticateAccountService ProvidesReauthenticateAccountService() {
        return new ReauthenticateAccountAccountService();
    }

}
