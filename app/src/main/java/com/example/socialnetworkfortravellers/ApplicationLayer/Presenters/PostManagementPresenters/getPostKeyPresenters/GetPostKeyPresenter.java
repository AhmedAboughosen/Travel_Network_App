package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.getPostKeyPresenters;

import androidx.annotation.NonNull;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.getLastPostKeyServices.IGetPostKeyService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.getLastPostKeyServices.IGetPostKeyServiceCallBack;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * this class used to get post key then sort post then get last key
 */
public class GetPostKeyPresenter implements IGetPostKeyPresenter {


    private IGetPostKeyPresenterCallBack mGetPostKeyPresenterCallBack;
    private IGetPostKeyService mGetPostKeyService;
    private HashMap<String, Object> userKeyMap;

    public GetPostKeyPresenter(IGetPostKeyService getPostKeyService) {
        this.mGetPostKeyService = getPostKeyService;
        userKeyMap = new HashMap<>();
        setGetLastPostKeyServiceCallBack();
    }


    @Override
    public void getKeysOfPost(String userKey) {
        mGetPostKeyService.getKeysOfPost(userKey);
    }

    private void setGetLastPostKeyServiceCallBack() {
        mGetPostKeyService.setGetLastPostKeyServiceCallBack(new IGetPostKeyServiceCallBack() {
            @Override
            public void postKeys(HashMap<String, Object> map) {

                if (map.size() >= 1) {
                    userKeyMap = sortPostKeyBasedOnDate(map);
                    mGetPostKeyPresenterCallBack.sortedPost(userKeyMap);
                } else {
                    mGetPostKeyPresenterCallBack.noPostsExists();
                }

            }

            @Override
            public void showMessageError(@NonNull String databaseError) {
                mGetPostKeyPresenterCallBack.showMessageError(databaseError);
            }

            @Override
            public void noInternetFound() {
                mGetPostKeyPresenterCallBack.networkErrorMessage();
            }
        });
    }


    // function to sort hashmap by values
    private HashMap<String, Object> sortPostKeyBasedOnDate(HashMap<String, Object> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Object>> list =
                new LinkedList<>(hm.entrySet());

        // Sort the list
        Collections.sort(list, (o1, o2) -> ((Long) o1.getValue()).compareTo((Long) o2.getValue()));

        // put data from sorted list to hashmap
        HashMap<String, Object> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Object> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }


    @Override
    public void setUpGetPostKeyPresenterCallBack(IGetPostKeyPresenterCallBack mGetPostKeyPresenterCallBack) {
        this.mGetPostKeyPresenterCallBack = mGetPostKeyPresenterCallBack;
    }
}
