package com.example.socialnetworkfortravellers.ViewLayer.Activties.baseActivity;


import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.IPickFile;

import java.util.ArrayList;
import java.util.List;

import droidninja.filepicker.FilePickerBuilder;

/**
 * responsibility of this class is used to display Image Picker Activity to user.
 */

public abstract class ImagePickerActivity extends FilePickerActivity  {

    public static final int CUSTOM_IMAGE_REQUEST_CODE = 532;
    private List<String> photoPaths;
    public int COUNT = 1;


    public ImagePickerActivity() {
        photoPaths = new ArrayList<>();
    }


    private List<String> getPhotoPaths() {
        return photoPaths;
    }

    public void setPhotoPaths(List<String> photoPaths) {
        this.photoPaths = photoPaths;
    }


    /**
     * start Image Picker activity.
     */
    @Override
    public void onPickFile() {
        try {
            FilePickerBuilder.getInstance()
                    .setMaxCount(COUNT)
                    .setSelectedFiles(new ArrayList<>(getPhotoPaths()))
                    .setActivityTheme(R.style.AppTheme)
                    .setActivityTitle("select Picture")
                    .enableVideoPicker(false)
                    .enableCameraSupport(true)
                    .showGifs(false)
                    .showFolderView(true)
                    .enableSelectAll(false)
                    .enableImagePicker(true)
                    .pickPhoto(this, CUSTOM_IMAGE_REQUEST_CODE);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
