package com.example.socialnetworkfortravellers.ApplicationLayer.Services.tripManagementServices.deleteTripServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.removeDataServices.IRemoveDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.removeDataServices.IRemoveDataServiceCallBack;
import com.example.socialnetworkfortravellers.nodesLayer.TripNode;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteTripService implements IDeleteTripService {


    private IDeleteTripServiceCallBack mDeleteTripServiceCallBack;
    private IRemoveDataService mRemoveDataService;
    private String mTripKey, mUserKey;

    public DeleteTripService(IRemoveDataService removeDataService) {
        this.mRemoveDataService = removeDataService;
    }

    @Override
    public void setUpDeleteTripServiceCallBack(IDeleteTripServiceCallBack deleteTripServiceCallBack) {
        this.mDeleteTripServiceCallBack = deleteTripServiceCallBack;
    }


    @Override
    public void deleteTrip(String tripKey, String userKey) {
        this.mTripKey = tripKey;
        this.mUserKey = userKey;
        removeTrip();
    }


    private void removeTrip() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(TripNode.TRIP).child(mUserKey).child(mTripKey);
        mRemoveDataService.setUpDatabaseReference(databaseReference);
        mRemoveDataService.setUpRemoveDataServiceCallBack(new IRemoveDataServiceCallBack() {
            @Override
            public void failure(String message) {
                mDeleteTripServiceCallBack.failure(message);
            }

            @Override
            public void isSuccessful() {
                mDeleteTripServiceCallBack.isSuccessful();
            }
        });

        mRemoveDataService.removeData();
    }


}
