package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.bioPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.bioService.IBioService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.bioService.IBioServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Validators.baseValidators.IBaseValidator;
import com.example.socialnetworkfortravellers.ApplicationLayer.Validators.baseValidators.IValidatorCallBack;
import com.mobsandgeeks.saripaar.ValidationError;

import java.util.List;

public class BioPresenter implements IBioPresenter {


    private IBaseValidator mBaseValidator;
    private String mBio;
    private IBioPresenterCallBack mBioPresenterCallBack;
    private IBioService mBioService;

    public BioPresenter(IBaseValidator baseValidator, IBioService bioService) {
        this.mBaseValidator = baseValidator;
        this.mBioService = bioService;
        setupValidatorCallback();
    }


    @Override
    public void saveBio(String bio) {
        this.mBio = bio;
        mBaseValidator.validation();
    }


    private void setupValidatorCallback() {
        mBaseValidator.setValidatorCallBack(new IValidatorCallBack() {
            @Override
            public void onValidationSucceeded() {
                saveBio();
            }

            @Override
            public void onValidationFailed(List<ValidationError> errors) {
                mBioPresenterCallBack.errorInValidator(errors);
            }
        });
    }


    private void saveBio() {
        mBioService.setUpBioServiceCallBack(new IBioServiceCallBack() {
            @Override
            public void showMessageError(String message) {
                mBioPresenterCallBack.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mBioPresenterCallBack.networkErrorMessage();
            }

            @Override
            public void Successful(String message) {
                mBioPresenterCallBack.saveBioSuccessful(message);
            }

        });
        mBioService.saveBio(mBio);
    }

    @Override
    public void setUpBioPresenterCallBack(IBioPresenterCallBack mBioPresenterCallBack) {
        this.mBioPresenterCallBack = mBioPresenterCallBack;
    }
}
