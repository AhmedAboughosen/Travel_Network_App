package com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels;

public class MessageBranchModel {

    private String userKey, friendKey;
    private String lastMessage, messageKey;
    private Object timestamp;
    private long orderMessage;

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }

    public long getOrderMessage() {
        return orderMessage;
    }

    public void setOrderMessage(long orderMessage) {
        this.orderMessage = orderMessage;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getFriendKey() {
        return friendKey;
    }

    public void setFriendKey(String friendKey) {
        this.friendKey = friendKey;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }
}
