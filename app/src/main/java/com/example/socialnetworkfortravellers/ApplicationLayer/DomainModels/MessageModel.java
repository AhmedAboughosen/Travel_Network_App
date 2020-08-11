package com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;

public class MessageModel {

    private String MessageContent, profileImage;
    private String messageKey, userKey;
    private Object Timestamp;
    private UserModel userModel;
    private Long orderMessage;

    public Long getOrderMessage() {
        return orderMessage;
    }

    public void setOrderMessage(Long orderMessage) {
        this.orderMessage = orderMessage;
    }

    public Object getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(Object timestamp) {
        Timestamp = timestamp;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getMessageContent() {
        return MessageContent;
    }

    public void setMessageContent(String messageContent) {
        MessageContent = messageContent;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }
}
