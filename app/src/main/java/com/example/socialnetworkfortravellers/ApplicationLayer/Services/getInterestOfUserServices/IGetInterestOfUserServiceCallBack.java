package com.example.socialnetworkfortravellers.ApplicationLayer.Services.getInterestOfUserServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.IBaseServiceCallBack;

import java.util.HashMap;

public interface IGetInterestOfUserServiceCallBack extends IBaseServiceCallBack {

    void selectedInterest(HashMap<String, Object> list);
}
