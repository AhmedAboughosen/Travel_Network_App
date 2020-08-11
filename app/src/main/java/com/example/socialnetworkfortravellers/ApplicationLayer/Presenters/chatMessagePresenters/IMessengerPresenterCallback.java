package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.chatMessagePresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.MessageModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.IPresenterCallBack;

import java.util.List;

public interface IMessengerPresenterCallback extends IPresenterCallBack {

    void currentUserDoesNotHaveFriends();

    void listOfChatFriends(List<MessageModel> messageModels);

}
