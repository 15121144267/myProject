package com.dispatching.feima.listener;

import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.dispatching.feima.DaggerApplication;
import com.dispatching.feima.utils.TimeUtil;

/**
 * Created by helei on 2017/4/28.
 * MyLocationListener
 */

public class MyLocationListener implements AMapLocationListener {
    private final DaggerApplication mDaggerApplication;

    public MyLocationListener(DaggerApplication application) {
        this.mDaggerApplication = application;
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                mDaggerApplication.transformLocation(amapLocation);
            } else {
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }

    private void parseLocation(AMapLocation amapLocation) {
        amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
        amapLocation.getLatitude();//获取纬度
        amapLocation.getLongitude();//获取经度
        amapLocation.getAccuracy();//获取精度信息
        amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
        amapLocation.getCountry();//国家信息
        amapLocation.getProvince();//省信息
        amapLocation.getCity();//城市信息
        amapLocation.getDistrict();//城区信息
        amapLocation.getStreet();//街道信息
        amapLocation.getStreetNum();//街道门牌号信息
        amapLocation.getCityCode();//城市编码
        amapLocation.getAdCode();//地区编码
        amapLocation.getAoiName();//获取当前定位点的AOI信息
        amapLocation.getBuildingId();//获取当前室内定位的建筑物Id
        amapLocation.getFloor();//获取当前室内定位的楼层
        amapLocation.getGpsAccuracyStatus();//获取GPS的当前状态
        TimeUtil.longToTime(amapLocation.getTime(), TimeUtil.TIME_YYMMDD_HHMMSS);
    }
}
