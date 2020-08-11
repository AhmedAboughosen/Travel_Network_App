package com.example.socialnetworkfortravellers.ViewLayer.Dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.socialnetworkfortravellers.R;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * responsibility of this class is to start ProgressDialog or message Dialog to user.
 */
public class BaseProgressDialog {


    private ProgressDialog mProgressDialog;
    private List<ProgressDialog> mProgressDialogList;
    private SweetAlertDialog mSweetAlertDialog;

    private BaseProgressDialog() {
        mProgressDialogList = new ArrayList<>();
    }


    private static BaseProgressDialog ourInstance;


    public static BaseProgressDialog getInstance() {
        ourInstance = (ourInstance != null) ? ourInstance : new BaseProgressDialog();
        return ourInstance;
    }


    /**
     * this method used to convert list of string to one string
     *
     * @param message
     * @return
     */
    private String parseMessage(List<String> message) {
        StringBuilder mMessage = null;
        for (int i = 0; i < message.size(); i++) {
            if (mMessage != null) {
                mMessage.append("\n\n").append(message.get(i));
            } else {
                mMessage = new StringBuilder(message.get(i));
            }
        }

        return mMessage.toString();
    }


    /**
     * this method used to display Message Error to user when get error from  fire base or server
     *
     * @param message
     * @param context
     */
    public void showMessagesError(List<String> message, Context context) {
        String mMessage = parseMessage(message);
        mSweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);

        mSweetAlertDialog.setTitleText("Attention");
        mSweetAlertDialog.setContentText(mMessage);
        mSweetAlertDialog.setConfirmText("Ok");
        mSweetAlertDialog.setConfirmClickListener(sweetAlertDialog -> mSweetAlertDialog.dismiss());
        mSweetAlertDialog.setOnShowListener(dialog -> {
            SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;

            TextView contentTextView = alertDialog.findViewById(R.id.content_text);
            contentTextView.setSingleLine(false);
            contentTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            contentTextView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        });

        mSweetAlertDialog.show();
    }


    /**
     * this method used to display Message Error to user when get error from  fire base or server
     *
     * @param message
     * @param context
     */
    public void showMessagesError(String message, Context context) {
        mSweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);

        mSweetAlertDialog.setTitleText("Attention");
        mSweetAlertDialog.setContentText(message);
        mSweetAlertDialog.setConfirmText("Ok");
        mSweetAlertDialog.setConfirmClickListener(sweetAlertDialog -> mSweetAlertDialog.dismiss());
        mSweetAlertDialog.setOnShowListener(dialog -> {
            SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;

            TextView contentTextView = alertDialog.findViewById(R.id.content_text);
            contentTextView.setSingleLine(false);
            contentTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            contentTextView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        });

        mSweetAlertDialog.show();
    }


    /**
     * this method used to display Message success to user when get success from  fire base or server
     *
     * @param message
     * @param context
     */
    public void showMessagesSuccess(String message, Context context) {
        mSweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);

        mSweetAlertDialog.setTitleText("successfully");
        mSweetAlertDialog.setContentText(message);
        mSweetAlertDialog.setConfirmText("Ok");
        mSweetAlertDialog.setConfirmClickListener(sweetAlertDialog -> mSweetAlertDialog.dismiss());
        mSweetAlertDialog.setOnShowListener(dialog -> {
            SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;

            TextView contentTextView = alertDialog.findViewById(R.id.content_text);
            contentTextView.setSingleLine(false);
            contentTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            contentTextView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        });

        mSweetAlertDialog.show();
    }


    /**
     * this method used to display progressDialog to user when send request to fire base or server
     *
     * @param context
     * @param Title
     * @param Message
     * @param setCancelable
     */
    public void progressDialog(Context context, String Title, String Message, boolean setCancelable) {
        mProgressDialog = new ProgressDialog(context);

        try {
            mProgressDialog.setTitle(Title);
            mProgressDialog.setMessage(Message);
            mProgressDialog.show();
            mProgressDialog.setCancelable(setCancelable);
            mProgressDialog.setCanceledOnTouchOutside(setCancelable);
            showReliableDialog(mProgressDialog);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void showReliableDialog(ProgressDialog sweetAlertDialog) {
        try {
            mProgressDialogList.add(sweetAlertDialog);
            sweetAlertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void finishProgressDialog() {
        try {
            for (ProgressDialog dialog : mProgressDialogList)
                if (dialog.isShowing()) dialog.dismiss();

            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void Nullable() {
        ourInstance = null;
        mProgressDialogList = null;
        mProgressDialog = null;
    }

}
