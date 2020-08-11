package com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class TripModel implements Serializable {
    private String CountryName, from, to, tripKey, userKey;
    private HashMap<String, Boolean> friendsList;
    private String summary;
    private List<String> tripInterests;
    private HashMap<String, Object> TripInterest;
    private Object TIMESTAMP;

    @Inject
    public TripModel() {
        tripInterests = new ArrayList<>();
        TripInterest = new HashMap<>();
    }

    public HashMap<String, Object> getTripInterest() {
        return TripInterest;
    }

    public void setTripInterest(HashMap<String, Object> tripInterest) {
        TripInterest = tripInterest;
    }

    public Object getTIMESTAMP() {
        return TIMESTAMP;
    }

    public void setTIMESTAMP(Object TIMESTAMP) {
        this.TIMESTAMP = TIMESTAMP;
    }


    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String countryName) {
        this.CountryName = countryName;
    }

    public List<String> getTripInterests() {
        return tripInterests;
    }

    public void setTripInterests(List<String> tripInterests) {
        this.tripInterests = tripInterests;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTripKey() {
        return tripKey;
    }

    public void setTripKey(String tripKey) {
        this.tripKey = tripKey;
    }

    public HashMap<String, Boolean> getFriendsList() {
        return friendsList;
    }

    public void setFriendsList(HashMap<String, Boolean> friendsList) {
        this.friendsList = friendsList;
    }
}
