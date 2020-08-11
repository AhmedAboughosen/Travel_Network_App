package com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.addPostServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.PostModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataServiceCallBack;
import com.example.socialnetworkfortravellers.utilLayer.StringConfigUtil;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import static com.example.socialnetworkfortravellers.nodesLayer.PostNode.DESCRIPTION;
import static com.example.socialnetworkfortravellers.nodesLayer.PostNode.IS_IMAGE_EXISTS;
import static com.example.socialnetworkfortravellers.nodesLayer.PostNode.LOCATION;
import static com.example.socialnetworkfortravellers.nodesLayer.PostNode.POST;
import static com.example.socialnetworkfortravellers.nodesLayer.PostNode.TIMESTAMP;
import static com.example.socialnetworkfortravellers.nodesLayer.PostNode.USER_ID;

public class AddPostService implements IAddPostService {


    private ISaveRawDataService mSaveRawDataService;
    private IAddPostServiceCallBack mAddPostServiceCallBack;
    private String postKey;


    public AddPostService(ISaveRawDataService saveRawDataService) {
        this.mSaveRawDataService = saveRawDataService;
    }

    @Override
    public void addPostContent(PostModel postModel) {

        HashMap<String, Object> postMap = new HashMap<>();
        postMap.put(DESCRIPTION, (postModel.getDescription()));
        postMap.put(TIMESTAMP, postModel.getDate());
        postMap.put(IS_IMAGE_EXISTS, (postModel.getImageUrl().size() != 0));
        postMap.put(LOCATION, postModel.getLocationPost());
        postMap.put(USER_ID, postModel.getUserPostModel().getUserInfoModel().getKeyOfUser());


        DatabaseReference PostsRef = FirebaseDatabase.getInstance().getReference().child(POST).child(postModel.getUserPostModel().getUserInfoModel().getKeyOfUser());

        postKey = PostsRef.push().getKey();
        if (postKey != null) {
            PostsRef = PostsRef.child(postKey);

            mSaveRawDataService.setDatabaseReference(PostsRef);
            mSaveRawDataService.setMapData(postMap);
            setUpSaveInfoServiceCallBack();
            mSaveRawDataService.updateData();
        } else {
            mAddPostServiceCallBack.showMessageError(StringConfigUtil.MESSAGE_PROBLEM);
        }
    }

    private void setUpSaveInfoServiceCallBack() {
        mSaveRawDataService.setUpSaveInfoServiceCallBack(new ISaveRawDataServiceCallBack() {
            @Override
            public void showMessageError(String message) {
                mAddPostServiceCallBack.showMessageError(message);

            }

            @Override
            public void noInternetFound() {
                mAddPostServiceCallBack.noInternetFound();

            }

            @Override
            public void Successful() {
                mAddPostServiceCallBack.savePosSuccessful(postKey);
            }

        });
    }


    @Override
    public void setUpAddPostServiceCallBack(IAddPostServiceCallBack mAddPostServiceCallBack) {
        this.mAddPostServiceCallBack = mAddPostServiceCallBack;
    }
}
