package com.example.socialnetworkfortravellers.ApplicationLayer.Services.saveRelatedInterestServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.InterestModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataServiceCallBack;
import com.example.socialnetworkfortravellers.utilLayer.ConfigUtil;
import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;
import com.example.socialnetworkfortravellers.utilLayer.StringConfigUtil;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

import static com.example.socialnetworkfortravellers.nodesLayer.InterestNode.RELATED_INTEREST;

/**
 * responsibility of this class is to save Related interest data  into firebase database.
 * <p>
 * just give map of DATA and DatabaseReference which you want store data in.
 */
public class SaveUserRelatedInterestService implements ISaveUserRelatedInterestService {


    private ISaveRawDataService mSaveRawDataService;
    private ISaveUserRelatedInterestServiceCallBack mSaveUserInterestServiceCallBack;
    private int countNumber;

    public SaveUserRelatedInterestService(ISaveRawDataService saveRawDataService) {
        this.mSaveRawDataService = saveRawDataService;
        countNumber = 0;
    }


    /**
     * saveRelatedInterest
     *
     * @param interestModels
     */
    @Override
    public void saveRelatedInterest(List<InterestModel> interestModels) {


        if (countNumber < interestModels.size()) {
            DatabaseReference userInterestRef = FirebaseDatabase.getInstance().getReference().child(RELATED_INTEREST).child(interestModels.get(countNumber).getHobbyName());
            countNumber++;
            HashMap<String, Object> interestMap = new HashMap<>();
            interestMap.put(CurrentUserIDUtil.getInstance().getCurrentUserID(), true);
            mSaveRawDataService.setDatabaseReference(userInterestRef);
            mSaveRawDataService.setMapData(interestMap);
            mSaveRawDataService.setUpSaveInfoServiceCallBack(new ISaveRawDataServiceCallBack() {
                @Override
                public void showMessageError(String message) {
                    mSaveUserInterestServiceCallBack.showMessageError(StringConfigUtil.MESSAGE_PROBLEM);
                }

                @Override
                public void noInternetFound() {
                    mSaveUserInterestServiceCallBack.noInternetFound();
                }

                @Override
                public void Successful() {
                    if (countNumber < interestModels.size()) {
                        saveRelatedInterest(interestModels);
                    } else {
                        mSaveUserInterestServiceCallBack.Successful();
                    }
                }

            });

            mSaveRawDataService.updateData();

        }


    }

    @Override
    public void setUpSaveUserInterestServiceCallBack(ISaveUserRelatedInterestServiceCallBack mSaveUserInterestServiceCallBack) {
        this.mSaveUserInterestServiceCallBack = mSaveUserInterestServiceCallBack;
    }
}
