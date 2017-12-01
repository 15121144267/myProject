package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerGoodsDetailActivityComponent;
import com.banshengyuan.feima.dagger.module.GoodsDetailActivityModule;
import com.banshengyuan.feima.entity.BroConstant;
import com.banshengyuan.feima.entity.CollectionResponse;
import com.banshengyuan.feima.entity.GoodsInfoResponse;
import com.banshengyuan.feima.entity.SkuProductResponse;
import com.banshengyuan.feima.entity.SpecificationResponse;
import com.banshengyuan.feima.help.DialogFactory;
import com.banshengyuan.feima.help.GlideLoader;
import com.banshengyuan.feima.help.HtmlHelp.MxgsaTagHandler;
import com.banshengyuan.feima.help.HtmlHelp.URLImageParser;
import com.banshengyuan.feima.utils.ValueUtil;
import com.banshengyuan.feima.view.PresenterControl.GoodsDetailControl;
import com.banshengyuan.feima.view.fragment.PhotoChoiceDialog;
import com.banshengyuan.feima.view.fragment.SpecificationDialog;
import com.jakewharton.rxbinding2.view.RxView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.banshengyuan.feima.R.id.goods_detail_specification;

/**
 * Created by lei.he on 2017/6/30.
 * GoodDetailActivity
 */

