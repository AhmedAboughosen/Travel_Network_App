package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.likeAndDisLikePresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.removeDataServices.IRemoveDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.removeDataServices.IRemoveDataServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataServiceCallBack;
import com.example.socialnetworkfortravellers.nodesLayer.PostNode;
import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import static com.example.socialnetworkfortravellers.nodesLayer.PostNode.LIKES;

/**
 * handle like and dislike to post.
 */
public class LikeAndDisLikePresenter implements ILikeAndDisLikePresenter {

    private ILikeAndDisLikePresenterCallBack mLikeAndDisLikePresenterCallBack;
    private IRemoveDataService mRemoveDataService;
    private ISaveRawDataService mSaveRawDataService;


    public LikeAndDisLikePresenter(IRemoveDataService removeDataService, ISaveRawDataService saveRawDataService) {
        this.mRemoveDataService = removeDataService;
        this.mSaveRawDataService = saveRawDataService;
    }


    @Override
    public void increasePostLikes(String userKey, String postKey) {
        DatabaseReference mLikesPostRef = FirebaseDatabase.getInstance().getReference().child(PostNode.POST).child(userKey).child(postKey).child(LIKES);
        mSaveRawDataService.setDatabaseReference(mLikesPostRef);
        HashMap<String, Object> map = new HashMap<>();
        map.put(CurrentUserIDUtil.getInstance().getCurrentUserID(), true);
        mSaveRawDataService.setMapData(map);
        mSaveRawDataService.setUpSaveInfoServiceCallBack(new ISaveRawDataServiceCallBack() {
            @Override
            public void showMessageError(String message) {
                mLikeAndDisLikePresenterCallBack.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mLikeAndDisLikePresenterCallBack.networkErrorMessage();
            }

            @Override
            public void Successful() {
                mLikeAndDisLikePresenterCallBack.addLikeSuccessful();
            }

        });

        mSaveRawDataService.updateData();
    }

    @Override
    public void decreasePostLikes(String userKey, String postKey) {
        DatabaseReference mLikesPostRef = FirebaseDatabase.getInstance().getReference().child(PostNode.POST).child(userKey).child(postKey).child(LIKES).child(CurrentUserIDUtil.getInstance().getCurrentUserID());
        mRemoveDataService.setUpDatabaseReference(mLikesPostRef);
        mRemoveDataService.setUpRemoveDataServiceCallBack(new IRemoveDataServiceCallBack() {
            @Override
            public void failure(String message) {
                mLikeAndDisLikePresenterCallBack.showMessageError(message);
            }

            @Override
            public void isSuccessful() {
                mLikeAndDisLikePresenterCallBack.removeLikeSuccessful();
            }
        });


        mRemoveDataService.removeData();
    }

    public void setUpLikeAndDisLikePresenterCallBack(ILikeAndDisLikePresenterCallBack mLikeAndDisLikePresenterCallBack) {
        this.mLikeAndDisLikePresenterCallBack = mLikeAndDisLikePresenterCallBack;
    }
}
