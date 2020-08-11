package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.displayPostsPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.MessageErrorModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.PostModel;

import java.util.List;

public interface IDisplayPostsPresenterCallBack {
    void thereIsNoDataToProvide();

    void listIsFinished();

    void addFakeData();


    void removeFakeData();


    void updatePostsList(List<PostModel> postModels);


    void updateLoading(boolean isLoading);


    void showMessageError(MessageErrorModel message);

    void noInternetFound();

    void onPostChanged(PostModel newPost, PostModel oldPost);

    void addLikeSuccessful();

    void removeLikeSuccessful();
}
