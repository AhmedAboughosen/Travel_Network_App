package com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices;

import android.content.Context;

import com.example.socialnetworkfortravellers.utilLayer.ConfigUtil;
import com.example.socialnetworkfortravellers.utilLayer.NetworkState;
import com.example.socialnetworkfortravellers.utilLayer.StringConfigUtil;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Objects;

import javax.inject.Inject;

/**
 * responsibility of this class is to save data into firebase database.
 * <p>
 * just give map of DATA and DatabaseReference which you want store data in.
 */
public class SaveRawDataService implements ISaveRawDataService {

    private DatabaseReference mDatabaseReference;
    private HashMap<String, Object> mMapData;
    private ISaveRawDataServiceCallBack mSaveInfoServiceCallBack;
    private Context mContext;


    @Inject
    public SaveRawDataService(Context context) {
        this.mContext = context;
    }


    @Override
    public void setUpSaveInfoServiceCallBack(ISaveRawDataServiceCallBack saveInfoServiceCallBack) {
        this.mSaveInfoServiceCallBack = saveInfoServiceCallBack;
    }


    @Override
    public void updateData() {

        try {

            if (!NetworkState.isNetworkConnected(this.mContext)) {
                mSaveInfoServiceCallBack.noInternetFound();
                return;
            }


            this.mDatabaseReference.updateChildren(mMapData).addOnCompleteListener(task -> {

                try {
                    if (task.isSuccessful()) {
                        this.mSaveInfoServiceCallBack.Successful();
                    } else {
                        this.mSaveInfoServiceCallBack.showMessageError(Objects.requireNonNull(task.getException()).getMessage());
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    this.mSaveInfoServiceCallBack.showMessageError(StringConfigUtil.MESSAGE_PROBLEM);
                }

            });
        } catch (Exception ex) {
            ex.printStackTrace();
            this.mSaveInfoServiceCallBack.showMessageError(StringConfigUtil.MESSAGE_PROBLEM);
        }
    }

    @Override
    public void setDatabaseReference(DatabaseReference mDatabaseReference) {
        this.mDatabaseReference = mDatabaseReference;
    }

    @Override
    public void setMapData(HashMap<String, Object> mMapData) {
        this.mMapData = mMapData;
    }
}
