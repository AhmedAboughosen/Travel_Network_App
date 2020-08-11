package com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.reauthenticateAccountServices;

public interface IReauthenticateAccountServiceCallBack {

    void isSuccessful();

    void failure(String message);
}
