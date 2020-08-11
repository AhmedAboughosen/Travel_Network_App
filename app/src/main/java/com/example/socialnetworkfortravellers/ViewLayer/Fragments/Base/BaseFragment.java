package com.example.socialnetworkfortravellers.ViewLayer.Fragments.Base;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Dialog.ShowMessageSweetDialog;

import javax.inject.Inject;

import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class BaseFragment extends Fragment {

    @Inject
    public ShowMessageSweetDialog mShowMessageSweetDialog;


    public void initView(View view) {
        ButterKnife.bind(this, view);
    }

    protected void startWaiting(String message, boolean setCancelable) {
        mShowMessageSweetDialog.startWaiting(message, setCancelable);
    }

    public void finishWaiting() {
        mShowMessageSweetDialog.finishWaiting();
    }

    public void showMessagesError(String message) {
        mShowMessageSweetDialog.showMessagesError(message);
    }

    public SweetAlertDialog showMessagesSuccess(String message) {
        return mShowMessageSweetDialog.showMessagesSuccess(message);
    }

    protected void animateWithZoom() {
        if (getActivity() != null)
            getActivity().overridePendingTransition(R.anim.zoom_in_transition, R.anim.zoom_out_transition);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        getLifecycle().removeObserver(mShowMessageSweetDialog);
    }
}
