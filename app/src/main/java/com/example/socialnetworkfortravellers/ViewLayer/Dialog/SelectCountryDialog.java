package com.example.socialnetworkfortravellers.ViewLayer.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.FrameLayout;

import com.example.socialnetworkfortravellers.R;

import javax.inject.Inject;

public class SelectCountryDialog extends BaseDialog {

    @Inject
    public SelectCountryDialog(Context context) {
        super(context);
    }

    public Dialog checkSelectedCountryDialog() {

        try {
            //create Dialog
            final Dialog mdialog = dialog(R.layout.dialog_box_set_country_view);
            //create instance

            mdialog.show();
            FrameLayout mDialogNo = mdialog.findViewById(R.id.frmNo);

            mDialogNo.setOnClickListener(v -> mdialog.dismiss());
            return mdialog;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
