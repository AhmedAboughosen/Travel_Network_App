package com.example.socialnetworkfortravellers.ApplicationLayer.Services.tripManagementServices.addTripSerivces;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.TripModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataServiceCallBack;
import com.example.socialnetworkfortravellers.InfrastructureLayer.ConstantValues;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;

import static com.example.socialnetworkfortravellers.nodesLayer.TripNode.COUNTRY_NAME;
import static com.example.socialnetworkfortravellers.nodesLayer.TripNode.FROM;
import static com.example.socialnetworkfortravellers.nodesLayer.TripNode.SUMMARY;
import static com.example.socialnetworkfortravellers.nodesLayer.TripNode.TIMESTAMP;
import static com.example.socialnetworkfortravellers.nodesLayer.TripNode.TO;
import static com.example.socialnetworkfortravellers.nodesLayer.TripNode.TRIP;
import static com.example.socialnetworkfortravellers.nodesLayer.TripNode.TRIP_COUNTRY;
import static com.example.socialnetworkfortravellers.nodesLayer.TripNode.TRIP_INTEREST;
import static com.example.socialnetworkfortravellers.nodesLayer.TripNode.USER_ID;

public class AddTripService implements IAddTripService {


    private ISaveRawDataService mSaveRawDataService;
    private IAddTripServiceCallBack mAddTripServiceCallBack;
    private String tripKey, mUserKey;
    private TripModel mTripModel;


    public AddTripService(ISaveRawDataService saveRawDataService) {
        this.mSaveRawDataService = saveRawDataService;
    }


    @Override
    public void addTripContent(TripModel tripModel, String userKey) {
        this.mUserKey = userKey;
        this.mTripModel = tripModel;

        DatabaseReference tripsRef = FirebaseDatabase.getInstance().getReference().child(TRIP).child(userKey);
        HashMap<String, Object> tripContent = new HashMap<>();
        tripContent.put(COUNTRY_NAME, tripModel.getCountryName());
        tripContent.put(FROM, tripModel.getFrom());
        tripContent.put(TO, tripModel.getTo());
        tripContent.put(SUMMARY, tripModel.getSummary());
        tripContent.put(TIMESTAMP, ServerValue.TIMESTAMP);

        tripKey = tripsRef.push().getKey();
        if (tripKey != null) {
            tripsRef = tripsRef.child(tripKey);

            mSaveRawDataService.setDatabaseReference(tripsRef);
            mSaveRawDataService.setMapData(tripContent);
            setUpSaveTripServiceCallBack();
            mSaveRawDataService.updateData();
        } else {
            mAddTripServiceCallBack.showMessageError(ConstantValues.SERVER_PROBLEM);
        }
    }

    private void setUpSaveTripServiceCallBack() {
        mSaveRawDataService.setUpSaveInfoServiceCallBack(new ISaveRawDataServiceCallBack() {
            @Override
            public void showMessageError(String message) {
                mAddTripServiceCallBack.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mAddTripServiceCallBack.noInternetFound();
            }

            @Override
            public void Successful() {
                if (mTripModel.getTripInterests().size() == 0) {
                    saveTripCountryNode();
                    return;
                }
                saveTripInterest();
            }

        });
    }


    private void saveTripInterest() {
        DatabaseReference tripsRef = FirebaseDatabase.getInstance().getReference().child(TRIP).child(mUserKey).child(tripKey).child(TRIP_INTEREST);
        HashMap<String, Object> tripInterestContent = new HashMap<>();
        for (int i = 0; i < mTripModel.getTripInterests().size(); i++) {
            tripInterestContent.put(mTripModel.getTripInterests().get(i), true);
        }
        mSaveRawDataService.setDatabaseReference(tripsRef);
        mSaveRawDataService.setMapData(tripInterestContent);
        setUpSaveTripInterestServiceCallBack();
        mSaveRawDataService.updateData();
    }

    private void setUpSaveTripInterestServiceCallBack() {
        mSaveRawDataService.setUpSaveInfoServiceCallBack(new ISaveRawDataServiceCallBack() {
            @Override
            public void showMessageError(String message) {
                mAddTripServiceCallBack.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mAddTripServiceCallBack.noInternetFound();
            }

            @Override
            public void Successful() {
                saveTripCountryNode();
            }

        });
    }


    private void saveTripCountryNode() {
        DatabaseReference tripsRef = FirebaseDatabase.getInstance().getReference().child(TRIP_COUNTRY).child(mTripModel.getCountryName()).child(tripKey);
        HashMap<String, Object> tripContent = new HashMap<>();
        tripContent.put(FROM, mTripModel.getFrom());
        tripContent.put(TO, mTripModel.getTo());
        tripContent.put(USER_ID, mUserKey);

        mSaveRawDataService.setDatabaseReference(tripsRef);
        mSaveRawDataService.setMapData(tripContent);
        setUpSaveTripCountryServiceCallBack();
        mSaveRawDataService.updateData();
    }

    private void setUpSaveTripCountryServiceCallBack() {
        mSaveRawDataService.setUpSaveInfoServiceCallBack(new ISaveRawDataServiceCallBack() {
            @Override
            public void Successful() {
                mAddTripServiceCallBack.Successful();
            }

            public void showMessageError(String message) {
                mAddTripServiceCallBack.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mAddTripServiceCallBack.noInternetFound();
            }
        });
    }

    @Override
    public void setUpAddTripServiceCallBack(IAddTripServiceCallBack mAddTripServiceCallBack) {
        this.mAddTripServiceCallBack = mAddTripServiceCallBack;
    }
}
