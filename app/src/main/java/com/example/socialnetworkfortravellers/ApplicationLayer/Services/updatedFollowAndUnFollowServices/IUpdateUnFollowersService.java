package com.example.socialnetworkfortravellers.ApplicationLayer.Services.updatedFollowAndUnFollowServices;

public interface IUpdateUnFollowersService {

    void updateUnFollowers(String FriendKey);
    void setUpUpdateUnFollowersServiceCallBack(IUpdateUnFollowersServiceCallBack mUpdateUnFollowersServiceCallBack);
}
