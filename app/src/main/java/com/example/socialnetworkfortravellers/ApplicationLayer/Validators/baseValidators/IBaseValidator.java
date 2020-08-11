package com.example.socialnetworkfortravellers.ApplicationLayer.Validators.baseValidators;

public interface IBaseValidator {

    void validation();

    void setValidatorCallBack(IValidatorCallBack validatorCallBack);
}
