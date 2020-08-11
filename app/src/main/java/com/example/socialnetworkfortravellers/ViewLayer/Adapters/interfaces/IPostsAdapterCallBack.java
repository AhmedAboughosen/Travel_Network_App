package com.example.socialnetworkfortravellers.ViewLayer.Adapters.interfaces;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.PostModel;

import java.util.List;

public interface IPostsAdapterCallBack {


    void userLikeThisPost(String userKey , String postKey, int position);

    void numberOfLikesClick(List<String> list);

    void userDisLikeThisPost(String userKey , String postKey, int position);

    void onOverFlowButtonClick(PostModel postModel, int position);

    void onCommentClick(PostModel postModel);
    void onProfileImageClick(String  key);
}
