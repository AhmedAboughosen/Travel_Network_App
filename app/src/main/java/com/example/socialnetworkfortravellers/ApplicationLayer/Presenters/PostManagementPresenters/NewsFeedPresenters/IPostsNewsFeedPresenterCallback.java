package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.NewsFeedPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.PostModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.IPresenterCallBack;

import java.util.List;

public interface IPostsNewsFeedPresenterCallback extends IPresenterCallBack {

    void thereIsNoUserForNewsFeed();

    void listIsFinished();

    void addFakeData();



    void removeFakeData();


    void updatePostsList(List<PostModel> postModels);

    void updatePostsListRefreshMode(List<PostModel> postModels);


    void updateLoading(boolean isLoading);


    void onPostChanged(PostModel newPost, PostModel oldPost);

    void addLikeSuccessful();

    void removeLikeSuccessful();
}
