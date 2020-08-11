package com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getUserInfoServices;

public interface IGetUserInfoService {

    void getUserInfo(String userKey);

    void removeEventListener();

    void setUpGetUserInfoServiceCallBack(IGetUserInfoServiceCallBack mGetUserInfoServiceCallBack);
}
