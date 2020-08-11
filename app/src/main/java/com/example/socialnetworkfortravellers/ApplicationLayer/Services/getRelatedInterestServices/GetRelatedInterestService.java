package com.example.socialnetworkfortravellers.ApplicationLayer.Services.getRelatedInterestServices;

import androidx.annotation.NonNull;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseSingleValueService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseSingleValueServiceCallBack;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import static com.example.socialnetworkfortravellers.nodesLayer.InterestNode.RELATED_INTEREST;
import static com.example.socialnetworkfortravellers.utilLayer.StringConfigUtil.NO_INTEREST_USER_EXISTS;

/**
 * responsibility of this class is to get related interest , pass interest name get all users which like interest name.
 */
public class GetRelatedInterestService implements IGetRelatedInterestService {


    private IGetDataByUseSingleValueService mGetDataByUseSingleValueService;
    private IGetRelatedInterestServiceCallBack mGetRelatedInterestServiceCallBack;

    public GetRelatedInterestService(IGetDataByUseSingleValueService singleValueService) {
        this.mGetDataByUseSingleValueService = singleValueService;
    }


    /**
     * get all users which like passed  interestName.
     *
     * @param interestName
     */
    @Override
    public void getUsersInInterest(String interestName) {
        DatabaseReference usersInterestRef = FirebaseDatabase.getInstance().getReference().child(RELATED_INTEREST).child(interestName);
        mGetDataByUseSingleValueService.setUpDatabaseReference(usersInterestRef);

        HashMap<String, Object> selectedInterest = new HashMap<>();

        mGetDataByUseSingleValueService.setUpGetDataServiceCallBack(new IGetDataByUseSingleValueServiceCallBack() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userKey : dataSnapshot.getChildren()) {
                        String key = userKey.getKey();
                        selectedInterest.put(key, 1);
                    }
                    mGetRelatedInterestServiceCallBack.usersInCurrentInterest(selectedInterest);
                } else {
                    mGetRelatedInterestServiceCallBack.showMessageError(NO_INTEREST_USER_EXISTS);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mGetRelatedInterestServiceCallBack.showMessageError(databaseError.getMessage());

            }

            @Override
            public void noInternetFound() {
                mGetRelatedInterestServiceCallBack.noInternetFound();

            }
        });


        mGetDataByUseSingleValueService.getData();
    }

    @Override
    public void setUpGetRelatedInterestServiceCallBack(IGetRelatedInterestServiceCallBack mGetRelatedInterestServiceCallBack) {
        this.mGetRelatedInterestServiceCallBack = mGetRelatedInterestServiceCallBack;
    }
}
