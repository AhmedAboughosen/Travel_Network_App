package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.UserManagementInjectors;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents.DaggerUpdateEmailComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents.UpdateEmailComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.editUserInformationActivity.UpdateEmailActivity;

public class UpdateEmailInjector {


    private static UpdateEmailInjector mUpdateEmailInjector;

    private UpdateEmailInjector() {
    }

    public static UpdateEmailInjector getSharedInjector() {
        if (mUpdateEmailInjector == null) {
            mUpdateEmailInjector = new UpdateEmailInjector();
        }
        return mUpdateEmailInjector;
    }


    public void injectIn(UpdateEmailActivity updateEmailActivity) {
      UpdateEmailComponent mUpdateEmailComponent = DaggerUpdateEmailComponent.builder()
              .contextModule(new ContextModule(updateEmailActivity))
                .build();

      mUpdateEmailComponent.inject(updateEmailActivity);

    }

}
