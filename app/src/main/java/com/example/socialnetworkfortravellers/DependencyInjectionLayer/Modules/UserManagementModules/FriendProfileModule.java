package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.UserManagementModules;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.FriendProfilePresenters.FriendProfilePresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.FriendProfilePresenters.IFriendProfilePresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.userInteractionsPresenters.IUserInteractionsPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.userInteractionsPresenters.UserInteractionsPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseSingleValueService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.isCurrentUserFollowFriendServices.IIsCurrentUserFollowFriendService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.isCurrentUserFollowFriendServices.IsCurrentUserFollowFriendService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.notficastionServices.AddNotificationsService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.notficastionServices.IAddNotificationsService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.updatedFollowAndUnFollowServices.IUpdateFollowersService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.updatedFollowAndUnFollowServices.IUpdateFollowingService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.updatedFollowAndUnFollowServices.IUpdateUnFollowersService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.updatedFollowAndUnFollowServices.IUpdateUnFollowingService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.updatedFollowAndUnFollowServices.UpdateFollowersService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.updatedFollowAndUnFollowServices.UpdateFollowingService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.updatedFollowAndUnFollowServices.UpdateUnFollowersService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.updatedFollowAndUnFollowServices.UpdateUnFollowingService;

import dagger.Module;
import dagger.Provides;

@Module
public class FriendProfileModule {

    @Provides
    IFriendProfilePresenter ProvidesFriendProfilePresenter(IUserInteractionsPresenter followPresenter, IIsCurrentUserFollowFriendService currentUserFollowFriendService) {

        return new FriendProfilePresenter(followPresenter, currentUserFollowFriendService);
    }


    @Provides
    IUserInteractionsPresenter ProvidesFollowPresenter(IUpdateFollowersService updateFollowersService,
                                                       IUpdateFollowingService updateFollowingService, IUpdateUnFollowingService updateUnFollowingService, IUpdateUnFollowersService updateUnFollowersService,
                                                       IAddNotificationsService addNotificationsService) {
        return new UserInteractionsPresenter(updateFollowersService, updateFollowingService, updateUnFollowingService, updateUnFollowersService, addNotificationsService);
    }


    @Provides
    IIsCurrentUserFollowFriendService ProvidesIsCurrentUserFollowFriendService(IGetDataByUseSingleValueService getDataByUseSingleValueService) {
        return new IsCurrentUserFollowFriendService(getDataByUseSingleValueService);
    }

    @Provides
    IUpdateFollowersService ProvidesUpdateFollowersService(ISaveRawDataService saveRawDataService) {
        return new UpdateFollowersService(saveRawDataService);
    }

    @Provides
    IUpdateFollowingService ProvidesUpdateFollowingService(ISaveRawDataService saveRawDataService) {
        return new UpdateFollowingService(saveRawDataService);
    }

    @Provides
    IUpdateUnFollowingService ProvidesUpdateUnFollowingService() {
        return new UpdateUnFollowingService();
    }

    @Provides
    IUpdateUnFollowersService ProvidesUpdateUnFollowersService() {
        return new UpdateUnFollowersService();
    }

    @Provides
    IAddNotificationsService ProvidesAddNotificationsService(ISaveRawDataService saveRawDataService) {
        return new AddNotificationsService(saveRawDataService);
    }

}
