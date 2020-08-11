package com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels;

/**
 * \
 * InterestModel
 */
public class InterestModel {

    private String HobbyName;
    private int HobbyImage;
    private boolean isMarked;

    public String getHobbyName() {
        return HobbyName;
    }

    public void setHobbyName(String hobbyName) {
        HobbyName = hobbyName;
    }

    public int getHobbyImage() {
        return HobbyImage;
    }

    public void setHobbyImage(int hobbyImage) {
        HobbyImage = hobbyImage;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean marked) {
        isMarked = marked;
    }
}
