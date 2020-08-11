package com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveImageServices;

import android.net.Uri;

public interface ISaveImageServiceCallBack {

    void failure(String message);

    void urlFile(Uri mUri);

}
