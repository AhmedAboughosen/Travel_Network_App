package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.mainPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.IBaseServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.notficastionServices.IGetNewNotificationsService;

public class MainPresenter implements IMainPresenter {

    private IGetNewNotificationsService getNotificationsService;
    private IMainPresenterCallback mMainPresenterCallback;

    public MainPresenter(IGetNewNotificationsService getNotificationsService) {
        this.getNotificationsService = getNotificationsService;
    }


    @Override
    public void getNotifications(String userKey) {
        getNotificationsService.setUpBaseServiceCallBack(new IBaseServiceCallBack() {
            @Override
            public void showMessageError(String message) {
                mMainPresenterCallback.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mMainPresenterCallback.networkErrorMessage();
            }
        });

        getNotificationsService.getNotifications(userKey);
    }


    @Override
    public void removeEventListener() {
        getNotificationsService.removeEventListener();
    }

    @Override
    public void setUpMainPresenterCallback(IMainPresenterCallback mMainPresenterCallback) {
        this.mMainPresenterCallback = mMainPresenterCallback;
    }
}
