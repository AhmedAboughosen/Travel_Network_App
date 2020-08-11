package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.commentManagementPresenters.addCommentPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.CommentModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

public interface IAddCommentPresenter {

    void updateRepliesState(DatabaseReference databaseReference, boolean is_has_replies);

    void saveComment(CommentModel commentModel, DatabaseReference databaseReference, StorageReference storageReference);

    void setUpAddCommentPresenterCallBack(IAddCommentPresenterCallBack mAddCommentPresenterCallBack);
}
