package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.commentManagementPresenters.displayCommentPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.CommentModel;

import java.util.List;

public interface IDisPlayCommentPresenterCallBack {


    void showMessageError(String message);

    void getAllComments(List<CommentModel> commentModels);

    void noInternetFound();

    void thereIsNoCommentForThisThread();
}
