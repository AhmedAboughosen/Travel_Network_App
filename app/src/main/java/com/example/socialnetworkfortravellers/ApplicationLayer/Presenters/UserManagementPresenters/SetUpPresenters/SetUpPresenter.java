package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.SetUpPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.CountryModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.CountriesListServices.ICountriesListService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.CountriesListServices.ICountriesListServiceCallback;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.saveUserInfoServices.ISaveUserInfoService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.saveUserInfoServices.ISaveUserInfoServiceCallback;
import com.example.socialnetworkfortravellers.ApplicationLayer.Validators.baseValidators.IBaseValidator;
import com.example.socialnetworkfortravellers.ApplicationLayer.Validators.baseValidators.IValidatorCallBack;
import com.mobsandgeeks.saripaar.ValidationError;

import java.util.List;


public class SetUpPresenter implements ISetUpPresenter {


    private IBaseValidator mBaseValidator;
    private ISetUpPresenterCallBack mSetUpPresenterCallBack;
    private ISaveUserInfoService mSaveInfoService;
    private UserModel mUserModel;
    private ICountriesListService mCountriesListService;

    public SetUpPresenter(IBaseValidator baseValidator, ISaveUserInfoService saveInfoService, ICountriesListService countriesListService) {
        this.mBaseValidator = baseValidator;
        this.mCountriesListService = countriesListService;
        this.mSaveInfoService = saveInfoService;
        setValidatorCallBack();
    }


    @Override
    public void getAllCountry() {
        mCountriesListService.setUpCountriesListServiceCallback(new ICountriesListServiceCallback() {
            @Override
            public void getAllCountry(List<CountryModel> list) {
                mSetUpPresenterCallBack.getAllCountry(list);
            }

            @Override
            public void noCountry(String str) {
                mSetUpPresenterCallBack.noCountry(str);
            }
        });

        mCountriesListService.getALLCountries();
    }


    @Override
    public void setUpSetUpPresenterCallBack(ISetUpPresenterCallBack setUpPresenterCallBack) {
        this.mSetUpPresenterCallBack = setUpPresenterCallBack;
    }

    @Override
    public void setUpNewAccount(UserModel userModel) {
        mUserModel = userModel;
        mBaseValidator.validation();
    }


    /**
     * call back if validation Succeeded or Failed.
     */
    private void setValidatorCallBack() {
        mBaseValidator.setValidatorCallBack(new IValidatorCallBack() {
            @Override
            public void onValidationSucceeded() {
                saveUserInfo();
            }

            @Override
            public void onValidationFailed(List<ValidationError> errors) {
                mSetUpPresenterCallBack.errorInValidator(errors);
            }
        });
    }


    /**
     * save user object in firebase
     */
    private void saveUserInfo() {
        mSaveInfoService.setUpSetUpUserInfoServiceCallback(new ISaveUserInfoServiceCallback() {
            @Override
            public void saveUserSuccessful() {
                mSetUpPresenterCallBack.completeSaveDataSuccessfully();
            }

            @Override
            public void showMessageError(String message) {
                mSetUpPresenterCallBack.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mSetUpPresenterCallBack.networkErrorMessage();
            }

        });

        mSaveInfoService.saveUserInfo(mUserModel);
    }

}
