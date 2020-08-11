package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.editUserInformationPresenters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.CountryModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.CountriesListServices.ICountriesListService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.CountriesListServices.ICountriesListServiceCallback;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.saveProfileImageServices.ISaveProfileImageService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.saveProfileImageServices.ISaveProfileImageServiceCallback;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.saveUserInfoServices.ISaveUserInfoService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.saveUserInfoServices.ISaveUserInfoServiceCallback;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.removeUserCountryService.IRemoveUserCountryService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.removeUserCountryService.IRemoveUserCountryServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Validators.baseValidators.IBaseValidator;
import com.example.socialnetworkfortravellers.ApplicationLayer.Validators.baseValidators.IValidatorCallBack;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.baseActivity.ImagePickerActivity;
import com.example.socialnetworkfortravellers.utilLayer.interfaces.ICompressImageUtil;
import com.mobsandgeeks.saripaar.ValidationError;

import java.io.File;
import java.util.List;
import java.util.Objects;

import droidninja.filepicker.FilePickerConst;

public class EditUserInformationPresenter implements IEditUserInformationPresenter {

    private ISaveUserInfoService mSaveUserInfoService;
    private ISaveProfileImageService mSaveProfileImageService;
    private IBaseValidator mBaseValidator;
    private ICountriesListService mCountriesListService;
    private IEditUserInformationPresenterCallBack mEditUserInformationPresenterCallBack;
    private IRemoveUserCountryService mRemoveUserCountryService;
    private ICompressImageUtil mCompressImageUtil;
    private UserModel mUserModel;
    private boolean isUserInfoChange, isProfilePictureChange;
    private String mOldCountry;

    public EditUserInformationPresenter(ISaveUserInfoService saveUserInfoService, ISaveProfileImageService saveProfileImageService, IBaseValidator baseValidator,
                                        ICountriesListService countriesListService, IRemoveUserCountryService removeUserCountryService, ICompressImageUtil compressImageUtil) {
        this.mSaveUserInfoService = saveUserInfoService;
        this.mSaveProfileImageService = saveProfileImageService;
        this.mCountriesListService = countriesListService;
        this.mBaseValidator = baseValidator;
        this.mRemoveUserCountryService = removeUserCountryService;
        this.mCompressImageUtil = compressImageUtil;
        setValidatorCallBack();
        setUpSetUpUserInfoServiceCallback();
        setUpRemoveUserCountryServiceCallBack();
        setSaveProfileImageServiceCallback();
    }


    @Override
    public void setUpEditUserInformationPresenterCallBack(IEditUserInformationPresenterCallBack mEditUserInformationPresenterCallBack) {
        this.mEditUserInformationPresenterCallBack = mEditUserInformationPresenterCallBack;
    }


    /**
     * check if intent cotaint data or not if yes save data in list.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void getFilePath(int requestCode, int resultCode, Intent data) {
        try {

            if (requestCode == ImagePickerActivity.CUSTOM_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
                List<String> listOfImages = Objects.requireNonNull(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));
                if (listOfImages.size() >= 1) {
                    mEditUserInformationPresenterCallBack.updateProfileImage(listOfImages.get(0));
                } else {
                    mEditUserInformationPresenterCallBack.releasePicker();
                }
            } else {
                mEditUserInformationPresenterCallBack.releasePicker();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            mEditUserInformationPresenterCallBack.ExceptionMessage(ex.getMessage());
        }
    }


    @Override
    public void getAllCountry(String oldCountry) {
        this.mOldCountry = oldCountry;


        mCountriesListService.setUpCountriesListServiceCallback(new ICountriesListServiceCallback() {
            @Override
            public void getAllCountry(List<CountryModel> list) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getCityName().equals(oldCountry)) {
                        list.remove(i);
                        break;
                    }
                }

                mEditUserInformationPresenterCallBack.getAllCountry(list);
            }

            @Override
            public void noCountry(String str) {
                mEditUserInformationPresenterCallBack.noCountry(str);
            }
        });
        mCountriesListService.getALLCountries();
    }


    @Override
    public void updateUserInfo(UserModel userModel, boolean isProfilePictureChange) {
        this.mUserModel = userModel;
        this.isProfilePictureChange = isProfilePictureChange;
        mBaseValidator.validation();
    }

    /**
     * call back if validation Succeeded or Failed.
     */
    private void setValidatorCallBack() {
        mBaseValidator.setValidatorCallBack(new IValidatorCallBack() {
            @Override
            public void onValidationSucceeded() {
                updateUserNodeData();
            }

            @Override
            public void onValidationFailed(List<ValidationError> errors) {
                mEditUserInformationPresenterCallBack.errorInValidator(errors);
            }
        });
    }


    private void updateUserNodeData() {
        mSaveUserInfoService.saveUserInfo(mUserModel);
    }

    /**
     * setUpSetUpUserInfoServiceCallback
     */
    private void setUpSetUpUserInfoServiceCallback() {
        mSaveUserInfoService.setUpSetUpUserInfoServiceCallback(new ISaveUserInfoServiceCallback() {
            @Override
            public void saveUserSuccessful() {
                if (!mUserModel.getCountry().equals(mOldCountry)) {
                    mRemoveUserCountryService.removeCountry(mUserModel.getUserInfoModel().getKeyOfUser(), mOldCountry);
                } else {
                    if (isProfilePictureChange)
                        updateProfilePicture();
                    else
                        mEditUserInformationPresenterCallBack.completeSaveDataSuccessfully("");
                }
            }

            @Override
            public void showMessageError(String message) {
                mEditUserInformationPresenterCallBack.ExceptionMessage(message);
            }

            @Override
            public void noInternetFound() {
                mEditUserInformationPresenterCallBack.internetIsNotConnected();
            }

        });
    }


    private void setUpRemoveUserCountryServiceCallBack() {

        mRemoveUserCountryService.setUpRemoveUserCountryServiceCallBack(new IRemoveUserCountryServiceCallBack() {
            @Override
            public void removeCountrySuccessful() {
                if (isProfilePictureChange)
                    updateProfilePicture();
                else
                    mEditUserInformationPresenterCallBack.completeSaveDataSuccessfully("");
            }

            @Override
            public void showMessageError(String message) {
                mEditUserInformationPresenterCallBack.ExceptionMessage(message);

            }

            @Override
            public void noInternetFound() {
                mEditUserInformationPresenterCallBack.internetIsNotConnected();

            }
        });
    }

    private void updateProfilePicture() {
        Uri imageUri = Uri.fromFile(new File(mUserModel.getProfilePicture()));

        byte[] data = mCompressImageUtil.compressImage(imageUri);
        mSaveProfileImageService.saveProfileImage(data);
    }


    private void setSaveProfileImageServiceCallback() {


        mSaveProfileImageService.setSaveProfileImageServiceCallback(new ISaveProfileImageServiceCallback() {
            @Override
            public void uploadProfileImageSuccessful(String message, String url) {
                mEditUserInformationPresenterCallBack.completeSaveDataSuccessfully(url);
            }

            @Override
            public void showMessageError(String message) {
                mEditUserInformationPresenterCallBack.ExceptionMessage(message);
            }

            @Override
            public void networkErrorMessage() {
                mEditUserInformationPresenterCallBack.internetIsNotConnected();

            }
        });
    }
}
