package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.chatMessagePresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.MessageModel;

public interface IChatMessagePresenter {
    void getMessageBranch(String userKey, String friendKey);

    void addNewMessage(MessageModel messageModel);

    void getUserInfo(String userKey);

    void setUpChatMessagePresenterCallback(IChatMessagePresenterCallback chatMessagePresenterCallback);
}
