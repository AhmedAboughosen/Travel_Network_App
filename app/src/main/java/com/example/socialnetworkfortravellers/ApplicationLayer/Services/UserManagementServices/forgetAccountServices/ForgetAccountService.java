package com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.forgetAccountServices;

import com.example.socialnetworkfortravellers.utilLayer.ConfigUtil;
import com.example.socialnetworkfortravellers.utilLayer.StringConfigUtil;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

/**
 * responsibility of this this Service used to create user in database.
 */
public class ForgetAccountService implements IForgetAccountService {


    private IForgetAccountServiceCallBack mForgetAccountServiceCallBack;

    @Inject
    public ForgetAccountService() {
    }


    @Override
    public void sendPasswordResetEmail(String email) {
        try {

            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(task -> {
                        try {
                            if (task.isSuccessful()) {
                                mForgetAccountServiceCallBack.sendPasswordSuccessful();
                            } else {
                                mForgetAccountServiceCallBack.showMessageError(task.getException().getMessage());
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                            mForgetAccountServiceCallBack.showMessageError(StringConfigUtil.MESSAGE_PROBLEM);
                        }

                    });

        } catch (Exception ex) {
            ex.printStackTrace();
            mForgetAccountServiceCallBack.showMessageError(StringConfigUtil.MESSAGE_PROBLEM);
        }
    }


    @Override
    public void setUpForgetAccountServiceCallBack(IForgetAccountServiceCallBack forgetAccountServiceCallBack) {
        this.mForgetAccountServiceCallBack = forgetAccountServiceCallBack;
    }


}
