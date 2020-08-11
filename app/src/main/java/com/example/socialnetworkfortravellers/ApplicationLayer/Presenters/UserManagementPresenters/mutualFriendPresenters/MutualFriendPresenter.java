package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.mutualFriendPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getUserInfoServices.IGetUserInfoService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getUserInfoServices.IGetUserInfoServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getFriendsOfUserServices.IGetFriendsOfUserService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getFriendsOfUserServices.IGetFriendsOfUserServiceCallBack;
import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MutualFriendPresenter implements IMutualFriendPresenter {


    private IGetFriendsOfUserService mGetFollowingDataService;
    private IMutualFriendPresenterCallBack mMutualFriendPresenterCallBack;
    private HashMap<String, String> mFriendsOfCurrentUser;
    private HashMap<String, Integer> mFriendsOfYourFriends;
    private List<String> mListOfCurrentUserFriends, mMutualFriends;
    private IGetUserInfoService mGetUserInfoService;
    private int startAtIndex, ITEM_LOAD_COUNT, currentID;
    private List<UserModel> listOfUser;

    public MutualFriendPresenter(IGetFriendsOfUserService getFollowingDataService, IGetUserInfoService getUserInfoService) {
        this.mGetFollowingDataService = getFollowingDataService;
        mListOfCurrentUserFriends = new ArrayList<>();
        mMutualFriends = new ArrayList<>();
        mFriendsOfYourFriends = new HashMap<>();
        this.mGetUserInfoService = getUserInfoService;
        listOfUser = new ArrayList<>();
        setUpGetUserInfoServiceCallBack();
        currentID = 0;
        startAtIndex = 0;
    }

    @Override
    public void reSetIndex() {
        startAtIndex = 0;
    }


    @Override
    public void setUpMutualFriendPresenterCallBack(IMutualFriendPresenterCallBack mMutualFriendPresenterCallBack) {
        this.mMutualFriendPresenterCallBack = mMutualFriendPresenterCallBack;
    }


    @Override
    public void getFriendsOfCurrentUser() {
        mGetFollowingDataService.setUpGetFollowingDataServiceCallBack(new IGetFriendsOfUserServiceCallBack() {
            /*
            key is friend id and value is user id
             */
            @Override
            public void getFriends(HashMap<String, String> map) {
                mFriendsOfCurrentUser = new HashMap<>(map);
                getMutualFriends();
            }

            @Override
            public void CurrentUserDoesNotHaveFriends() {
                mMutualFriendPresenterCallBack.currentUserDoesNotHaveFriends();
            }

            @Override
            public void showMessageError(String message) {
                mMutualFriendPresenterCallBack.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mMutualFriendPresenterCallBack.noInternetFound();
            }
        });

        mGetFollowingDataService.getFriendsOfUser(CurrentUserIDUtil.getInstance().getCurrentUserID());
    }


    /**
     * convert friend key to list of string then get friends of user
     */
    private void getMutualFriends() {
        //Getting Set of keys from HashMap
        Set<String> keySet = mFriendsOfCurrentUser.keySet();
        //Creating an ArrayList of keys by passing the keySet
        mListOfCurrentUserFriends = new ArrayList<>(keySet);

        if (currentID < mListOfCurrentUserFriends.size()) {
            getFriendsOfUser(mListOfCurrentUserFriends.get(currentID));
            currentID++;
        }

    }

    private void getFriendsOfUser(String userId) {
        mGetFollowingDataService.setUpGetFollowingDataServiceCallBack(new IGetFriendsOfUserServiceCallBack() {
            /*
            user id and friend id
             */
            @Override
            public void getFriends(HashMap<String, String> map) {

                mutualFriends(map);
                if (currentID < mListOfCurrentUserFriends.size()) {
                    mGetFollowingDataService.getFriendsOfUser(mListOfCurrentUserFriends.get(currentID));
                    currentID++;
                } else {
                    if (mMutualFriends.size() == 0){
                        mMutualFriendPresenterCallBack.currentUserDoesNotHaveFriends();
                    }else {
                        getUser();
                    }
                }
            }

            @Override
            public void CurrentUserDoesNotHaveFriends() {
                whenSomethingWrongWithFollowingPath();
            }

            @Override
            public void showMessageError(String message) {
                whenSomethingWrongWithFollowingPath();
            }

            @Override
            public void noInternetFound() {
                mMutualFriendPresenterCallBack.noInternetFound();
            }
        });

        mGetFollowingDataService.getFriendsOfUser(userId);
    }


    private void whenSomethingWrongWithFollowingPath() {
        if (currentID < mListOfCurrentUserFriends.size()) {
            mGetFollowingDataService.getFriendsOfUser(mListOfCurrentUserFriends.get(currentID));
            currentID++;
        } else {
            if (mMutualFriends.size() == 0){
                mMutualFriendPresenterCallBack.currentUserDoesNotHaveFriends();
            }else {
                getUser();
            }
        }
    }

    /**
     * check if there is mutual friends or not.
     *
     * @param users
     */
    private void mutualFriends(HashMap<String, String> users) {

        try {
            for (Map.Entry<String, String> entry : users.entrySet()) {
                String friendKey = entry.getKey();

                if (!friendKey.equals(CurrentUserIDUtil.getInstance().getCurrentUserID())) {
                    if (!mFriendsOfCurrentUser.containsKey(friendKey)) {
                        if (mFriendsOfYourFriends.containsKey(friendKey)) {
                            mMutualFriends.add(friendKey);
                        } else {
                            mFriendsOfYourFriends.put(friendKey, 1);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    /**
     * get object of each mutual friends.
     */
    public void getUser() {

        if (startAtIndex < this.mMutualFriends.size()) {
            listOfUser = new ArrayList<>();
            ITEM_LOAD_COUNT = 5;
            mMutualFriendPresenterCallBack.updateLoading(true);
            mMutualFriendPresenterCallBack.addFakeData();
            this.mGetUserInfoService.getUserInfo(this.mMutualFriends.get(startAtIndex));
            startAtIndex++;
        } else {
            mMutualFriendPresenterCallBack.listIsFinished();
        }
    }


    private void setUpGetUserInfoServiceCallBack() {
        this.mGetUserInfoService.setUpGetUserInfoServiceCallBack(new IGetUserInfoServiceCallBack() {
            @Override
            public void userData(UserModel mUserModel) {
                if (startAtIndex < mMutualFriends.size() && ITEM_LOAD_COUNT > 0) {
                    listOfUser.add(mUserModel);
                    mGetUserInfoService.getUserInfo(mMutualFriends.get(startAtIndex));
                    ++startAtIndex;
                    --ITEM_LOAD_COUNT;
                } else {
                    listOfUser.add(mUserModel);
                    ITEM_LOAD_COUNT = 5;
                    mMutualFriendPresenterCallBack.updateLoading(false);
                    mMutualFriendPresenterCallBack.removeFakeData();
                    mMutualFriendPresenterCallBack.updateFriendsList(listOfUser);
                }
            }

            @Override
            public void showMessageError(String message) {
                whenSomethingWrongWithUserObj();
            }

            @Override
            public void thisUserNotExists() {
                whenSomethingWrongWithUserObj();
            }

            @Override
            public void noInternetFound() {
                mMutualFriendPresenterCallBack.noInternetFound();
            }
        });
    }


    private void whenSomethingWrongWithUserObj() {
        if (startAtIndex < mMutualFriends.size() && ITEM_LOAD_COUNT > 0) {
            mGetUserInfoService.getUserInfo(mMutualFriends.get(startAtIndex));
            ++startAtIndex;
            --ITEM_LOAD_COUNT;
        } else {
            mMutualFriendPresenterCallBack.updateLoading(false);
            mMutualFriendPresenterCallBack.removeFakeData();
            mMutualFriendPresenterCallBack.updateFriendsList(listOfUser);
        }
    }
}
