package com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class PostModel implements Serializable {


    private UserModel userPostModel;
    private String locationPost;
    private ArrayList<String> imageUrl;
    private String description;
    private Object date;
    private String mPostKey;
    private boolean isTrendingUsersItem;
    private long order, newsFeedOrder;
    private HashMap<String, Boolean> mUserLikePost;
    private List<Integer> mEmptySpace;
    private long numberOfComment;


    @Inject
    public PostModel() {
        userPostModel = new UserModel();
        setTrendingUsersItem(false);
        this.imageUrl = new ArrayList<>();
        this.description = "";
        mEmptySpace = new ArrayList<>();
        mUserLikePost = new HashMap<>();
    }



    public boolean isContainDescription() {
        return description.length() != 0;
    }

    public boolean isContainImages() {

        return imageUrl.size() != 0;
    }


    public ArrayList<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(ArrayList<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getDate() {
        return date;
    }

    public void setDate(Object date) {
        this.date = date;
    }


    public boolean isTrendingUsersItem() {
        return isTrendingUsersItem;
    }

    public void setTrendingUsersItem(boolean trendingUsersItem) {
        isTrendingUsersItem = trendingUsersItem;
    }

    public void setOrder(long order) {
        this.order = order;
    }

    public long getOrder() {
        return order;
    }

    public long getNewsFeedOrder() {
        return newsFeedOrder;
    }

    public void setNewsFeedOrder(long newsFeedOrder) {
        this.newsFeedOrder = newsFeedOrder;
    }

    public String getPostKey() {
        return mPostKey;
    }

    public void setPostKey(String mPostKey) {
        this.mPostKey = mPostKey;
    }

    public UserModel getUserPostModel() {
        return userPostModel;
    }

    public void setUserPostModel(UserModel userPostModel) {
        this.userPostModel = userPostModel;
    }

    public String getLocationPost() {
        return locationPost;
    }

    public void setLocationPost(String locationPost) {
        this.locationPost = locationPost;
    }

    public HashMap<String, Boolean> getUserLikePost() {
        return mUserLikePost;
    }

    public void setUserLikePost(HashMap<String, Boolean> mUserLikePost) {
        this.mUserLikePost = mUserLikePost;
    }

    public List<Integer> getEmptySpace() {
        return mEmptySpace;
    }

    public void setEmptySpace(List<Integer> mEmptySpace) {
        this.mEmptySpace = mEmptySpace;
    }

    public long getNumberOfComment() {
        return numberOfComment;
    }

    public void setNumberOfComment(long numberOfComment) {
        this.numberOfComment = numberOfComment;
    }
}
