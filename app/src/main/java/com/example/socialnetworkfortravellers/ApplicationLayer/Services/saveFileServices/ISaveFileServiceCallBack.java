package com.example.socialnetworkfortravellers.ApplicationLayer.Services.saveFileServices;

import android.net.Uri;

public interface ISaveFileServiceCallBack {
    void failure(String message);

    void urlFile(Uri mUri);

}
