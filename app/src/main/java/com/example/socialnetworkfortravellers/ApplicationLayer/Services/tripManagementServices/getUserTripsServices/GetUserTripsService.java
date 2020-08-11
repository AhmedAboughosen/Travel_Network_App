package com.example.socialnetworkfortravellers.ApplicationLayer.Services.tripManagementServices.getUserTripsServices;

import androidx.annotation.NonNull;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.TripModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseSingleValueService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseSingleValueServiceCallBack;
import com.example.socialnetworkfortravellers.nodesLayer.TripNode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class GetUserTripsService implements IGetUserTripsService {


    private IGetDataByUseSingleValueService mQueriesGetDataService;
    private IGetUserTripsServiceCallBack mGetUserTripServiceCallBack;

    public GetUserTripsService(IGetDataByUseSingleValueService getDataByUseSingleValueService) {
        mQueriesGetDataService = getDataByUseSingleValueService;
        setUpQueriesGetDataServiceCallBack();
    }


    @Override
    public void getTrip(String userKey) {
        DatabaseReference tripQuery = FirebaseDatabase.getInstance().getReference().child(TripNode.TRIP).child(userKey);
        mQueriesGetDataService.setUpDatabaseReference(tripQuery);
        mQueriesGetDataService.getData();
    }

    @Override
    public void setGetUserTripServiceCallBack(IGetUserTripsServiceCallBack getUserTripServiceCallBack) {
        this.mGetUserTripServiceCallBack = getUserTripServiceCallBack;
    }

    private void setUpQueriesGetDataServiceCallBack() {
        mQueriesGetDataService.setUpGetDataServiceCallBack(new IGetDataByUseSingleValueServiceCallBack() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    if (dataSnapshot.exists()) {
                        List<TripModel> tripModelList = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            TripModel tripModel = snapshot.getValue(TripModel.class);
                            tripModel.getTripInterests().addAll(tripModel.getTripInterest().keySet());
                            tripModel.setTripKey(snapshot.getKey());
                            tripModelList.add(tripModel);
                        }
                        mGetUserTripServiceCallBack.getAllUserTrips(tripModelList);
                        return;
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                mGetUserTripServiceCallBack.noRelatedTrips();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mGetUserTripServiceCallBack.showMessageError(databaseError.getMessage());
            }

            @Override
            public void noInternetFound() {
                mGetUserTripServiceCallBack.noInternetFound();
            }
        });
    }

}
