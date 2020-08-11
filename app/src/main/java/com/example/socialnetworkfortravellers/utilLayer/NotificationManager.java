package com.example.socialnetworkfortravellers.utilLayer;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.view.Gravity;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.profileActivity.FriendProfileActivity;
import com.tapadoo.alerter.Alerter;

import java.util.List;


/**
 * Created by Hatem
 */
public class NotificationManager {


    public static final String CHANNEL_ID = "CHANNEL_ID";
    private Activity mContext;
    private String fullName, friendKey;
    private int mCount = 0;


    public NotificationManager(Activity context) {
        this.mContext = context;
    }


    public void createNotificationInApp(String fullName, String friendKey) {
        this.fullName = fullName;
        this.friendKey = friendKey;
        if (isAppIsInBackground(mContext)) {
            createNotificationOutApp(mContext);
        } else {
            createNotificationInAppIsStarted(mContext);
        }
    }

    private boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;

        try {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return isInBackground;
    }

    private void createNotificationInAppIsStarted(final Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel_name_2";
            String description = "channel_description_2";
            int importance = android.app.NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            android.app.NotificationManager notificationManager = activity.getSystemService(android.app.NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        Alerter.create(activity)
                .setContentGravity(Gravity.LEFT)
                .setIcon(R.drawable.main_logo)
                .setTitle("new Followers")
                .setTitleTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/font_style_medium.ttf"))
                .setText(fullName + " started Follow you.")
                .setTextTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/font_style_medium.ttf"))
                .enableSwipeToDismiss()
                .setDuration(6000)
                .setOnClickListener(view -> {
                    Alerter.hide();
                    Intent intent = new Intent(mContext, FriendProfileActivity.class);
                    intent.putExtra("FriendKey", friendKey);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    mContext.startActivity(intent);
                })
                .setBackgroundColorRes(R.color.colorPrimary) // or setBackgroundColorInt(Color.CYAN)
                .show();
    }

    private void createNotificationOutApp(final Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel_name";
            String description = "channel_description";
            int importance = android.app.NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            android.app.NotificationManager notificationManager = context.getSystemService(android.app.NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent = new Intent(context, ClickOnNotification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSound(soundUri)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.main_logo)
                .setContentTitle("new Followers")
                .setAutoCancel(true)
                .setContentText(fullName + " started Follow you.");

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(mCount, notification.build());
        mCount++;
    }


}
