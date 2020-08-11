package com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.getDataByUseChildEventListenerServices;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.socialnetworkfortravellers.utilLayer.NetworkState;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public class GetDataByUseChildEventListenerService implements IGetDataByUseChildEventListenerService {

    private IGetDataByUseChildEventListenerServiceCallBack mGetDataByUseChildEventListenerServiceCallBack;
    private DatabaseReference mDatabaseReference;
    private Context mContext;
    private ChildEventListener mChildEventListener;


    public GetDataByUseChildEventListenerService(Context context) {
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
            mGetDataByUseChildEventListenerServiceCallBack.noInternetFound();
            return;
        }

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                mGetDataByUseChildEventListenerServiceCallBack.onChildAdded(dataSnapshot, s);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                mGetDataByUseChildEventListenerServiceCallBack.onChildChanged(dataSnapshot, s);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                mGetDataByUseChildEventListenerServiceCallBack.onChildRemoved(dataSnapshot);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                mGetDataByUseChildEventListenerServiceCallBack.onChildMoved(dataSnapshot, s);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mGetDataByUseChildEventListenerServiceCallBack.onCancelled(databaseError);
            }
        };

        mDatabaseReference.addChildEventListener(mChildEventListener);

    }


    @Override
    public void removeEventListener() {
        mDatabaseReference.removeEventListener(mChildEventListener);

    }

    @Override
    public void setUpGetDataByUseChildEventListenerServiceCallBack(IGetDataByUseChildEventListenerServiceCallBack mGetDataByUseChildEventListenerServiceCallBack) {
        this.mGetDataByUseChildEventListenerServiceCallBack = mGetDataByUseChildEventListenerServiceCallBack;
    }
}
