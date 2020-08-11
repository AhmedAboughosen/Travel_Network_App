package com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.reauthenticateAccountServices;

import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class ReauthenticateAccountAccountService implements IReauthenticateAccountService {


    private IReauthenticateAccountServiceCallBack mReauthenticateServiceCallBack;

    public ReauthenticateAccountAccountService() {

    }

    /**
     * update Email
     */
    @Override
    public void reauthenticate(String password) {
        try {
            FirebaseUser user = CurrentUserIDUtil.getInstance().getCurrentUser();

            assert user != null;
            AuthCredential credential = EmailAuthProvider
                    .getCredential(Objects.requireNonNull(user.getEmail()), Objects.requireNonNull(password));

            user.reauthenticate(credential)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            mReauthenticateServiceCallBack.isSuccessful();
                        } else {
                            mReauthenticateServiceCallBack.failure(task.getException().getMessage());
                        }
                    });

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    @Override
    public void setReauthenticateServiceCallBack(IReauthenticateAccountServiceCallBack mReauthenticateServiceCallBack) {
        this.mReauthenticateServiceCallBack = mReauthenticateServiceCallBack;
    }
}
