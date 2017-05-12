package com.dispatching.feima.dagger.module;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;

import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.MyLocationStyle;
import com.dispatching.feima.BuildConfig;
import com.dispatching.feima.R;
import com.dispatching.feima.dagger.PerActivity;
import com.dispatching.feima.network.RetrofitUtil;
import com.dispatching.feima.network.networkapi.MainApi;
import com.dispatching.feima.view.PresenterControl.OrderDetailControl;
import com.dispatching.feima.view.PresenterImpl.PresenterOrderDetailImpl;
import com.dispatching.feima.view.model.MainModel;
import com.dispatching.feima.view.model.ModelTransform;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/4/26.
 * OrderDetailActivityModule
 */
@Module
public class OrderDetailActivityModule {
    private final AppCompatActivity activity;

    public OrderDetailActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    AppCompatActivity activity() {
        return this.activity;
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

    @Provides
    @PerActivity
    MainApi provideMainApi() {
        return new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.DISPATCH_SERVICE)
                .isToJson(false)
                .builder()
                .create(MainApi.class);
    }

    @Provides
    @PerActivity
    MainModel provideMainModel(Gson gson, ModelTransform modelTransform, MainApi mainApi) {
        return new MainModel(mainApi, gson, modelTransform);
    }

    @Provides
    @PerActivity
    OrderDetailControl.PresenterOrderDetail providePresenterOrderDetail(PresenterOrderDetailImpl presenter) {
        return presenter;
    }
}
