package com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels;

public class MessageErrorModel {

    public MessageErrorModel(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private int id;
    private String message;
}
