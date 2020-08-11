package com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getFullNameOfUsersServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.FoundUserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.IBaseServiceCallBack;

import java.util.ArrayList;
import java.util.List;

public interface IGetFullNameOfUsersServiceCallBack extends IBaseServiceCallBack {

    void listOfUser(ArrayList<FoundUserModel> list);
    void noUserExists();
}
