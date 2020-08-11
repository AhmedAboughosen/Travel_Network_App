package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.findFriendsPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.FoundUserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getUserInfoServices.IGetUserInfoService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getUserInfoServices.IGetUserInfoServiceCallBack;
import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;

import java.util.ArrayList;
import java.util.List;

public class FindFriendPresenter implements IFindFriendPresenter {


    private List<FoundUserModel> mListOfAllUsers, mMatchesUsers;
    private IFindFriendPresenterCallBack mFindFriendPresenterCallBack;
    private int startAtIndex, ITEM_LOAD_COUNT;
    private IGetUserInfoService mGetUserInfoService;
    private List<UserModel> listOfUser;


    public FindFriendPresenter(IGetUserInfoService getUserInfoService) {
        mListOfAllUsers = new ArrayList<>();
        listOfUser = new ArrayList<>();
        this.mGetUserInfoService = getUserInfoService;
        startAtIndex = 0;
        ITEM_LOAD_COUNT = 5;
        setUpGetUserInfoServiceCallBack();
    }


    @Override
    public void reSetIndex() {
        startAtIndex = 0;
    }

    @Override
    public void AllUserNames(List<FoundUserModel> foundUserModel) {
        this.mListOfAllUsers = new ArrayList<>(foundUserModel);
    }

    @Override
    public void findFriend(String fullName) {
        this.mMatchesUsers = new ArrayList<>();

        try {

            for (int i = 0; i < this.mListOfAllUsers.size(); ++i) {
                if (!this.mListOfAllUsers.get(i).getUserKey().equals(CurrentUserIDUtil.getInstance().getCurrentUserID()) && this.mListOfAllUsers.get(i).getFullName().toLowerCase().contains(fullName.toLowerCase())) {
                    this.mMatchesUsers.add(this.mListOfAllUsers.get(i));
                }
            }


            if (this.mMatchesUsers.size() == 0) {
                mFindFriendPresenterCallBack.noFriendExists();
            } else {
                getUser();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            mFindFriendPresenterCallBack.showMessageError(ex.getMessage());
        }
    }


    @Override
    public void getUser() {

        if (startAtIndex < this.mMatchesUsers.size()) {
            listOfUser = new ArrayList<>();
            ITEM_LOAD_COUNT = 5;
            mFindFriendPresenterCallBack.updateLoading(true);
            mFindFriendPresenterCallBack.addFakeData();
            this.mGetUserInfoService.getUserInfo(this.mMatchesUsers.get(startAtIndex).getUserKey());
            startAtIndex++;
        } else {
            mFindFriendPresenterCallBack.listIsFinished();
        }
    }


    private void setUpGetUserInfoServiceCallBack() {
        this.mGetUserInfoService.setUpGetUserInfoServiceCallBack(new IGetUserInfoServiceCallBack() {
            @Override
            public void userData(UserModel mUserModel) {
                if (startAtIndex < mMatchesUsers.size() && ITEM_LOAD_COUNT > 0) {
                    listOfUser.add(mUserModel);
                    mGetUserInfoService.getUserInfo(mMatchesUsers.get(startAtIndex).getUserKey());
                    ++startAtIndex;
                    --ITEM_LOAD_COUNT;
                } else {
                    listOfUser.add(mUserModel);
                    ITEM_LOAD_COUNT = 5;
                    mFindFriendPresenterCallBack.updateLoading(false);
                    mFindFriendPresenterCallBack.removeFakeData();
                    mFindFriendPresenterCallBack.updateFriendsList(listOfUser);
                }
            }

            @Override
            public void showMessageError(String message) {
                whenUserHaveSomethingWrong();
            }

            @Override
            public void thisUserNotExists() {
                whenUserHaveSomethingWrong();
            }

            @Override
            public void noInternetFound() {
                mFindFriendPresenterCallBack.networkErrorMessage();
            }
        });
    }


    private void whenUserHaveSomethingWrong() {
        if (startAtIndex < mMatchesUsers.size() && ITEM_LOAD_COUNT > 0) {
            mGetUserInfoService.getUserInfo(mMatchesUsers.get(startAtIndex).getUserKey());
            ++startAtIndex;
            --ITEM_LOAD_COUNT;
        } else {
            ITEM_LOAD_COUNT = 5;
            mFindFriendPresenterCallBack.updateLoading(false);
            mFindFriendPresenterCallBack.removeFakeData();
            mFindFriendPresenterCallBack.updateFriendsList(listOfUser);
        }
    }

    @Override
    public void setUpFindFriendPresenterCallBack(IFindFriendPresenterCallBack mFindFriendPresenterCallBack) {
        this.mFindFriendPresenterCallBack = mFindFriendPresenterCallBack;
    }
}
