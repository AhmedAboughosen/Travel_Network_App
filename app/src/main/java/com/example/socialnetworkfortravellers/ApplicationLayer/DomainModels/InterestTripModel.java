package com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels;

public class InterestTripModel {

    private String interestName;
    private boolean isMarked;

    public String getInterestName() {
        return interestName;
    }

    public void setInterestName(String interestName) {
        this.interestName = interestName;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean marked) {
        isMarked = marked;
    }
}
