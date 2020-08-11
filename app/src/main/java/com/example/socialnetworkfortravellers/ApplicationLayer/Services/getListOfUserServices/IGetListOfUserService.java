package com.example.socialnetworkfortravellers.ApplicationLayer.Services.getListOfUserServices;

import java.util.List;

public interface IGetListOfUserService {
    void setUpGetListOfUserServiceCallback(IGetListOfUserServiceCallback mPeopleWhoLikeServiceCallback);
    void getUsers(List<String> list_of_users);
}
