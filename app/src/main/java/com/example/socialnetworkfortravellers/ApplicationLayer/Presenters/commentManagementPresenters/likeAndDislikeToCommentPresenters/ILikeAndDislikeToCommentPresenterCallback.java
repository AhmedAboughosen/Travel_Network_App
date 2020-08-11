package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.commentManagementPresenters.likeAndDislikeToCommentPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.IPresenterCallBack;

public interface ILikeAndDislikeToCommentPresenterCallback extends IPresenterCallBack {
    void addLikeSuccessful();
    void removeLikeSuccessful();
}
