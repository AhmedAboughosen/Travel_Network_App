package com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.GetMatchesFullNameServices;

public interface IGetMatchesFullNameService {

    void getAllFullNameOfUsers();

    void setFindFriendServiceCallBack(IGetMatchesFullNameServiceCallBack findFriendServiceCallBack);
}
