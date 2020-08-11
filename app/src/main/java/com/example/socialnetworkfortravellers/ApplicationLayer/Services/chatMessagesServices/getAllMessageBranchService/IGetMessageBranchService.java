package com.example.socialnetworkfortravellers.ApplicationLayer.Services.chatMessagesServices.getAllMessageBranchService;

public interface IGetMessageBranchService {
    void setUpGetMessageBranchServiceCallback(IGetMessageBranchServiceCallback getAllMessageBranchServiceCallback);

    void getAllMessageBranch(String userKey);

    void getMessageBranch(String userKey, String friendKey);
}
