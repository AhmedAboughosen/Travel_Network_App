package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.NewsFeedPresenters;

import androidx.annotation.NonNull;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.PostModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.likeAndDisLikePresenters.ILikeAndDisLikePresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.likeAndDisLikePresenters.ILikeAndDisLikePresenterCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.getLastPostKeyServices.IGetPostKeyService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.getLastPostKeyServices.IGetPostKeyServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.getPostOfUserServices.IGetPostOfUserService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.getPostOfUserServices.IGetPostOfUserServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getFriendsOfUserServices.IGetFriendsOfUserService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getFriendsOfUserServices.IGetFriendsOfUserServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getListOfUserServices.IGetListOfUserService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getListOfUserServices.IGetListOfUserServiceCallback;
import com.example.socialnetworkfortravellers.utilLayer.StringConfigUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PostsNewsFeedPresenter implements IPostsNewsFeedPresenter {
    private IGetFriendsOfUserService mGetFriendsOfUserService;
    private IGetListOfUserService mGetListOfUserForNewsFeedService;
    private IPostsNewsFeedPresenterCallback mPostsNewsFeedPresenterCallback;
    private IGetPostKeyService mGetPostKeyService;
    private List<UserModel> list_of_user;
    private int postKeyIndex;
    private HashMap<String, Integer> mappingPostsAndUser;
    private HashMap<String, Object> mappingPostsWithDate;
    private List<String> ListOfPostKeys, ListOfPostKeysRefreshMode;
    private List<PostModel> ListOfPostModel, ListOfPostModelRefreshMode;
    private IGetPostOfUserService mGetPostOfUserService;
    private int startAtIndex, ITEM_LOAD_COUNT = 4, startAtIndexRefreshMode;
    private HashMap<String, PostModel> savePostInMap;
    private IGetPostOfUserService mGetPostOfUserRefreshModeService;
    private ILikeAndDisLikePresenter mLikeAndDisLikePresenter;


    public PostsNewsFeedPresenter(IGetFriendsOfUserService getFriendsOfUserService, IGetListOfUserService getListOfUserForNewsFeedService, IGetPostKeyService getPostKeyService
            , IGetPostOfUserService getPostOfUserService, IGetPostOfUserService getPostOfUserRefreshModeService
            , ILikeAndDisLikePresenter likeAndDisLikePresenter) {

        this.mGetFriendsOfUserService = getFriendsOfUserService;
        this.mGetListOfUserForNewsFeedService = getListOfUserForNewsFeedService;
        this.mGetPostKeyService = getPostKeyService;
        this.mLikeAndDisLikePresenter = likeAndDisLikePresenter;
        this.mGetPostOfUserService = getPostOfUserService;
        this.mGetPostOfUserRefreshModeService = getPostOfUserRefreshModeService;
        mappingPostsAndUser = new HashMap<>();
        ListOfPostModel = new ArrayList<>();
        mappingPostsWithDate = new HashMap<>();
        savePostInMap = new HashMap<>();
        ListOfPostModelRefreshMode = new ArrayList<>();
        ListOfPostKeysRefreshMode = new ArrayList<>();
        setUpGetFollowingDataServiceCallBack();
        setUpGetListOfUserServiceCallback();
        setUpGetPostOfUserRefreshModeServiceCallBack();
        setGetLastPostKeyServiceCallBack();
        setUpGetPostOfUserServiceCallBack();
        setUpLikeAndDisLikePresenterCallBack();
    }


    @Override
    public void setUpPostsNewsFeedPresenterCallback(IPostsNewsFeedPresenterCallback postsNewsFeedPresenterCallback) {
        this.mPostsNewsFeedPresenterCallback = postsNewsFeedPresenterCallback;
    }


    @Override
    public void getPostOfFriends(String userKey) {
        mGetFriendsOfUserService.getFriendsOfUser(userKey);
    }

    private void setUpGetFollowingDataServiceCallBack() {
        mGetFriendsOfUserService.setUpGetFollowingDataServiceCallBack(new IGetFriendsOfUserServiceCallBack() {
            @Override
            public void getFriends(HashMap<String, String> map) {
                Set<String> keySet = map.keySet();
                getUsersObjects(new ArrayList<>(keySet));
            }

            @Override
            public void CurrentUserDoesNotHaveFriends() {
                mPostsNewsFeedPresenterCallback.thereIsNoUserForNewsFeed();
            }

            @Override
            public void showMessageError(String message) {
                mPostsNewsFeedPresenterCallback.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mPostsNewsFeedPresenterCallback.networkErrorMessage();
            }
        });
    }


    private void getUsersObjects(List<String> list_of_users) {
        mGetListOfUserForNewsFeedService.getUsers(list_of_users);
    }

    private void setUpGetListOfUserServiceCallback() {
        mGetListOfUserForNewsFeedService.setUpGetListOfUserServiceCallback(new IGetListOfUserServiceCallback() {
            @Override
            public void ListOfUsers(List<UserModel> userModels) {
                list_of_user = new ArrayList<>(userModels);
                getKeysOfPost();
            }

            @Override
            public void internetIsNotConnected() {
                mPostsNewsFeedPresenterCallback.networkErrorMessage();
            }
        });
    }

    private void getKeysOfPost() {
        mGetPostKeyService.getKeysOfPost(list_of_user.get(postKeyIndex).getUserInfoModel().getKeyOfUser());
        postKeyIndex++;
    }


    private void setGetLastPostKeyServiceCallBack() {
        mGetPostKeyService.setGetLastPostKeyServiceCallBack(new IGetPostKeyServiceCallBack() {
            @Override
            public void postKeys(HashMap<String, Object> map) {
                savePostKeyWithUserIndex(map);
                mappingPostsWithDate.putAll(map);

                if (postKeyIndex < list_of_user.size()) {
                    getKeysOfPost();
                } else {
                    mappingPostsWithDate = sortPostKeyBasedOnDate(mappingPostsWithDate);
                    Set<String> keySet = mappingPostsWithDate.keySet();

                    //Creating an ArrayList of keys by passing the keySet
                    ListOfPostKeys = new ArrayList<>(keySet);
                    loadMorePost();
                }
            }

            @Override
            public void showMessageError(@NonNull String databaseError) {
                if (postKeyIndex < list_of_user.size()) {
                    getKeysOfPost();
                } else {
                    mappingPostsWithDate = sortPostKeyBasedOnDate(mappingPostsWithDate);
                    Set<String> keySet = mappingPostsWithDate.keySet();

                    //Creating an ArrayList of keys by passing the keySet
                    ListOfPostKeys = new ArrayList<>(keySet);
                    loadMorePost();
                }

                mPostsNewsFeedPresenterCallback.showMessageError(databaseError);
            }

            @Override
            public void noInternetFound() {
                mPostsNewsFeedPresenterCallback.networkErrorMessage();
            }
        });
    }


    private void savePostKeyWithUserIndex(HashMap<String, Object> map) {
        for (String key : map.keySet()) {
            mappingPostsAndUser.put(key, postKeyIndex - 1);
        }
    }


    // function to sort hashmap by values
    private HashMap<String, Object> sortPostKeyBasedOnDate(HashMap<String, Object> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Object>> list =
                new LinkedList<>(hm.entrySet());

        // Sort the list
        Collections.sort(list, (o1, o2) -> ((Long) o1.getValue()).compareTo((Long) o2.getValue()));

        // put data from sorted list to hashmap
        HashMap<String, Object> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Object> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }


    public void loadMorePost() {
        try {
            if (startAtIndex < this.ListOfPostKeys.size()) {
                ITEM_LOAD_COUNT = 4;
                ListOfPostModel = new ArrayList<>();
                mPostsNewsFeedPresenterCallback.updateLoading(true);
                mPostsNewsFeedPresenterCallback.addFakeData();
                String postKey = this.ListOfPostKeys.get(startAtIndex);
                int userIndex = mappingPostsAndUser.get(postKey);

                String userKey = list_of_user.get(userIndex).getUserInfoModel().getKeyOfUser();

                this.mGetPostOfUserService.getPost(userKey, postKey);
                startAtIndex++;
            } else {
                mPostsNewsFeedPresenterCallback.listIsFinished();
            }
        } catch (Exception ex) {
            mPostsNewsFeedPresenterCallback.showMessageError(StringConfigUtil.MESSAGE_PROBLEM);
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
                mPostsNewsFeedPresenterCallback.networkErrorMessage();
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


        if (startAtIndex == 4) {
            ListOfPostModel.add(postModel);
            ListOfPostModel.add(postModel);
        } else {
            ListOfPostModel.add(postModel);
        }
        savePostInMap.put(postModel.getPostKey(), postModel);


        if (startAtIndex < ListOfPostKeys.size() && ITEM_LOAD_COUNT > 0) {

            String postKey = this.ListOfPostKeys.get(startAtIndex);
            int userIndex = mappingPostsAndUser.get(postKey);
            String userKey = list_of_user.get(userIndex).getUserInfoModel().getKeyOfUser();

            mGetPostOfUserService.getPost(userKey, postKey);
            ++startAtIndex;
            --ITEM_LOAD_COUNT;
        } else {

            mPostsNewsFeedPresenterCallback.updateLoading(false);
            mPostsNewsFeedPresenterCallback.removeFakeData();
            mPostsNewsFeedPresenterCallback.updatePostsList(new ArrayList<>(ListOfPostModel));
            ListOfPostModel = new ArrayList<>();
        }
    }


    private void setUerInfoInPost(PostModel postModel) {
        try {

            int userIndex;
            UserModel mUserModel;
            userIndex = mappingPostsAndUser.get(postModel.getPostKey());
            mUserModel = list_of_user.get(userIndex);

            if (mUserModel != null) {
                postModel.getUserPostModel().setFullName(mUserModel.getFullName());
                postModel.getUserPostModel().setProfilePicture(mUserModel.getProfilePicture());
            } else {
                postModel.getUserPostModel().setFullName("........");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            mPostsNewsFeedPresenterCallback.showMessageError(StringConfigUtil.MESSAGE_PROBLEM);
        }

    }


    private boolean onPostChanged(PostModel postModel) {
        if (savePostInMap.containsKey(postModel.getPostKey())) {
            PostModel oldPost = savePostInMap.get(postModel.getPostKey());
            mPostsNewsFeedPresenterCallback.onPostChanged(postModel, oldPost);
            savePostInMap.put(postModel.getPostKey(), postModel);
            return true;
        }
        return false;
    }

    /**
     * load more user when something wrong with this user
     */
    private void loadMorePostsWhenSomethingWrong() {
        if (startAtIndex < ListOfPostKeys.size() && ITEM_LOAD_COUNT > 0) {
            String postKey = this.ListOfPostKeys.get(startAtIndex);
            int userIndex = mappingPostsAndUser.get(postKey);
            String userKey = list_of_user.get(userIndex).getUserInfoModel().getKeyOfUser();

            mGetPostOfUserService.getPost(userKey, postKey);
            ++startAtIndex;
            --ITEM_LOAD_COUNT;
        } else {
            mPostsNewsFeedPresenterCallback.updateLoading(false);
            mPostsNewsFeedPresenterCallback.removeFakeData();
            mPostsNewsFeedPresenterCallback.updatePostsList(new ArrayList<>(ListOfPostModel));
            ListOfPostModel = new ArrayList<>();
        }
    }


    /**
     * on Posts Refresh
     */
    public void onPostsRefresh() {
        postKeyIndex = 0;
        startAtIndexRefreshMode = 0;
        mappingPostsWithDate = new HashMap<>();
        setUpGetListOfUserServicePostsRefreshCallback();
        if (list_of_user.size() > 0) {
            mGetPostKeyService.getKeysOfPost(list_of_user.get(postKeyIndex).getUserInfoModel().getKeyOfUser());
            postKeyIndex++;
        }
    }


    public void loadMoreOnRefreshPost() {
        try {
            if (startAtIndexRefreshMode < this.ListOfPostKeysRefreshMode.size()) {
                ITEM_LOAD_COUNT = 4;
                mPostsNewsFeedPresenterCallback.updateLoading(true);
                String postKey = this.ListOfPostKeysRefreshMode.get(startAtIndexRefreshMode);
                int userIndex = mappingPostsAndUser.get(postKey);

                String userKey = list_of_user.get(userIndex).getUserInfoModel().getKeyOfUser();

                this.mGetPostOfUserRefreshModeService.getPost(userKey, postKey);
                startAtIndexRefreshMode++;
            }
        } catch (Exception ex) {
            mPostsNewsFeedPresenterCallback.showMessageError(StringConfigUtil.MESSAGE_PROBLEM);
        }
    }


    private void setUpGetListOfUserServicePostsRefreshCallback() {
        mGetPostKeyService.setGetLastPostKeyServiceCallBack(new IGetPostKeyServiceCallBack() {
            @Override
            public void postKeys(HashMap<String, Object> map) {

                for (String key : map.keySet()) {
                    if (!mappingPostsAndUser.containsKey(key)) {
                        mappingPostsWithDate.put(key, map.get(key));
                        mappingPostsAndUser.put(key, postKeyIndex - 1);
                    }
                }

                if (postKeyIndex < list_of_user.size()) {
                    getKeysOfPost();
                } else {
                    mappingPostsWithDate = sortPostKeyBasedOnDate(mappingPostsWithDate);
                    Set<String> keySet = mappingPostsWithDate.keySet();

                    //Creating an ArrayList of keys by passing the keySet
                    ListOfPostKeysRefreshMode = new ArrayList<>(keySet);
                    loadMoreOnRefreshPost();
                }
            }

            @Override
            public void showMessageError(@NonNull String databaseError) {
                if (postKeyIndex < list_of_user.size()) {
                    getKeysOfPost();
                } else {
                    mappingPostsWithDate = sortPostKeyBasedOnDate(mappingPostsWithDate);
                    Set<String> keySet = mappingPostsWithDate.keySet();

                    //Creating an ArrayList of keys by passing the keySet
                    ListOfPostKeysRefreshMode = new ArrayList<>(keySet);
                    loadMoreOnRefreshPost();
                }

                mPostsNewsFeedPresenterCallback.showMessageError(databaseError);
            }

            @Override
            public void noInternetFound() {
                mPostsNewsFeedPresenterCallback.networkErrorMessage();
            }
        });
    }


    private void setUpGetPostOfUserRefreshModeServiceCallBack() {
        mGetPostOfUserRefreshModeService.setUpGetPostOfUserServiceCallBack(new IGetPostOfUserServiceCallBack() {
            @Override
            public void newPost(PostModel postModel) {
                fetchPostRefreshMode(postModel);
            }

            @Override
            public void postDoesNotExists() {
                loadMorePostsWhenSomethingWrongRefreshMode();
            }

            @Override
            public void showMessageError(String message) {
                loadMorePostsWhenSomethingWrongRefreshMode();
            }

            @Override
            public void noInternetFound() {
                mPostsNewsFeedPresenterCallback.networkErrorMessage();
            }
        });
    }

    /**
     * when get Post From firebase
     */
    private void fetchPostRefreshMode(PostModel postModel) {

        setUerInfoInPost(postModel);

        if (onPostChanged(postModel))
            return;

        if (startAtIndexRefreshMode == 4) {
            ListOfPostModelRefreshMode.add(postModel);
            ListOfPostModelRefreshMode.add(postModel);
        } else {
            ListOfPostModelRefreshMode.add(postModel);
        }

        savePostInMap.put(postModel.getPostKey(), postModel);

        if (startAtIndexRefreshMode < ListOfPostKeysRefreshMode.size()) {

            String postKey = this.ListOfPostKeysRefreshMode.get(startAtIndexRefreshMode);
            int userIndex = mappingPostsAndUser.get(postKey);
            String userKey = list_of_user.get(userIndex).getUserInfoModel().getKeyOfUser();

            mGetPostOfUserRefreshModeService.getPost(userKey, postKey);
            ++startAtIndexRefreshMode;
        } else {
            mPostsNewsFeedPresenterCallback.updatePostsListRefreshMode(new ArrayList<>(ListOfPostModelRefreshMode));
            ListOfPostModelRefreshMode = new ArrayList<>();
        }
    }

    private void loadMorePostsWhenSomethingWrongRefreshMode() {
        if (startAtIndexRefreshMode < ListOfPostKeysRefreshMode.size()) {
            String postKey = this.ListOfPostKeysRefreshMode.get(startAtIndexRefreshMode);
            int userIndex = mappingPostsAndUser.get(postKey);
            String userKey = list_of_user.get(userIndex).getUserInfoModel().getKeyOfUser();

            mGetPostOfUserRefreshModeService.getPost(userKey, postKey);
            ++startAtIndexRefreshMode;
        } else {
            mPostsNewsFeedPresenterCallback.updatePostsListRefreshMode(new ArrayList<>(ListOfPostModelRefreshMode));
            ListOfPostModelRefreshMode = new ArrayList<>();
        }
    }

    private void setUpLikeAndDisLikePresenterCallBack() {
        mLikeAndDisLikePresenter.setUpLikeAndDisLikePresenterCallBack(new ILikeAndDisLikePresenterCallBack() {
            @Override
            public void showMessageError(String message) {
                mPostsNewsFeedPresenterCallback.showMessageError(message);
            }

            @Override
            public void networkErrorMessage() {
                mPostsNewsFeedPresenterCallback.networkErrorMessage();

            }

            @Override
            public void addLikeSuccessful() {
                mPostsNewsFeedPresenterCallback.addLikeSuccessful();
            }

            @Override
            public void removeLikeSuccessful() {
                mPostsNewsFeedPresenterCallback.removeLikeSuccessful();
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
}
