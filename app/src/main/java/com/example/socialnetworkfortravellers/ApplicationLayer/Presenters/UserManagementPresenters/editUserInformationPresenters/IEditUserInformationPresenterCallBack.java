package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.editUserInformationPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.CountryModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.mobsandgeeks.saripaar.ValidationError;

import java.util.List;

public interface IEditUserInformationPresenterCallBack {

    void ExceptionMessage(String message);
    void getAllCountry(List<CountryModel> list);
    void noCountry(String str);

    void internetIsNotConnected();

    void updateProfileImage(String profileImage);

    void releasePicker();

    void errorInValidator(List<ValidationError> errors);

    void completeSaveDataSuccessfully(String message);

}
