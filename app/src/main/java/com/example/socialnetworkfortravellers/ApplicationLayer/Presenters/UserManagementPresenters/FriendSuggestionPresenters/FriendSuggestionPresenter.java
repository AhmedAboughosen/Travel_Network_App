package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.FriendSuggestionPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.MessageErrorModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getUserInfoServices.IGetUserInfoService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getUserInfoServices.IGetUserInfoServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getInterestOfUserServices.IGetInterestOfUserService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getInterestOfUserServices.IGetInterestOfUserServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getRelatedInterestServices.IGetRelatedInterestService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getRelatedInterestServices.IGetRelatedInterestServiceCallBack;
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


/**
 * the responsibility of  this class is to displayed suggested friends.
 */
public class FriendSuggestionPresenter implements IFriendSuggestionPresenter {


    private IGetRelatedInterestService mGetRelatedInterestService;
    private IGetInterestOfUserService mGetInterestOfUserService;
    private List<String> list_Interest_User;
    private IFriendSuggestionPresenterCallBack mFriendSuggestionPresenterCallBack;
    private IGetUserCountryService mGetUserCountryService;
    private HashMap<String, Object> mMapListOfUsers;
    private List<String> mArrayListOfUser;
    private IGetUserRatingService mGetUserRatingService;
    private int index = 0, startAtIndex = 0, ITEM_LOAD_COUNT;
    private String userKey, mCountryName;
    private List<UserModel> listOfUser;
    private IGetUserInfoService mGetUserInfoService;
    private HashMap<String, Object> mListOfUserFollowers;


    public FriendSuggestionPresenter(IGetRelatedInterestService getRelatedInterestService, IGetInterestOfUserService getInterestOfUserService, IGetUserCountryService getUserCountryService
            , IGetUserRatingService getUserRatingService, IGetUserInfoService getUserInfoService) {
        this.mGetRelatedInterestService = getRelatedInterestService;
        this.mGetInterestOfUserService = getInterestOfUserService;
        this.mGetUserCountryService = getUserCountryService;
        this.mGetUserRatingService = getUserRatingService;
        this.mMapListOfUsers = new HashMap<>();
        mListOfUserFollowers = new HashMap<>();
        this.mArrayListOfUser = new ArrayList<>();
        this.mGetUserInfoService = getUserInfoService;
        startAtIndex = 0;
        listOfUser = new ArrayList<>();
        setUpGetUserInfoServiceCallBack();

    }


    @Override
    public void getSuggestionFriends(String userKey, String countryName) {
        this.userKey = userKey;
        this.mCountryName = countryName;
        getInterestOfUser();
    }

    /**
     * get interest of user.
     */
    private void getInterestOfUser() {
        this.mGetInterestOfUserService.setUpGetInterestOfUserServiceCallBack(new IGetInterestOfUserServiceCallBack() {
            @Override
            public void selectedInterest(HashMap<String, Object> list) {

                //Getting Set of keys from HashMap
                Set<String> keySet = list.keySet();

                //Creating an ArrayList of keys by passing the keySet
                list_Interest_User = new ArrayList<>(keySet);

                getRelatedInterest();
            }

            @Override
            public void showMessageError(String message) {
                mFriendSuggestionPresenterCallBack.showMessageError(new MessageErrorModel(1, "we can't get your Interest from package, this service  not work correctly + \n" + message));
                getUsersOfCountry();
            }

            @Override
            public void noInternetFound() {
                mFriendSuggestionPresenterCallBack.noInternetFound();
            }
        });

        this.mGetInterestOfUserService.getInterestOfUser(userKey);

    }


    /**
     * for each interest  of current user get friends which have same interest.
     */
    private void getRelatedInterest() {
        if (list_Interest_User.size() > 0) {
            mGetRelatedInterestService.setUpGetRelatedInterestServiceCallBack(new IGetRelatedInterestServiceCallBack() {
                @Override
                public void usersInCurrentInterest(HashMap<String, Object> list) {

                    if (index < list_Interest_User.size()) {
                        mMapListOfUsers.putAll(list);
                        mGetRelatedInterestService.getUsersInInterest(list_Interest_User.get(index));
                        index++;
                    } else {
                        mMapListOfUsers.putAll(list);
                        getUsersOfCountry();
                    }
                }

                @Override
                public void showMessageError(String message) {
                    if (index < list_Interest_User.size()) {
                        mGetRelatedInterestService.getUsersInInterest(list_Interest_User.get(index));
                        index++;
                    } else {
                        getUsersOfCountry();
                    }
                }

                @Override
                public void noInternetFound() {
                    mFriendSuggestionPresenterCallBack.noInternetFound();
                }
            });

            mGetRelatedInterestService.getUsersInInterest(list_Interest_User.get(index));
            index++;
        } else {
            getUsersOfCountry();
        }

    }


    /**
     * friends which is from same  user country
     */
    private void getUsersOfCountry() {

        mGetUserCountryService.setUpGetUserCountryServiceCallBack(new IGetUserCountryServiceCallBack() {
            @Override
            public void usersInCurrentCountry(HashMap<String, Object> users) {
                mMapListOfUsers.putAll(users);
                getRatingOfUser();
            }

            @Override
            public void showMessageError(String message) {
                mFriendSuggestionPresenterCallBack.showMessageError(new MessageErrorModel(2, "we can't get your friends from your country or there is not  friends with same country in this app  , this service  not work correctly +" + message));
                getRatingOfUser();
            }

            @Override
            public void noInternetFound() {
                mFriendSuggestionPresenterCallBack.noInternetFound();
            }
        });

        mGetUserCountryService.getUserCountry(this.mCountryName);
    }


