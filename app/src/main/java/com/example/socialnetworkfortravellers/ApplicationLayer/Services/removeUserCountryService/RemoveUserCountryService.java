package com.example.socialnetworkfortravellers.ApplicationLayer.Services.removeUserCountryService;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.removeDataServices.IRemoveDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.removeDataServices.IRemoveDataServiceCallBack;
import com.example.socialnetworkfortravellers.nodesLayer.UserNode;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RemoveUserCountryService implements IRemoveUserCountryService {


    private IRemoveDataService mRemoveDataService;
    private IRemoveUserCountryServiceCallBack mRemoveUserCountryCallBack;

    public RemoveUserCountryService(IRemoveDataService removeDataService) {
        this.mRemoveDataService = removeDataService;
    }


    /**
     * remove country of user
     */
    @Override
    public void removeCountry(String userKey, String oldCountry) {

        DatabaseReference countryRef = FirebaseDatabase.getInstance().getReference().child(UserNode.COUNTRY).child(oldCountry).child(userKey);

        this.mRemoveDataService.setUpDatabaseReference(countryRef);
        mRemoveDataService.setUpRemoveDataServiceCallBack(new IRemoveDataServiceCallBack() {
            @Override
            public void failure(String message) {
                mRemoveUserCountryCallBack.showMessageError(message);
            }

            @Override
            public void isSuccessful() {
                mRemoveUserCountryCallBack.removeCountrySuccessful();
            }
        });

        mRemoveDataService.removeData();

    }


    @Override
    public void setUpRemoveUserCountryServiceCallBack(IRemoveUserCountryServiceCallBack removeUserCountryServiceCallBack) {
        this.mRemoveUserCountryCallBack = removeUserCountryServiceCallBack;
    }
}
