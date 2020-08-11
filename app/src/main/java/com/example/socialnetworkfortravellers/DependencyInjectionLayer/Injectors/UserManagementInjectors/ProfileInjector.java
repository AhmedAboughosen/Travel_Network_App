package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.UserManagementInjectors;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents.DaggerProfileComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents.ProfileComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.Base.ProfileFragment;

public class ProfileInjector {


    private static ProfileInjector mInjector;

    private ProfileInjector() {
    }

    public static ProfileInjector getSharedInjector() {
        if (mInjector == null) {
            mInjector = new ProfileInjector();
        }
        return mInjector;
    }


    public void injectIn(ProfileFragment profileFragment) {
        ProfileComponent mProfileComponent = DaggerProfileComponent.builder().contextModule(new ContextModule(profileFragment.getActivity())).build();


        mProfileComponent.inject(profileFragment);

    }
}
