package com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.bioService;

public interface IBioService {


    void saveBio(String bio);
    void setUpBioServiceCallBack(IBioServiceCallBack mBioServiceCallBack);
}
