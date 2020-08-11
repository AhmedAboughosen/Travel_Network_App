package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.UserManagementModules;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.deactivateAccountPresenters.DeactivateAccountPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.deactivateAccountPresenters.IDeactivateAccountPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.reauthenticateAccountServices.IReauthenticateAccountService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.reauthenticateAccountServices.ReauthenticateAccountAccountService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.removeDataServices.IRemoveDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.removeDataServices.RemoveDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.removeUserInfoServices.IRemoveUserInfoService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.removeUserInfoServices.RemoveUserInfoService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Validators.baseValidators.IBaseValidator;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public class DeactivateAccountModule {

    @Provides
    IDeactivateAccountPresenter ProvidesUpdateEmailPresenter(IBaseValidator baseValidator, IReauthenticateAccountService reauthenticateAccountService, IRemoveUserInfoService removeUserInfoService) {
        return new DeactivateAccountPresenter(baseValidator, reauthenticateAccountService, removeUserInfoService);
    }


    @Provides
    IReauthenticateAccountService ProvidesReauthenticateAccountService() {
        return new ReauthenticateAccountAccountService();
    }

    @Provides
    IRemoveDataService ProvidesRemoveDataService() {
        return new RemoveDataService();
    }

    @Provides
    IRemoveUserInfoService ProvidesRemoveUserInfoService(IRemoveDataService removeDataService) {
        return new RemoveUserInfoService(removeDataService);
    }
}
