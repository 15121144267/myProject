package com.dispatching.feima.listener;

import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;

/**
 * Created by helei on 2017/5/15.
 * MyRouteSearchListener
 */

public abstract class MyRouteSearchListener implements RouteSearch.OnRouteSearchListener {

    /*public MyRouteSearchListener(Context context, OrderDetailControl.OrderDetailView view){

    }*/
    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {

    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {
        OnMyRideRouteSearched(rideRouteResult);
    }

    public abstract void OnMyRideRouteSearched(RideRouteResult rideRouteResult);
}
