package com.example.socialnetworkfortravellers.ApplicationLayer.Services.chatMessagesServices.addNewMessageBranchService;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.MessageBranchModel;

public interface IAddNewMessageBranchService {
    void addNewMessageBranch(MessageBranchModel messageModel);

    void setUpAddNewMessageBranchServiceCallback(IAddNewMessageBranchServiceCallback addNewMessageBranchServiceCallback);
}
