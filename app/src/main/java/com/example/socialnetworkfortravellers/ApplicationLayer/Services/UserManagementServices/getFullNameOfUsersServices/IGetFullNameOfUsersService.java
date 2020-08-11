package com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getFullNameOfUsersServices;

public interface IGetFullNameOfUsersService {
    void setUpGetFullNameOfUsersServiceCallBack(IGetFullNameOfUsersServiceCallBack mGetAllFollowersOfUserServiceCallBack);
    void getFullNameOfUsers();
}
