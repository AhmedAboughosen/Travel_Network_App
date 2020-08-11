package com.example.socialnetworkfortravellers.ViewLayer.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;
import android.view.Window;

public class BaseDialog {


    protected Context mContext;

    public BaseDialog(Context context) {
        this.mContext = context;
    }


    /**
     * create a dialog.
     *
     * @param i
     * @return
     */
    public Dialog dialog(int i) {
        final Dialog dialog = new Dialog(mContext);

        try {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(i);
            Window window = dialog.getWindow();

            window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dialog;
    }

}
