package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.UserManagementModules;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.UpdatePasswordPresenters.IUpdatePassswordPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.UpdatePasswordPresenters.UpdatePassswordPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.reauthenticateAccountServices.IReauthenticateAccountService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.reauthenticateAccountServices.ReauthenticateAccountAccountService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Validators.baseValidators.IBaseValidator;

import dagger.Module;
import dagger.Provides;

@Module
public class UpdatePasswordModule {


    @Provides
    IUpdatePassswordPresenter ProvidesUpdatePassswordPresenter(IBaseValidator baseValidator, IReauthenticateAccountService reauthenticateAccountService) {
        return new UpdatePassswordPresenter(baseValidator, reauthenticateAccountService);
    }


    @Provides
    IReauthenticateAccountService ProvidesReauthenticateAccountService() {
        return new ReauthenticateAccountAccountService();
    }
}
