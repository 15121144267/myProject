package com.dispatching.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dispatching.feima.R;
import com.dispatching.feima.dagger.component.DaggerGoodsDetailActivityComponent;
import com.dispatching.feima.dagger.module.GoodsDetailActivityModule;
import com.dispatching.feima.entity.GoodsInfoResponse;
import com.dispatching.feima.entity.ShopDetailResponse;
import com.dispatching.feima.entity.SpecificationResponse;
import com.dispatching.feima.help.DialogFactory;
import com.dispatching.feima.help.GlideLoader;
import com.dispatching.feima.help.HtmlHelp.MxgsaTagHandler;
import com.dispatching.feima.help.HtmlHelp.URLImageParser;
import com.dispatching.feima.utils.ValueUtil;
import com.dispatching.feima.view.PresenterControl.GoodsDetailControl;
import com.dispatching.feima.view.fragment.SpecificationDialog;
import com.dispatching.feima.view.fragment.SpecificationEmptyDialog;
import com.jakewharton.rxbinding2.view.RxView;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/30.
 * GoodDetailActivity
 */

public class GoodDetailActivity extends BaseActivity implements GoodsDetailControl.GoodsDetailView, SpecificationDialog.specificationDialogListener {

    public static Intent getIntent(Context context, ShopDetailResponse.ProductsBean goodsInfo) {
        Intent intent = new Intent(context, GoodDetailActivity.class);
        intent.putExtra("goodsInfo", goodsInfo);
        return intent;
    }

    @BindView(R.id.toolbar_right_icon)
    ImageView mToolbarRightIcon;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.goods_des)
    TextView mGoodsDes;
    @BindView(R.id.goods_name)
    TextView mGoodsName;
    @BindView(R.id.goods_price)
    TextView mGoodsPrice;
    @BindView(R.id.goods_specification)
    TextView mGoodsSpecification;
    @BindView(R.id.goods_detail_button)
    Button mGoodsDetailButton;
    @BindView(R.id.goods_detail_linear)
    TextView mGoodsDetailLinear;
    @BindView(R.id.banner)
    Banner mBanner;

    @Inject
    GoodsDetailControl.PresenterGoodsDetail mPresenter;

    private List<String> mImageList;
    private ShopDetailResponse.ProductsBean mGoodsInfo;
    private SpecificationResponse mSpecificationResponse;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_detail);
        ButterKnife.bind(this);
        initializeInjector();
        supportActionBar(mToolbar, true);
        initView();
        initData();
    }

    @Override
    public void goodInfoSpecificationSuccess(SpecificationResponse data) {
       /* mSpecificationResponse = data;
        List<SpecificationResponse.ProductsBean> list  = mSpecificationResponse.products;
        SpecificationResponse.ProductsBean bean = list.get(0);
        List<SpecificationResponse.ProductsBean.ProductSpecificationBean> been = bean.productSpecification;*/
    }

    @Override
    public void getGoodsInfoSuccess(GoodsInfoResponse data) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mGoodsDetailLinear.setText(Html.fromHtml(data.detailText,Html.FROM_HTML_MODE_LEGACY, new URLImageParser(mGoodsDetailLinear,this), new MxgsaTagHandler(this)));
        } else {
            mGoodsDetailLinear.setText(Html.fromHtml(data.detailText, new URLImageParser(mGoodsDetailLinear,this), new MxgsaTagHandler(this)));
        }
    }

    @Override
    public void reduceCountListener() {
        showToast("減少");
    }

    @Override
    public void addCountListener() {
        showToast("增加");
    }

    @Override
    public void buyButtonListener() {
        showToast("购买");
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
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    private void initView() {
        mGoodsInfo = (ShopDetailResponse.ProductsBean) getIntent().getSerializableExtra("goodsInfo");
        if (mGoodsInfo != null) {
            mBuProcessor.setGoodsInfo(mGoodsInfo);
            mGoodsName.setText(mGoodsInfo.name);
            mGoodsPrice.setText("￥" + ValueUtil.formatAmount(mGoodsInfo.finalPrice));
        }
        mImageList = new ArrayList<>();
        mImageList.add(mGoodsInfo.picture);
        mBanner.isAutoPlay(false);
        mBanner.setImages(mImageList).setImageLoader(new GlideLoader()).start();

        mToolbarRightIcon.setVisibility(View.VISIBLE);
        RxView.clicks(mToolbarRightIcon).throttleFirst(1, TimeUnit.SECONDS).subscribe(v -> onBackPressed());
        RxView.clicks(mGoodsDetailButton).throttleFirst(1, TimeUnit.SECONDS).subscribe(v -> requestBugGoods());
//        RxView.clicks(mExpandGoodDetail).throttleFirst(1, TimeUnit.SECONDS).subscribe(v -> requestShowDetail());
        RxView.clicks(mGoodsSpecification).throttleFirst(1, TimeUnit.SECONDS).subscribe(v -> requestGoodsSpecification());
        mCollapsingToolbarLayout.setTitle(" ");
    }

    private void initData() {
        mPresenter.requestGoodInfo(mGoodsInfo.pid);
//        mPresenter.requestGoodsSpecification(mGoodsInfo.pid);
    }

    private void requestBugGoods() {
        if (TextUtils.isEmpty(mGoodsSpecification.getText())) {
//            showEmptyDialog();
            startActivity(PayActivity.getIntent(this));
        }
    }

    private void showEmptyDialog() {
        SpecificationEmptyDialog dialog = SpecificationEmptyDialog.newInstance();
        DialogFactory.showDialogFragment(getSupportFragmentManager(), dialog, SpecificationEmptyDialog.TAG);
    }

    private void requestGoodsSpecification() {
        SpecificationDialog dialog = new SpecificationDialog();
        dialog.setInstance(dialog);
        dialog.setImageLoadHelper(mImageLoaderHelper);
        dialog.productSpecification(mGoodsInfo);
        if(mBuProcessor.getShopInfo()!=null){
            dialog.setStoreCode(mBuProcessor.getShopInfo().storeCode);
        }else {
            dialog.setStoreCode(mBuProcessor.getShopResponse().storeCode);
        }

        dialog.setListener(this);
        DialogFactory.showDialogFragment(getSupportFragmentManager(), dialog, SpecificationDialog.TAG);
    }

    private void initializeInjector() {
        DaggerGoodsDetailActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .goodsDetailActivityModule(new GoodsDetailActivityModule(GoodDetailActivity.this, this))
                .build().inject(this);
    }
}
