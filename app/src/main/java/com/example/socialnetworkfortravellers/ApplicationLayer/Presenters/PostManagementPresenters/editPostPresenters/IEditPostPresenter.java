package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.editPostPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.PostModel;

import java.util.List;

public interface IEditPostPresenter {
    void setUpEditPostPresenterCallBack(IEditPostPresenterCallBack mEditPostPresenterCallBack);

    void updatePost( PostModel postModel, List<Integer> deletedImage);
}
