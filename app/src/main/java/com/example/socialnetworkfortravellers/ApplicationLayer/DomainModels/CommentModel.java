package com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels;


import android.net.Uri;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;

import java.util.HashMap;

import javax.inject.Inject;

public class CommentModel implements Cloneable {

    private String mPostKey;
    private String mUserKey;
    private String mCommentText;
    private Uri mCommentImage;
    private UserModel mUserModel;
    private String mTime;
    private int number_of_likes;
    private boolean isHasReplies, isSend, isCurrentUserLikeComment;
    private String commentKey;
    private HashMap<String, Boolean> UsersLikesKey;
    private Long sortComment;
    private String userOfPostKey;


    @Inject
    public CommentModel() {
        mUserModel = new UserModel();
        UsersLikesKey = new HashMap<>();
    }


    public String getPostKey() {
        return mPostKey;
    }

    public void setPostKey(String mPostKey) {
        this.mPostKey = mPostKey;
    }

    public String getUserKey() {
        return mUserKey;
    }

    public void setUserKey(String mUserKey) {
        this.mUserKey = mUserKey;
    }

    public String getCommentText() {
        return mCommentText;
    }

    public void setCommentText(String mCommentText) {
        this.mCommentText = mCommentText;
    }

    public Uri getCommentImage() {
        return mCommentImage;
    }

    public void setCommentImage(Uri mCommentImage) {
        this.mCommentImage = mCommentImage;
    }

    public UserModel getUserModel() {
        return mUserModel;
    }

    public void setUserModel(UserModel mUserModel) {
        this.mUserModel = mUserModel;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String mTime) {
        this.mTime = mTime;
    }

    public int getNumber_of_likes() {
        return number_of_likes;
    }

    public void setNumber_of_likes(int number_of_likes) {
        this.number_of_likes = number_of_likes;
    }

    public boolean isHasReplies() {
        return isHasReplies;
    }

    public void setHasReplies(boolean hasReplies) {
        isHasReplies = hasReplies;
    }

    public boolean isCurrentUserLikeComment() {
        return isCurrentUserLikeComment;
    }

    public void setCurrentUserLikeComment(boolean currentUserLikeComment) {
        isCurrentUserLikeComment = currentUserLikeComment;
    }

    public String getCommentKey() {
        return commentKey;
    }

    public void setCommentKey(String commentKey) {
        this.commentKey = commentKey;
    }

    public HashMap<String, Boolean> getUsersLikesKey() {
        return UsersLikesKey;
    }

    public void setUsersLikesKey(HashMap<String, Boolean> usersLikesKey) {
        UsersLikesKey = usersLikesKey;
    }

    public Long getSortComment() {
        return sortComment;
    }

    public void setSortComment(Long sortComment) {
        this.sortComment = sortComment;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getUserOfPostKey() {
        return userOfPostKey;
    }

    public void setUserOfPostKey(String userOfPostKey) {
        this.userOfPostKey = userOfPostKey;
    }
}

