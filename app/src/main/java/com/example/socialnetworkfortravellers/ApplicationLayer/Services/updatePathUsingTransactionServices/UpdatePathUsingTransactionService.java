package com.example.socialnetworkfortravellers.ApplicationLayer.Services.updatePathUsingTransactionServices;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import org.jetbrains.annotations.NotNull;

public class UpdatePathUsingTransactionService implements IUpdatePathUsingTransactionService {

    private DatabaseReference mDatabaseReference;
    private IUpdatePathUsingTransactionServiceCallBack mUpdatePathUsingTransactionServiceCallBack;


    public UpdatePathUsingTransactionService() {

    }

    public void setUpDatabaseReference(DatabaseReference mDatabaseReference) {
        this.mDatabaseReference = mDatabaseReference;
    }


    /**
     * this method used to Increase number of item
     */
    @Override
    public void updateNumberOfPath(boolean isIncrease) {
        //update number by one
        mDatabaseReference.runTransaction(new Transaction.Handler() {
            @NotNull
            @Override
            public Transaction.Result doTransaction(@NotNull MutableData mutableData) {
                if (mutableData.getValue() == null) {
                    mutableData.setValue(1);
                } else {
                    mutableData.setValue((isIncrease) ? (Long) mutableData.getValue() + 1 : (Long) mutableData.getValue() - 1);
                }

                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                mUpdatePathUsingTransactionServiceCallBack.onComplete(databaseError, b, dataSnapshot);
            }
        });

    }

    public void setUpUpdatePathUsingTransactionServiceCallBack(IUpdatePathUsingTransactionServiceCallBack mUpdatePathUsingTransactionServiceCallBack) {
        this.mUpdatePathUsingTransactionServiceCallBack = mUpdatePathUsingTransactionServiceCallBack;
    }
}
