package com.example.socialnetworkfortravellers.ApplicationLayer.Services.getUserCounterSerivces;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserInfoModel;

public interface IGetUserCountServiceCallBack {
    void userCountData(UserInfoModel mUserModel);

    void ExceptionMessage(String message);

}
