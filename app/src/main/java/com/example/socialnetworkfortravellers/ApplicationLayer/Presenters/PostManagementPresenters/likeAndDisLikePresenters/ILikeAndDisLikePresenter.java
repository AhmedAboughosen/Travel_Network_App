package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.likeAndDisLikePresenters;

public interface ILikeAndDisLikePresenter {

    void setUpLikeAndDisLikePresenterCallBack(ILikeAndDisLikePresenterCallBack mLikeAndDisLikePresenterCallBack);

    void increasePostLikes(String userKey, String postKey);

    void decreasePostLikes(String userKey, String postKey);
}
