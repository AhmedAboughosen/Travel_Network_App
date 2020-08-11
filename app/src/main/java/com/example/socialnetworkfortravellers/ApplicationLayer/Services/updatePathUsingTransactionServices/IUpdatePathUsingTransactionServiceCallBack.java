package com.example.socialnetworkfortravellers.ApplicationLayer.Services.updatePathUsingTransactionServices;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public interface IUpdatePathUsingTransactionServiceCallBack {
    void onComplete(DatabaseError databaseError, boolean b,
                    DataSnapshot dataSnapshot);
}
