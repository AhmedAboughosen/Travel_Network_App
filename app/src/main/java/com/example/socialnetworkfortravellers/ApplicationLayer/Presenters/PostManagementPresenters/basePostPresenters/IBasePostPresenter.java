package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.basePostPresenters;

import android.content.Intent;

public interface IBasePostPresenter {
    void getFilePath(int requestCode, int resultCode, Intent data);
    void setUpIBasePostPresenterCallBack(IBasePostPresenterCallBack mIBasePostPresenterCallBack);
    void getAllCountry();
}
