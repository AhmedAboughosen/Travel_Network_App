package com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.getLastPostKeyServices;

import androidx.annotation.NonNull;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseSingleValueService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseSingleValueServiceCallBack;
import com.example.socialnetworkfortravellers.utilLayer.ConvertTimeUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import static com.example.socialnetworkfortravellers.nodesLayer.PostNode.KEYS_POST;

public class GetPostKeyService implements IGetPostKeyService {


    private IGetDataByUseSingleValueService mGetDataByUseSingleValueService;
    private IGetPostKeyServiceCallBack mGetPostKeyServiceCallBack;

    public GetPostKeyService(IGetDataByUseSingleValueService getDataByUseSingleValueService) {
        this.mGetDataByUseSingleValueService = getDataByUseSingleValueService;
    }



    /**
     * get keys from post
     */
    @Override
    public void getKeysOfPost(String userKey) {

        DatabaseReference postsRef = FirebaseDatabase.getInstance().getReference().child(KEYS_POST).child(userKey);
        mGetDataByUseSingleValueService.setUpDatabaseReference(postsRef);
        HashMap<String, Object> map = new HashMap<>();
        mGetDataByUseSingleValueService.setUpGetDataServiceCallBack(new IGetDataByUseSingleValueServiceCallBack() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {

                    if (dataSnapshot.exists()) {
                        for (DataSnapshot first_key : dataSnapshot.getChildren()) {
                            long value = ConvertTimeUtil.toTimeStampToSeconds(first_key.getValue().toString());
                            map.put(first_key.getKey(), value);
                        }
                    }
                    mGetPostKeyServiceCallBack.postKeys(map);
                } catch (Exception ex) {
                    mGetPostKeyServiceCallBack.showMessageError((ex.getMessage() != null) ? ex.getMessage() : "error");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mGetPostKeyServiceCallBack.showMessageError(databaseError.getMessage());
            }

            @Override
            public void noInternetFound() {
                mGetPostKeyServiceCallBack.noInternetFound();
            }
        });

        mGetDataByUseSingleValueService.getData();
    }


    @Override
    public void setGetLastPostKeyServiceCallBack(IGetPostKeyServiceCallBack getLastPostKeyServiceCallBack) {
        mGetPostKeyServiceCallBack = getLastPostKeyServiceCallBack;
    }
}
