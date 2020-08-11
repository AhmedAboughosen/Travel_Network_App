package com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.removeDataServices;

import com.google.firebase.database.DatabaseReference;

public class RemoveDataService implements IRemoveDataService {


    private DatabaseReference mDatabaseReference;
    private IRemoveDataServiceCallBack mRemoveDataServiceCallBack;

    public void setUpDatabaseReference(DatabaseReference mDatabaseReference) {
        this.mDatabaseReference = mDatabaseReference;
    }


    public void removeData() {
        mDatabaseReference.removeValue().addOnCompleteListener(task -> {

            try {
                if (task.isSuccessful()) {
                    mRemoveDataServiceCallBack.isSuccessful();
                } else {
                    mRemoveDataServiceCallBack.failure(task.getException().getMessage());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        });
    }

    public void setUpRemoveDataServiceCallBack(IRemoveDataServiceCallBack mRemoveDataServiceCallBack) {
        this.mRemoveDataServiceCallBack = mRemoveDataServiceCallBack;
    }
}
