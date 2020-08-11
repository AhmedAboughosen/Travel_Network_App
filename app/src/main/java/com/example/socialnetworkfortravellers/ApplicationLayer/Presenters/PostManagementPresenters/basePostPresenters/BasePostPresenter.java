package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.basePostPresenters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.CountryModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.GetAllCountryServices.GetAllCountryService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.GetAllCountryServices.IGetAllCountryServicesCallBack;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.baseActivity.ImagePickerActivity;

import java.util.List;
import java.util.Objects;

import droidninja.filepicker.FilePickerConst;

public class BasePostPresenter implements IBasePostPresenter {


    private IBasePostPresenterCallBack mBasePostPresenterCallBack;
    private Context mContext;


    public BasePostPresenter(Context context) {
        this.mContext = context;
    }


    /**
     * check if intent contain data or not if yes save data in list.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void getFilePath(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == ImagePickerActivity.CUSTOM_IMAGE_REQUEST_CODE) {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    try {
                        List<String> listOfImages = Objects.requireNonNull(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));
                        if (listOfImages.size() >= 1) {
                            mBasePostPresenterCallBack.setUpImageUrl(listOfImages);
                        } else {
                            mBasePostPresenterCallBack.showMessageError("you don't select Image !");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        mBasePostPresenterCallBack.showMessageError(ex.getMessage());
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            mBasePostPresenterCallBack.showMessageError(ex.getMessage());
        }
    }


    @Override
    public void getAllCountry() {
        GetAllCountryService.getInstance().getCountryInfo(this.mContext, new IGetAllCountryServicesCallBack() {
            @Override
            public void getAllCountry(List<CountryModel> list) {
                mBasePostPresenterCallBack.getAllCountry(list);
            }

            @Override
            public void noCountry(String str) {
                mBasePostPresenterCallBack.noCountry(str);
            }
        });
    }


    @Override
    public void setUpIBasePostPresenterCallBack(IBasePostPresenterCallBack mIBasePostPresenterCallBack) {
        this.mBasePostPresenterCallBack = mIBasePostPresenterCallBack;
    }
}
