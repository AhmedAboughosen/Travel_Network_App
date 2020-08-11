package com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.removeUserInfoServices;

public interface IRemoveUserInfoService {

    void deactivateUserInfo(String userKey);

    void setUpRemoveUserInfoServiceCallBack(IRemoveUserInfoServiceCallBack mGetUserCounterSerivceCallBack);
}
