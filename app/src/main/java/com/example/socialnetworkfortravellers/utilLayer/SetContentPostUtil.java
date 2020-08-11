package com.example.socialnetworkfortravellers.utilLayer;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.socialnetworkfortravellers.R;

import java.util.HashMap;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetContentPostUtil {

    /**
     * set details of Post
     *
     * @param holder
     * @param description
     */
    public static void setDescription(@NonNull TextView holder, String description) {
        if (StringEmptyUtil.isEmptyString(description)) {
            holder.setVisibility(View.GONE);
            return;
        }
        holder.setVisibility(View.VISIBLE);
        holder.setText(description);
    }


    /**
     * set Profile Picture of user and FullName of user
     *
     * @param circleImageView
     * @param textView
     * @param fullName
     * @param ProfileImage
     */
    public static void setUserInfo(@NonNull CircleImageView circleImageView, TextView textView, String fullName, String ProfileImage, Context context, String country) {
        if (!StringEmptyUtil.isEmptyString(country)) {

            String sourceString = "<b>" + fullName + "</b> " + "is at " + "<b>" + country + "</b> ";

            textView.setText(Html.fromHtml(sourceString));
        } else {
            String sourceString = "<b>" + fullName + "</b>";

            textView.setText(Html.fromHtml(sourceString));
        }

        if (!StringEmptyUtil.isEmptyString(ProfileImage)) {
            Glide.with(Objects.requireNonNull(Objects.requireNonNull(context))).load(ProfileImage).into(circleImageView);
        } else {
            Glide.with(Objects.requireNonNull(Objects.requireNonNull(context))).load(R.drawable.user_image).into(circleImageView);
        }
    }


    /**
     * set Location Of User if exists
     *
     * @param location_id
     * @param current_location
     * @param location
     */
    public static void setLocation(@NonNull ImageView location_id, TextView current_location, String location) {

        if (!StringEmptyUtil.isEmptyString(location)) {
            current_location.setVisibility(View.VISIBLE);
            location_id.setVisibility(View.VISIBLE);
            current_location.setText(location);
            return;
        }
        current_location.setVisibility(View.GONE);
        location_id.setVisibility(View.GONE);
    }


    /**
     * set Post date
     *
     * @param holder
     * @param date
     */
    public static void setDate(@NonNull TextView holder, String date) {

        try {
            String current_date = ConvertTimeUtil.toTimeStamp(date);
            holder.setText(current_date + " . story");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * setUserLikes
     *
     * @param holder
     * @param numberOfLikes
     */
    public static void setUserLikes(@NonNull TextView holder, String numberOfLikes, HashMap<String, Boolean> likesMap, ImageView loveImageView, TextView likesTextViews, Context context) {

        try {
            if (likesMap.containsKey(CurrentUserIDUtil.getInstance().getCurrentUserID())) {
                loveImageView.setImageResource(R.drawable.ic_like_red);
                likesTextViews.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            } else {
                loveImageView.setImageResource(R.drawable.ic_unlike_black);
                likesTextViews.setTextColor(context.getResources().getColor(R.color.black));
            }

            holder.setText((!numberOfLikes.equals("0")) ? numberOfLikes + " likes" : "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
