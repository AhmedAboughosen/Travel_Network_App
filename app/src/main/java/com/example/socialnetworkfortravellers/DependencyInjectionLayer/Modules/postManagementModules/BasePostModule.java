package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.postManagementModules;

import android.content.Context;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.basePostPresenters.BasePostPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.basePostPresenters.IBasePostPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class BasePostModule {


    @Provides
    public IBasePostPresenter ProvidesBasePostPresenter(Context context) {
        return new BasePostPresenter(context);
    }
}
