package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.commentManagementPresenters.likeAndDislikeToCommentPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.LikeModel;
import com.google.firebase.database.DatabaseReference;

public interface ILikeAndDislikeToCommentPresenter {
    void removeLike(DatabaseReference databaseReference);

    void addLike(DatabaseReference databaseReference);

    void setUpILikeAndDislikeToCommentPresenterCallback(ILikeAndDislikeToCommentPresenterCallback mILikeAndDislikeToCommentPresenterCallback);

}
