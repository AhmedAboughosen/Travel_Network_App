package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.getPostKeyPresenters;

public interface IGetPostKeyPresenter {
    void getKeysOfPost(String userKey);

    void setUpGetPostKeyPresenterCallBack(IGetPostKeyPresenterCallBack mGetPostKeyPresenterCallBack);
}
