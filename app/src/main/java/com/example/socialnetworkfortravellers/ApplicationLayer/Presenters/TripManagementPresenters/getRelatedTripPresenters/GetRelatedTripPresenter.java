package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.TripManagementPresenters.getRelatedTripPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.TripModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.tripManagementServices.getRelatedTripServices.IGetRelatedTripService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.tripManagementServices.getRelatedTripServices.IGetRelatedTripServiceCallBack;
import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.example.socialnetworkfortravellers.InfrastructureLayer.ConstantValues.SOMETHING_WENT_WRONG;

public class GetRelatedTripPresenter implements IGetRelatedTripPresenter {


    private IGetRelatedTripPresenterCallBack mGetRelatedTripPresenterCallBack;
    private IGetRelatedTripService mGetRelatedTripService;
    private String dateFrom, dateTo;
    private List<String> list_of_user;

    public GetRelatedTripPresenter(IGetRelatedTripService getRelatedTripService) {
        this.mGetRelatedTripService = getRelatedTripService;
        list_of_user = new ArrayList<>();
        setGetRelatedTripServiceCallBack();
    }


    @Override
    public void setUpGetRelatedTripPresenterCallBack(IGetRelatedTripPresenterCallBack getRelatedTripPresenterCallBack) {
        this.mGetRelatedTripPresenterCallBack = getRelatedTripPresenterCallBack;
    }


    @Override
    public void getRelatedTrip(String from, String to, String countryName) {
        this.dateFrom = from;
        this.dateTo = to;
        mGetRelatedTripService.getRelatedTrip(countryName);
    }

    private void setGetRelatedTripServiceCallBack() {
        mGetRelatedTripService.setGetRelatedTripServiceCallBack(new IGetRelatedTripServiceCallBack() {
            @Override
            public void getRelatedTrips(List<TripModel> tripModelList) {
                matchesTrip(tripModelList);
            }

            @Override
            public void noRelatedTrips() {
                mGetRelatedTripPresenterCallBack.noRelatedTrips();
            }

            @Override
            public void showMessageError(String message) {
                mGetRelatedTripPresenterCallBack.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mGetRelatedTripPresenterCallBack.networkErrorMessage();
            }
        });
    }


    /**
     * check if list have matches trips
     *
     * @param tripModelList
     */
    private void matchesTrip(List<TripModel> tripModelList) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date clientTripFromDate = format.parse(dateFrom);
            Date clientTripToDate = format.parse(dateTo);

            HashMap<String, Boolean> userMap = new HashMap<>();
            for (int i = 0; i < tripModelList.size(); i++) {
                Date serverTripFromDate = format.parse(tripModelList.get(i).getFrom());
                Date serverTripToDate = format.parse(tripModelList.get(i).getTo());

                int f = clientTripFromDate.compareTo(serverTripFromDate);
                int t =clientTripToDate.compareTo(serverTripToDate);

                if (clientTripFromDate.compareTo(serverTripFromDate) >= 0 && clientTripToDate.compareTo(serverTripToDate) <= 0) {
                    if (!userMap.containsKey(tripModelList.get(i).getUserKey()) && !tripModelList.get(i).getUserKey().equals(CurrentUserIDUtil.getInstance().getCurrentUserID()))
                        list_of_user.add(tripModelList.get(i).getUserKey());
                    userMap.put(tripModelList.get(i).getUserKey(), true);
                }
            }

            if (list_of_user.size() != 0) {
                mGetRelatedTripPresenterCallBack.getRelatedUserSuccessful(list_of_user);
            } else {
                mGetRelatedTripPresenterCallBack.noRelatedTrips();
            }
        } catch (Exception e) {
            e.printStackTrace();
            mGetRelatedTripPresenterCallBack.showMessageError(SOMETHING_WENT_WRONG);
        }
    }

}
