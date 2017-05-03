package com.dispatching.feima.dagger.module;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.MyLocationStyle;
import com.dispatching.feima.R;
import com.dispatching.feima.dagger.PerActivity;
import com.dispatching.feima.listener.MyLocationListener;
import com.dispatching.feima.view.PresenterControl.OrderDetailControl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/4/26.
 * OrderDetailActivityModule
 */
@Module
public class OrderDetailActivityModule {
    private final AppCompatActivity activity;
    private OrderDetailControl.OrderDetailView mView;

    public OrderDetailActivityModule(AppCompatActivity activity, OrderDetailControl.OrderDetailView mView) {
        this.activity = activity;
        this.mView = mView;
    }

    @Provides
    @PerActivity
    AppCompatActivity activity() {
        return this.activity;
    }

    @Provides
    @PerActivity
    AMapLocationClient provideAMapLocationClient() {
        AMapLocationListener mLocationListener = new MyLocationListener(mView);
        AMapLocationClient client = new AMapLocationClient(activity.getApplicationContext());
        client.setLocationListener(mLocationListener);
        return client;
    }

    @Provides
    @PerActivity
    AMapLocationClientOption AMapLocationClientOptionProvide() {
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setNeedAddress(true);
        option.setLocationCacheEnable(true);
        option.setHttpTimeOut(20000);
        option.setWifiScan(true);
        option.setInterval(4000);
        return option;
    }

    @Provides
    @PerActivity
    MyLocationStyle MyLocationStyleProvide() {
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.
                fromResource(R.mipmap.location_style));
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        myLocationStyle.interval(2000);
        return myLocationStyle;
    }

}
