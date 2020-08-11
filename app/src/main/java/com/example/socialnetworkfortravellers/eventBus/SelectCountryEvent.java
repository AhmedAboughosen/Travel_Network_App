package com.example.socialnetworkfortravellers.eventBus;

public class SelectCountryEvent {

    private String mCountryName;

    public SelectCountryEvent(String mCountryName) {
        this.mCountryName = mCountryName;
    }

    public String getCountryName() {
        return mCountryName;
    }
}
