package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.chatMessagePresenters;

import androidx.annotation.NonNull;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.MessageModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.IPresenterCallBack;

public interface IChatMessagePresenterCallback extends IPresenterCallBack {
    void onMessagedAdded(@NonNull MessageModel messageModel);

    void userData(UserModel mUserModel);

    void thisUserNotExists();

}
