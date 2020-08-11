package com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getUserInfoServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.IBaseServiceCallBack;

public interface IGetUserInfoServiceCallBack  extends IBaseServiceCallBack{

    void userData(UserModel mUserModel);

    void thisUserNotExists();

}
