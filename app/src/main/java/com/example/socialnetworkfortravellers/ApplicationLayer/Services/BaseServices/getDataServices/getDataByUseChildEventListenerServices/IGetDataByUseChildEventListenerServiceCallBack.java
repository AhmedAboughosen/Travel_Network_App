package com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.getDataByUseChildEventListenerServices;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.IBaseServiceCallBack;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public interface IGetDataByUseChildEventListenerServiceCallBack extends IBaseServiceCallBack {
    void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s);

    void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s);

    void onChildRemoved(@NonNull DataSnapshot dataSnapshot);

    void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s);

    void onCancelled(@NonNull DatabaseError databaseError);
}
