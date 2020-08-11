package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.chatMessagePresenters;

public interface IMessengerPresenter {

    void getMessageBranch(String userKey);

    void setUpMessengerPresenterCallback(IMessengerPresenterCallback messengerPresenterCallback);
}
