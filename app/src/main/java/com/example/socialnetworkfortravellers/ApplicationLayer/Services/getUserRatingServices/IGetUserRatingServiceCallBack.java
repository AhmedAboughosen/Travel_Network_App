package com.example.socialnetworkfortravellers.ApplicationLayer.Services.getUserRatingServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.IBaseServiceCallBack;

import java.util.HashMap;

public interface IGetUserRatingServiceCallBack extends IBaseServiceCallBack {

    void listOfUserRating(HashMap<String, Object> lis);
}
