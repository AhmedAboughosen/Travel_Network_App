package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.tripManagementInjectors;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.tripManagementComponents.DaggerRelatedTripComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.tripManagementComponents.RelatedTripComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.tripManagementActivities.MatchesTripActivity;

public class RelatedTripInjector {

    private static RelatedTripInjector mInjector;

    private RelatedTripInjector() {
    }

    public static RelatedTripInjector getSharedInjector() {
        if (mInjector == null) {
            mInjector = new RelatedTripInjector();
        }
        return mInjector;
    }


    public void injectIn(MatchesTripActivity matchesTripActivity) {
        RelatedTripComponent mComponent = DaggerRelatedTripComponent.builder()
                .contextModule(new ContextModule(matchesTripActivity))
                .build();

        mComponent.inject(matchesTripActivity);

    }
}
