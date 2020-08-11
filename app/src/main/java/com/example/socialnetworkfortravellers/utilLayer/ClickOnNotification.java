package com.example.socialnetworkfortravellers.utilLayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.profileActivity.FriendProfileActivity;

import static com.example.socialnetworkfortravellers.ViewLayer.Activties.mainActivity.MainActivity.FRIENDKEY;


/**
 * Created by Hatem_Lap
 */

public class ClickOnNotification extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (!FRIENDKEY.isEmpty()) {
            Intent intentMain = new Intent(context.getApplicationContext(), FriendProfileActivity.class);
            intentMain.putExtra("FriendKey", FRIENDKEY);
            startActivity(context, intentMain);
        }
        else {
            Toast.makeText(context, StringConfigUtil.MESSAGE_PROBLEM, Toast.LENGTH_SHORT).show();
        }
    }

    private void startActivity(Context context, Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(intent);
    }
}
