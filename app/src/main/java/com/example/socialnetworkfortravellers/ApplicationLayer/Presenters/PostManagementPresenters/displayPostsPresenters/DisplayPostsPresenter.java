package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.displayPostsPresenters;

import androidx.annotation.NonNull;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.MessageErrorModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.PostModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.getPostKeyPresenters.IGetPostKeyPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.getPostKeyPresenters.IGetPostKeyPresenterCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.likeAndDisLikePresenters.ILikeAndDisLikePresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.likeAndDisLikePresenters.ILikeAndDisLikePresenterCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.getPostOfUserServices.IGetPostOfUserService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.getPostOfUserServices.IGetPostOfUserServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getUserInfoServices.IGetUserInfoService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getUserInfoServices.IGetUserInfoServiceCallBack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class DisplayPostsPresenter implements IDisplayPostsPresenter {


    private IGetPostKeyPresenter mGetPostKeyPresenter;
    private IGetPostOfUserService mGetPostOfUserService;
    private List<String> ListOfPost;
    private int startAtIndex, ITEM_LOAD_COUNT = 5;
    private IDisplayPostsPresenterCallBack mDisplayPostsPresenterCallBack;
    private String mUserKey;
    private List<PostModel> addedListOfUser;
    private HashMap<String, PostModel> savePostInMap;
    private IGetUserInfoService mGetUserInfoService;
    private UserModel mUserModel;
    private ILikeAndDisLikePresenter mLikeAndDisLikePresenter;


    public DisplayPostsPresenter(IGetPostOfUserService getPostOfUserService, IGetPostKeyPresenter getPostKeyPresenter, IGetUserInfoService getUserInfoService, ILikeAndDisLikePresenter likeAndDisLikePresenter) {
        this.mGetPostKeyPresenter = getPostKeyPresenter;
        this.mGetPostOfUserService = getPostOfUserService;
        this.mGetUserInfoService = getUserInfoService;
        this.mLikeAndDisLikePresenter = likeAndDisLikePresenter;
        ListOfPost = new ArrayList<>();
        addedListOfUser = new ArrayList<>();
        startAtIndex = 0;
        savePostInMap = new HashMap<>();
        setUpGetPostOfUserServiceCallBack();
        setUpLikeAndDisLikePresenterCallBack();
    }


    @Override
    public void getPosts(String userKey) {
        mUserKey = userKey;
        getUserInfo();
    }


    private void getUserInfo() {

        mGetUserInfoService.setUpGetUserInfoServiceCallBack(new IGetUserInfoServiceCallBack() {
            @Override
            public void userData(UserModel userModel) {
                mUserModel = userModel;
                getPost();
            }

            @Override
            public void showMessageError(String message) {
                getPost();
                MessageErrorModel messageErrorModel = new MessageErrorModel(1, "there is something wrong with profile Pic and full Name ,  we can't get correctly.\n " + message);
                mDisplayPostsPresenterCallBack.showMessageError(messageErrorModel);
            }

            @Override
            public void thisUserNotExists() {
                getPost();
                MessageErrorModel messageErrorModel = new MessageErrorModel(1, "there is something wrong with profile Pic and full Name ,  we can't get correctly.");
                mDisplayPostsPresenterCallBack.showMessageError(messageErrorModel);
            }

            @Override
            public void noInternetFound() {
                mDisplayPostsPresenterCallBack.noInternetFound();
            }
        });
        mGetUserInfoService.getUserInfo(mUserKey);
    }


    private void getPost() {
        mGetPostKeyPresenter.setUpGetPostKeyPresenterCallBack(new IGetPostKeyPresenterCallBack() {

            @Override
            public void showMessageError(@NonNull String databaseError) {
                MessageErrorModel messageErrorModel = new MessageErrorModel(1, databaseError);
                mDisplayPostsPresenterCallBack.showMessageError(messageErrorModel);
            }

            @Override
            public void networkErrorMessage() {
                mDisplayPostsPresenterCallBack.noInternetFound();
            }

            @Override
            public void sortedPost(HashMap<String, Object> map) {
                Set<String> keySet = map.keySet();

                //Creating an ArrayList of keys by passing the keySet
                ListOfPost = new ArrayList<>(keySet);

                loadMorePost();
            }

            @Override
            public void noPostsExists() {
                mDisplayPostsPresenterCallBack.thereIsNoDataToProvide();
            }
        });

        mGetPostKeyPresenter.getKeysOfPost(mUserKey);
    }


    public void loadMorePost() {
        if (startAtIndex < this.ListOfPost.size()) {
            ITEM_LOAD_COUNT = 4;
            addedListOfUser = new ArrayList<>();
            mDisplayPostsPresenterCallBack.updateLoading(true);
            mDisplayPostsPresenterCallBack.addFakeData();
            this.mGetPostOfUserService.getPost(mUserKey, this.ListOfPost.get(startAtIndex));
            startAtIndex++;

        } else {
            mDisplayPostsPresenterCallBack.listIsFinished();
        }
    }


    /**
     * setUpGetUserInfoServiceCallBack
     */
    private void setUpGetPostOfUserServiceCallBack() {
        mGetPostOfUserService.setUpGetPostOfUserServiceCallBack(new IGetPostOfUserServiceCallBack() {
            @Override
            public void newPost(PostModel postModel) {
                fetchPost(postModel);
            }

            @Override
            public void postDoesNotExists() {
                loadMorePostsWhenSomethingWrong();
            }

            @Override
            public void showMessageError(String message) {
                loadMorePostsWhenSomethingWrong();
            }

            @Override
            public void noInternetFound() {
                mDisplayPostsPresenterCallBack.noInternetFound();
            }
        });
    }


    /**
     * when get Post From firebase
     */
    private void fetchPost(PostModel postModel) {
        setUerInfoInPost(postModel);


        if (onPostChanged(postModel))
            return;


        addedListOfUser.add(postModel);
        savePostInMap.put(postModel.getPostKey(), postModel);

        if (startAtIndex < ListOfPost.size() && ITEM_LOAD_COUNT > 0) {
            mGetPostOfUserService.getPost(mUserKey, ListOfPost.get(startAtIndex));
            ++startAtIndex;
            --ITEM_LOAD_COUNT;
        } else {
            mDisplayPostsPresenterCallBack.updateLoading(false);
            mDisplayPostsPresenterCallBack.removeFakeData();
            mDisplayPostsPresenterCallBack.updatePostsList(new ArrayList<>(addedListOfUser));
            addedListOfUser = new ArrayList<>();
        }
    }


    private void setUerInfoInPost(PostModel postModel) {
        if (mUserModel != null) {
            postModel.getUserPostModel().setFullName(mUserModel.getFullName());
            postModel.getUserPostModel().setProfilePicture(mUserModel.getProfilePicture());
        } else {
            postModel.getUserPostModel().setFullName("........");
        }
    }


    private boolean onPostChanged(PostModel postModel) {
        if (savePostInMap.containsKey(postModel.getPostKey())) {
            PostModel oldPost = savePostInMap.get(postModel.getPostKey());
            mDisplayPostsPresenterCallBack.onPostChanged(postModel, oldPost);
            savePostInMap.put(postModel.getPostKey(), postModel);
            return true;
        }
        return false;
    }

    /**
     * load more user when something wrong with this user
     */
    private void loadMorePostsWhenSomethingWrong() {
        if (startAtIndex < ListOfPost.size() && ITEM_LOAD_COUNT > 0) {
            mGetPostOfUserService.getPost(mUserKey, ListOfPost.get(startAtIndex));
            ++startAtIndex;
            --ITEM_LOAD_COUNT;
        } else {

            mDisplayPostsPresenterCallBack.updateLoading(false);
            mDisplayPostsPresenterCallBack.removeFakeData();
            mDisplayPostsPresenterCallBack.updatePostsList(addedListOfUser);
        }
    }


    private void setUpLikeAndDisLikePresenterCallBack() {
        mLikeAndDisLikePresenter.setUpLikeAndDisLikePresenterCallBack(new ILikeAndDisLikePresenterCallBack() {
            @Override
            public void showMessageError(String message) {
                mDisplayPostsPresenterCallBack.showMessageError(new MessageErrorModel(0, message));

            }

            @Override
            public void networkErrorMessage() {
                mDisplayPostsPresenterCallBack.noInternetFound();

            }

            @Override
            public void addLikeSuccessful() {
                mDisplayPostsPresenterCallBack.addLikeSuccessful();
            }

            @Override
            public void removeLikeSuccessful() {
                mDisplayPostsPresenterCallBack.removeLikeSuccessful();
            }

        });
    }

    @Override
    public void increasePostLikes(String userKey, String postKey) {
        mLikeAndDisLikePresenter.increasePostLikes(userKey, postKey);
    }

    @Override
    public void decreasePostLikes(String userKey, String postKey) {
        mLikeAndDisLikePresenter.decreasePostLikes(userKey, postKey);
    }


    @Override
    public void setUpDisplayPostsPresenterCallBack(IDisplayPostsPresenterCallBack mDisplayPostsPresenterCallBack) {
        this.mDisplayPostsPresenterCallBack = mDisplayPostsPresenterCallBack;
    }
}
