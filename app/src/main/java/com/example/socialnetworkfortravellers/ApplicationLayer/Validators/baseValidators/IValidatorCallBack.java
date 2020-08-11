package com.example.socialnetworkfortravellers.ApplicationLayer.Validators.baseValidators;

import com.mobsandgeeks.saripaar.ValidationError;

import java.util.List;

public interface IValidatorCallBack {
    void onValidationSucceeded();
    void onValidationFailed(List<ValidationError> errors);
}
