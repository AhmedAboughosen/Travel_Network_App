package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.getListOfUserPresenters;

import java.util.List;

public interface IGetListOfUserPresenter {
    void getUsers(List<String> list_of_user);
    void setUpGetListOfUserPresenterCallback(IGetListOfUserPresenterCallback mGetListOfUserPresenterCallback);
}
