package com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels;


import java.io.Serializable;

import javax.inject.Inject;

/**
 * responsibility of this class is used to save state of user such as number of Following and Followers and posts etc.
 */
public class UserInfoModel implements Serializable {


    private String KeyOfUser;
    private int number_Of_Following;
    private int number_Of_Followers;
    private int number_Of_Posts;
    private Object Joined_date;
    private boolean isFollower;
    private String countryFlag;


    @Inject
    public UserInfoModel() {

    }


    public String getKeyOfUser() {
        return KeyOfUser;
    }

    public void setKeyOfUser(String keyOfUser) {
        this.KeyOfUser = keyOfUser;
    }

    public int getNumber_Of_Following() {
        return number_Of_Following;
    }

    public void setNumber_Of_Following(int number_Of_Following) {
        this.number_Of_Following = number_Of_Following;
    }

    public int getNumber_Of_Followers() {
        return number_Of_Followers;
    }

    public void setNumber_Of_Followers(int number_Of_Followers) {
        this.number_Of_Followers = number_Of_Followers;
    }

    public int getNumber_Of_Posts() {
        return number_Of_Posts;
    }

    public void setNumber_Of_Posts(int number_Of_Posts) {
        this.number_Of_Posts = number_Of_Posts;
    }

    public Object getJoined_date() {
        return Joined_date;
    }

    public void setJoined_date(Object joined_date) {
        Joined_date = joined_date;
    }

    public boolean isFollower() {
        return isFollower;
    }

    public void setFollower(boolean follower) {
        isFollower = follower;
    }

    public String getCountryFlag() {
        return countryFlag;
    }

    public void setCountryFlag(String countryFlag) {
        this.countryFlag = countryFlag;
    }
}
