package com.example.socialnetworkfortravellers.ViewLayer.Dialog;

import android.content.Context;
import android.view.MotionEvent;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BaseSweetAlertDialog extends SweetAlertDialog {


    public BaseSweetAlertDialog(Context context) {
        super(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }


}
