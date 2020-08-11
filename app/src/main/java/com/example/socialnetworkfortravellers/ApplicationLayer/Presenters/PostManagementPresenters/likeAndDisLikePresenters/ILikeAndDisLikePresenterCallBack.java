package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.likeAndDisLikePresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.IPresenterCallBack;

public interface ILikeAndDisLikePresenterCallBack extends IPresenterCallBack {
    void addLikeSuccessful();
    void removeLikeSuccessful();
}
