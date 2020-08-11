package com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels;


import java.io.Serializable;

import javax.inject.Inject;

public class AccountModel implements Serializable {
    private String Email;
    private String Password;


    @Inject
    public AccountModel()
    {

    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
