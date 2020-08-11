package com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.deletePostServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.removeDataServices.IRemoveDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.removeDataServices.IRemoveDataServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.updateNumberOfPostsServices.IUpdateNumberOfPostsService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.updateNumberOfPostsServices.IUpdateNumberOfPostsServiceCallBack;
import com.example.socialnetworkfortravellers.nodesLayer.CommentNode;
import com.example.socialnetworkfortravellers.nodesLayer.PostNode;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeletePostService implements IDeletePostService {


    private IDeletePostServiceCallBack mDeletePostServiceCallBack;
    private IRemoveDataService mRemoveDataService;
    private String mPostKey, mUserKey;
    private IUpdateNumberOfPostsService mUpdateNumberOfPostsService;

    public DeletePostService(IRemoveDataService removeDataService, IUpdateNumberOfPostsService updateNumberOfPostsService) {
        this.mRemoveDataService = removeDataService;
        this.mUpdateNumberOfPostsService = updateNumberOfPostsService;

    }

    @Override
    public void setUpDeletePostServiceCallBack(IDeletePostServiceCallBack mDeletePostServiceCallBack) {
        this.mDeletePostServiceCallBack = mDeletePostServiceCallBack;
    }


    @Override
    public void deletePost(String postKey, String userKey) {
        this.mPostKey = postKey;
        this.mUserKey = userKey;

        removePost();
    }


    private void removePost() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(PostNode.POST).child(mUserKey).child(mPostKey);
        mRemoveDataService.setUpDatabaseReference(databaseReference);
        mRemoveDataService.setUpRemoveDataServiceCallBack(new IRemoveDataServiceCallBack() {
            @Override
            public void failure(String message) {
                mDeletePostServiceCallBack.showMessageError(message);
            }

            @Override
            public void isSuccessful() {
                removeKeyPost();
            }
        });

        mRemoveDataService.removeData();
    }

    private void removeKeyPost() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(PostNode.KEYS_POST).child(mUserKey).child(mPostKey);
        mRemoveDataService.setUpDatabaseReference(databaseReference);
        mRemoveDataService.setUpRemoveDataServiceCallBack(new IRemoveDataServiceCallBack() {
            @Override
            public void failure(String message) {
                mDeletePostServiceCallBack.showMessageError(message);
            }

            @Override
            public void isSuccessful() {
                removeCommentRelatedToPost();
            }
        });

        mRemoveDataService.removeData();
    }

    private void removeCommentRelatedToPost() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(CommentNode.COMMENT_POST).child(mPostKey);
        mRemoveDataService.setUpDatabaseReference(databaseReference);
        mRemoveDataService.setUpRemoveDataServiceCallBack(new IRemoveDataServiceCallBack() {
            @Override
            public void failure(String message) {
                mDeletePostServiceCallBack.showMessageError(message);
            }

            @Override
            public void isSuccessful() {
                decreaseNumberOfPost();
            }
        });

        mRemoveDataService.removeData();
    }

    private void decreaseNumberOfPost() {

        mUpdateNumberOfPostsService.setUpUpdateNumberOfPostsServiceCallBack(new IUpdateNumberOfPostsServiceCallBack() {
            @Override
            public void showMessageError(String message) {
                mDeletePostServiceCallBack.showMessageError("we can't remove this post , please try later.");

            }

            @Override
            public void noInternetFound() {
                mDeletePostServiceCallBack.noInternetFound();
            }

            @Override
            public void updateNumberOfPostsSuccessful() {
                mDeletePostServiceCallBack.deletePostSuccessful();
            }

        });

        mUpdateNumberOfPostsService.updateNumberOfPost(mUserKey, false);
    }
}
