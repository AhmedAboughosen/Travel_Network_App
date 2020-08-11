package com.example.socialnetworkfortravellers.ViewLayer.Dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.IOkCancelDialogCallBack;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ShowMessageSweetDialog implements LifecycleObserver {


    private SweetAlertDialog mProgressDialog, mSweetAlertDialog;
    private List<SweetAlertDialog> mSweetAlertDialogList = new ArrayList<>();
    private Context mContext;
    private boolean isInMainThread = true, isShowDialog = false;


    @Inject
    public ShowMessageSweetDialog(Context context) {
        this.mContext = context;
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        isInMainThread = false;
        Log.d("onPause", "ON_PAUSE");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        isInMainThread = true;
        if (isShowDialog && mSweetAlertDialogList.size() >= 1) {
            isShowDialog = false;
            mSweetAlertDialogList.get(mSweetAlertDialogList.size() - 1).show();
        }
        Log.d("onResume", "ON_RESUME");
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        isInMainThread = false;
        Log.d("onStop", "ON_STOP");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        isInMainThread = true;
        Log.d("onStart", "ON_START");
    }


    public void startWaiting(String message, boolean setCancelable) {
        mProgressDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        mProgressDialog.getProgressHelper().setBarColor(Color.parseColor("#0277BD"));
        mProgressDialog.setTitleText(message);
        mProgressDialog.setCancelable(setCancelable);

        mProgressDialog.setOnShowListener(dialog -> {
            SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
            TextView titleTextView = alertDialog.findViewById(R.id.title_text);
            Typeface face = Typeface.createFromAsset(mContext.getAssets(), "fonts/font_style_regular.ttf");
            titleTextView.setTypeface(face);
        });

        showDialog(mProgressDialog);
    }

    private void showDialog(SweetAlertDialog sweetAlertDialog) {
        try {
            mSweetAlertDialogList.add(sweetAlertDialog);
            if (isInMainThread) {
                sweetAlertDialog.show();
            } else {
                isShowDialog = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void finishWaiting() {
        try {
            for (SweetAlertDialog dialog : mSweetAlertDialogList)
                if (dialog.isShowing())
                    dialog.dismiss();

            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showMessagesError(String message) {
        mSweetAlertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE);

        mSweetAlertDialog.setTitleText("Warning Message");
        mSweetAlertDialog.setContentText(message);
        mSweetAlertDialog.setConfirmText("Ok");
        mSweetAlertDialog.setConfirmClickListener(sweetAlertDialog -> mSweetAlertDialog.cancel());
        mSweetAlertDialog.setOnShowListener(dialog -> {
            SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;

            TextView titleTextView = alertDialog.findViewById(R.id.title_text);
            TextView contentTextView = alertDialog.findViewById(R.id.content_text);
            Button confirmButton = alertDialog.findViewById(R.id.confirm_button);
            contentTextView.setSingleLine(false);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                contentTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }
            contentTextView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            Typeface face = Typeface.createFromAsset(mContext.getAssets(), "fonts/font_style_regular.ttf");
            titleTextView.setTypeface(face);
            contentTextView.setTypeface(face);
            confirmButton.setTypeface(face);
        });

        showDialog(mSweetAlertDialog);
    }

    public SweetAlertDialog showMessagesSuccess(String message) {
        mSweetAlertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE);

        mSweetAlertDialog.setTitleText("Warning Message");
        mSweetAlertDialog.setContentText(message);
        mSweetAlertDialog.setConfirmText("OK");
        mSweetAlertDialog.setCancelable(false);
        mSweetAlertDialog.setConfirmClickListener(sweetAlertDialog -> mSweetAlertDialog.dismiss());
        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        mSweetAlertDialog.getWindow().setLayout(width, height);
        mSweetAlertDialog.setOnShowListener(dialog -> {
            TextView titleTextView = mSweetAlertDialog.findViewById(R.id.title_text);
            TextView contentTextView = mSweetAlertDialog.findViewById(R.id.content_text);
            Button confirmButton = mSweetAlertDialog.findViewById(R.id.confirm_button);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                contentTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }
            contentTextView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            Typeface face = Typeface.createFromAsset(mContext.getAssets(), "fonts/font_style_regular.ttf");
            titleTextView.setTypeface(face);
            contentTextView.setTypeface(face);
            confirmButton.setTypeface(face);
        });
        showDialog(mSweetAlertDialog);
        return mSweetAlertDialog;
    }


    public void okCancelDialog(String message, IOkCancelDialogCallBack okCancelDialogCallBack) {
        mSweetAlertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE);

        mSweetAlertDialog.setTitleText("Warning Message");
        mSweetAlertDialog.setContentText(message);
        mSweetAlertDialog.setConfirmText("OK");
        mSweetAlertDialog.setCancelText("Cancel");
        mSweetAlertDialog.showCancelButton(true);
        mSweetAlertDialog.setCancelClickListener(sweetAlertDialog -> okCancelDialogCallBack.onCancelClick());

        mSweetAlertDialog.setConfirmClickListener(sweetAlertDialog -> okCancelDialogCallBack.onOkClick());


        mSweetAlertDialog.setOnShowListener(dialog -> {
            SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;

            TextView titleTextView = alertDialog.findViewById(R.id.title_text);
            TextView contentTextView = alertDialog.findViewById(R.id.content_text);
            Button confirmButton = alertDialog.findViewById(R.id.confirm_button);
            Button cancelButton = alertDialog.findViewById(R.id.cancel_button);

            Typeface face = Typeface.createFromAsset(mContext.getAssets(), "fonts/font_style_regular.ttf");
            titleTextView.setTypeface(face);
            contentTextView.setTypeface(face);
            confirmButton.setTypeface(face);
            cancelButton.setTypeface(face);
        });
        showDialog(mSweetAlertDialog);
    }

}
