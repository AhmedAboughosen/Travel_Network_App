package com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.forgetAccountServices;

public interface IForgetAccountService {
    void sendPasswordResetEmail(String email);
    void setUpForgetAccountServiceCallBack(IForgetAccountServiceCallBack forgetAccountServiceCallBack);

}
