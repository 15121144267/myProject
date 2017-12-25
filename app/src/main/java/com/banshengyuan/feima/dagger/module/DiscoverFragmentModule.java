package com.banshengyuan.feima.dagger.module;


import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.view.PresenterControl.FairControl;
import com.banshengyuan.feima.view.PresenterControl.LoadDataView;
import com.banshengyuan.feima.view.PresenterControl.ProductControl;
import com.banshengyuan.feima.view.PresenterControl.SellerControl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/5/3.
 * FragmentModule
 */
@Module
public class DiscoverFragmentModule {
    private final AppCompatActivity activity;

    private FairControl.FairView mFairView;
    private ProductControl.ProductView mProductView;
    private SellerControl.SellerView mSellerView;

    public DiscoverFragmentModule(LoadDataView view, AppCompatActivity activity) {
        this.activity = activity;
        if (view instanceof FairControl.FairView) {
            mFairView = (FairControl.FairView) view;
        } else if (view instanceof ProductControl.ProductView) {
            mProductView = (ProductControl.ProductView) view;
        } else if (view instanceof SellerControl.SellerView) {
            mSellerView = (SellerControl.SellerView) view;
        }
    }

    @Provides
    @PerActivity
    FairControl.FairView fairView() {
        return this.mFairView;
    }

    @Provides
    @PerActivity
    ProductControl.ProductView productView() {
        return this.mProductView;
    }

    @Provides
    @PerActivity
    SellerControl.SellerView sellerView() {
        return this.mSellerView;
    }

}
