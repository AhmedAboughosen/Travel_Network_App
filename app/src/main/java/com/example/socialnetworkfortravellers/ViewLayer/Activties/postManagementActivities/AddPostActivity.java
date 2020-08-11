package com.example.socialnetworkfortravellers.ViewLayer.Activties.postManagementActivities;

import android.os.Bundle;
import android.widget.Toast;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.addPostPresenters.IAddPostPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.addPostPresenters.IAddPostPresenterCallBack;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.postManagementInjector.AddPostInjector;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;
import com.google.firebase.database.ServerValue;

import javax.inject.Inject;

import butterknife.OnClick;


public class AddPostActivity extends BasePostActivity {


    @Inject
    IAddPostPresenter mAddPostPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            setUpInject();
            super.onCreate(savedInstanceState);
            getSupportActionBar().setTitle("");
            mToolbarTitle.setText(getString(R.string.create_post));
            initView();
            setUpAddPostPresenterCallBack();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void setUpInject() {
        AddPostInjector.getSharedInjector().injectIn(this);
    }


    /**
     * setUpAddPostPresenterCallBack
     */
    private void setUpAddPostPresenterCallBack() {
        mAddPostPresenter.setUpAddPostPresenterCallBack(new IAddPostPresenterCallBack() {

            @Override
            public void addPostSuccessful() {
                finishWaiting();
                Toast.makeText(AddPostActivity.this, "you shared this post.", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void showMessageError(String message) {
                finishWaiting();
                Toast.makeText(AddPostActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void networkErrorMessage() {
                finishWaiting();
                Toast.makeText(AddPostActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
            }

        });

    }


    @OnClick(R.id.share_post)
    public void onAddPost() {
        startWaiting("Please wait,while we Create your Posts.....", false);
        mPostModel.setDate(ServerValue.TIMESTAMP);
        mUserModel.getUserInfoModel().setKeyOfUser(CurrentUserIDUtil.getInstance().getCurrentUserID());
        mPostModel.setUserPostModel(mUserModel);
        mPostModel.setDescription(details.getText().toString());
        mAddPostPresenter.addPost(mPostModel);
    }


    @Override
    public void onBackPressed() {
        finish();
        animateWithZoom();
    }
}
