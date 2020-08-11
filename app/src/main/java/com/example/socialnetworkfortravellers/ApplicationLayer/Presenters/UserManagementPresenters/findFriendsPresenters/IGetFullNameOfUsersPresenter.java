package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.findFriendsPresenters;

public interface IGetFullNameOfUsersPresenter {

    void getAllFullNameOfUsers();

    void setUpGetAllUsersNamePresenterCallBack(IGetAllUserNamesPresenterCallBack mGetAllUsersNamePresenterCallBack);
}
