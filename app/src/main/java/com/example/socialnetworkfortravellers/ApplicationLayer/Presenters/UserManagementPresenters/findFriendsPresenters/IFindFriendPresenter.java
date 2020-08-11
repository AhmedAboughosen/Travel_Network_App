package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.findFriendsPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.FoundUserModel;

import java.util.List;

public interface IFindFriendPresenter {
    void reSetIndex();

    void findFriend(String fullName);

    void getUser();

    void setUpFindFriendPresenterCallBack(IFindFriendPresenterCallBack mFindFriendPresenterCallBack);

    void AllUserNames(List<FoundUserModel> foundUserModel);
}
