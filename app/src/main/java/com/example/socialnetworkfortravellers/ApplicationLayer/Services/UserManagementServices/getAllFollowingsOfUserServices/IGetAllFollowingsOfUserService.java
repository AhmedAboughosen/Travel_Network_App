package com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getAllFollowingsOfUserServices;

public interface IGetAllFollowingsOfUserService {
    void setUpGetAllFollowingsOfUserServiceCallBack(IGetAllFollowingsOfUserServiceCallBack mGetAllFollowersOfUserServiceCallBack);
    void getAllFollowingUsers(String userKey);
    void removeEventListener();
}
