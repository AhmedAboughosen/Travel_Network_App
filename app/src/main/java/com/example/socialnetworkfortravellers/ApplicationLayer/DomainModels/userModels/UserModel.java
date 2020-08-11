package com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels;

import java.io.Serializable;

import javax.inject.Inject;

/**
 * responsibility of this class is used to save  meta data  of user such as name and bio and photo etc.
 */

public class UserModel implements Serializable {

    @Inject
    UserInfoModel userInfoModel;
    @Inject
    AccountModel accountModel;
    private String FullName;
    private String profilePicture;
    private String Bio;
    private boolean gender;
    private String date_of_birth;
    private String Country;


    @Inject
    public UserModel() {
        accountModel = new AccountModel();
        userInfoModel = new UserInfoModel();
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getBio() {
        return Bio;
    }

    public void setBio(String bio) {
        Bio = bio;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public UserInfoModel getUserInfoModel() {
        return userInfoModel;
    }

    public void setUserInfoModel(UserInfoModel userInfoModel) {
        this.userInfoModel = userInfoModel;
    }

    public AccountModel getAccountModel() {
        return accountModel;
    }

    public void setAccountModel(AccountModel accountModel) {
        this.accountModel = accountModel;
    }
}
