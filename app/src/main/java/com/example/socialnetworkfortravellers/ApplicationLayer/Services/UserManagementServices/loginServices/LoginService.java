package com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.loginServices;

import android.content.Context;

import com.example.socialnetworkfortravellers.utilLayer.NetworkState;
import com.google.firebase.auth.FirebaseAuth;

/**
 * responsibility of this Service is to check if user exists in fire base or not.
 */
public class LoginService implements ILoginService {


    private Context mContext;
    private ILoginServiceCallBack mLoginServiceCallBack;

    public LoginService(Context context) {
        this.mContext = context;
    }


    /**
     * check if user exists in fire base or not.
     *
     * @param email
     * @param password
     */
    @Override
    public void login(String email, String password) {

        if (NetworkState.isNetworkConnected(this.mContext)) {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {

                        try {
                            if (task.isSuccessful()) {
                                mLoginServiceCallBack.Successful();
                            } else {
                                mLoginServiceCallBack.showMessageError("The email or password you entered is incorrect");
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            mLoginServiceCallBack.showMessageError(ex.getMessage());
                        }
                    });
        } else {
            //"there is no internet connection"
            mLoginServiceCallBack.noInternetFound();
        }
    }

    /**
     * setLoginServiceCallBack
     *
     * @param mLoginServiceCallBack
     */
    @Override
    public void setLoginServiceCallBack(ILoginServiceCallBack mLoginServiceCallBack) {
        this.mLoginServiceCallBack = mLoginServiceCallBack;
    }
}
