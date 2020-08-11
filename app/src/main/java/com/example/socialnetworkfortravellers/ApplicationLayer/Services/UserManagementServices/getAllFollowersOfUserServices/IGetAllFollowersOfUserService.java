package com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getAllFollowersOfUserServices;

public interface IGetAllFollowersOfUserService {
    void setUpGetAllFollowersOfUserServiceCallBack(IGetAllFollowersOfUserServiceCallBack mGetAllFollowersOfUserServiceCallBack);
    void getAllFollowerUsers(String userKey);
    void removeEventListener();
}
