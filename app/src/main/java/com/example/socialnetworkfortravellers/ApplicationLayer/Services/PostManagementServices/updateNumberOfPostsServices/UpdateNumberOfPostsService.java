package com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.updateNumberOfPostsServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.updatePathUsingTransactionServices.IUpdatePathUsingTransactionService;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.socialnetworkfortravellers.nodesLayer.PostNode.NUMBER_OF_POSTS;
import static com.example.socialnetworkfortravellers.nodesLayer.UserInteractionNode.USER_COUNTER;
import static com.example.socialnetworkfortravellers.nodesLayer.UserInteractionNode.USER_INTERACTIONS;

public class UpdateNumberOfPostsService implements IUpdateNumberOfPostsService {


    private IUpdatePathUsingTransactionService mUpdatePathUsingTransactionService;
    private IUpdateNumberOfPostsServiceCallBack mUpdateNumberOfPostsServiceCallBack;

    public UpdateNumberOfPostsService(IUpdatePathUsingTransactionService updatePathUsingTransactionService) {
        this.mUpdatePathUsingTransactionService = updatePathUsingTransactionService;
    }

    @Override
    public void updateNumberOfPost(String userKey, boolean increase_or_decrease) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(USER_INTERACTIONS).child(USER_COUNTER).child(userKey).child(NUMBER_OF_POSTS);

        mUpdatePathUsingTransactionService.setUpDatabaseReference(databaseReference);
        mUpdatePathUsingTransactionService.setUpUpdatePathUsingTransactionServiceCallBack((databaseError, b, dataSnapshot) -> {

            if (databaseError == null) {
                mUpdateNumberOfPostsServiceCallBack.updateNumberOfPostsSuccessful();
            } else {
                mUpdateNumberOfPostsServiceCallBack.showMessageError(databaseError.getMessage());
            }
        });
        mUpdatePathUsingTransactionService.updateNumberOfPath(increase_or_decrease);
    }


    @Override
    public void setUpUpdateNumberOfPostsServiceCallBack(IUpdateNumberOfPostsServiceCallBack mUpdateNumberOfPostsServiceCallBack) {
        this.mUpdateNumberOfPostsServiceCallBack = mUpdateNumberOfPostsServiceCallBack;
    }
}
