package com.banshengyuan.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.dagger.module.ShopProductDetailActivityModule;
import com.banshengyuan.feima.dagger.module.ShopProductDetailFragmentModule;
import com.banshengyuan.feima.view.fragment.CouponListFragment;
import com.banshengyuan.feima.view.fragment.ProductListFragment;
import com.banshengyuan.feima.view.fragment.SummaryFragment;

import dagger.Component;

/**
 * Created by helei on 2017/5/3.
 * FragmentComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ShopProductDetailActivityModule.class, ShopProductDetailFragmentModule.class})
public interface ShopProductDetailFragmentComponent extends ShopProductDetailActivityComponent{
    AppCompatActivity activity();
    void inject(ProductListFragment productListFragment);
    void inject(CouponListFragment couponListFragment);
    void inject(SummaryFragment summaryFragment);
}
