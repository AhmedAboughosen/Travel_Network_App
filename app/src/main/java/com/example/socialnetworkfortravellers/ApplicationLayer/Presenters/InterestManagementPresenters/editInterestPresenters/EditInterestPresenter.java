package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.InterestManagementPresenters.editInterestPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.InterestModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getInterestOfUserServices.IGetInterestOfUserService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getInterestOfUserServices.IGetInterestOfUserServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.saveRelatedInterestServices.ISaveUserRelatedInterestService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.saveRelatedInterestServices.ISaveUserRelatedInterestServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.saveUserInterestServices.ISaveUserInterestService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.saveUserInterestServices.ISaveUserInterestServiceCallBack;
import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EditInterestPresenter implements IEditInterestPresenter {

    private IGetInterestOfUserService mGetInterestOfUserService;
    private IEditInterestPresenterCallBack mEditInterestPresenterCallBack;
    private HashMap<String, Object> selectedInterest;
    private List<InterestModel> mListInterestModel;
    private ISaveUserInterestService mSaveUserInterestService;
    private ISaveUserRelatedInterestService mSaveUserRelatedInterestService;


    public EditInterestPresenter(IGetInterestOfUserService getInterestOfUserService, ISaveUserInterestService saveUserInterestService, ISaveUserRelatedInterestService saveUserRelatedInterestService) {
        this.mGetInterestOfUserService = getInterestOfUserService;
        selectedInterest = new HashMap<>();
        mListInterestModel = new ArrayList<>();
        this.mSaveUserInterestService = saveUserInterestService;
        this.mSaveUserRelatedInterestService = saveUserRelatedInterestService;
    }

    /**
     * get interest of user
     */
    @Override
    public void getInterestOfUser() {

        mGetInterestOfUserService.setUpGetInterestOfUserServiceCallBack(new IGetInterestOfUserServiceCallBack() {
            @Override
            public void selectedInterest(HashMap<String, Object> list) {
                selectedInterest = new HashMap<>(list);
                mEditInterestPresenterCallBack.getDataSuccessfully();
            }

            @Override
            public void showMessageError(String message) {
                mEditInterestPresenterCallBack.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mEditInterestPresenterCallBack.noInternetFound();
            }
        });

        mGetInterestOfUserService.getInterestOfUser(CurrentUserIDUtil.getInstance().getCurrentUserID());
    }


    /**
     * updateMarkedInterest
     *
     * @param list
     */
    @Override
    public void updateMarkedInterest(List<InterestModel> list) {

        for (int i = 0; i < list.size(); i++) {
            if (selectedInterest.containsKey(list.get(i).getHobbyName())) {
                list.get(i).setMarked(true);
            }
        }

        mListInterestModel = new ArrayList<>(list);

        mEditInterestPresenterCallBack.updateMarkedInterest(mListInterestModel);
    }


    /**
     * get marked Object then save data to firebase.
     *
     * @param interestModels
     */
    @Override
    public void updateInterest(List<InterestModel> interestModels) {

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
                mEditInterestPresenterCallBack.noItemSelected();
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

            }

            @Override
            public void noInternetFound() {

            }

            @Override
            public void Successful() {
                saveRelatedInterest(selectedInterestModels);
            }

            //@Override
            public void failure(String message) {
                mEditInterestPresenterCallBack.showMessageError(message);
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

            }

            @Override
            public void noInternetFound() {

            }

            @Override
            public void Successful() {
                mEditInterestPresenterCallBack.updateDataSuccessful();
            }

            //@Override
            public void failure(String message) {
                mEditInterestPresenterCallBack.showMessageError(message);
            }
        });

        this.mSaveUserRelatedInterestService.saveRelatedInterest(selectedInterestModels);
    }



    public void setEditInterestPresenterCallBack(IEditInterestPresenterCallBack mEditInterestPresenterCallBack) {
        this.mEditInterestPresenterCallBack = mEditInterestPresenterCallBack;
    }
}
