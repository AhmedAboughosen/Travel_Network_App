package com.example.socialnetworkfortravellers.ApplicationLayer.Services.commentManagementServices.addDisLikeToCommentService;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.removeDataServices.IRemoveDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.removeDataServices.IRemoveDataServiceCallBack;
import com.google.firebase.database.DatabaseReference;

public class AddDisLikeToCommentService implements IAddDisLikeToCommentService {


    private IAddDisLikeToCommentServiceCallback mAddDisLikeToCommentServiceCallback;
    private IRemoveDataService mRemoveDataService;


    public AddDisLikeToCommentService(IRemoveDataService removeDataService) {
        this.mRemoveDataService = removeDataService;
    }


    /**
     * add disLike to comment
     *
     * @param databaseReference
     */
    @Override
    public void addDisLikeToComment(DatabaseReference databaseReference) {
        mRemoveDataService.setUpDatabaseReference(databaseReference);
        mRemoveDataService.setUpRemoveDataServiceCallBack(new IRemoveDataServiceCallBack() {
            @Override
            public void failure(String message) {
                mAddDisLikeToCommentServiceCallback.showMessageError(message);
            }

            @Override
            public void isSuccessful() {
                mAddDisLikeToCommentServiceCallback.removeLikeSuccessful();
            }
        });

        mRemoveDataService.removeData();
    }


    @Override
    public void setUpAddDisLikeToCommentService(IAddDisLikeToCommentServiceCallback mAddDisLikeToCommentService) {
        this.mAddDisLikeToCommentServiceCallback = mAddDisLikeToCommentService;
    }
}
