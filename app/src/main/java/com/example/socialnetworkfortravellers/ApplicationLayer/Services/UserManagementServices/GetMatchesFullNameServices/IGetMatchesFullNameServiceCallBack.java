package com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.GetMatchesFullNameServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.FoundUserModel;

import java.util.List;

public interface IGetMatchesFullNameServiceCallBack {

    void allUsers(List<FoundUserModel> listOfAllUsers);


    void errorMessage(String message);

    void noUserExists();
}
