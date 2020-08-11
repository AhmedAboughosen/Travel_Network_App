package com.example.socialnetworkfortravellers.ApplicationLayer.Services.commentManagementServices.addRepliesServices;

import com.google.firebase.database.DatabaseReference;

public interface IAddRepliesService {
    void setUpAddRepliesServiceCallBack(IAddRepliesServiceCallBack mAddRepliesServiceCallBack);
    void updateRepliesState(DatabaseReference databaseReference, boolean is_has_replies);
}