    /**
     * get number of followers for each user then sort data based on values.
     */
    private void getRatingOfUser() {
        mGetUserRatingService.setUpGetUserRatingServiceCallBack(new IGetUserRatingServiceCallBack() {
            @Override
            public void listOfUserRating(HashMap<String, Object> lis) {
                mListOfUserFollowers = lis;
                updateRatingUserValue(lis);
                finalResultOfSuggestionUserData();
            }

            @Override
            public void showMessageError(String message) {
                //  mFriendSuggestionPresenterCallBack.showMessageError(new MessageErrorModel(3, message));

                finalResultOfSuggestionUserData();
            }

            @Override
            public void noInternetFound() {
                mFriendSuggestionPresenterCallBack.noInternetFound();
            }
        });

        mGetUserRatingService.getUserRating();
    }


    private void updateRatingUserValue(HashMap<String, Object> lis) {
        for (Map.Entry<String, Object> entry : lis.entrySet()) {

            if (mMapListOfUsers.containsKey(entry.getKey())) {
                        /*
                        update value
                         */
                mMapListOfUsers.put(entry.getKey(), entry.getValue());
            }
        }

    }

    private void finalResultOfSuggestionUserData() {
        mMapListOfUsers.remove(CurrentUserIDUtil.getInstance().getCurrentUserID());
        if (mMapListOfUsers.size() > 0) {
            mMapListOfUsers = sortUserRating(mMapListOfUsers);
            Set<String> keySet = mMapListOfUsers.keySet();

            //Creating an ArrayList of keys by passing the keySet
            mArrayListOfUser = new ArrayList<>(keySet);
            loadMoreUser();
        } else {
            mFriendSuggestionPresenterCallBack.thereIsNoDataToProvide();
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


    /**
     * start load user data and paging.
     */
    @Override
    public void loadMoreUser() {
        if (startAtIndex < this.mArrayListOfUser.size()) {
            ITEM_LOAD_COUNT = 5;
            listOfUser = new ArrayList<>();
            mFriendSuggestionPresenterCallBack.updateLoading(true);
            mFriendSuggestionPresenterCallBack.addFakeData();
            this.mGetUserInfoService.getUserInfo(this.mArrayListOfUser.get(startAtIndex));
            startAtIndex++;
        } else {
            mFriendSuggestionPresenterCallBack.listIsFinished();
        }
    }


    /**
     * setUpGetUserInfoServiceCallBack
     */
    private void setUpGetUserInfoServiceCallBack() {
        mGetUserInfoService.setUpGetUserInfoServiceCallBack(new IGetUserInfoServiceCallBack() {
            @Override
            public void userData(UserModel mUserModel) {

                if (mListOfUserFollowers.containsKey(mUserModel.getUserInfoModel().getKeyOfUser())) {
                    mUserModel.getUserInfoModel().setNumber_Of_Followers((int) mListOfUserFollowers.get(mUserModel.getUserInfoModel().getKeyOfUser()));
                }


                if (startAtIndex < mArrayListOfUser.size() && ITEM_LOAD_COUNT > 0) {
                    listOfUser.add(mUserModel);
                    mGetUserInfoService.getUserInfo(mArrayListOfUser.get(startAtIndex));
                    ++startAtIndex;
                    --ITEM_LOAD_COUNT;
                } else {
                    listOfUser.add(mUserModel);
                    ITEM_LOAD_COUNT = 5;
                    mFriendSuggestionPresenterCallBack.updateLoading(false);
                    mFriendSuggestionPresenterCallBack.removeFakeData();
                    mFriendSuggestionPresenterCallBack.updateFriendsList(listOfUser);
                }
            }

            @Override
            public void showMessageError(String message) {
                loadMoreUserWhenSomethingWrong();
            }

            @Override
            public void thisUserNotExists() {
                loadMoreUserWhenSomethingWrong();
            }

            @Override
            public void noInternetFound() {
                mFriendSuggestionPresenterCallBack.noInternetFound();
            }
        });
    }

    /**
     * load more user when something wrong with this user
     */
    private void loadMoreUserWhenSomethingWrong() {
        if (startAtIndex < mArrayListOfUser.size() && ITEM_LOAD_COUNT > 0) {
            mGetUserInfoService.getUserInfo(mArrayListOfUser.get(startAtIndex));
            ++startAtIndex;
            --ITEM_LOAD_COUNT;
        } else {
            ITEM_LOAD_COUNT = 5;
            mFriendSuggestionPresenterCallBack.updateLoading(false);
            mFriendSuggestionPresenterCallBack.removeFakeData();
            mFriendSuggestionPresenterCallBack.updateFriendsList(listOfUser);
        }
    }


    @Override
    public void setUpFriendSuggestionPresenterCallBack(IFriendSuggestionPresenterCallBack mFriendSuggestionPresenterCallBack) {
        this.mFriendSuggestionPresenterCallBack = mFriendSuggestionPresenterCallBack;
    }
}
