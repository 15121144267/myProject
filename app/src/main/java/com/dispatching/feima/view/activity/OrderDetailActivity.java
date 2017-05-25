package com.dispatching.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.RidePath;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RideStep;
import com.amap.api.services.route.RouteSearch;
import com.dispatching.feima.DaggerApplication;
import com.dispatching.feima.R;
import com.dispatching.feima.dagger.component.DaggerOrderDetailActivityComponent;
import com.dispatching.feima.dagger.module.OrderDetailActivityModule;
import com.dispatching.feima.entity.IntentConstant;
import com.dispatching.feima.entity.MyOrders;
import com.dispatching.feima.listener.MyRouteSearchListener;
import com.dispatching.feima.utils.ValueUtil;
import com.dispatching.feima.view.PresenterControl.OrderDetailControl;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by helei on 2017/4/27.
 * OrderDetailActivity
 */

public class OrderDetailActivity extends BaseActivity implements OrderDetailControl.OrderDetailView, AMap.OnMapLoadedListener, AMap.OnMapTouchListener {
    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.map_view)
    MapView mMapView;
    @BindView(R.id.order_detail_start_distance)
    TextView mOrderDetailStartDistance;
    @BindView(R.id.order_detail_start_city)
    TextView mOrderDetailStartCity;
    @BindView(R.id.order_detail_start_address)
    TextView mOrderDetailStartAddress;
    @BindView(R.id.order_detail_end_distance)
    TextView mOrderDetailEndDistance;
    @BindView(R.id.order_detail_end_city)
    TextView mOrderDetailEndCity;
    @BindView(R.id.order_detail_end_address)
    TextView mOrderDetailEndAddress;
    @BindView(R.id.order_id)
    TextView mOrderId;
    @BindView(R.id.order_time)
    TextView mOrderTime;
    @BindView(R.id.order_channel)
    TextView mOrderChannel;
    @BindView(R.id.delivery_id)
    TextView mDeliveryId;
    @BindView(R.id.order_amount)
    TextView mOrderAmount;
    @BindView(R.id.order_customer_name)
    TextView mOrderCustomerName;
    @BindView(R.id.order_customer_phone)
    TextView mOrderCustomerPhone;
    @BindView(R.id.order_customer_remark)
    TextView mOrderCustomerRemark;
    @BindView(R.id.layout_remark)
    LinearLayout mLayoutRemark;
    @BindView(R.id.order_get_time)
    TextView mOrderGetTime;
    @BindView(R.id.layout_take)
    LinearLayout mLayoutTake;
    @BindView(R.id.order_arrived_time)
    TextView mOrderArrivedTime;
    @BindView(R.id.layout_arrived)
    LinearLayout mLayoutArrived;
    @BindView(R.id.layout_deliver_order)
    LinearLayout mLayoutDeliverOrder;
    @BindView(R.id.order_detail_button)
    Button mOrderDetailButton;

    @Inject
    MyLocationStyle mMyLocationStyle;
    @Inject
    OrderDetailControl.PresenterOrderDetail mPresenter;
    @Inject
    RouteSearch mRouteSearch;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    public static Intent getOrderDetailIntent(Context context, MyOrders orders, Integer position) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra(IntentConstant.ORDER_DETAIL, orders);
        intent.putExtra(IntentConstant.ORDER_DETAIL_POSITION, position);
        return intent;
    }

    private AMap aMap;
    private MyOrders mOrder;
    private AMapLocation mAMapLocation;
    private Integer position = 0;
    private boolean flag = false;
    private List<RidePath> mRidePathList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetail);
        ButterKnife.bind(this);
        supportActionBar(mToolbar, true);
        initializeInjector();
        mPresenter.setView(this);
        transformIntent();
        mAMapLocation = ((DaggerApplication) getApplicationContext()).getaMapLocation();
        mCollapsingToolbarLayout.setTitle(getResources().getText(R.string.user_order_detail));
        mMapView.onCreate(savedInstanceState);
        aMap = mMapView.getMap();
        aMap.setOnMapLoadedListener(this);
        mRouteSearch.setRouteSearchListener(new MyRouteSearchListener() {
            @Override
            public void OnMyRideRouteSearched(RideRouteResult rideRouteResult) {
                List<LatLng> latLines = new ArrayList<>();
                mRidePathList = rideRouteResult.getPaths();
                for (RidePath ridePath : mRidePathList) {
                    List<RideStep> rideStepList = ridePath.getSteps();
                    for (RideStep rideStep : rideStepList) {
                        List<LatLonPoint> polylineList = rideStep.getPolyline();
                        for (LatLonPoint latLonPoint : polylineList) {
                            latLines.add(new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude()));
                        }
                    }
                }
                aMap.addPolyline(new PolylineOptions().
                        addAll(latLines).width(10).color(ContextCompat.getColor(OrderDetailActivity.this, R.color.colorPrimary)));
            }

        });
        initView();
    }

    @Override
    public void updateOrderStatusSuccess() {
        flag = true;
        showToast(getString(R.string.text_do_success));
        mOrderDetailButton.setEnabled(false);
    }

    @Override
    public void onMapLoaded() {
        aMap.setOnMapTouchListener(this);
        aMap.setMyLocationStyle(mMyLocationStyle);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        aMap.setMyLocationEnabled(true);

    }

    @Override
    public void onTouch(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mCollapsingToolbarLayout.requestDisallowInterceptTouchEvent(true);
                break;

        }
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
    public void showLoading(String msg) {
        showDialogLoading(msg);
    }

    @Override
    public void dismissLoading() {
        dismissDialogLoading();
    }

    @Override
    public void showToast(String message) {
        showBaseToast(message);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onBackPressed() {
        if (position.equals(IntentConstant.ORDER_POSITION_THREE)) {
            super.onBackPressed();
            return;
        }
        returnFlag();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        mPresenter.onDestroy();
    }

    private void initView() {
        RxView.clicks(mOrderDetailButton).throttleFirst(2, TimeUnit.SECONDS).subscribe(o -> requestUpdateOrder());
        switch (mOrder.deliveryStatus) {
            case 1:
                mOrderDetailButton.setText(getResources().getString(R.string.text_take_order));
                break;
            case 3:
                mOrderDetailButton.setText(getResources().getString(R.string.text_complete_order));
                break;
            case 4:
                mOrderDetailButton.setVisibility(View.GONE);
                break;
        }
        mOrderDetailStartAddress.setText(mOrder.ShopAddress);
        mOrderDetailEndAddress.setText(mOrder.customerAddress);
        mOrderId.setText(mOrder.businessId);
        mOrderTime.setText(mOrder.createTime);
        mOrderChannel.setText(mOrder.channel);
        if(mOrder.flowid !=null){
            mLayoutDeliverOrder.setVisibility(View.VISIBLE);
            mDeliveryId.setText(mOrder.flowid);
        }
        mOrderAmount.setText(ValueUtil.formatAmount(mOrder.totalFee));
        mOrderCustomerName.setText(mOrder.customer);
        mOrderCustomerPhone.setText(mOrder.phone);
        if (mOrder.remark != null) {
            mLayoutRemark.setVisibility(View.VISIBLE);
            mOrderCustomerRemark.setText(mOrder.remark);
        }

        if (!TextUtils.isEmpty(mOrder.takeTime)) {
            mLayoutTake.setVisibility(View.VISIBLE);
            mOrderGetTime.setText(mOrder.takeTime);
        }
        if (!TextUtils.isEmpty(mOrder.endTime)) {
            mLayoutArrived.setVisibility(View.VISIBLE);
            mOrderArrivedTime.setText(mOrder.endTime);
        }


        double shopLongitude = mOrder.shopLongitude;
        double shopLatitude = mOrder.shopLatitude;
        double customerLongitude = mOrder.customerLongitude;
        double customerLatitude = mOrder.customerLatitude;
        if (mAMapLocation != null) {
            mOrderDetailStartCity.setVisibility(View.VISIBLE);
            mOrderDetailEndCity.setVisibility(View.VISIBLE);
            String city = mAMapLocation.getCity();//城市信息
            //String district = mAMapLocation.getDistrict();//城区信息
            //String cityDistrict = city + district;
            mOrderDetailStartCity.setText(city);
            mOrderDetailEndCity.setText(city);
            double latitude = mAMapLocation.getLatitude();//获取纬度
            double longitude = mAMapLocation.getLongitude();//获取经度
            LatLng latLngSelf = new LatLng(latitude, longitude);
            LatLng latLngShop = new LatLng(shopLatitude, shopLongitude);
            LatLng latLngCustomer = new LatLng(customerLatitude, customerLongitude);
            LatLonPoint startPoint = new LatLonPoint(shopLatitude, shopLongitude);
            LatLonPoint endPoint = new LatLonPoint(customerLatitude, customerLongitude);
            markPoint(latLngShop, true);
            markPoint(latLngCustomer, false);
            float distance1 = AMapUtils.calculateLineDistance(latLngSelf, latLngShop);
            float distance2 = AMapUtils.calculateLineDistance(latLngSelf, latLngCustomer);
            RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(startPoint, endPoint);
            RouteSearch.RideRouteQuery query = new RouteSearch.RideRouteQuery(fromAndTo);
            mRouteSearch.calculateRideRouteAsyn(query);
            mOrderDetailEndDistance.setText(ValueUtil.formatDistance(distance2));
            mOrderDetailStartDistance.setText(ValueUtil.formatDistance(distance1));

        }
    }

    private void markPoint(LatLng latLng, boolean flag) {
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
        markerOption.draggable(false);//设置Marker可拖动
        if (flag) {
            markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                    .decodeResource(getResources(), R.mipmap.start_point)));
        } else {
            markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                    .decodeResource(getResources(), R.mipmap.end_point)));
        }
        markerOption.setFlat(true);//设置marker平贴地图效果
        aMap.addMarker(markerOption);
    }

    private void transformIntent() {
        mOrder = (MyOrders) getIntent().getSerializableExtra(IntentConstant.ORDER_DETAIL);
        position = getIntent().getIntExtra(IntentConstant.ORDER_DETAIL_POSITION, 0);
    }

    private void requestUpdateOrder() {
        mPresenter.requestUpdateOrder(position, mBuProcessor.getUserToken(), mBuProcessor.getUserId(), mOrder.deliveryId);
    }

    private void returnFlag() {
        Intent intent = new Intent();
        intent.putExtra(IntentConstant.ORDER_DETAIL_FLASH, flag);
        setResult(position, intent);
        finish();
    }

    private void initializeInjector() {
        DaggerOrderDetailActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .orderDetailActivityModule(new OrderDetailActivityModule(OrderDetailActivity.this))
                .build().inject(this);
    }
}
