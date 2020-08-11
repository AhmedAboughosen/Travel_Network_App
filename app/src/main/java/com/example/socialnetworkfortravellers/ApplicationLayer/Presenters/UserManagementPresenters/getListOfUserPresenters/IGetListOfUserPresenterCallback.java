package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.getListOfUserPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.IPresenterCallBack;

import java.util.List;

public interface IGetListOfUserPresenterCallback extends IPresenterCallBack {
    void ListOfUsers(List<UserModel> userModels);
}
