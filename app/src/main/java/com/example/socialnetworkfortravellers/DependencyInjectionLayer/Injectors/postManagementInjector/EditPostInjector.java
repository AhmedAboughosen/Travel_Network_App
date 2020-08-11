package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.postManagementInjector;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.postManagementComponents.DaggerEditPostComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.postManagementComponents.EditPostComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.postManagementActivities.EditPostActivity;

public class EditPostInjector {


    private static EditPostInjector mInjector;

    private EditPostInjector() {
    }

    public static EditPostInjector getSharedInjector() {
        if (mInjector == null) {
            mInjector = new EditPostInjector();
        }
        return mInjector;
    }


    public void injectIn(EditPostActivity editPostActivity) {
       EditPostComponent mEditPostComponent = DaggerEditPostComponent.builder()
                .contextModule(new ContextModule(editPostActivity))
                .build();

        mEditPostComponent.inject(editPostActivity);

    }
}
