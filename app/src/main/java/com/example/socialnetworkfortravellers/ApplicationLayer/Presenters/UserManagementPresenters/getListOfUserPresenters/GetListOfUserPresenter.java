package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.getListOfUserPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getListOfUserServices.IGetListOfUserService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getListOfUserServices.IGetListOfUserServiceCallback;

import java.util.List;

/**
 * pass list of key return list of user Object.
 */
public class GetListOfUserPresenter implements IGetListOfUserPresenter {


    private IGetListOfUserPresenterCallback mGetListOfUserPresenterCallback;
    private IGetListOfUserService mGetListOfUserService;


    public GetListOfUserPresenter(IGetListOfUserService getListOfUserService) {
        this.mGetListOfUserService = getListOfUserService;
        setUpGetListOfUserServiceCallback();
    }


    /**
     * get users
     *
     * @param list_of_user
     */
    @Override
    public void getUsers(List<String> list_of_user) {
        mGetListOfUserService.getUsers(list_of_user);
    }


    private void setUpGetListOfUserServiceCallback() {
        mGetListOfUserService.setUpGetListOfUserServiceCallback(new IGetListOfUserServiceCallback() {
            @Override
            public void ListOfUsers(List<UserModel> userModels) {
                if (userModels.size() == 0) {
                    mGetListOfUserPresenterCallback.showMessageError("we can't find friends.");
                } else {
                    mGetListOfUserPresenterCallback.ListOfUsers(userModels);
                }
            }

            @Override
            public void internetIsNotConnected() {
                mGetListOfUserPresenterCallback.networkErrorMessage();
            }
        });
    }

    /**
     * setUpGetListOfUserPresenterCallback
     *
     * @param mGetListOfUserPresenterCallback
     */
    @Override
    public void setUpGetListOfUserPresenterCallback(IGetListOfUserPresenterCallback mGetListOfUserPresenterCallback) {
        this.mGetListOfUserPresenterCallback = mGetListOfUserPresenterCallback;
    }
}
