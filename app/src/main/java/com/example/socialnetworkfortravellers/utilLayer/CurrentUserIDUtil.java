package com.example.socialnetworkfortravellers.utilLayer;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class CurrentUserIDUtil {


    private static CurrentUserIDUtil ourInstance;
    private FirebaseAuth mFirebaseAuth;

    public static CurrentUserIDUtil getInstance() {
        return ourInstance = (ourInstance != null) ? ourInstance : new CurrentUserIDUtil();
    }


    private CurrentUserIDUtil() {
        try {
            mFirebaseAuth = FirebaseAuth.getInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public String getCurrentUserID() {
        if (mFirebaseAuth.getCurrentUser() != null)
        return Objects.requireNonNull(mFirebaseAuth.getCurrentUser()).getUid();
        return "";
    }


    public FirebaseUser getCurrentUser() {
        return mFirebaseAuth.getCurrentUser();
    }
}
