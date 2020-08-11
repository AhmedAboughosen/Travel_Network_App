package com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.saveUserInfoServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;

public interface ISaveUserInfoService {
    void saveUserInfo(UserModel userModel);

    void setUpSetUpUserInfoServiceCallback(ISaveUserInfoServiceCallback mSetUpUserInfoServiceCallback);
}
