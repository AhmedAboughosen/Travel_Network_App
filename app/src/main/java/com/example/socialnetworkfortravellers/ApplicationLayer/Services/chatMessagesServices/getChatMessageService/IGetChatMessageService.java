package com.example.socialnetworkfortravellers.ApplicationLayer.Services.chatMessagesServices.getChatMessageService;

public interface IGetChatMessageService {
    void setUpGetChatMessageServiceCallback(IGetChatMessageServiceCallback getChatMessageServiceCallback);
    void listenForMessages(String messageKey);
}
