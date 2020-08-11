package com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.queriesGetDataServices;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class QueriesGetDataService implements IQueriesGetDataService {


    private Query mDatabaseReference;
    private IQueriesGetDataServiceCallBack mQueriesGetDataServiceCallBack;

    @Override
    public void setUpDatabaseReference(Query mDatabaseReference) {
        this.mDatabaseReference = mDatabaseReference;
    }


    @Override
    public void getData() {
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mQueriesGetDataServiceCallBack.onDataChange(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mQueriesGetDataServiceCallBack.onCancelled(databaseError);
            }
        });
    }

    @Override
    public void setUpQueriesGetDataServiceCallBack(IQueriesGetDataServiceCallBack queriesGetDataServiceCallBack) {
        this.mQueriesGetDataServiceCallBack = queriesGetDataServiceCallBack;
    }
}
