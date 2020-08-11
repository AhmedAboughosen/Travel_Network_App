package com.example.socialnetworkfortravellers.ViewLayer.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.IOkCancelDialogCallBack;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

public class OkCancelDialog extends BaseDialog {

    private Dialog mDialog;

    @Inject
    public OkCancelDialog(Context context) {
        super(context);
    }

    public void show(String title, String subTitle, IOkCancelDialogCallBack okCancelDialogCallBack) {

        try {
            //create Dialog
            mDialog = dialog(R.layout.ok_cancel_dialog);

            //create instance
            TextView textViewTitle = mDialog.findViewById(R.id.title);
            TextView textViewSubTitle = mDialog.findViewById(R.id.sub_title);

            textViewTitle.setText(title);
            textViewSubTitle.setText(subTitle);

            FloatingActionButton mDialogNo = mDialog.findViewById(R.id.frmNo);
            mDialogNo.setOnClickListener(v -> okCancelDialogCallBack.onCancelClick());
            FloatingActionButton mDialogOk = mDialog.findViewById(R.id.frmOk);
            mDialogOk.setOnClickListener(v -> okCancelDialogCallBack.onOkClick());
            mDialog.show();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void dismiss() {
        mDialog.dismiss();
    }
}
