package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.displayPostsPresenters;

public interface IDisplayPostsPresenter {
    void getPosts(String userKey);

    void loadMorePost();

    void setUpDisplayPostsPresenterCallBack(IDisplayPostsPresenterCallBack mDisplayPostsPresenterCallBack);

    void increasePostLikes(String userKey, String postKey);

    void decreasePostLikes(String userKey, String postKey);
}
