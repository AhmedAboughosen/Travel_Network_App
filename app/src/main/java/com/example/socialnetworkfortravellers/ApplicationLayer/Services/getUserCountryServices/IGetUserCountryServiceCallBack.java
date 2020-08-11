package com.example.socialnetworkfortravellers.ApplicationLayer.Services.getUserCountryServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.IBaseServiceCallBack;

import java.util.HashMap;

public interface IGetUserCountryServiceCallBack extends IBaseServiceCallBack {

    void usersInCurrentCountry(HashMap<String , Object> users);
}
