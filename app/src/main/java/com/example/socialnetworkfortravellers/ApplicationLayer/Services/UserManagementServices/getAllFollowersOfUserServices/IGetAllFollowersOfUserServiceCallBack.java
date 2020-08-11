package com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getAllFollowersOfUserServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.IBaseServiceCallBack;

import java.util.HashMap;
import java.util.List;

public interface IGetAllFollowersOfUserServiceCallBack extends IBaseServiceCallBack {

    void listOfUser(List<String> lis);
}
