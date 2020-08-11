package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.NewsFeedPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.IPresenterCallBack;

import java.util.List;

public interface IUserTrendingAndNearByPresenterCallback extends IPresenterCallBack {
    void ListOfUsersForNearByTravellers(List<UserModel> userModels);

    void ListOfUsersForUserTrending(List<UserModel> userModels);
}
