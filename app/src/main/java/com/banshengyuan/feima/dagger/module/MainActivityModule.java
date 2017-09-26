package com.banshengyuan.feima.dagger.module;

import android.support.v7.app.AppCompatActivity;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.dagger.PerActivity;
import com.banshengyuan.feima.gen.DaoSession;
import com.banshengyuan.feima.network.RetrofitUtil;
import com.banshengyuan.feima.network.networkapi.AddShoppingCardApi;
import com.banshengyuan.feima.network.networkapi.MainApi;
import com.banshengyuan.feima.view.PresenterControl.CelebrityControl;
import com.banshengyuan.feima.view.PresenterControl.CompletedOrderControl;
import com.banshengyuan.feima.view.PresenterControl.FairControl;
import com.banshengyuan.feima.view.PresenterControl.FollowControl;
import com.banshengyuan.feima.view.PresenterControl.HotControl;
import com.banshengyuan.feima.view.PresenterControl.MagicMusicControl;
import com.banshengyuan.feima.view.PresenterControl.MainControl;
import com.banshengyuan.feima.view.PresenterControl.MainFairControl;
import com.banshengyuan.feima.view.PresenterControl.PendingOrderControl;
import com.banshengyuan.feima.view.PresenterControl.ProductControl;
import com.banshengyuan.feima.view.PresenterControl.RecommendControl;
import com.banshengyuan.feima.view.PresenterControl.SellerControl;
import com.banshengyuan.feima.view.PresenterControl.SendingOrderControl;
import com.banshengyuan.feima.view.PresenterControl.ShoppingCardControl;
import com.banshengyuan.feima.view.PresenterControl.TrendsControl;
import com.banshengyuan.feima.view.PresenterControl.VistaControl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterCelebrityImpl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterCompletedImpl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterFairImpl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterFollowImpl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterHotImpl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterMagicMusicImpl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterMainFairImpl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterMainImpl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterPendingImpl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterProductImpl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterRecommendImpl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterSellerImpl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterSendingImpl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterShoppingCardImpl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterTrendsImpl;
import com.banshengyuan.feima.view.PresenterImpl.PresenterVistaImpl;
import com.banshengyuan.feima.view.model.MainModel;
import com.banshengyuan.feima.view.model.ModelTransform;
import com.banshengyuan.feima.view.model.ShoppingCardModel;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by helei on 2017/4/26.
 * MainActivityModule
 */
@Module
public class MainActivityModule {
    private final AppCompatActivity activity;
    private MainControl.MainView view;

    public MainActivityModule(AppCompatActivity activity, MainControl.MainView view) {
        this.activity = activity;
        this.view = view;
    }

    public MainActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    AppCompatActivity activity() {
        return this.activity;
    }

    @Provides
    @PerActivity
    MainControl.MainView view() {
        return this.view;
    }


    @Provides
    @PerActivity
    MainModel provideMainModel(Gson gson, ModelTransform modelTransform, DaoSession daoSession) {
        return new MainModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.DISPATCH_SERVICE)
                .isHttps(!BuildConfig.DEBUG)
                .key(BuildConfig.STORE_NAME, BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(MainApi.class), gson, modelTransform, daoSession.getOrderNoticeDao());
    }

    @Provides
    @PerActivity
    ShoppingCardModel provideShoppingCardModel(Gson gson, ModelTransform modelTransform) {
        return new ShoppingCardModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.ORDER_SERVICE)
                .isToJson(false)
                .builder()
                .create(AddShoppingCardApi.class), gson, modelTransform);
    }

    @Provides
    @PerActivity
    MainControl.PresenterMain providePresenterMain(PresenterMainImpl presenterMain) {
        return presenterMain;
    }

    @Provides
    @PerActivity
    CompletedOrderControl.PresenterCompletedOrder providePresenterCompleted(PresenterCompletedImpl presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    PendingOrderControl.PresenterPendingOrder providePresenterPending(PresenterPendingImpl presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    SendingOrderControl.PresenterSendingOrder providePresenterSending(PresenterSendingImpl presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    ShoppingCardControl.PresenterShoppingCard providePresenterShoppingCard(PresenterShoppingCardImpl presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    FairControl.PresenterFair providePresenterFair(PresenterFairImpl presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    ProductControl.PresenterProduct providePresenterProduct(PresenterProductImpl presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    SellerControl.PresenterSeller providePresenterSeller(PresenterSellerImpl presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    RecommendControl.PresenterRecommend providePresenterRecommend(PresenterRecommendImpl presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    MainFairControl.PresenterFair providePresenterMainFair(PresenterMainFairImpl presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    MagicMusicControl.PresenterMagicMusic providePresenterMagicMusic(PresenterMagicMusicImpl presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    HotControl.PresenterHot providePresenterHot(PresenterHotImpl presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    VistaControl.PresenterVista providePresenterVista(PresenterVistaImpl presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    TrendsControl.PresenterTrends providePresenterTrends(PresenterTrendsImpl presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    FollowControl.PresenterFollow providePresenterFollow(PresenterFollowImpl presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    CelebrityControl.PresenterCelebrity providePresenterCelebrity(PresenterCelebrityImpl presenter) {
        return presenter;
    }


}
