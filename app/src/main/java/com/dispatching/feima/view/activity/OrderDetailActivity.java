package com.dispatching.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyLocationStyle;
import com.dispatching.feima.R;
import com.dispatching.feima.dagger.component.DaggerOrderDetailActivityComponent;
import com.dispatching.feima.dagger.component.OrderDetailActivityComponent;
import com.dispatching.feima.dagger.module.OrderDetailActivityModule;
import com.dispatching.feima.utils.ToastUtils;
import com.dispatching.feima.view.PresenterControl.OrderDetailControl;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by helei on 2017/4/27.
 */

public class OrderDetailActivity extends BaseActivity implements OrderDetailControl.OrderDetailView, AMap.OnMapLoadedListener {
    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.map_view)
    MapView mMapView;

    @Inject
    AMapLocationClient mAMapLocationClient;
    @Inject
    AMapLocationClientOption mAMapLocationClientOption;
    @Inject
    MyLocationStyle mMyLocationStyle;

    private AMap aMap;
    private OrderDetailActivityComponent mActivityComponent;

    public static Intent getOrderDetailIntent(Context context) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetail);
        ButterKnife.bind(this);
        supportActionBar(mToolbar, true);
        mMiddleName.setText(R.string.user_order_detail);
        initializeInjector();

        mAMapLocationClient.setLocationOption(mAMapLocationClientOption);
        mAMapLocationClient.startLocation();
        mMapView.onCreate(savedInstanceState);
        aMap = mMapView.getMap();
        aMap.setOnMapLoadedListener(this);

    }

    @Override
    public void transformLocation(AMapLocation aMapLocation) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(aMapLocation.getCountry() + ""
                + aMapLocation.getProvince() + ""
                + aMapLocation.getCity() + ""
                + aMapLocation.getProvince() + ""
                + aMapLocation.getDistrict() + ""
                + aMapLocation.getStreet() + ""
                + aMapLocation.getStreetNum());
        Toast.makeText(getApplicationContext(), buffer.toString(), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onMapLoaded() {
        aMap.setMyLocationStyle(mMyLocationStyle);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.setMyLocationEnabled(true);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        if (null != mAMapLocationClient) {
            mAMapLocationClient.stopLocation();
            mAMapLocationClient.onDestroy();
        }
    }

    private void initializeInjector() {
        mActivityComponent = DaggerOrderDetailActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .orderDetailActivityModule(new OrderDetailActivityModule(OrderDetailActivity.this, this))
                .build();
        mActivityComponent.inject(this);
    }

    @Override
    public void showLoading(String msg) {
        showDialogLoading(msg);
    }

    @Override
    public void dismissLoading() {
        dismissDialogLoading();
    }

    @Override
    public void showToast(String message) {
        ToastUtils.showShortToast(message);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
