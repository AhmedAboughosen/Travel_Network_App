package com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.socialnetworkfortravellers.utilLayer.NetworkState;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * responsibility of this class is to get data from database by used Single Value Method.
 */
public class GetDataByUseSingleValueService implements IGetDataByUseSingleValueService {

    private DatabaseReference mDatabaseReference;
    private IGetDataByUseSingleValueServiceCallBack mGetDataServiceCallBack;
    private Context mContext;
    private ValueEventListener eventListener;


    public GetDataByUseSingleValueService(Context context) {
        this.mContext = context;
    }


    /**
     * set up url which you want get data from.
     *
     * @param mDatabaseReference
     */
    @Override
    public void setUpDatabaseReference(DatabaseReference mDatabaseReference) {
        this.mDatabaseReference = mDatabaseReference;
    }


    private boolean checkForInternet() {

        return NetworkState.isNetworkConnected(this.mContext);
    }


    /**
     * get data from firebase
     */
    @Override
    public void getData() {


        if (!checkForInternet()) {
            mGetDataServiceCallBack.noInternetFound();
            return;
        }

        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mGetDataServiceCallBack.onDataChange(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mGetDataServiceCallBack.onCancelled(databaseError);
            }
        };

        mDatabaseReference.addListenerForSingleValueEvent(eventListener);

    }

    @Override
    public void setUpGetDataServiceCallBack(IGetDataByUseSingleValueServiceCallBack mGetDataServiceCallBack) {
        this.mGetDataServiceCallBack = mGetDataServiceCallBack;
    }


    @Override
    public void removeEventListener() {
        if (mDatabaseReference != null && eventListener != null){
            mDatabaseReference.removeEventListener(eventListener);
        }
    }
}
