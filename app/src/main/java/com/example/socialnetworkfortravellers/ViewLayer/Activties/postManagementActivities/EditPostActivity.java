package com.example.socialnetworkfortravellers.ViewLayer.Activties.postManagementActivities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.PostModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.editPostPresenters.IEditPostPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.editPostPresenters.IEditPostPresenterCallBack;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.postManagementInjector.EditPostInjector;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Dialog.BaseProgressDialog;
import com.example.socialnetworkfortravellers.eventBus.PostEvent;
import com.example.socialnetworkfortravellers.utilLayer.StringConfigUtil;
import com.example.socialnetworkfortravellers.utilLayer.StringEmptyUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.OnClick;

public class EditPostActivity extends BasePostActivity {


    public static final String POSTOBJECT = "POSTOBJECT";
    private List<Integer> mRemoveableImage;
    private HashMap<String, Integer> indexesMapping;


    @Inject
    IEditPostPresenter mEditPostPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            setUpInject();
            getOutSideObject();
            super.onCreate(savedInstanceState);
            initView();
            setUpViews();
            mToolbarTitle.setText(getString(R.string.edit_post));
            setUpEditPostPresenterCallBack();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void getOutSideObject() {
        mPostModel = (PostModel) getIntent().getSerializableExtra(POSTOBJECT);
    }


    private void setUpInject() {
        EditPostInjector.getSharedInjector().injectIn(this);
    }

    private void setUpViews() {
        mRemoveableImage = new ArrayList<>();
        indexesMapping = new HashMap<>();
       // mTitleEditText.setText("Edit Post");
        mEditTextSharePost.setText("Edit");
        setImageToRecyclerView();
        setUpDetails();
        setUpLocation();
    }


    private void setUpEditPostPresenterCallBack() {
        mEditPostPresenter.setUpEditPostPresenterCallBack(new IEditPostPresenterCallBack() {
            @Override
            public void updatePostSuccessful() {
                BaseProgressDialog.getInstance().finishProgressDialog();
                Toast.makeText(EditPostActivity.this, "you update this post successfully.", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void showMessageError(String message) {
                BaseProgressDialog.getInstance().finishProgressDialog();
                Toast.makeText(EditPostActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void networkErrorMessage() {
                BaseProgressDialog.getInstance().finishProgressDialog();
                Toast.makeText(EditPostActivity.this, StringConfigUtil.NO_INTERNET, Toast.LENGTH_SHORT).show();
            }

        });
    }


    @OnClick(R.id.share_post)
    public void onUpdatePost() {
        BaseProgressDialog.getInstance().progressDialog(EditPostActivity.this, "update Post", "Please wait,while we updating your Posts.....", false);
        mPostModel.setDescription(details.getText().toString());
        mEditPostPresenter.updatePost(mPostModel, mRemoveableImage);
    }


    private void setImageToRecyclerView() {
        if (mPostModel.getImageUrl().size() != 0) {
            for (int i = 0; i < mPostModel.getImageUrl().size(); ++i) {
                indexesMapping.put(mPostModel.getImageUrl().get(i), i);
            }
            mSelectedImageAdapter.updateItems(mPostModel.getImageUrl());
            setUpListOfImageRecyclerView();
        }

    }


    /**
     * when user remove Image
     */
    @Override
    public void removeImageItem() {

        mSelectedImageAdapter.setUpRemoveImageItem(position -> {
            if (indexesMapping.size() > 0) {
                if (indexesMapping.containsKey(mSelectedImageAdapter.getImages().get(position))) {
                    mRemoveableImage.add(indexesMapping.get(mSelectedImageAdapter.getImages().get(position)));
                }
            }
            removeImage(position);

        });
    }

    private void setUpDetails() {
        if (!StringEmptyUtil.isEmptyString(mPostModel.getDescription()))
            details.setText(mPostModel.getDescription());
    }


    private void setUpLocation() {
        if (!StringEmptyUtil.isEmptyString(mPostModel.getLocationPost())) {
            this.drop_location.setVisibility(View.VISIBLE);
            this.location_view.setVisibility(View.VISIBLE);
            this.current_location.setVisibility(View.VISIBLE);
            this.current_location.setText(this.mPostModel.getLocationPost());
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //    EventBus.getDefault().unregister(this);

    }
}
