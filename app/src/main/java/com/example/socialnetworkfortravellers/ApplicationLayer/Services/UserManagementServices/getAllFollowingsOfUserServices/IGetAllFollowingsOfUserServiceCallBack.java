package com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getAllFollowingsOfUserServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.IBaseServiceCallBack;

import java.util.List;

public interface IGetAllFollowingsOfUserServiceCallBack extends IBaseServiceCallBack {

    void listOfUser(List<String> lis);
}
