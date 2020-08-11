package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.commentManagementPresenters.displayCommentPresenters;

import com.google.firebase.database.DatabaseReference;

public interface IDisplayCommentPresenter {
    void setDisPlayCommentPresenter(IDisPlayCommentPresenterCallBack mDisPlayCommentPresenter);

    void getAllComments( DatabaseReference databaseReference );
}
