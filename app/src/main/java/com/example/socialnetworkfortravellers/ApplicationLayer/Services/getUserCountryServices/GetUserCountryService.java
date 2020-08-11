package com.example.socialnetworkfortravellers.ApplicationLayer.Services.getUserCountryServices;

import androidx.annotation.NonNull;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseSingleValueService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseSingleValueServiceCallBack;
import com.example.socialnetworkfortravellers.nodesLayer.UserNode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import static com.example.socialnetworkfortravellers.utilLayer.StringConfigUtil.NO_USERS_IN_YOUR_COUNTRY_EXISTS;

/**
 * responsibility of this class is to get users in country name.
 */
public class GetUserCountryService implements IGetUserCountryService {

    private IGetDataByUseSingleValueService mGetDataByUseSingleValueService;
    private IGetUserCountryServiceCallBack mGetUserCountryServiceCallBack;

    public GetUserCountryService(IGetDataByUseSingleValueService singleValueService) {
        this.mGetDataByUseSingleValueService = singleValueService;
    }


    @Override
    public void getUserCountry(String countryName) {
        DatabaseReference usersInterestRef = FirebaseDatabase.getInstance().getReference().child(UserNode.COUNTRY).child(countryName);
        mGetDataByUseSingleValueService.setUpDatabaseReference(usersInterestRef);

        HashMap<String, Object> selectedCountries = new HashMap<>();

        mGetDataByUseSingleValueService.setUpGetDataServiceCallBack(new IGetDataByUseSingleValueServiceCallBack() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot userKey : dataSnapshot.getChildren()) {
                        String key = userKey.getKey();
                        selectedCountries.put(key, 1);
                    }
                    mGetUserCountryServiceCallBack.usersInCurrentCountry(selectedCountries);
                } else {
                    mGetUserCountryServiceCallBack.showMessageError(NO_USERS_IN_YOUR_COUNTRY_EXISTS);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mGetUserCountryServiceCallBack.showMessageError(databaseError.getMessage());
            }

            @Override
            public void noInternetFound() {
                mGetUserCountryServiceCallBack.noInternetFound();
            }
        });
        mGetDataByUseSingleValueService.getData();
    }

    @Override
    public void setUpGetUserCountryServiceCallBack(IGetUserCountryServiceCallBack mGetUserCountryServiceCallBack) {
        this.mGetUserCountryServiceCallBack = mGetUserCountryServiceCallBack;
    }
}
