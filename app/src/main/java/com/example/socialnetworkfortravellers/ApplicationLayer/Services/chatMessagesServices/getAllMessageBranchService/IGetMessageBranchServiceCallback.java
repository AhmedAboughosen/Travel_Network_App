package com.example.socialnetworkfortravellers.ApplicationLayer.Services.chatMessagesServices.getAllMessageBranchService;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.MessageBranchModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.IBaseServiceCallBack;

import java.util.List;

public interface IGetMessageBranchServiceCallback extends IBaseServiceCallBack {

    void listOfMessageBranch(List<MessageBranchModel> messageBranchModelList, List<String> friendsList);

    void getMessageBranch(MessageBranchModel messageBranchModel);

    void currentUserDoesNotHaveFriends();

    void thisFriendDoesNotExists();

}
