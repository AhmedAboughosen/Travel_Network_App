package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.postManagementInjector;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.postManagementComponents.DaggerDeletePostComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.postManagementComponents.DeletePostComponent;
import com.example.socialnetworkfortravellers.ViewLayer.Dialog.PostMenuSheetDialog;

public class DeletePostInjector {

    private static DeletePostInjector mInjector;

    private DeletePostInjector() {
    }

    public static DeletePostInjector getSharedInjector() {
        if (mInjector == null) {
            mInjector = new DeletePostInjector();
        }
        return mInjector;
    }


    public void injectIn(PostMenuSheetDialog postMenuSheetDialog) {
       DeletePostComponent mDeletePostComponent = DaggerDeletePostComponent.builder()
                .build();

        mDeletePostComponent.setUpInject(postMenuSheetDialog);

    }
}
