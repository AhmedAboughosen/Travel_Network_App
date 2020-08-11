package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.commentManagementPresenters.displayCommentPresenters;

import androidx.annotation.NonNull;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.CommentModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.commentManagementServices.getAllCommentServices.IGetAllCommentService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.commentManagementServices.getAllCommentServices.IGetAllCommentServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getUserInfoServices.IGetUserInfoService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getUserInfoServices.IGetUserInfoServiceCallBack;
import com.google.firebase.database.DatabaseReference;

import java.util.Collections;
import java.util.List;

public class DisplayCommentPresenter implements IDisplayCommentPresenter {


    private IGetAllCommentService mGetAllCommentService;
    private IDisPlayCommentPresenterCallBack mDisPlayCommentPresenterCallBack;
    private List<CommentModel> list_of_comments;
    private IGetUserInfoService mGetUserInfoService;
    private int currentUserIndex = 0;

    public DisplayCommentPresenter(IGetAllCommentService getAllCommentService, IGetUserInfoService getUserInfoService) {
        this.mGetAllCommentService = getAllCommentService;
        this.mGetUserInfoService = getUserInfoService;
    }


    /**
     * get all comment of this post
     *
     */
    @Override
    public void getAllComments( DatabaseReference databaseReference) {

        mGetAllCommentService.setGetAllCommentServiceCallBack(new IGetAllCommentServiceCallBack() {
            @Override
            public void getAllComments(List<CommentModel> commentModels) {
                list_of_comments = commentModels;
                if (list_of_comments.size() == 0) {
                    mDisPlayCommentPresenterCallBack.thereIsNoCommentForThisThread();
                } else {
                    sortCommentBasedOnDate();
                }
            }


            @Override
            public void onCancelled(@NonNull String databaseError) {
                mDisPlayCommentPresenterCallBack.showMessageError(databaseError);
            }

            @Override
            public void noInternetFound() {
                mDisPlayCommentPresenterCallBack.noInternetFound();
            }
        });

        mGetAllCommentService.getComments(databaseReference);
    }


    /**
     * this method used to sort comment based on date
     */
    private void sortCommentBasedOnDate() {
        Collections.sort(list_of_comments, (o1, o2) -> o1.getSortComment().compareTo(o2.getSortComment()));
        getUserForEachComment();
    }


    /**
     * get user data for each comment.
     */
    private void getUserForEachComment() {

        mGetUserInfoService.setUpGetUserInfoServiceCallBack(new IGetUserInfoServiceCallBack() {
            @Override
            public void userData(UserModel mUserModel) {
                list_of_comments.get(currentUserIndex).setUserModel(mUserModel);
                ++currentUserIndex;
                if (list_of_comments.size() > currentUserIndex) {
                    mGetUserInfoService.getUserInfo(list_of_comments.get(currentUserIndex).getUserKey());
                } else {
                    mDisPlayCommentPresenterCallBack.getAllComments(list_of_comments);
                }
            }

            @Override
            public void showMessageError(String message) {
                whenSomeThingWrong();
            }

            @Override
            public void thisUserNotExists() {
                whenSomeThingWrong();
            }

            @Override
            public void noInternetFound() {
                mDisPlayCommentPresenterCallBack.noInternetFound();
            }
        });

        mGetUserInfoService.getUserInfo(list_of_comments.get(currentUserIndex).getUserKey());
    }


    /**
     * sometimes we can't load user data or data is not exists , in this case , we should load other data , we can't stop.
     */
    private void whenSomeThingWrong() {
        ++currentUserIndex;
        if (list_of_comments.size() > currentUserIndex) {
            mGetUserInfoService.getUserInfo(list_of_comments.get(currentUserIndex).getUserKey());
        } else {
            mDisPlayCommentPresenterCallBack.getAllComments(list_of_comments);
        }
    }

    @Override
    public void setDisPlayCommentPresenter(IDisPlayCommentPresenterCallBack mDisPlayCommentPresenter) {
        this.mDisPlayCommentPresenterCallBack = mDisPlayCommentPresenter;
    }
}
