package com.example.socialnetworkfortravellers.ViewLayer.Dialog;

import android.app.Dialog;
import android.content.Context;

import com.example.socialnetworkfortravellers.R;

import javax.inject.Inject;

public class EnterLocationDialog extends BaseDialog {


    @Inject
    public EnterLocationDialog(Context context) {
        super(context);
    }

    public Dialog enterLocationDialog() {

        try {
            //create Dialog
            final Dialog mdialog = dialog(R.layout.dialog_enter_location);
            //create instance

            mdialog.show();

            return mdialog;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
