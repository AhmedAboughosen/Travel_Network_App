package com.example.socialnetworkfortravellers.ApplicationLayer.Services.tripManagementServices.getRelatedTripServices;

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

public class GetRelatedTripService implements IGetRelatedTripService {


    private IGetDataByUseSingleValueService mQueriesGetDataService;
    private IGetRelatedTripServiceCallBack mGetRelatedTripServiceCallBack;

    public GetRelatedTripService(IGetDataByUseSingleValueService getDataByUseSingleValueService) {
        mQueriesGetDataService = getDataByUseSingleValueService;
        setUpQueriesGetDataServiceCallBack();
    }


    @Override
    public void getRelatedTrip(String countryName) {
        DatabaseReference topRelatedTripQuery = FirebaseDatabase.getInstance().getReference().child(TripNode.TRIP_COUNTRY).child(countryName);
        mQueriesGetDataService.setUpDatabaseReference(topRelatedTripQuery);
        mQueriesGetDataService.getData();
    }

    @Override
    public void setGetRelatedTripServiceCallBack(IGetRelatedTripServiceCallBack mGetRelatedTripServiceCallBack) {
        this.mGetRelatedTripServiceCallBack = mGetRelatedTripServiceCallBack;
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
                            if (tripModel != null) {
                                tripModelList.add(tripModel);
                            }
                        }
                        mGetRelatedTripServiceCallBack.getRelatedTrips(tripModelList);
                        return;
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                mGetRelatedTripServiceCallBack.noRelatedTrips();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mGetRelatedTripServiceCallBack.showMessageError(databaseError.getMessage());
            }

            @Override
            public void noInternetFound() {
                mGetRelatedTripServiceCallBack.noInternetFound();
            }
        });
    }

}
