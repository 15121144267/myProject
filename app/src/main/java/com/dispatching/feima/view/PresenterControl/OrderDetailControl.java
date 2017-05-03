package com.dispatching.feima.view.PresenterControl;

import com.amap.api.location.AMapLocation;

/**
 * Created by helei on 2017/4/27.
 */

public class OrderDetailControl {
    public interface OrderDetailView extends LoadDataView{
        void transformLocation(AMapLocation amapLocation);
    }

    public interface PresenterOrderDetail extends Presenter<OrderDetailView> {

    }

}
