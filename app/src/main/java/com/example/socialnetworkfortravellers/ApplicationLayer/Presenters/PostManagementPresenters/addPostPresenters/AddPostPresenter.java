package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.addPostPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.PostModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.addPostServices.IAddPostService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.addPostServices.IAddPostServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.savePostImageServices.ISavePostImageService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.savePostImageServices.ISavePostImageServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.updateNumberOfPostsServices.IUpdateNumberOfPostsService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.updateNumberOfPostsServices.IUpdateNumberOfPostsServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.updatePostKeyServices.IUpdatePostKeyService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.updatePostKeyServices.IUpdatePostKeyServiceCallBack;
import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;
import com.example.socialnetworkfortravellers.utilLayer.StringEmptyUtil;

import java.util.Arrays;
import java.util.List;

public class AddPostPresenter implements IAddPostPresenter {


    private IAddPostPresenterCallBack mAddPostPresneterCallBack;
    private IAddPostService mAddPostService;
    private ISavePostImageService mSavePostImageService;
    private PostModel mPostModel;
    private IUpdateNumberOfPostsService mUpdateNumberOfPostsService;
    private IUpdatePostKeyService mUpdatePostKeyService;
    private String mPostKey;


    public AddPostPresenter(IAddPostService addPostService, ISavePostImageService savePostImageService, IUpdateNumberOfPostsService updateNumberOfPostsService
            , IUpdatePostKeyService updatePostKeyService) {
        this.mUpdatePostKeyService = updatePostKeyService;
        this.mAddPostService = addPostService;
        this.mSavePostImageService = savePostImageService;
        this.mUpdateNumberOfPostsService = updateNumberOfPostsService;
    }


    public void setUpAddPostPresenterCallBack(IAddPostPresenterCallBack mAddPostPresneterCallBack) {
        this.mAddPostPresneterCallBack = mAddPostPresneterCallBack;
    }


    public void addPost(PostModel postModel) {

        if (isValid(postModel.getImageUrl().size(), postModel.getDescription())) {
            mPostModel = postModel;
            addNewPost();
        } else {
            mAddPostPresneterCallBack.showMessageError("Post must contain text or an images");
        }
    }

    private void addNewPost() {
        setUpAddPostServiceCallBack();
        mAddPostService.addPostContent(mPostModel);
    }

    private void setUpAddPostServiceCallBack() {
        mAddPostService.setUpAddPostServiceCallBack(new IAddPostServiceCallBack() {
            @Override
            public void showMessageError(String message) {
                mAddPostPresneterCallBack.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mAddPostPresneterCallBack.networkErrorMessage();
            }

            @Override
            public void savePosSuccessful(String postKey) {
                mPostKey = postKey;
                saveImage();
            }

        });
    }


    private void saveImage() {
        if (mPostModel.getImageUrl().size() != 0) {
            setUpSavePostImageServiceCallBack();
            List<Integer> list = Arrays.asList(0, 1, 2, 3);
            mSavePostImageService.saveImage(list, mPostModel.getImageUrl(), mPostKey, mPostModel.getUserPostModel().getUserInfoModel().getKeyOfUser());
        } else {
            setUpUpdateNumberOfPostsServiceCallBack();
            mUpdateNumberOfPostsService.updateNumberOfPost(mPostModel.getUserPostModel().getUserInfoModel().getKeyOfUser(), true);
        }
    }

    private void setUpSavePostImageServiceCallBack() {
        mSavePostImageService.setUpSavePostImageServiceCallBack(new ISavePostImageServiceCallBack() {
            @Override
            public void showMessageError(String message) {
                mAddPostPresneterCallBack.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mAddPostPresneterCallBack.networkErrorMessage();
            }

            @Override
            public void saveImagePostSuccessful() {
                setUpUpdateNumberOfPostsServiceCallBack();
                mUpdateNumberOfPostsService.updateNumberOfPost(mPostModel.getUserPostModel().getUserInfoModel().getKeyOfUser(), true);
            }

        });
    }


    private void setUpUpdateNumberOfPostsServiceCallBack() {
        mUpdateNumberOfPostsService.setUpUpdateNumberOfPostsServiceCallBack(new IUpdateNumberOfPostsServiceCallBack() {
            @Override
            public void showMessageError(String message) {
                setUpdatePostKeyServiceCallBack();
                mUpdatePostKeyService.addNewPostKey(mPostModel.getDate(), mPostKey, CurrentUserIDUtil.getInstance().getCurrentUserID());
                mAddPostPresneterCallBack.showMessageError("there is problem while saving your post");
            }

            @Override
            public void noInternetFound() {
                mAddPostPresneterCallBack.networkErrorMessage();
            }

            @Override
            public void updateNumberOfPostsSuccessful() {
                setUpdatePostKeyServiceCallBack();
                mUpdatePostKeyService.addNewPostKey(mPostModel.getDate(), mPostKey, CurrentUserIDUtil.getInstance().getCurrentUserID());
            }

        });
    }


    private void setUpdatePostKeyServiceCallBack() {
        mUpdatePostKeyService.setUpdatePostKeyServiceCallBack(new IUpdatePostKeyServiceCallBack() {
            @Override
            public void showMessageError(String message) {
                mAddPostPresneterCallBack.showMessageError(message);

            }

            @Override
            public void noInternetFound() {
                mAddPostPresneterCallBack.networkErrorMessage();
            }

            @Override
            public void addPostSuccessful() {
                mAddPostPresneterCallBack.addPostSuccessful();
            }

        });
    }

    private boolean isValid(int number_of_Images, String description) {

        return !StringEmptyUtil.isEmptyString(description) || number_of_Images != 0;

    }
}
