package com.example.socialnetworkfortravellers.ApplicationLayer.Services.getInterestOfUserServices;

import androidx.annotation.NonNull;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseSingleValueService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseSingleValueServiceCallBack;
import com.example.socialnetworkfortravellers.utilLayer.ConfigUtil;
import com.example.socialnetworkfortravellers.utilLayer.StringConfigUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import static com.example.socialnetworkfortravellers.nodesLayer.InterestNode.USER_INTEREST;

/**
 * responsibility of this class is to get Interest Of User.
 */
public class GetInterestOfUserService implements IGetInterestOfUserService {


    private IGetDataByUseSingleValueService mGetDataByUseSingleValueService;
    private IGetInterestOfUserServiceCallBack mGetInterestOfUserServiceCallBack;

    public GetInterestOfUserService(IGetDataByUseSingleValueService singleValueService) {
        this.mGetDataByUseSingleValueService = singleValueService;
    }


    /**
     * get the interest of user by use user Key.
     *
     * @param userKey
     */
    @Override
    public void getInterestOfUser(String userKey) {
        DatabaseReference usersInterestRef = FirebaseDatabase.getInstance().getReference().child(USER_INTEREST).child(userKey);
        mGetDataByUseSingleValueService.setUpDatabaseReference(usersInterestRef);

        HashMap<String, Object> selectedInsterest = new HashMap<>();

        mGetDataByUseSingleValueService.setUpGetDataServiceCallBack(new IGetDataByUseSingleValueServiceCallBack() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot userKey : dataSnapshot.getChildren()) {
                        String key = userKey.getKey();
                        selectedInsterest.put(key, 1);
                    }
                    mGetInterestOfUserServiceCallBack.selectedInterest(selectedInsterest);
                } else {
                    mGetInterestOfUserServiceCallBack.showMessageError(StringConfigUtil.MESSAGE_PROBLEM);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mGetInterestOfUserServiceCallBack.showMessageError(databaseError.getMessage());
            }

            @Override
            public void noInternetFound() {
                mGetInterestOfUserServiceCallBack.noInternetFound();
            }
        });
        mGetDataByUseSingleValueService.getData();
    }

    @Override
    public void setUpGetInterestOfUserServiceCallBack(IGetInterestOfUserServiceCallBack mGetInterestOfUserServiceCallBack) {
        this.mGetInterestOfUserServiceCallBack = mGetInterestOfUserServiceCallBack;
    }

    @Override
    public void removeEventListener() {
        mGetDataByUseSingleValueService.removeEventListener();
    }


}
