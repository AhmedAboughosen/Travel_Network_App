package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.chatMessagePresenters;


import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.MessageBranchModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.MessageModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.chatMessagesServices.getAllMessageBranchService.IGetMessageBranchService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.chatMessagesServices.getAllMessageBranchService.IGetMessageBranchServiceCallback;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getListOfUserServices.IGetListOfUserService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getListOfUserServices.IGetListOfUserServiceCallback;
import com.example.socialnetworkfortravellers.utilLayer.StringEmptyUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class MessengerPresenter implements IMessengerPresenter {

    private IGetMessageBranchService mGetMessageBranchService;
    private IGetListOfUserService mGetListOfUserService;
    private List<MessageModel> list_of_user_chat;
    private IMessengerPresenterCallback mMessengerPresenterCallback;
    private HashMap<String, MessageBranchModel> mapMessageBranchModel;

    public MessengerPresenter(IGetMessageBranchService getMessageBranchService, IGetListOfUserService getListOfUserService) {
        this.mGetMessageBranchService = getMessageBranchService;
        this.mGetListOfUserService = getListOfUserService;
        mapMessageBranchModel = new HashMap<>();
        list_of_user_chat = new ArrayList<>();
        setUpGetMessageBranchServiceCallback();
        setUpGetListOfUserServiceCallback();
    }


    @Override
    public void setUpMessengerPresenterCallback(IMessengerPresenterCallback messengerPresenterCallback) {
        this.mMessengerPresenterCallback = messengerPresenterCallback;
    }

    @Override
    public void getMessageBranch(String userKey) {
        mGetMessageBranchService.getAllMessageBranch(userKey);
    }


    private void setUpGetMessageBranchServiceCallback() {
        mGetMessageBranchService.setUpGetMessageBranchServiceCallback(new IGetMessageBranchServiceCallback() {


            @Override
            public void listOfMessageBranch(List<MessageBranchModel> messageBranchModelList, List<String> friendsList) {

                for (int i = 0; i < messageBranchModelList.size(); i++) {
                    mapMessageBranchModel.put(messageBranchModelList.get(i).getFriendKey(), messageBranchModelList.get(i));
                }
                mGetListOfUserService.getUsers(friendsList);
            }

            @Override
            public void getMessageBranch(MessageBranchModel messageBranchModel) {

            }

            @Override
            public void currentUserDoesNotHaveFriends() {
                mMessengerPresenterCallback.currentUserDoesNotHaveFriends();
            }

            @Override
            public void thisFriendDoesNotExists() {

            }

            @Override
            public void showMessageError(String message) {
                mMessengerPresenterCallback.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mMessengerPresenterCallback.networkErrorMessage();
            }
        });
    }


    private void setUpGetListOfUserServiceCallback() {
        mGetListOfUserService.setUpGetListOfUserServiceCallback(new IGetListOfUserServiceCallback() {
            @Override
            public void ListOfUsers(List<UserModel> userModels) {

                for (int i = 0; i < userModels.size(); i++) {
                    MessageBranchModel messageBranchModel = mapMessageBranchModel.get(userModels.get(i).getUserInfoModel().getKeyOfUser());
                    MessageModel messageModel = new MessageModel();
                    if (messageBranchModel != null && !StringEmptyUtil.isEmptyString(messageBranchModel.getLastMessage())) {
                        messageModel.setMessageContent(messageBranchModel.getLastMessage());
                        messageModel.setTimestamp(messageBranchModel.getTimestamp());
                    }
                    messageModel.setOrderMessage(messageBranchModel.getOrderMessage());
                    messageModel.setUserModel(userModels.get(i));
                    list_of_user_chat.add(messageModel);
                }

                Collections.sort(list_of_user_chat, new Comparator<MessageModel>() {

                    public int compare(MessageModel o1, MessageModel o2) {
                        return o1.getOrderMessage().compareTo(o2.getOrderMessage());
                    }
                });
                mMessengerPresenterCallback.listOfChatFriends(list_of_user_chat);
            }

            @Override
            public void internetIsNotConnected() {
                mMessengerPresenterCallback.networkErrorMessage();
            }
        });
    }


}
