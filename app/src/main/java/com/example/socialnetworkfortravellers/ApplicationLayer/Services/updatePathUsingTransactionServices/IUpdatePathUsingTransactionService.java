package com.example.socialnetworkfortravellers.ApplicationLayer.Services.updatePathUsingTransactionServices;

import com.google.firebase.database.DatabaseReference;

public interface IUpdatePathUsingTransactionService {
    void updateNumberOfPath(boolean isIncrease);

    void setUpDatabaseReference(DatabaseReference mDatabaseReference);

    void setUpUpdatePathUsingTransactionServiceCallBack(IUpdatePathUsingTransactionServiceCallBack mUpdatePathUsingTransactionServiceCallBack);
}
