package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.NewsFeedPresenters;

public interface IUserTrendingAndNearByPresenter {
    void getUserNearBy(String country);
    void getUserRating();
    void setUpUserTrendingAndNearByPresenterCallback(IUserTrendingAndNearByPresenterCallback getUsersFromCountryPresenterCallback);
}
