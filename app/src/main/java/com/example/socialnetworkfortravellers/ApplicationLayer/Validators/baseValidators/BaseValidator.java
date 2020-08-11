package com.example.socialnetworkfortravellers.ApplicationLayer.Validators.baseValidators;

import android.content.Context;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;

import java.util.List;

public class BaseValidator implements Validator.ValidationListener , IBaseValidator{

    private Validator mValidator;
    private IValidatorCallBack mValidatorCallBack;
    private Context mContext;


    public BaseValidator(Context context) {
        mContext = context;
        createNewInstance();
    }


    @Override
    public void validation() {
        mValidator.validate(false);
    }


    @Override
    public void onValidationSucceeded() {
        mValidatorCallBack.onValidationSucceeded();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

        mValidatorCallBack.onValidationFailed(errors);
    }


    @Override
    public void setValidatorCallBack(IValidatorCallBack validatorCallBack) {
        mValidatorCallBack = validatorCallBack;
    }

    private void createNewInstance() {
        this.mValidator = new Validator(mContext);
        mValidator.setValidationListener(BaseValidator.this);
    }
}
