package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.InterestManagementPresenters.addInterestPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.InterestModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.saveRelatedInterestServices.ISaveUserRelatedInterestService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.saveRelatedInterestServices.ISaveUserRelatedInterestServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.saveUserInterestServices.ISaveUserInterestService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.saveUserInterestServices.ISaveUserInterestServiceCallBack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * responsibility of this class is to save data by create  services which passed in constructor
 */
public class AddInterestPresenter implements IAddInterestPresenter {


    private ISaveUserInterestService mSaveUserInterestService;
    private ISaveUserRelatedInterestService mSaveUserRelatedInterestService;
    private IAddInterestPresenterCallBack mAddInterestPresenterCallBack;

    public AddInterestPresenter(ISaveUserInterestService saveUserInterestService, ISaveUserRelatedInterestService saveUserRelatedInterestService) {
        this.mSaveUserInterestService = saveUserInterestService;
        this.mSaveUserRelatedInterestService = saveUserRelatedInterestService;
    }

    /**
     * get marked Object then save data to firebase.
     *
     * @param interestModels
     */
    @Override
    public void addInterest(List<InterestModel> interestModels) {

        try {

            List<InterestModel> selectedInterestModels = new ArrayList<>();
            HashMap<String, Object> selectedInterestMaps = new HashMap<>();


            for (int i = 0; i < interestModels.size(); i++) {
                if (interestModels.get(i).isMarked()) {
                    selectedInterestModels.add(interestModels.get(i));
                    selectedInterestMaps.put(interestModels.get(i).getHobbyName(), true);
                }
            }

            if (selectedInterestModels.size() != 0) {
                saveInterest(selectedInterestMaps, selectedInterestModels);
            } else {
                mAddInterestPresenterCallBack.showMessageError("Please Select Some of Interest!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * save interest
     *
     * @param selectedInterestMaps
     * @param selectedInterestModels
     */
    private void saveInterest(HashMap<String, Object> selectedInterestMaps, List<InterestModel> selectedInterestModels) {

        mSaveUserInterestService.setUpSaveUserInterestServiceCallBack(new ISaveUserInterestServiceCallBack() {
            @Override
            public void showMessageError(String message) {
                mAddInterestPresenterCallBack.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mAddInterestPresenterCallBack.networkErrorMessage();
            }

            @Override
            public void Successful() {
                saveRelatedInterest(selectedInterestModels);
            }
        });

        mSaveUserInterestService.saveInterest(selectedInterestMaps);
    }


    /**
     * save Related Interest
     *
     * @param selectedInterestModels
     */
    private void saveRelatedInterest(List<InterestModel> selectedInterestModels) {

        this.mSaveUserRelatedInterestService.setUpSaveUserInterestServiceCallBack(new ISaveUserRelatedInterestServiceCallBack() {
            @Override
            public void showMessageError(String message) {
                mAddInterestPresenterCallBack.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mAddInterestPresenterCallBack.networkErrorMessage();
            }

            @Override
            public void Successful() {
                mAddInterestPresenterCallBack.addInterestSuccessful();
            }

        });

        this.mSaveUserRelatedInterestService.saveRelatedInterest(selectedInterestModels);
    }

    @Override
    public void setUpAddInterestPresenterCallBack(IAddInterestPresenterCallBack mAddInterestPresenterCallBack) {
        this.mAddInterestPresenterCallBack = mAddInterestPresenterCallBack;
    }
}
