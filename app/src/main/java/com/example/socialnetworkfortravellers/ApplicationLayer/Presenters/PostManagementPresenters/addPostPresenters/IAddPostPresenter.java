package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.addPostPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.PostModel;

public interface IAddPostPresenter {

    void addPost(PostModel postModel);


    void setUpAddPostPresenterCallBack(IAddPostPresenterCallBack mAddPostPresneterCallBack);
}
