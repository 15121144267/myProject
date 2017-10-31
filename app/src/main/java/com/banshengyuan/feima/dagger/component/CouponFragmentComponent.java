package com.banshengyuan.feima.dagger.component;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.dagger.module.CoupleActivityModule;
import com.banshengyuan.feima.dagger.module.CouponFragmentModule;
import com.banshengyuan.feima.view.fragment.CouponAvailableFragment;
import com.banshengyuan.feima.view.fragment.CouponNotAvailableFragment;
import com.banshengyuan.feima.view.fragment.CouponPastFragment;

import dagger.Component;

/**
 * Created by helei on 2017/5/3.
 * FragmentComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {CoupleActivityModule.class, CouponFragmentModule.class})
public interface CouponFragmentComponent extends CoupleActivityComponent{
    AppCompatActivity activity();
    void inject(CouponAvailableFragment couponAvailableFragment);
    void inject(CouponNotAvailableFragment couponNotAvailableFragment);
    void inject(CouponPastFragment couponPastFragment);

}
