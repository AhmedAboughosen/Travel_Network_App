package com.example.socialnetworkfortravellers.ApplicationLayer.Services.saveUserInterestServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.removeDataServices.IRemoveDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.removeDataServices.IRemoveDataServiceCallBack;
import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import static com.example.socialnetworkfortravellers.nodesLayer.InterestNode.USER_INTEREST;

/**
 * responsibility of this class is to save interest data  into firebase database.
 * <p>
 * just give map of DATA and DatabaseReference which you want store data in.
 */
public class SaveUserInterestService implements ISaveUserInterestService {


    private ISaveRawDataService mSaveRawDataService;
    private ISaveUserInterestServiceCallBack mSaveUserInterestServiceCallBack;
    private IRemoveDataService mRemoveDataService;

    public SaveUserInterestService(ISaveRawDataService saveRawDataService, IRemoveDataService removeDataService) {
        this.mSaveRawDataService = saveRawDataService;
        this.mRemoveDataService = removeDataService;
    }


    /**
     * before save user interest , check if have deleted it.
     *
     * @param interestMap
     */
    private void removeReference(HashMap<String, Object> interestMap) {
        DatabaseReference userInterestRef = FirebaseDatabase.getInstance().getReference().child(USER_INTEREST).child(CurrentUserIDUtil.getInstance().getCurrentUserID());

        mRemoveDataService.setUpDatabaseReference(userInterestRef);
        mRemoveDataService.setUpRemoveDataServiceCallBack(new IRemoveDataServiceCallBack() {
            @Override
            public void failure(String message) {
                mSaveUserInterestServiceCallBack.showMessageError(message);
            }

            @Override
            public void isSuccessful() {
                updateInterest(interestMap);
            }
        });

        mRemoveDataService.removeData();
    }


    /**
     * save user interest
     *
     * @param interestMap
     */
    @Override
    public void saveInterest(HashMap<String, Object> interestMap) {
        removeReference(interestMap);
    }


    private void updateInterest(HashMap<String, Object> interestMap) {
        DatabaseReference userInterestRef = FirebaseDatabase.getInstance().getReference().child(USER_INTEREST).child(CurrentUserIDUtil.getInstance().getCurrentUserID());

        mSaveRawDataService.setDatabaseReference(userInterestRef);
        mSaveRawDataService.setMapData(interestMap);
        mSaveRawDataService.setUpSaveInfoServiceCallBack(new ISaveRawDataServiceCallBack() {
            @Override
            public void showMessageError(String message) {
                mSaveUserInterestServiceCallBack.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mSaveUserInterestServiceCallBack.noInternetFound();
            }

            @Override
            public void Successful() {
                mSaveUserInterestServiceCallBack.Successful();
            }

        });

        mSaveRawDataService.updateData();
    }

    @Override
    public void setUpSaveUserInterestServiceCallBack(ISaveUserInterestServiceCallBack mSaveUserInterestServiceCallBack) {
        this.mSaveUserInterestServiceCallBack = mSaveUserInterestServiceCallBack;
    }
}
