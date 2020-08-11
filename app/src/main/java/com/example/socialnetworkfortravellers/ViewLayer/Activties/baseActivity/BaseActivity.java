package com.example.socialnetworkfortravellers.ViewLayer.Activties.baseActivity;

import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Dialog.ShowMessageSweetDialog;

import javax.inject.Inject;

import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public abstract class BaseActivity extends AppCompatActivity {


    @Inject
    public ShowMessageSweetDialog mShowMessageSweetDialog;



    public void initView() {
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
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
        overridePendingTransition(R.anim.zoom_in_transition, R.anim.zoom_out_transition);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getLifecycle().removeObserver(mShowMessageSweetDialog);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
