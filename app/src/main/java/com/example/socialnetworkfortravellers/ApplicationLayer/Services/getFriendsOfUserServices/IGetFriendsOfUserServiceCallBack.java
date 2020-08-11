package com.example.socialnetworkfortravellers.ApplicationLayer.Services.getFriendsOfUserServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.IBaseServiceCallBack;

import java.util.HashMap;

public interface IGetFriendsOfUserServiceCallBack extends IBaseServiceCallBack {

    void getFriends(HashMap<String, String> map);
    void CurrentUserDoesNotHaveFriends();

}
