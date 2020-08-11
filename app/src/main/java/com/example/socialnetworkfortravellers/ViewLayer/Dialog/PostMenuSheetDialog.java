package com.example.socialnetworkfortravellers.ViewLayer.Dialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.PostModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.deletePostPresenters.IDeletePostPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.deletePostPresenters.IDeletePostPresenterCallBack;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.postManagementInjector.DeletePostInjector;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.postManagementActivities.EditPostActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.postManagementFragments.PostsFragment;
import com.example.socialnetworkfortravellers.eventBus.DeletePostEvent;
import com.example.socialnetworkfortravellers.utilLayer.StringConfigUtil;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.socialnetworkfortravellers.ViewLayer.Fragments.postManagementFragments.PostsFragment.POSTMODEL;

public class PostMenuSheetDialog extends BottomSheetDialogFragment {


    private PostModel mPostModel;
    private int index;


    @Inject
    IDeletePostPresenter deletePostPresenter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.postmenusheetdialog, container, false);
        ButterKnife.bind(this, view);
        getData();
        setUpInject();
        return view;
    }


    private void setUpInject() {
        DeletePostInjector.getSharedInjector().injectIn(this);
    }

    private void getData() {
        if (getArguments() != null) {
            if (getArguments().getSerializable(POSTMODEL) != null) {
                mPostModel = (PostModel) getArguments().getSerializable(POSTMODEL);
                index = getArguments().getInt(PostsFragment.POST_INDEX);
            }
            setArguments(null);
        }
    }

    @OnClick(R.id.SavePost)
    public void savePost() {
        Toast.makeText(getActivity(), "we will add this feature soon!", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.EditPost)
    public void editPost() {

        Intent intent = new Intent(getActivity(), EditPostActivity.class);
        intent.putExtra(EditPostActivity.POSTOBJECT, mPostModel);
        startActivity(intent);
        dismiss();
    }

    @OnClick(R.id.DeletePost)
    public void deletePost() {
        alertDeletePostDialog();
    }

    private void alertDeletePostDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        builder.setMessage("Do you want to delete this Post?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    try {
                        dialog.cancel();
                        removePost();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }


                })
                .setNegativeButton("No", (dialog, id) -> {
                    //  Action for 'NO' Button
                    dialog.cancel();
                });


        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Delete");
        alert.show();
    }

    private void removePost() {
        BaseProgressDialog.getInstance().progressDialog(getActivity(), "Delete Post", "Please wait,while we Deleting your Post.....", false);

        deletePostPresenter.setDeletePostPresenterCallBack(new IDeletePostPresenterCallBack() {
            @Override
            public void showMessageError(String message) {
                BaseProgressDialog.getInstance().finishProgressDialog();
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void networkErrorMessage() {
                Toast.makeText(getActivity(), StringConfigUtil.NO_INTERNET, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void deletePostSuccessful() {
                EventBus.getDefault().postSticky(new DeletePostEvent(index));

                BaseProgressDialog.getInstance().finishProgressDialog();
                Toast.makeText(getActivity(), "your Post  Deleted successfully", Toast.LENGTH_SHORT).show();
                dismiss();
            }

        });
        deletePostPresenter.deletePost(mPostModel.getUserPostModel().getUserInfoModel().getKeyOfUser(), mPostModel.getPostKey());
    }

}
