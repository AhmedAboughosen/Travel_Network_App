package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.findFriendsPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.FoundUserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.IPresenterCallBack;

import java.util.List;

public interface IGetAllUserNamesPresenterCallBack extends IPresenterCallBack {

    void allUsers(List<FoundUserModel> allUsers);

    void noUserExists();

}
