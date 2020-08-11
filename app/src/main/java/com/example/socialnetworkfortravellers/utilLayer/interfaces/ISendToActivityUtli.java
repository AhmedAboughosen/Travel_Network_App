package com.example.socialnetworkfortravellers.utilLayer.interfaces;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

public interface ISendToActivityUtli {

    void SendUserToOtherActivityWithTransitionLeftin_out(AppCompatActivity activity, Class<?> cls);

    void SendUserToOtherActivityWithTransitionEnterFromRight(AppCompatActivity activity, Class<?> cls);

    void SendUserToOtherActivity(Context activity, Class<?> cls);
}
