package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.commentManagementPresenters.addCommentPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.CommentModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.IPresenterCallBack;

public interface IAddCommentPresenterCallBack extends IPresenterCallBack {

    void saveCommentSuccessful(CommentModel commentObj);

    void updateRepliesStateSuccessful();
}
