package com.example.socialnetworkfortravellers.ApplicationLayer.Services.updatedFollowAndUnFollowServices;

public interface IUpdateFollowersService {

    void updateFollowers(String mFriendKey);

    void setUpUpdateFollowersServiceCallBack(IUpdateFollowersServiceCallBack mUpdateFollowersServiceCallBack);
}
