package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.findFriendsPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.FoundUserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getFullNameOfUsersServices.IGetFullNameOfUsersService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getFullNameOfUsersServices.IGetFullNameOfUsersServiceCallBack;

import java.util.ArrayList;

public class GetFullNameOfUsersPresenter implements IGetFullNameOfUsersPresenter {


    private IGetFullNameOfUsersService mGetDataService;
    private IGetAllUserNamesPresenterCallBack mGetAllUsersNamePresenterCallBack;

    public GetFullNameOfUsersPresenter(IGetFullNameOfUsersService getFullNameOfUsersService) {
        mGetDataService = getFullNameOfUsersService;
    }


    @Override
    public void getAllFullNameOfUsers() {
        mGetDataService.setUpGetFullNameOfUsersServiceCallBack(new IGetFullNameOfUsersServiceCallBack() {

            @Override
            public void listOfUser(ArrayList<FoundUserModel> list) {
                mGetAllUsersNamePresenterCallBack.allUsers(list);
            }

            @Override
            public void noUserExists() {
                mGetAllUsersNamePresenterCallBack.noUserExists();
            }

            @Override
            public void showMessageError(String message) {
                mGetAllUsersNamePresenterCallBack.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mGetAllUsersNamePresenterCallBack.networkErrorMessage();
            }

        });

        mGetDataService.getFullNameOfUsers();
    }

    @Override
    public void setUpGetAllUsersNamePresenterCallBack(IGetAllUserNamesPresenterCallBack mGetAllUsersNamePresenterCallBack) {
        this.mGetAllUsersNamePresenterCallBack = mGetAllUsersNamePresenterCallBack;
    }


}
