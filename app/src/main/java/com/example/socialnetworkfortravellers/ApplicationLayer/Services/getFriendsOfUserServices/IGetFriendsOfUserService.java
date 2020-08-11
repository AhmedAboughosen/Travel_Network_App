package com.example.socialnetworkfortravellers.ApplicationLayer.Services.getFriendsOfUserServices;

public interface IGetFriendsOfUserService {
    void getFriendsOfUser(String userId);

    void setUpGetFollowingDataServiceCallBack(IGetFriendsOfUserServiceCallBack mGetFollowingDataServiceCallBack);
}
