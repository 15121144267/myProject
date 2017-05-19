package com.dispatching.feima.view.adapter;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.dispatching.feima.DaggerApplication;
import com.dispatching.feima.R;
import com.dispatching.feima.entity.MyOrders;
import com.dispatching.feima.utils.ValueUtil;

import java.util.List;


public class PullToRefreshAdapter extends BaseQuickAdapter<MyOrders, BaseViewHolder> {
    private final Context mContext;
    private final AMapLocation mAMapLocation;

    public PullToRefreshAdapter(Context context, List<MyOrders> list) {
        super(R.layout.layout_animation, list);
        mContext = context;
        mAMapLocation = ((DaggerApplication) mContext.getApplicationContext()).getaMapLocation();
    }

    @Override
    protected void convert(BaseViewHolder helper, MyOrders item) {
        helper.addOnClickListener(R.id.item_get_order);
        helper.setText(R.id.order_start_point, item.ShopAddress);
        helper.setText(R.id.order_end_point, item.customerAddress);
        if (item.remark != null) {
            helper.setVisible(R.id.layout_remark, true);
            helper.setText(R.id.order_remark, item.remark);
        }
        helper.setVisible(R.id.item_get_order, true);
        switch (item.deliveryStatus) {
            case 1:
                helper.setText(R.id.item_get_order, mContext.getResources().getString(R.string.text_take_order));
                break;
            case 3:
                helper.setText(R.id.item_get_order, mContext.getResources().getString(R.string.text_complete_order));
                break;
            case 4:
                helper.setVisible(R.id.item_get_order, false);
                break;
        }
        double shopLongitude = item.shopLongitude;
        double shopLatitude = item.shopLatitude;
        double customerLongitude = item.customerLongitude;
        double customerLatitude = item.customerLatitude;
        if (mAMapLocation != null) {
            helper.setVisible(R.id.order_end_city, true);
            helper.setVisible(R.id.order_start_city, true);
            String city = mAMapLocation.getCity();//城市信息
            //String district = mAMapLocation.getDistrict();//城区信息
            //String cityDistrict = city ;
            helper.setText(R.id.order_end_city, city);
            helper.setText(R.id.order_start_city, city);
            double latitude = mAMapLocation.getLatitude();//获取纬度
            double longitude = mAMapLocation.getLongitude();//获取经度
            LatLng latLngSelf = new LatLng(latitude, longitude);
            LatLng latLngShop = new LatLng(shopLatitude, shopLongitude);
            LatLng latLngCustomer = new LatLng(customerLatitude, customerLongitude);
            float distance1 = AMapUtils.calculateLineDistance(latLngSelf, latLngShop);
            float distance2 = AMapUtils.calculateLineDistance(latLngSelf, latLngCustomer);
            helper.setText(R.id.order_start_distance, ValueUtil.formatDistance(distance1));
            helper.setText(R.id.order_end_distance, ValueUtil.formatDistance(distance2));
        }

    }

}
