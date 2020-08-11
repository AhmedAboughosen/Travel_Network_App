package com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.loginServices;

public interface ILoginService {
    void login(String email, String password);
    void setLoginServiceCallBack(ILoginServiceCallBack mLoginServiceCallBack);
}
