package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.commentManagementPresenters.likeAndDislikeToCommentPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.commentManagementServices.addDisLikeToCommentService.IAddDisLikeToCommentService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.commentManagementServices.addDisLikeToCommentService.IAddDisLikeToCommentServiceCallback;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.commentManagementServices.addLikeToCommentServices.IAddLikeToCommentService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.commentManagementServices.addLikeToCommentServices.IAddLikeToCommentServiceCallback;
import com.google.firebase.database.DatabaseReference;

public class LikeAndDislikeToCommentPresenter implements ILikeAndDislikeToCommentPresenter {

    private ILikeAndDislikeToCommentPresenterCallback mILikeAndDislikeToCommentPresenterCallback;
    private IAddLikeToCommentService mAddLikeToCommentService;
    private IAddDisLikeToCommentService mAddDisLikeToCommentService;


    public LikeAndDislikeToCommentPresenter(IAddLikeToCommentService addLikeToCommentService, IAddDisLikeToCommentService addDisLikeToCommentService) {
        this.mAddLikeToCommentService = addLikeToCommentService;
        this.mAddDisLikeToCommentService = addDisLikeToCommentService;
    }


    /**
     * add like to comment
     *
     * @param databaseReference
     */
    @Override
    public void addLike(DatabaseReference databaseReference) {
        mAddLikeToCommentService.setUpAddLikeToCommentServiceCallback(new IAddLikeToCommentServiceCallback() {
            @Override
            public void showMessageError(String message) {
                mILikeAndDislikeToCommentPresenterCallback.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mILikeAndDislikeToCommentPresenterCallback.networkErrorMessage();
            }

            @Override
            public void addLikeToCommentSuccessful() {
                mILikeAndDislikeToCommentPresenterCallback.addLikeSuccessful();
            }

        });

        mAddLikeToCommentService.addLikeToComment(databaseReference);
    }


    /**
     * remove like from comment.
     *
     * @param databaseReference
     */
    @Override
    public void removeLike(DatabaseReference databaseReference) {
        mAddDisLikeToCommentService.setUpAddDisLikeToCommentService(new IAddDisLikeToCommentServiceCallback() {
            @Override
            public void showMessageError(String message) {
                mILikeAndDislikeToCommentPresenterCallback.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mILikeAndDislikeToCommentPresenterCallback.networkErrorMessage();
            }

            @Override
            public void removeLikeSuccessful() {
                mILikeAndDislikeToCommentPresenterCallback.removeLikeSuccessful();
            }

        });

        mAddDisLikeToCommentService.addDisLikeToComment(databaseReference);
    }

    @Override
    public void setUpILikeAndDislikeToCommentPresenterCallback(ILikeAndDislikeToCommentPresenterCallback mILikeAndDislikeToCommentPresenterCallback) {
        this.mILikeAndDislikeToCommentPresenterCallback = mILikeAndDislikeToCommentPresenterCallback;
    }
}
