package com.example.socialnetworkfortravellers.ApplicationLayer.Services.commentManagementServices.addCommentServices;

import android.net.Uri;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.CommentModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveImageServices.ISaveImageService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveImageServices.ISaveImageServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.updatePathUsingTransactionServices.IUpdatePathUsingTransactionService;
import com.example.socialnetworkfortravellers.utilLayer.interfaces.ICompressImageUtil;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

import static com.example.socialnetworkfortravellers.nodesLayer.CommentNode.IMAGE_COMMENT;
import static com.example.socialnetworkfortravellers.nodesLayer.CommentNode.IS_HAS_REPLIES;
import static com.example.socialnetworkfortravellers.nodesLayer.CommentNode.POST_KEY;
import static com.example.socialnetworkfortravellers.nodesLayer.CommentNode.TEXT_COMMENT;
import static com.example.socialnetworkfortravellers.nodesLayer.CommentNode.TIMESTAMP;
import static com.example.socialnetworkfortravellers.nodesLayer.CommentNode.USER_ID;
import static com.example.socialnetworkfortravellers.nodesLayer.PostNode.NUMBER_OF_COMMENTS;
import static com.example.socialnetworkfortravellers.nodesLayer.PostNode.POST;

/**
 * this service used to save Image Comment and text Comment in firebase.
 */
public class AddCommentService implements IAddCommentService {


    private IAddCommentServiceCallBack mAddCommentServiceCallBack;
    private ISaveImageService mSaveImageService;
    private ISaveRawDataService mSaveRawDataService;
    private CommentModel mCommentModel;
    private String mCommentKey;
    private ICompressImageUtil mCompressImageUtil;
    private String mImageUrl = "";
    private DatabaseReference mCommentPostReference;
    private StorageReference mStorageReference;
    private IUpdatePathUsingTransactionService mUpdatePathUsingTransactionService;


    public AddCommentService(ISaveImageService saveImageService, ICompressImageUtil compressImageUtil, ISaveRawDataService saveRawDataService, IUpdatePathUsingTransactionService updatePathUsingTransactionService) {
        this.mSaveImageService = saveImageService;
        this.mSaveRawDataService = saveRawDataService;
        this.mCompressImageUtil = compressImageUtil;
        this.mUpdatePathUsingTransactionService = updatePathUsingTransactionService;
    }


    /**
     * save comment , text and Image and user id and post key.
     *
     * @param commentModel
     */
    @Override
    public void saveComment(CommentModel commentModel, DatabaseReference databaseReference, StorageReference storageReference) {
        this.mCommentModel = commentModel;
        this.mStorageReference = storageReference;
        this.mCommentPostReference = databaseReference;


        generateCommentKey();

        if (commentModel.getCommentImage() == null) {
            saveRawComment();
        } else {
            saveImage();
        }
    }


    /**
     * generate Comment Key
     */
    private void generateCommentKey() {
        mCommentKey = mCommentPostReference.push().getKey();
        mCommentModel.setCommentKey(mCommentKey);
    }


    /**
     * save raw comment such as user id and text comment.
     */
    private void saveRawComment() {

        if (mCommentKey != null) {
            DatabaseReference CommentPostReference = mCommentPostReference.child(mCommentKey);
            HashMap<String, Object> map = new HashMap<>();
            map.put(USER_ID, mCommentModel.getUserKey());
            map.put(POST_KEY, mCommentModel.getPostKey());
            map.put(TEXT_COMMENT, mCommentModel.getCommentText());
            map.put(TIMESTAMP, ServerValue.TIMESTAMP);
            map.put(IMAGE_COMMENT, mImageUrl);
            map.put(IS_HAS_REPLIES, mCommentModel.isHasReplies());

            mSaveRawDataService.setMapData(map);
            mSaveRawDataService.setDatabaseReference(CommentPostReference);
            mSaveRawDataService.setUpSaveInfoServiceCallBack(new ISaveRawDataServiceCallBack() {
                @Override
                public void showMessageError(String message) {
                    mAddCommentServiceCallBack.showMessageError(message);
                }

                @Override
                public void noInternetFound() {
                    mAddCommentServiceCallBack.noInternetFound();
                }

                @Override
                public void Successful() {
                    increaseNumberOfComments();
                }

            });

            mSaveRawDataService.updateData();
        } else {
            mAddCommentServiceCallBack.showMessageError("we can't save  Comment , please try later.");
        }
    }


    /**
     * increase number of comments in post.
     */
    private void increaseNumberOfComments() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(POST).child(mCommentModel.getUserOfPostKey()).child(mCommentModel.getPostKey()).child(NUMBER_OF_COMMENTS);
        mUpdatePathUsingTransactionService.setUpDatabaseReference(databaseReference);
        mUpdatePathUsingTransactionService.setUpUpdatePathUsingTransactionServiceCallBack((databaseError, b, dataSnapshot) -> {
            if (databaseError == null) {
                mAddCommentServiceCallBack.addCommentSuccessful(mCommentModel);
            } else {
                mAddCommentServiceCallBack.showMessageError("we save your comment but there is problem while update number of comments in this post.");
            }
        });

        mUpdatePathUsingTransactionService.updateNumberOfPath(true);
    }

    /**
     * save Image of Comment if exists in firebase
     */
    private void saveImage() {
        mStorageReference = mStorageReference.child(mCommentKey);

        mSaveImageService.setStorageReference(mStorageReference);

        byte[] data = mCompressImageUtil.compressImage(mCommentModel.getCommentImage());

        if (data != null && mCommentKey != null) {
            mSaveImageService.setFileBytes(data);
            mSaveImageService.setSaveFileServiceCallBack(new ISaveImageServiceCallBack() {
                @Override
                public void failure(String message) {
                    mAddCommentServiceCallBack.showMessageError(message);
                }

                @Override
                public void urlFile(Uri mUri) {
                    mImageUrl = mUri.toString();
                    saveRawComment();
                }
            });

            mSaveImageService.saveFile();
        } else {
            mAddCommentServiceCallBack.showMessageError("we can't save  Image , please try later.");
        }
    }


    @Override
    public void setUpAddCommentServiceCallBack(IAddCommentServiceCallBack mAddCommentServiceCallBack) {
        this.mAddCommentServiceCallBack = mAddCommentServiceCallBack;
    }
}
