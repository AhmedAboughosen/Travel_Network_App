package com.example.socialnetworkfortravellers.ApplicationLayer.Services.getRelatedInterestServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.IBaseServiceCallBack;

import java.util.HashMap;

public interface IGetRelatedInterestServiceCallBack extends IBaseServiceCallBack {

    void usersInCurrentInterest(HashMap<String, Object> list);

}
