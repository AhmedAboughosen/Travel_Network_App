package com.example.socialnetworkfortravellers.utilLayer;

import android.content.Context;
import android.widget.Toast;


/**
 * responsibility of this class is display Toast Message to user.
 */
public class DisplayMessageToast {


    public static void MakeMessage(Context context , String text , int LENGTH)
    {
        Toast.makeText(context, text,LENGTH).show();
    }
}

