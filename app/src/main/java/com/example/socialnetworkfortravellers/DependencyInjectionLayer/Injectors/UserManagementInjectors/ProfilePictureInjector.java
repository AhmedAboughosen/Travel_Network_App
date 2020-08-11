package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.UserManagementInjectors;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents.DaggerProfilePictureComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents.ProfilePictureComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.userRegistrationActivities.ProfilePictureActivity;

public class ProfilePictureInjector {


    private static ProfilePictureInjector mInjector;

    private ProfilePictureInjector() {
    }

    public static ProfilePictureInjector getSharedInjector() {
        if (mInjector == null) {
            mInjector = new ProfilePictureInjector();
        }
        return mInjector;
    }


    public void injectIn(ProfilePictureActivity profilePictureActivity) {
        ProfilePictureComponent mProfilePictureComponent = DaggerProfilePictureComponent.builder()
                .contextModule(new ContextModule(profilePictureActivity))
                .build();

        mProfilePictureComponent.inject(profilePictureActivity);

    }
}
