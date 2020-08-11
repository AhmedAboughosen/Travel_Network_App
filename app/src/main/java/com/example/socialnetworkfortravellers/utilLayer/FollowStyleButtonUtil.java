package com.example.socialnetworkfortravellers.utilLayer;

import android.content.Context;
import android.widget.Button;

import com.example.socialnetworkfortravellers.R;

public class FollowStyleButtonUtil {


    private static FollowStyleButtonUtil ourinstance;

    public static FollowStyleButtonUtil getInstance() {
        return ourinstance = (ourinstance != null) ? ourinstance : new FollowStyleButtonUtil();
    }

    /**
     * change style of button to following
     *
     * @param follow_button
     * @param context
     */
    public void changeStyleButton_follow(Button follow_button, Context context) {
        follow_button.setText("Following");
        follow_button.setBackgroundResource(R.drawable.custom_button_circle_2);
        if (context != null)
        follow_button.setTextColor(context.getResources().getColor(R.color.white));
    }

    /**
     * change style of button to follow
     *
     * @param follow_button
     * @param context
     */
    public void changeStyleButton_UnFollow(Button follow_button, Context context) {
        follow_button.setText("Follow");
        follow_button.setBackgroundResource(R.drawable.custom_button_circle);
        if (context != null)
        follow_button.setTextColor(context.getResources().getColor(R.color.colorAccent));
    }


    public void Nullable() {
        ourinstance = null;
    }
}
