package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.deletePostPresenters;

public interface IDeletePostPresenter {
    void setDeletePostPresenterCallBack(IDeletePostPresenterCallBack mDeletePostPresenterCallBack);
    void deletePost(String userKey, String postKey);

}
