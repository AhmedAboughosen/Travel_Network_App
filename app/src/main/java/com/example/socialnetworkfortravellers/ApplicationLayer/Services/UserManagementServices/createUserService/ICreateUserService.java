package com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.createUserService;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.AccountModel;

public interface ICreateUserService {
    void createNewAccount(AccountModel accountModel);

    void setUpCreateUserServiceCallBack(ICreateUserServiceCallBack mCreateUserPresenterCallBack);

}
