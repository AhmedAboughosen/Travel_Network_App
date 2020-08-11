package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.NewsFeedPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getListOfUserServices.IGetListOfUserService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getListOfUserServices.IGetListOfUserServiceCallback;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getUserCountryServices.IGetUserCountryService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getUserCountryServices.IGetUserCountryServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getUserRatingServices.IGetUserRatingService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getUserRatingServices.IGetUserRatingServiceCallBack;
import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserTrendingAndNearByPresenter implements IUserTrendingAndNearByPresenter {


    private IGetListOfUserService mGetListOfUserService;
    private IGetUserCountryService mGetUserCountryService;
    private IUserTrendingAndNearByPresenterCallback mUserTrendingAndNearByPresenterCallback;
    private IGetUserRatingService mGetUserRatingService;
    private HashMap<String, Object> listOfUserRating;
    private List<String> userKeys;
    private IGetListOfUserService mGetListOfUserForUserRatingService;


    public UserTrendingAndNearByPresenter(IGetListOfUserService getListOfUserService, IGetUserCountryService getUserCountryService,
                                          IGetUserRatingService getUserRatingService, IGetListOfUserService getListOfUserForUserRatingService) {
        this.mGetListOfUserService = getListOfUserService;
        this.mGetUserCountryService = getUserCountryService;
        this.mGetUserRatingService = getUserRatingService;
        this.mGetListOfUserForUserRatingService = getListOfUserForUserRatingService;

        this.listOfUserRating = new HashMap<>();
        setUpGetListOfUserForNearByTravellersServiceCallback();
        setUpGetUserCountryServiceCallBack();
        setUpGetUserRatingServiceCallBack();
        setUpGetListOfUserServiceCallback();
    }


    public void setUpUserTrendingAndNearByPresenterCallback(IUserTrendingAndNearByPresenterCallback newsFeedPresenterCallback) {
        this.mUserTrendingAndNearByPresenterCallback = newsFeedPresenterCallback;
    }

    private void setUpGetListOfUserForNearByTravellersServiceCallback() {
        mGetListOfUserService.setUpGetListOfUserServiceCallback(new IGetListOfUserServiceCallback() {
            @Override
            public void ListOfUsers(List<UserModel> userModels) {
                mUserTrendingAndNearByPresenterCallback.ListOfUsersForNearByTravellers(userModels);
            }

            @Override
            public void internetIsNotConnected() {
                mUserTrendingAndNearByPresenterCallback.networkErrorMessage();
            }
        });
    }


    private void setUpGetUserRatingServiceCallBack() {
        mGetUserRatingService.setUpGetUserRatingServiceCallBack(new IGetUserRatingServiceCallBack() {
            @Override
            public void listOfUserRating(HashMap<String, Object> lis) {
                listOfUserRating = lis;
                finalResultOfSuggestionUserData();
            }

            @Override
            public void showMessageError(String message) {
                mUserTrendingAndNearByPresenterCallback.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mUserTrendingAndNearByPresenterCallback.networkErrorMessage();
            }
        });
    }


    private void finalResultOfSuggestionUserData() {
        listOfUserRating.remove(CurrentUserIDUtil.getInstance().getCurrentUserID());
        if (listOfUserRating.size() > 0) {
            listOfUserRating = sortUserRating(listOfUserRating);
            Set<String> keySet = listOfUserRating.keySet();

            //Creating an ArrayList of keys by passing the keySet
            userKeys = new ArrayList<>(keySet);
            loadUserForTendingUser(userKeys);
        }
    }


    // function to sort hashmap by values
    private HashMap<String, Object> sortUserRating(HashMap<String, Object> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Object>> list =
                new LinkedList<>(hm.entrySet());

        // Sort the list
        Collections.sort(list, (o1, o2) -> ((Integer) o2.getValue()).compareTo((Integer) o1.getValue()));

        // put data from sorted list to hashmap
        HashMap<String, Object> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Object> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    private void setUpGetListOfUserServiceCallback() {
        mGetListOfUserForUserRatingService.setUpGetListOfUserServiceCallback(new IGetListOfUserServiceCallback() {
            @Override
            public void ListOfUsers(List<UserModel> userModels) {
                mUserTrendingAndNearByPresenterCallback.ListOfUsersForUserTrending(userModels);
            }

            @Override
            public void internetIsNotConnected() {
                mUserTrendingAndNearByPresenterCallback.networkErrorMessage();
            }
        });
    }

    private void loadUserForTendingUser(List<String> list) {
        mGetListOfUserForUserRatingService.getUsers((list.size() >= 30) ? list.subList(0, 30) : list);
    }


    @Override
    public void getUserRating() {
        mGetUserRatingService.getUserRating();
    }


    private void setUpGetUserCountryServiceCallBack() {
        mGetUserCountryService.setUpGetUserCountryServiceCallBack(new IGetUserCountryServiceCallBack() {
            @Override
            public void usersInCurrentCountry(HashMap<String, Object> users) {

                Set<String> userKeys = users.keySet();
                //Creating an ArrayList of keys by passing the keySet
                ArrayList<String> list = new ArrayList<>(userKeys);
                getUsers((list.size() >= 30) ? list.subList(0, 30) : list);
            }

            @Override
            public void showMessageError(String message) {
                mUserTrendingAndNearByPresenterCallback.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mUserTrendingAndNearByPresenterCallback.networkErrorMessage();
            }
        });
    }

    private void getUsers(List<String> users) {
        mGetListOfUserService.getUsers(users);
    }


    @Override
    public void getUserNearBy(String country) {
        mGetUserCountryService.getUserCountry(country);
    }




}
