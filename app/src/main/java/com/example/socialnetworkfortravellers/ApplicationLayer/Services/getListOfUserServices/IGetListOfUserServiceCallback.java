package com.example.socialnetworkfortravellers.ApplicationLayer.Services.getListOfUserServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;

import java.util.List;

public interface IGetListOfUserServiceCallback {

    void ListOfUsers(List<UserModel> userModels);
    void internetIsNotConnected();
}
