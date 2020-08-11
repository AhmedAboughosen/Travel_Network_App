package com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.getPostOfUserServices;

import androidx.annotation.NonNull;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.PostModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseAddValueEventService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseAddValueEventServiceCallBack;
import com.example.socialnetworkfortravellers.nodesLayer.CommentNode;
import com.example.socialnetworkfortravellers.nodesLayer.PostNode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.example.socialnetworkfortravellers.nodesLayer.PostNode.POST;

/**
 * main idea of this post is get post Object from fierbase database
 */
public class GetPostOfUserService implements IGetPostOfUserService {

    private IGetDataByUseAddValueEventService mGetDataByUseAddValueEventService;
    private IGetPostOfUserServiceCallBack mGetPostOfUserServiceCallBack;

    public GetPostOfUserService(IGetDataByUseAddValueEventService getDataByUseAddValueEventService) {
        this.mGetDataByUseAddValueEventService = getDataByUseAddValueEventService;
    }


    @Override
    public void getPost(String userKey, String postKey) {

        DatabaseReference postsRef = FirebaseDatabase.getInstance().getReference().child(POST).child(userKey).child(postKey);

        this.mGetDataByUseAddValueEventService.setUpDatabaseReference(postsRef);
        mGetDataByUseAddValueEventService.setUpGetDataServiceCallBack(new IGetDataByUseAddValueEventServiceCallBack() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    mGetPostOfUserServiceCallBack.newPost(setData(dataSnapshot));
                } else {
                    mGetPostOfUserServiceCallBack.postDoesNotExists();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mGetPostOfUserServiceCallBack.showMessageError(databaseError.getMessage());
            }

            @Override
            public void noInternetFound() {
                mGetPostOfUserServiceCallBack.noInternetFound();
            }
        });


        mGetDataByUseAddValueEventService.getData();
    }


    private PostModel setData(@NonNull DataSnapshot dataSnapshot) {
        PostModel postModel = new PostModel();
        ArrayList<String> imageList = new ArrayList<>();
        List<Integer> emptySpace = Arrays.asList(0, 1, 2, 3);


        postModel.setPostKey(dataSnapshot.getKey());
        if (dataSnapshot.hasChild(PostNode.USER_ID)) {
            postModel.getUserPostModel().getUserInfoModel().setKeyOfUser(Objects.requireNonNull(dataSnapshot.child(PostNode.USER_ID).getValue()).toString());
        }

        if (dataSnapshot.hasChild(PostNode.DESCRIPTION)) {
            postModel.setDescription(Objects.requireNonNull(dataSnapshot.child(PostNode.DESCRIPTION).getValue()).toString());
        }


        if (dataSnapshot.hasChild(PostNode.POST_IMAGES)) {

            DataSnapshot ImageSnapshot = dataSnapshot.child(PostNode.POST_IMAGES);
            emptySpace = new ArrayList<>();

            for (int i = 0; i < 4; ++i) {
                if (ImageSnapshot.hasChild("image_" + i)) {
                    imageList.add(Objects.requireNonNull(ImageSnapshot.child("image_" + i).child("Image").getValue()).toString());
                } else {
                    emptySpace.add(i);
                }
            }

            postModel.setImageUrl(imageList);
            postModel.setEmptySpace(emptySpace);
        } else {
            postModel.setEmptySpace(emptySpace);
        }

        if (dataSnapshot.hasChild(CommentNode.LIKES)) {
            DataSnapshot ImageSnapshot = dataSnapshot.child(CommentNode.LIKES);

            HashMap<String, Boolean> likes = new HashMap<>();
            for (DataSnapshot dataSnapshotLikes : ImageSnapshot.getChildren()) {
                likes.put(dataSnapshotLikes.getKey(), (Boolean) dataSnapshotLikes.getValue());
            }
            postModel.setUserLikePost(likes);
        }

        if (dataSnapshot.hasChild(PostNode.TIMESTAMP)) {
            Long date = (Long) dataSnapshot.child(PostNode.TIMESTAMP).getValue();
            postModel.setDate(date);
        }

        if (dataSnapshot.hasChild(PostNode.LOCATION)) {
            postModel.setLocationPost(Objects.requireNonNull(dataSnapshot.child(PostNode.LOCATION).getValue()).toString());
        }

        if (dataSnapshot.hasChild(PostNode.NUMBER_OF_COMMENTS)) {
            postModel.setNumberOfComment((Long) Objects.requireNonNull(dataSnapshot.child(PostNode.NUMBER_OF_COMMENTS).getValue()));
        }
        return postModel;
    }


    @Override
    public void setUpGetPostOfUserServiceCallBack(IGetPostOfUserServiceCallBack mGetPostOfUserServiceCallBack) {
        this.mGetPostOfUserServiceCallBack = mGetPostOfUserServiceCallBack;
    }
}
