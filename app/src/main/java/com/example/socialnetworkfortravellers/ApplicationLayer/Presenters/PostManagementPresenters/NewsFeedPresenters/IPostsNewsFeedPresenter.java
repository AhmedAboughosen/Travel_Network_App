package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.NewsFeedPresenters;

public interface IPostsNewsFeedPresenter {
    void getPostOfFriends(String userKey);

    void loadMorePost();

    void onPostsRefresh();

    void increasePostLikes(String userKey, String postKey);

    void decreasePostLikes(String userKey, String postKey);

    void setUpPostsNewsFeedPresenterCallback(IPostsNewsFeedPresenterCallback postsNewsFeedPresenterCallback);
}
