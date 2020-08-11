package com.example.socialnetworkfortravellers.ViewLayer.Activties.baseActivity;


import androidx.annotation.NonNull;

import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.IPickFile;

import java.util.List;
import java.util.Objects;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * responsibility of this class is used to display File Picker Activity to user.
 */
public abstract class FilePickerActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {


    private final int RC_File_PICKER_PERM = 123;

    /**
     * check if activity has Permissions if yes then call fileActivity method
     */
    public void fileActivity() {
        try {
            String[] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA"};

            if (EasyPermissions.hasPermissions(Objects.requireNonNull(this.getApplicationContext()), permissions)) {
                onPickFile();
            } else {
                // Ask for one permission
                EasyPermissions.requestPermissions(this, this.getString(R.string.rationale_photo_picker), RC_File_PICKER_PERM, permissions);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public abstract void onPickFile();

    /**
     * on Request Permissions Result
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        try {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * onPermissionsGranted
     *
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

        try {
            if (requestCode == RC_File_PICKER_PERM) {
                onPickFile();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * onPermissionsDenied
     *
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        try {
            if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
                new AppSettingsDialog.Builder(this).build().show();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}