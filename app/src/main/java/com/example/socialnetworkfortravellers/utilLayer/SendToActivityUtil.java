package com.example.socialnetworkfortravellers.utilLayer;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.utilLayer.interfaces.ISendToActivityUtli;

/**
 * responsibility of this this class is create intent and send user from activity to other activity
 */
public class SendToActivityUtil implements ISendToActivityUtli {


    private static SendToActivityUtil ourInstance;


    public static SendToActivityUtil getInstance() {
        return ourInstance = (ourInstance != null) ? ourInstance : new SendToActivityUtil();
    }


    /**
     * send user from current activity to next activity
     */
    @Override
    public void SendUserToOtherActivityWithTransitionLeftin_out(AppCompatActivity activity, Class<?> cls) {
        try {
            StartIntent(activity.getApplicationContext(), cls);
            activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * send user from current activity to next activity
     */
    @Override
    public void SendUserToOtherActivityWithTransitionEnterFromRight(AppCompatActivity activity, Class<?> cls) {
        try {
            StartIntent(activity.getApplicationContext(), cls);
            activity.overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
            activity.finish();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void StartIntent(Context packageContext, Class<?> cls) {
        try {
            Intent intent = new Intent(packageContext, cls);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            packageContext.startActivity(intent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    @Override
    public void SendUserToOtherActivity(Context activity, Class<?> cls) {
        StartIntent(activity, cls);
    }


}
