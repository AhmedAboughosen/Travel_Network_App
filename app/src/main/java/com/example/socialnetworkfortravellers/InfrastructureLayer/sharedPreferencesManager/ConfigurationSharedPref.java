package com.example.socialnetworkfortravellers.InfrastructureLayer.sharedPreferencesManager;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.socialnetworkfortravellers.ViewLayer.Activties.InterestManagementAcitivites.AddInterestActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.loginActivity.LoginActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.suggestionActivity.FriendSuggestionActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.userRegistrationActivities.BioActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.userRegistrationActivities.ProfilePictureActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.userRegistrationActivities.SetUpActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.mainActivity.MainActivity;

public class ConfigurationSharedPref {

    private static final String SET_UP_START_UP_ACTIVITY = "setUpStartUpActivity";
    public static final String LOGIN = "Login";
    public static final String MAIN = "Main";
    public static final String SET_UP = "SetUp";
    public static final String REGISTER = "Register";
    public static final String PROFILE_PICTURE = "ProfilePicture";
    public static final String BIO = "BIO";
    public static final String ADD_INTEREST = "AddInterest";
    public static final String FRIEND_SUGGESTION = "FriendSuggestion";
    private static ConfigurationSharedPref mConfigurationSharedPref;
    private static final String SHARED_PREF_NAME = "ConfigurationSharedPref";

    public static ConfigurationSharedPref getInstance() {
        return mConfigurationSharedPref == null ? new ConfigurationSharedPref() : mConfigurationSharedPref;
    }

    private ConfigurationSharedPref() {
    }


    public  void setUpStartUpActivity(Context context, String activityName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SET_UP_START_UP_ACTIVITY, activityName);
        editor.apply();
    }

    /**
     * get country Name of user
     *
     * @return
     */
    public  Class<?> getStartUpActivity(Context context) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            String activityName = sharedPreferences.getString(SET_UP_START_UP_ACTIVITY, LOGIN);


            if (activityName.equals(LOGIN)) {
                return LoginActivity.class;
            }

            if (activityName.equals(SET_UP)) {
                return SetUpActivity.class;
            }

            if (activityName.equals(PROFILE_PICTURE)) {
                return ProfilePictureActivity.class;
            }


            if (activityName.equals(BIO)) {
                return BioActivity.class;
            }

            if (activityName.equals(ADD_INTEREST)) {
                return AddInterestActivity.class;
            }

            if (activityName.equals(FRIEND_SUGGESTION)) {
                return FriendSuggestionActivity.class;
            }

            if (activityName.equals(MAIN)) {
                return MainActivity.class;
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