public class GoodDetailActivity extends BaseActivity implements GoodsDetailControl.GoodsDetailView,
        SpecificationDialog.specificationDialogListener {

    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.toolbar_right_icon)
    ImageView mToolbarRightIcon;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.goods_detail_summary)
    TextView mGoodsDetailSummary;
    @BindView(R.id.goods_detail_price)
    TextView mGoodsDetailPrice;
    @BindView(R.id.goods_detail_dispatching_price)
    TextView mGoodsDetailDispatchingPrice;
    @BindView(goods_detail_specification)
    TextView mGoodsDetailSpecification;
    @BindView(R.id.goods_detail_comment)
    TextView mGoodsDetailComment;
    @BindView(R.id.goods_detail_shop_icon)
    ImageView mGoodsDetailShopIcon;
    @BindView(R.id.goods_detail_shop_name)
    TextView mGoodsDetailShopName;
    @BindView(R.id.goods_detail_html)
    TextView mGoodsDetailHtml;
    @BindView(R.id.goods_detail_phone)
    ImageView mGoodsDetailPhone;
    @BindView(R.id.goods_detail_collection)
    ImageView mGoodsDetailCollection;
    @BindView(R.id.goods_detail_add)
    Button mGoodsDetailAdd;
    @BindView(R.id.goods_detail_buy)
    Button mGoodsDetailBuy;
    @BindView(R.id.goods_detail_address)
    TextView mGoodsDetailAddress;
    @BindView(R.id.goods_detail_bottom_layout)
    LinearLayout mGoodsDetailBottomLayout;
    @BindView(R.id.goods_detail_content)
    LinearLayout mGoodsDetailContent;

    public static Intent getIntent(Context context, Integer productId) {
        Intent intent = new Intent(context, GoodDetailActivity.class);
        intent.putExtra("productId", productId);
        return intent;
    }


    @Inject
    GoodsDetailControl.PresenterGoodsDetail mPresenter;

    private StringBuilder mButter;
    private SpecificationDialog mDialog;
    private HashMap<Integer, String> mSelectProMap;
    private HashMap<Integer, Integer> mSkuProMap;
    private SpecificationResponse.ProductsBean.ProductSpecificationBean mProductSpecification;
    private String mCount;
    private SpecificationResponse.ProductsBean mProductsBean;
    private AMapLocation mLocationInfo;
    private Integer mProductId;
    private GoodsInfoResponse.InfoBean mInfoBean;
    private SkuProductResponse.InfoBean mSkuInfoBean;

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
//        mProduct = data.products.get(0);
    }

    @Override
    public void getGoodsCollectionSuccess(CollectionResponse response) {
        mGoodsDetailCollection.setImageResource(response.status == 1 ? R.mipmap.shop_detail_collection : R.mipmap.shop_detail_uncollection);
    }

    @Override
    public void getGoodsInfoSuccess(GoodsInfoResponse data) {
        mInfoBean = data.info;
        if (mInfoBean != null) {
            if (mInfoBean.top_img != null && mInfoBean.top_img.size() > 0) {
                mBanner.setImages(mInfoBean.top_img).setImageLoader(new GlideLoader()).start();
            }
            mGoodsDetailSummary.setText(TextUtils.isEmpty(mInfoBean.name) ? "未知" : mInfoBean.name);
            mGoodsDetailPrice.setText("￥" + ValueUtil.formatAmount2(mInfoBean.price));
            if(mInfoBean.freight!=null){
                mGoodsDetailDispatchingPrice.setText("快递:" + ValueUtil.formatAmount2(mInfoBean.freight.freight));
            }
            GoodsInfoResponse.InfoBean.StoreBean store = mInfoBean.store;
            if (store != null) {
                mGoodsDetailAddress.setText(TextUtils.isEmpty(store.location) ? "未知" : store.location);
                mGoodsDetailShopName.setText(TextUtils.isEmpty(store.name) ? "未知" : store.name);
            }
            if (!TextUtils.isEmpty(mInfoBean.content)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    mGoodsDetailHtml.setText(Html.fromHtml(mInfoBean.content, Html.FROM_HTML_MODE_LEGACY,
                            new URLImageParser(mGoodsDetailHtml, this), new MxgsaTagHandler(this)));
                } else {
                    mGoodsDetailHtml.setText(Html.fromHtml(mInfoBean.content,
                            new URLImageParser(mGoodsDetailHtml, this), new MxgsaTagHandler(this)));
                }
            }

        } else {
            mGoodsDetailBottomLayout.setVisibility(View.GONE);
            mGoodsDetailContent.setVisibility(View.GONE);
        }


    }

    @Override
    public void getGoodsInfoFail(String data) {
        mGoodsDetailBottomLayout.setVisibility(View.GONE);
        mGoodsDetailContent.setVisibility(View.GONE);
    }

    @Override
    public void closeSpecificationDialog(HashMap<Integer, String> selectProMap, HashMap<Integer, Integer> skuProMap, String content,GoodsInfoResponse.InfoBean infoBean) {
        commonContent(selectProMap, skuProMap, content,infoBean);
    }

    @Override
    public void closeSpecificationDialog2(SkuProductResponse.InfoBean skuInfoBean, HashMap<Integer, String> selectProMap, HashMap<Integer, Integer> skuProMap, String content,GoodsInfoResponse.InfoBean infoBean) {
        mSkuInfoBean = skuInfoBean;
        commonContent(selectProMap, skuProMap, content,infoBean);
    }

    private void commonContent(HashMap<Integer, String> selectProMap, HashMap<Integer, Integer> skuProMap, String content,GoodsInfoResponse.InfoBean infoBean) {
        mInfoBean = infoBean;
        mSelectProMap = selectProMap;
        mSkuProMap = skuProMap;
        mGoodsDetailSpecification.setText(content);
    }

    @Override
    public void buyButtonListener(HashMap<String, String> hashMap, Integer count) {
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
        mProductId = getIntent().getIntExtra("productId", 0);
        mToolbarRightIcon.setVisibility(View.VISIBLE);
        mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        mBanner.isAutoPlay(false);
        RxView.clicks(mGoodsDetailPhone).throttleFirst(1, TimeUnit.SECONDS).subscribe(v -> {
            try {
                Uri uri = Uri.parse("tel:" + mInfoBean.store.mobile);
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);
            } catch (Exception e) {
                showToast("该设备暂打电话功能");
            }
        });

        RxView.clicks(mGoodsDetailCollection).subscribe(v -> {
            if (mInfoBean.store != null) {
                mPresenter.requestGoodsCollection(mInfoBean.store.id+"", "goods");
            }
        });

        RxView.clicks(mGoodsDetailSpecification).throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> showSpecificationDialog(1));
        RxView.clicks(mGoodsDetailAdd).throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> showSpecificationDialog(2));
        RxView.clicks(mGoodsDetailBuy).throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> showSpecificationDialog(2));

    }

    private void showSpecificationDialog(Integer addOrBugFlag) {
        mDialog = SpecificationDialog.newInstance();
        mDialog.setContent(mInfoBean, addOrBugFlag, mImageLoaderHelper, this, mDialog, mSkuProMap,mSelectProMap, mSkuInfoBean);
        mDialog.setListener(this);
        DialogFactory.showDialogFragment(getSupportFragmentManager(), mDialog, PhotoChoiceDialog.TAG);

    }

    private void initData() {
        mPresenter.requestGoodInfo(mProductId);
    }


    @Override
    public void addToShoppingCard(Integer count) {

    }

    @Override
    public void addShoppingCardSuccess() {
        showToast("添加购物车成功");
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(BroConstant.UPDATE_SHOPPING_CARD_INFO));
    }

    @Override
    public void checkProductId(HashMap<Integer, Integer> skuProMap) {
        mButter = new StringBuilder();
        for (Map.Entry<Integer, Integer> stringStringEntry : skuProMap.entrySet()) {
            mButter.append(stringStringEntry.getValue()).append("_");
        }
        mButter.delete(mButter.toString().length() - 1, mButter.toString().length());
        mPresenter.requestUniqueGoodInfo(mProductId, mButter.toString());

    }

    @Override
    public void getUniqueGoodInfoSuccess(SkuProductResponse data) {
        if (data.info != null) {
            mDialog.setSkuDetail(data.info);
        } else {
            showToast("数据异常");
        }

    }

    @Override
    public void getUniqueGoodInfoFail(String des) {

    }

    private void initializeInjector() {
        DaggerGoodsDetailActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .goodsDetailActivityModule(new GoodsDetailActivityModule(GoodDetailActivity.this, this))
                .build().inject(this);
    }
}
