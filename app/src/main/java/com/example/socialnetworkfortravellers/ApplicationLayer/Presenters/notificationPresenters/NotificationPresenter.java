package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.notificationPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.notficastionServices.IGetNotificationsService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.notficastionServices.IGetNotificationsServiceCallback;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.NotificationsModel;

public class NotificationPresenter implements INotificationPresenter {

    private IGetNotificationsService mGetNotificationsService;
    private IGetNotificationsPresenterCallback mGetNotificationsPresenterCallback;

    public NotificationPresenter(IGetNotificationsService getNotificationsService) {
        this.mGetNotificationsService = getNotificationsService;
    }


    @Override
    public void getNotifications(String userKey) {
        mGetNotificationsService.setUpGetNotificationsServiceCallback(new IGetNotificationsServiceCallback() {
            @Override
            public void onGetNotifications(NotificationsModel notificationsModel) {
                mGetNotificationsPresenterCallback.onGetNotifications(notificationsModel);
            }

            @Override
            public void noNotifications() {
                mGetNotificationsPresenterCallback.noNotifications();
            }

            @Override
            public void showMessageError(String message) {
                mGetNotificationsPresenterCallback.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mGetNotificationsPresenterCallback.networkErrorMessage();
            }
        });

        mGetNotificationsService.getNotifications(userKey);
    }

    @Override
    public void removeEventListener() {
        mGetNotificationsService.removeEventListener();

    }

    @Override
    public void setUpGetNotificationsPresenterCallback(IGetNotificationsPresenterCallback getNotificationsPresenterCallback) {
        this.mGetNotificationsPresenterCallback = getNotificationsPresenterCallback;
    }
}
