package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.deletePostPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.deletePostServices.IDeleteImageOfPostService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.deletePostServices.IDeleteImageOfPostServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.deletePostServices.IDeletePostService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.deletePostServices.IDeletePostServiceCallBack;

public class DeletePostPresenter implements IDeletePostPresenter {


    private IDeletePostService mDeletePostService;
    private IDeletePostPresenterCallBack mDeletePostPresenterCallBack;
    private IDeleteImageOfPostService mDeleteImageOfPostService;
    private String mUserKey, mPostKey;

    public DeletePostPresenter(IDeletePostService deletePostService, IDeleteImageOfPostService deleteImageOfPostService) {
        this.mDeletePostService = deletePostService;
        this.mDeleteImageOfPostService = deleteImageOfPostService;
    }


    @Override
    public void deletePost(String userKey, String postKey) {
        this.mUserKey = userKey;
        this.mPostKey = postKey;

        removePostData();
    }

    private void removePostData() {
        mDeletePostService.setUpDeletePostServiceCallBack(new IDeletePostServiceCallBack() {
            @Override
            public void showMessageError(String message) {
                mDeletePostPresenterCallBack.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mDeletePostPresenterCallBack.networkErrorMessage();
            }

            @Override
            public void deletePostSuccessful() {
                deleteImageOfPost();
            }

        });
        mDeletePostService.deletePost(mPostKey, mUserKey);
    }


    private void deleteImageOfPost() {
        mDeleteImageOfPostService.setDeleteImageOfPostServiceCallBack(new IDeleteImageOfPostServiceCallBack() {
            @Override
            public void deleteImageOfPostSuccessful() {
                mDeletePostPresenterCallBack.deletePostSuccessful();
            }

            @Override
            public void showMessageError(String message) {
                mDeletePostPresenterCallBack.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mDeletePostPresenterCallBack.networkErrorMessage();
            }
        });
        mDeleteImageOfPostService.deleteImageOfPost(mUserKey, mPostKey);
    }


    @Override
    public void setDeletePostPresenterCallBack(IDeletePostPresenterCallBack mDeletePostPresenterCallBack) {
        this.mDeletePostPresenterCallBack = mDeletePostPresenterCallBack;
    }
}
