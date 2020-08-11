package com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.queriesGetDataServices;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public interface IQueriesGetDataServiceCallBack {

    void onDataChange(@NonNull DataSnapshot dataSnapshot);

    void onCancelled(@NonNull DatabaseError databaseError);

}
