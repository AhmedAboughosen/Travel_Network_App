package com.example.socialnetworkfortravellers.ApplicationLayer.Services.chatMessagesServices.saveNewMessageService;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.MessageModel;

public interface ISaveNewMessageService {
    void addNewMessage(MessageModel messageModel, String messageKey);

    void setUpSaveNewMessageServiceCallback(ISaveNewMessageServiceCallback saveNewMessageServiceCallback);
}
