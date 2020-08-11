package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.FriendSuggestionPresenters;

public interface IFriendSuggestionPresenter {

    void loadMoreUser();

    void getSuggestionFriends(String userKey, String countryName);

    void setUpFriendSuggestionPresenterCallBack(IFriendSuggestionPresenterCallBack mFriendSuggestionPresenterCallBack);
}
