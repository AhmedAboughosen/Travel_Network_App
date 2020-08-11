package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules;

import android.content.Context;

import com.example.socialnetworkfortravellers.ApplicationLayer.Validators.baseValidators.BaseValidator;
import com.example.socialnetworkfortravellers.ApplicationLayer.Validators.baseValidators.IBaseValidator;

import dagger.Module;
import dagger.Provides;

@Module
public class BaseValidatorModule {

    @Provides
    IBaseValidator ProvidesBaseValidator(Context context) {
        return new BaseValidator(context);
    }
}
