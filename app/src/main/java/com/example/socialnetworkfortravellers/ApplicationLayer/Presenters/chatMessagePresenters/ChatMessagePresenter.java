package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.chatMessagePresenters;

import androidx.annotation.NonNull;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.MessageBranchModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.MessageModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getUserInfoServices.IGetUserInfoService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getUserInfoServices.IGetUserInfoServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.chatMessagesServices.addNewMessageBranchService.IAddNewMessageBranchService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.chatMessagesServices.addNewMessageBranchService.IAddNewMessageBranchServiceCallback;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.chatMessagesServices.getAllMessageBranchService.IGetMessageBranchService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.chatMessagesServices.getAllMessageBranchService.IGetMessageBranchServiceCallback;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.chatMessagesServices.getChatMessageService.IGetChatMessageService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.chatMessagesServices.getChatMessageService.IGetChatMessageServiceCallback;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.chatMessagesServices.saveNewMessageService.ISaveNewMessageService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.chatMessagesServices.saveNewMessageService.ISaveNewMessageServiceCallback;
import com.example.socialnetworkfortravellers.utilLayer.StringEmptyUtil;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ChatMessagePresenter implements IChatMessagePresenter {

    private IGetMessageBranchService mGetMessageBranchService;
    private IAddNewMessageBranchService mAddNewMessageBranchService;
    private IChatMessagePresenterCallback mChatMessagePresenterCallback;
    private IGetChatMessageService mGetChatMessageService;
    private ISaveNewMessageService mSaveNewMessageService;
    private IGetUserInfoService mGetUserInfoService;
    private String userKey, friendKey;
    private String messageKey;
    private List<MessageBranchModel> messageBranchModels;
    private int branchIndex = 0;
    private boolean allowUserToAddMessage = false;
    private String lastMessage = "";

    public ChatMessagePresenter(IGetMessageBranchService getMessageBranchService, IAddNewMessageBranchService addNewMessageBranchService, IGetChatMessageService getChatMessageService
            , ISaveNewMessageService saveNewMessageService, IGetUserInfoService getUserInfoService) {
        this.mGetMessageBranchService = getMessageBranchService;
        this.mAddNewMessageBranchService = addNewMessageBranchService;
        this.mGetChatMessageService = getChatMessageService;
        this.mSaveNewMessageService = saveNewMessageService;
        this.mGetUserInfoService = getUserInfoService;
        messageBranchModels = new ArrayList<>();
        setUpGetMessageBranchServiceCallback();
        setUpGetChatMessageServiceCallback();
        setUpGetUserInfoServiceCallBack();
        setUpSaveNewMessageServiceCallback();
    }


    public void getUserInfo(String userKey) {
        mGetUserInfoService.getUserInfo(userKey);
    }


    private void setUpGetUserInfoServiceCallBack() {
        mGetUserInfoService.setUpGetUserInfoServiceCallBack(new IGetUserInfoServiceCallBack() {
            @Override
            public void userData(UserModel mUserModel) {
                mChatMessagePresenterCallback.userData(mUserModel);
            }

            @Override
            public void thisUserNotExists() {
                mChatMessagePresenterCallback.thisUserNotExists();
            }

            @Override
            public void showMessageError(String message) {
                mChatMessagePresenterCallback.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mChatMessagePresenterCallback.networkErrorMessage();
            }
        });
    }


    @Override
    public void addNewMessage(MessageModel messageModel) {


        if (allowUserToAddMessage && !StringEmptyUtil.isEmptyString(messageKey)) {
            if (!StringEmptyUtil.isEmptyString(messageModel.getMessageContent())) {
                lastMessage = messageModel.getMessageContent();
                mSaveNewMessageService.addNewMessage(messageModel, messageKey);
            }
        } else {
            mChatMessagePresenterCallback.showMessageError("Please wait while we are processing your request.");
        }
    }


    private void setUpSaveNewMessageServiceCallback() {
        mSaveNewMessageService.setUpSaveNewMessageServiceCallback(new ISaveNewMessageServiceCallback() {
            @Override
            public void saveMessageSuccessful() {
                branchIndex = 0;
                messageBranchModels.clear();
                messageBranchModels.add(createNewBranchForUser());
                messageBranchModels.add(createNewBranchForFriend());
                updateBranch();
            }

            @Override
            public void showMessageError(String message) {
                mChatMessagePresenterCallback.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mChatMessagePresenterCallback.networkErrorMessage();
            }
        });
    }

    @Override
    public void getMessageBranch(String userKey, String friendKey) {
        this.userKey = userKey;
        this.friendKey = friendKey;
        mGetMessageBranchService.getMessageBranch(userKey, friendKey);
    }


    private void setUpGetMessageBranchServiceCallback() {
        mGetMessageBranchService.setUpGetMessageBranchServiceCallback(new IGetMessageBranchServiceCallback() {


            @Override
            public void listOfMessageBranch(List<MessageBranchModel> messageBranchModelList, List<String> friendsList) {

            }

            @Override
            public void getMessageBranch(MessageBranchModel messageBranchModel) {
                messageKey = messageBranchModel.getMessageKey();
                allowUserToAddMessage = true;
                mGetChatMessageService.listenForMessages(messageBranchModel.getMessageKey());
            }

            @Override
            public void currentUserDoesNotHaveFriends() {

            }

            @Override
            public void thisFriendDoesNotExists() {
                generateMessageKey();
                messageBranchModels.add(createNewBranchForUser());
                messageBranchModels.add(createNewBranchForFriend());
                addNewBranch();
            }

            @Override
            public void showMessageError(String message) {
                mChatMessagePresenterCallback.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mChatMessagePresenterCallback.networkErrorMessage();
            }
        });
    }


    private void generateMessageKey() {
        DatabaseReference messageBranchRef = FirebaseDatabase.getInstance().getReference();
        messageKey = messageBranchRef.push().getKey();
    }


    private MessageBranchModel createNewBranchForUser() {

        MessageBranchModel mMessageBranchModel = new MessageBranchModel();

        mMessageBranchModel.setMessageKey(messageKey);
        mMessageBranchModel.setFriendKey(friendKey);
        mMessageBranchModel.setUserKey(userKey);
        mMessageBranchModel.setLastMessage(lastMessage);
        return mMessageBranchModel;
    }

    private MessageBranchModel createNewBranchForFriend() {

        MessageBranchModel mMessageBranchModel = new MessageBranchModel();

        mMessageBranchModel.setMessageKey(messageKey);
        mMessageBranchModel.setFriendKey(userKey);
        mMessageBranchModel.setUserKey(friendKey);
        mMessageBranchModel.setLastMessage(lastMessage);
        return mMessageBranchModel;
    }


    private void addNewBranch() {
        mAddNewMessageBranchService.setUpAddNewMessageBranchServiceCallback(new IAddNewMessageBranchServiceCallback() {
            @Override
            public void saveMessageBranchSuccessful() {
                branchIndex++;

                if (branchIndex < messageBranchModels.size()) {
                    mAddNewMessageBranchService.addNewMessageBranch(messageBranchModels.get(branchIndex));
                } else {
                    allowUserToAddMessage = true;
                    mGetChatMessageService.listenForMessages(messageKey);
                }
            }

            @Override
            public void showMessageError(String message) {
                mChatMessagePresenterCallback.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mChatMessagePresenterCallback.networkErrorMessage();
            }
        });
        mAddNewMessageBranchService.addNewMessageBranch(messageBranchModels.get(branchIndex));
    }

    private void updateBranch() {
        mAddNewMessageBranchService.setUpAddNewMessageBranchServiceCallback(new IAddNewMessageBranchServiceCallback() {
            @Override
            public void saveMessageBranchSuccessful() {
                branchIndex++;

                if (branchIndex < messageBranchModels.size()) {
                    mAddNewMessageBranchService.addNewMessageBranch(messageBranchModels.get(branchIndex));
                }
            }

            @Override
            public void showMessageError(String message) {
                mChatMessagePresenterCallback.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mChatMessagePresenterCallback.networkErrorMessage();
            }
        });
        mAddNewMessageBranchService.addNewMessageBranch(messageBranchModels.get(branchIndex));
    }

    private void setUpGetChatMessageServiceCallback() {
        mGetChatMessageService.setUpGetChatMessageServiceCallback(new IGetChatMessageServiceCallback() {
            @Override
            public void onMessagedAdded(@NonNull MessageModel messageModel) {
                mChatMessagePresenterCallback.onMessagedAdded(messageModel);
            }

            @Override
            public void showMessageError(String message) {
                mChatMessagePresenterCallback.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mChatMessagePresenterCallback.networkErrorMessage();
            }
        });
    }


    public void setUpChatMessagePresenterCallback(IChatMessagePresenterCallback chatMessagePresenterCallback) {
        this.mChatMessagePresenterCallback = chatMessagePresenterCallback;
    }
}
