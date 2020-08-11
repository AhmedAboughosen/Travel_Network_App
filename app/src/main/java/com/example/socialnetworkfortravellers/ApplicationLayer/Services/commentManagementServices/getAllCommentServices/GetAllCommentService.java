package com.example.socialnetworkfortravellers.ApplicationLayer.Services.commentManagementServices.getAllCommentServices;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.CommentModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseSingleValueService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseSingleValueServiceCallBack;
import com.example.socialnetworkfortravellers.utilLayer.ConvertTimeUtil;
import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.socialnetworkfortravellers.nodesLayer.CommentNode.IMAGE_COMMENT;
import static com.example.socialnetworkfortravellers.nodesLayer.CommentNode.IS_HAS_REPLIES;
import static com.example.socialnetworkfortravellers.nodesLayer.CommentNode.LIKES;
import static com.example.socialnetworkfortravellers.nodesLayer.CommentNode.POST_KEY;
import static com.example.socialnetworkfortravellers.nodesLayer.CommentNode.TEXT_COMMENT;
import static com.example.socialnetworkfortravellers.nodesLayer.CommentNode.TIMESTAMP;
import static com.example.socialnetworkfortravellers.nodesLayer.CommentNode.USER_ID;

/**
 * this service used to get all comments from database
 */
public class GetAllCommentService implements IGetAllCommentService {


    private IGetDataByUseSingleValueService mGetDataByUseSingleValueService;
    private List<CommentModel> mListOfComment;
    private IGetAllCommentServiceCallBack mGetAllCommentServiceCallBack;

    public GetAllCommentService(IGetDataByUseSingleValueService getDataByUseSingleValueService) {
        this.mGetDataByUseSingleValueService = getDataByUseSingleValueService;
        mListOfComment = new ArrayList<>();
    }


    @Override
    public void getComments(DatabaseReference databaseReference) {

        mGetDataByUseSingleValueService.setUpDatabaseReference(databaseReference);
        mGetDataByUseSingleValueService.setUpGetDataServiceCallBack(new IGetDataByUseSingleValueServiceCallBack() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mGetAllCommentServiceCallBack.getAllComments(paresComment(dataSnapshot));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mGetAllCommentServiceCallBack.onCancelled(databaseError.getMessage());
            }

            @Override
            public void noInternetFound() {
                mGetAllCommentServiceCallBack.noInternetFound();
            }
        });

        mGetDataByUseSingleValueService.getData();
    }


    private List<CommentModel> paresComment(DataSnapshot dataSnapshot) {

        try {
            if (dataSnapshot.hasChildren()) {
                for (DataSnapshot dataSnapshotComments : dataSnapshot.getChildren()) {

                    CommentModel commentModel = new CommentModel();
                    commentModel.setCommentKey(dataSnapshotComments.getKey());

                    if (dataSnapshotComments.hasChild(POST_KEY)) {
                        commentModel.setPostKey(dataSnapshotComments.child(POST_KEY).getValue().toString());
                    }


                    if (dataSnapshotComments.hasChild(IMAGE_COMMENT)) {
                        commentModel.setCommentImage(Uri.parse(dataSnapshotComments.child(IMAGE_COMMENT).getValue().toString()));
                    }

                    if (dataSnapshotComments.hasChild(TEXT_COMMENT)) {
                        commentModel.setCommentText(dataSnapshotComments.child(TEXT_COMMENT).getValue().toString());
                    }
                    if (dataSnapshotComments.hasChild(TIMESTAMP)) {

                        commentModel.setTime(dataSnapshotComments.child(TIMESTAMP).getValue().toString());
                        commentModel.setSortComment(ConvertTimeUtil.toTimeStampToSeconds(commentModel.getTime()));
                    }
                    if (dataSnapshotComments.hasChild(USER_ID)) {
                        commentModel.setUserKey(dataSnapshotComments.child(USER_ID).getValue().toString());
                    }

                    if (dataSnapshotComments.hasChild(IS_HAS_REPLIES)) {
                        commentModel.setHasReplies((Boolean) dataSnapshotComments.child(IS_HAS_REPLIES).getValue());
                    }

                    if (dataSnapshotComments.hasChild(LIKES)) {
                        DataSnapshot userLikes = dataSnapshotComments.child(LIKES);
                        HashMap<String, Boolean> usersKey = new HashMap<>();

                        if (userLikes.hasChildren()) {
                            for (DataSnapshot users : userLikes.getChildren()) {
                                usersKey.put(users.getKey() + "", true);
                            }
                        }
                        commentModel.setNumber_of_likes(usersKey.size());
                        commentModel.setUsersLikesKey(usersKey);
                    }

                    //this code used to check if current user like comment or not
                    if (commentModel.getUsersLikesKey() != null && commentModel.getUsersLikesKey().containsKey(CurrentUserIDUtil.getInstance().getCurrentUserID())) {
                        commentModel.setCurrentUserLikeComment(true);
                    }

                    mListOfComment.add(commentModel);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return mListOfComment;
    }

    @Override
    public void setGetAllCommentServiceCallBack(IGetAllCommentServiceCallBack mGetAllCommentServiceCallBack) {
        this.mGetAllCommentServiceCallBack = mGetAllCommentServiceCallBack;
    }
}
