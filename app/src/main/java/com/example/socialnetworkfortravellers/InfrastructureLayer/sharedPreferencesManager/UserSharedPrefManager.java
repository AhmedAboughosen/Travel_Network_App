package com.example.socialnetworkfortravellers.InfrastructureLayer.sharedPreferencesManager;


import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

public class UserSharedPrefManager {
    public static final String LOGIN_AS_USER_COMPLETED = "RegisterActivityCompleted";
    public static final String SET_UP_ACTIVITY_COMPLETED = "SetUpActivityCompleted";
    public static final String PROFILE_PICTURE_ACTIVITY_COMPLETED = "ProfilePictureActivityCompleted";
    private final String SHARED_PREF_NAME = "UserSharedPrefManager";
    private final String KEY_IMAGEURL = "ImageUrl";
    private final String KEY_FULLNAME = "FullName";
    private final String KEY_Country = "Country";
    private final String USERACCOUNT = "USERACCOUNT";

    private Context mCtx;

    @Inject
    public UserSharedPrefManager(Context mCtx) {
        this.mCtx = mCtx;
    }







    /**
     * get country Name of user
     *
     * @return
     */
    public String getStateOfUser() {
        try {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            return sharedPreferences.getString(USERACCOUNT, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    /**
     * set Country Name
     *
     * @param countryName
     * @return
     */
    public boolean setCountryName(String countryName) {

        try {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_Country, countryName);
            editor.apply();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return false;

    }


    /**
     * get country Name of user
     *
     * @return
     */
    public String getCountryName() {
        try {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            return sharedPreferences.getString(KEY_Country, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public boolean setImageUrl(String ImageUrl) {

        try {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_IMAGEURL, ImageUrl);
            editor.apply();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return false;
    }


    public boolean setFullName(String FullName) {

        try {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_FULLNAME, FullName);
            editor.apply();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return false;
    }

    public String getImageUrl() {
        try {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            return sharedPreferences.getString(KEY_IMAGEURL, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public String getFullName() {
        try {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            return sharedPreferences.getString(KEY_FULLNAME, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
