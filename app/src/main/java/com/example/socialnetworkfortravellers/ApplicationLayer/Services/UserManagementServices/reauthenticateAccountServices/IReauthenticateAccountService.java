package com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.reauthenticateAccountServices;

public interface IReauthenticateAccountService {

    void reauthenticate(String password);

    void setReauthenticateServiceCallBack(IReauthenticateAccountServiceCallBack mReauthenticateServiceCallBack);
}
