package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.editPostPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.PostModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.updateImagePostServices.IUpdateImagePostService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.updateImagePostServices.IUpdateImagePostServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.updatePostServices.IUpdatePostService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.updatePostServices.IUpdatePostServiceCallBack;
import com.example.socialnetworkfortravellers.nodesLayer.PostNode;
import com.example.socialnetworkfortravellers.utilLayer.StringEmptyUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EditPostPresenter implements IEditPostPresenter {


    private IEditPostPresenterCallBack mEditPostPresenterCallBack;
    private IUpdatePostService mUpdatePostService;
    private PostModel mPostModel;
    private IUpdateImagePostService mUpdateImagePostService;
    private List<Integer> deletedImage, mRemainingSpace;
    private int numberOfImage;

    public EditPostPresenter(IUpdatePostService postService, IUpdateImagePostService updateImagePostService) {
        this.mUpdatePostService = postService;
        this.mUpdateImagePostService = updateImagePostService;
        deletedImage = new ArrayList<>();
        mRemainingSpace = new ArrayList<>();
        setUpUpdatePostServiceCallBack();

    }


    @Override
    public void setUpEditPostPresenterCallBack(IEditPostPresenterCallBack mEditPostPresenterCallBack) {
        this.mEditPostPresenterCallBack = mEditPostPresenterCallBack;
    }


    @Override
    public void updatePost(PostModel postModel, List<Integer> deletedImage) {
        if (isValid(postModel.getImageUrl().size(), postModel.getDescription())) {
            mPostModel = postModel;
            this.deletedImage = deletedImage;
            checkImageAsFile();
            updatePost();
        } else {
            mEditPostPresenterCallBack.showMessageError("Post must contain text or an images");
        }
    }


    /**
     * check if object contain new image or not.
     */
    private void checkImageAsFile() {

        ArrayList<String> listOfNewImage = new ArrayList<>();
        numberOfImage = mPostModel.getImageUrl().size();

        for (int i = 0; i < mPostModel.getImageUrl().size(); ++i) {
            File file = new File(mPostModel.getImageUrl().get(i));

            if (file.exists() || file.isFile()) {
                listOfNewImage.add(mPostModel.getImageUrl().get(i));
            }
        }

        mPostModel.setImageUrl(listOfNewImage);


        if (mPostModel.getImageUrl().size() == deletedImage.size()) {
            mRemainingSpace = new ArrayList<>(deletedImage);
            return;
        }

        if (mPostModel.getImageUrl().size() < deletedImage.size()) {


            int i = 0;
            while (mRemainingSpace.size() < mPostModel.getImageUrl().size()) {
                mRemainingSpace.add(deletedImage.get(i));
                ++i;
            }
            return;
        }

        if (mPostModel.getImageUrl().size() > deletedImage.size()) {
            mRemainingSpace.addAll(deletedImage);

            int i = 0;
            while (mRemainingSpace.size() < mPostModel.getImageUrl().size()) {
                mRemainingSpace.add(mPostModel.getEmptySpace().get(i));
                ++i;
            }
        }


    }

    private void updatePost() {
        HashMap<String, Object> map = new HashMap<>();
        map.put(PostNode.DESCRIPTION, (mPostModel.getDescription()));
        map.put(PostNode.IS_IMAGE_EXISTS, (numberOfImage != 0));
        map.put(PostNode.LOCATION, mPostModel.getLocationPost());

        mUpdatePostService.setPostContent(map);
        mUpdatePostService.updatePostContent(mPostModel.getUserPostModel().getUserInfoModel().getKeyOfUser(), mPostModel.getPostKey());
    }

    private void setUpUpdatePostServiceCallBack() {
        mUpdatePostService.setUpdatePostServiceCallBack(new IUpdatePostServiceCallBack() {
            @Override
            public void showMessageError(String message) {
                mEditPostPresenterCallBack.showMessageError(message);

            }

            @Override
            public void noInternetFound() {
                mEditPostPresenterCallBack.networkErrorMessage();
            }

            @Override
            public void updatePostSuccessful() {
                saveImage();
            }

        });
    }


    private void saveImage() {
        setUpdateImagePostServiceCallBack();
        if (mPostModel.getImageUrl().size() != 0) {
            mUpdateImagePostService.updateImageOfPost(mRemainingSpace, deletedImage, mPostModel.getImageUrl(), mPostModel.getPostKey(), mPostModel.getUserPostModel().getUserInfoModel().getKeyOfUser());
        } else if (deletedImage.size() != 0) {
            mUpdateImagePostService.deleteImages(deletedImage, mPostModel.getPostKey(), mPostModel.getUserPostModel().getUserInfoModel().getKeyOfUser());
        } else {
            mEditPostPresenterCallBack.updatePostSuccessful();
        }
    }


    private void setUpdateImagePostServiceCallBack() {
        mUpdateImagePostService.setUpdateImagePostServiceCallBack(new IUpdateImagePostServiceCallBack() {
            @Override
            public void updateImagePostSuccessful() {
                mEditPostPresenterCallBack.updatePostSuccessful();
            }

            @Override
            public void showMessageError(String message) {
                mEditPostPresenterCallBack.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mEditPostPresenterCallBack.networkErrorMessage();

            }

        });
    }

    private boolean isValid(int number_of_Images, String description) {

        return !StringEmptyUtil.isEmptyString(description) || number_of_Images != 0;
    }
}
