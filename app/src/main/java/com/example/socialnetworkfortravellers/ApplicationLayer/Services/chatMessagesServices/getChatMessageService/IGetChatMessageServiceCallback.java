package com.example.socialnetworkfortravellers.ApplicationLayer.Services.chatMessagesServices.getChatMessageService;

import androidx.annotation.NonNull;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.MessageModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.IBaseServiceCallBack;

public interface IGetChatMessageServiceCallback extends IBaseServiceCallBack {
    void onMessagedAdded(@NonNull MessageModel messageModel);
}
