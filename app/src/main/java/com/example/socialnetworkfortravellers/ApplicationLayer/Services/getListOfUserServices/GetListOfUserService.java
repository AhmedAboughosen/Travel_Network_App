package com.example.socialnetworkfortravellers.ApplicationLayer.Services.getListOfUserServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getUserInfoServices.IGetUserInfoService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getUserInfoServices.IGetUserInfoServiceCallBack;
import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;

import java.util.ArrayList;
import java.util.List;

import static com.example.socialnetworkfortravellers.utilLayer.ConfigUtil.allowToAddCurrentUser;

/**
 * this class used to get list of user , just send list of keys then we will return list of user model contain data about each key.
 */
public class GetListOfUserService implements IGetListOfUserService {


    private IGetListOfUserServiceCallback mGetListOfUserServiceCallback;
    private IGetUserInfoService mGetUserInfoService;
    private List<String> mListOfUsers;
    private int index = 0;
    private List<UserModel> usersModle;


    public GetListOfUserService(IGetUserInfoService getDataByUseSingleValueService) {
        this.mGetUserInfoService = getDataByUseSingleValueService;
        usersModle = new ArrayList<>();
        setUpGetUserInfoServiceCallBack();
    }


    /**
     * list of user keys
     *
     * @param list_of_users
     */
    @Override
    public void getUsers(List<String> list_of_users) {
        this.mListOfUsers = list_of_users;
        mGetUserInfoService.getUserInfo(mListOfUsers.get(index));
    }


    private void setUpGetUserInfoServiceCallBack() {
        mGetUserInfoService.setUpGetUserInfoServiceCallBack(new IGetUserInfoServiceCallBack() {
            @Override
            public void userData(UserModel mUserModel) {
                boolean isCurrentUser = mUserModel.getUserInfoModel().getKeyOfUser().equals(CurrentUserIDUtil.getInstance().getCurrentUserID());
                if (!isCurrentUser || allowToAddCurrentUser) {
                        usersModle.add(mUserModel);
                }

                index++;
                if (index < mListOfUsers.size()) {
                    mGetUserInfoService.getUserInfo(mListOfUsers.get(index));
                } else {
                    mGetListOfUserServiceCallback.ListOfUsers(usersModle);
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
                mGetListOfUserServiceCallback.internetIsNotConnected();
            }
        });
    }

    /**
     * when data is not exists in database.
     */
    private void whenSomeThingWrong() {
        index++;
        if (index < mListOfUsers.size()) {
            mGetUserInfoService.getUserInfo(mListOfUsers.get(index));
        } else {
            mGetListOfUserServiceCallback.ListOfUsers(usersModle);
        }
    }


    @Override
    public void setUpGetListOfUserServiceCallback(IGetListOfUserServiceCallback mPeopleWhoLikeServiceCallback) {
        this.mGetListOfUserServiceCallback = mPeopleWhoLikeServiceCallback;
    }


}
