package com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.createUserService;

import android.content.Context;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.AccountModel;
import com.example.socialnetworkfortravellers.InfrastructureLayer.ConstantValues;
import com.example.socialnetworkfortravellers.utilLayer.ConfigUtil;
import com.example.socialnetworkfortravellers.utilLayer.NetworkState;
import com.google.firebase.auth.FirebaseAuth;

/**
 * responsibility of this this Service used to create user in Authentication firebase.
 */
public class CreateUserService implements ICreateUserService {


    private Context mContext;
    private AccountModel mAccountModel;
    private ICreateUserServiceCallBack mCreateUserServiceCallBack;

    public CreateUserService(Context context) {
        this.mContext = context;
    }


    /**
     * create a new account
     */
    @Override
    public void createNewAccount(AccountModel accountModel) {
        this.mAccountModel = accountModel;

        if (NetworkState.isNetworkConnected(this.mContext)) {
            sendRequest();
        } else {
            mCreateUserServiceCallBack.noInternetFound();
        }
    }


    /**
     * save Email and Password in authentication
     */

    private void sendRequest() {
        try {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();

            mAuth.createUserWithEmailAndPassword(mAccountModel.getEmail(), mAccountModel.getPassword())
                    .addOnCompleteListener(task -> {
                        try {
                            if (task.isSuccessful()) {
                                mCreateUserServiceCallBack.Successful();
                            } else {
                                mCreateUserServiceCallBack.showMessageError("The email address  you entered is not valid or already registered.");
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            mCreateUserServiceCallBack.showMessageError(ConstantValues.SOMETHING_WENT_WRONG);
                        }
                    });

        } catch (Exception ex) {
            ex.printStackTrace();
            mCreateUserServiceCallBack.showMessageError(ConstantValues.SOMETHING_WENT_WRONG);
        }

    }

    @Override
    public void setUpCreateUserServiceCallBack(ICreateUserServiceCallBack createUserServiceCallBack) {
        this.mCreateUserServiceCallBack = createUserServiceCallBack;
    }


}
