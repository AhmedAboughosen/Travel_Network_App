package com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.removeUserInfoServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.removeDataServices.IRemoveDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.removeDataServices.IRemoveDataServiceCallBack;
import com.example.socialnetworkfortravellers.nodesLayer.PostNode;
import com.example.socialnetworkfortravellers.nodesLayer.TripNode;
import com.example.socialnetworkfortravellers.nodesLayer.UserNode;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RemoveUserInfoService implements IRemoveUserInfoService {


    private IRemoveDataService mRemoveDataService;
    private IRemoveUserInfoServiceCallBack mRemoveUserInfoServiceCallBack;
    private String userKey;

    public RemoveUserInfoService(IRemoveDataService removeDataService) {
        this.mRemoveDataService = removeDataService;
    }


    /**
     * remove user from user path
     */
    @Override
    public void deactivateUserInfo(String userKey) {
        this.userKey = userKey;
        DatabaseReference postRefer = FirebaseDatabase.getInstance().getReference().child(UserNode.USER).child(userKey);

        mRemoveDataService.setUpDatabaseReference(postRefer);
        mRemoveDataService.setUpRemoveDataServiceCallBack(new IRemoveDataServiceCallBack() {
            @Override
            public void failure(String message) {

                mRemoveUserInfoServiceCallBack.showMessageError(message);
            }

            @Override
            public void isSuccessful() {
                deactivatePostInfo();
            }
        });
        mRemoveDataService.removeData();
    }


    /**
     * remove posts of user
     */
    private void deactivatePostInfo() {
        DatabaseReference postRefer = FirebaseDatabase.getInstance().getReference().child(PostNode.POST).child(userKey);

        mRemoveDataService.setUpDatabaseReference(postRefer);
        mRemoveDataService.setUpRemoveDataServiceCallBack(new IRemoveDataServiceCallBack() {
            @Override
            public void failure(String message) {
                mRemoveUserInfoServiceCallBack.showMessageError(message);
            }

            @Override
            public void isSuccessful() {

                deactivateTripsInfo();
            }
        });
        mRemoveDataService.removeData();
    }

    /**
     * remove trips of user
     */
    private void deactivateTripsInfo() {
        DatabaseReference postRefer = FirebaseDatabase.getInstance().getReference().child(TripNode.TRIP).child(userKey);

        mRemoveDataService.setUpDatabaseReference(postRefer);
        mRemoveDataService.setUpRemoveDataServiceCallBack(new IRemoveDataServiceCallBack() {
            @Override
            public void failure(String message) {
                mRemoveUserInfoServiceCallBack.showMessageError(message);
            }

            @Override
            public void isSuccessful() {
                mRemoveUserInfoServiceCallBack.removeUserInfoSuccessful();
            }
        });

        mRemoveDataService.removeData();
    }

    @Override
    public void setUpRemoveUserInfoServiceCallBack(IRemoveUserInfoServiceCallBack removeUserInfoServiceCallBack) {
        this.mRemoveUserInfoServiceCallBack = removeUserInfoServiceCallBack;
    }
}
