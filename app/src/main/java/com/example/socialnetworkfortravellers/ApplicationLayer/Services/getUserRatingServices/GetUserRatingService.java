package com.example.socialnetworkfortravellers.ApplicationLayer.Services.getUserRatingServices;

import androidx.annotation.NonNull;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseSingleValueService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseSingleValueServiceCallBack;
import com.example.socialnetworkfortravellers.nodesLayer.UserInteractionNode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * responsibility of this class is to get user Rating keys with values.
 */
public class GetUserRatingService implements IGetUserRatingService {


    private IGetDataByUseSingleValueService mGetDataByUseSingleValueService;
    private IGetUserRatingServiceCallBack mGetUserRatingServiceCallBack;

    public GetUserRatingService(IGetDataByUseSingleValueService singleValueService) {
        this.mGetDataByUseSingleValueService = singleValueService;
    }

    @Override
    public void getUserRating() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(UserInteractionNode.USER_INTERACTIONS).child(UserInteractionNode.USER_RATING);
        mGetDataByUseSingleValueService.setUpDatabaseReference(databaseReference);

        HashMap<String, Object> userRating = new HashMap<>();

        mGetDataByUseSingleValueService.setUpGetDataServiceCallBack(new IGetDataByUseSingleValueServiceCallBack() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot userKey : dataSnapshot.getChildren()) {
                        String key = userKey.getKey();
                        Integer value = Integer.parseInt(userKey.getValue() + "");
                        userRating.put(key, value);
                    }
                    mGetUserRatingServiceCallBack.listOfUserRating(userRating);
                } else {
                    mGetUserRatingServiceCallBack.showMessageError("No User Exists For User Rating");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mGetUserRatingServiceCallBack.showMessageError(databaseError.getMessage());
            }

            @Override
            public void noInternetFound() {
                mGetUserRatingServiceCallBack.noInternetFound();

            }
        });
        mGetDataByUseSingleValueService.getData();
    }


    @Override
    public void setUpGetUserRatingServiceCallBack(IGetUserRatingServiceCallBack mGetUserRatingServiceCallBack) {
        this.mGetUserRatingServiceCallBack = mGetUserRatingServiceCallBack;
    }
}
