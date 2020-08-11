package com.example.socialnetworkfortravellers.ViewLayer.Interfaces;

import com.kofigyan.stateprogressbar.StateProgressBar;

import java.util.List;

public interface IAddTripActivity {
    void updateView(String title, StateProgressBar.StateNumber pos);

    void setUpDateFragment(String CountryName);

    void setUpSummaryFragment(String from, String to);

    void addTripObject(String summary, List<String> interestList);
}
