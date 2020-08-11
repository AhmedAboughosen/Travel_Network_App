package com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public interface IGetDataByUseAddValueEventServiceCallBack {
    void onDataChange(@NonNull DataSnapshot dataSnapshot);

    void onCancelled(@NonNull DatabaseError databaseError);

    void noInternetFound();
}
