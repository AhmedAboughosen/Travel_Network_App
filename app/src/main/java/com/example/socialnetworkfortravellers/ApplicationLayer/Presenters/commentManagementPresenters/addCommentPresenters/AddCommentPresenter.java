package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.commentManagementPresenters.addCommentPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.CommentModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.commentManagementServices.addCommentServices.IAddCommentService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.commentManagementServices.addCommentServices.IAddCommentServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.commentManagementServices.addRepliesServices.IAddRepliesService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.commentManagementServices.addRepliesServices.IAddRepliesServiceCallBack;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

/**
 * this Presenter used to invock service to save comment.
 */
public class AddCommentPresenter implements IAddCommentPresenter {


    private IAddCommentService mAddCommentService;
    private IAddCommentPresenterCallBack mAddCommentPresenterCallBack;
    private IAddRepliesService mAddRepliesService;

    public AddCommentPresenter(IAddCommentService addCommentService, IAddRepliesService addRepliesService) {
        this.mAddCommentService = addCommentService;
        this.mAddRepliesService = addRepliesService;
    }


    /**
     * save comment into data base
     *
     * @param commentModel
     */
    public void saveComment(CommentModel commentModel, DatabaseReference databaseReference, StorageReference storageReference) {
        mAddCommentService.setUpAddCommentServiceCallBack(new IAddCommentServiceCallBack() {
            @Override
            public void showMessageError(String message) {
                mAddCommentPresenterCallBack.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mAddCommentPresenterCallBack.networkErrorMessage();
            }

            @Override
            public void addCommentSuccessful(CommentModel commentModel) {
                mAddCommentPresenterCallBack.saveCommentSuccessful(commentModel);
            }

        });

        mAddCommentService.saveComment(commentModel, databaseReference, storageReference);
    }


    @Override
    public void updateRepliesState(DatabaseReference databaseReference, boolean is_has_replies) {
        mAddRepliesService.setUpAddRepliesServiceCallBack(new IAddRepliesServiceCallBack() {
            @Override
            public void showMessageError(String message) {
                mAddCommentPresenterCallBack.showMessageError(message);

            }

            @Override
            public void noInternetFound() {
                mAddCommentPresenterCallBack.networkErrorMessage();

            }

            @Override
            public void addRepliesSuccessful() {
                mAddCommentPresenterCallBack.updateRepliesStateSuccessful();
            }
        });

        mAddRepliesService.updateRepliesState(databaseReference, is_has_replies);
    }

    /**
     * setUpAddCommentPresenterCallBack
     *
     * @param mAddCommentPresenterCallBack
     */
    public void setUpAddCommentPresenterCallBack(IAddCommentPresenterCallBack mAddCommentPresenterCallBack) {
        this.mAddCommentPresenterCallBack = mAddCommentPresenterCallBack;
    }
}
