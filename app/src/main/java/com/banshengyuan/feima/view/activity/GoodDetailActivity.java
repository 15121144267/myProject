package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerGoodsDetailActivityComponent;
import com.banshengyuan.feima.dagger.module.GoodsDetailActivityModule;
import com.banshengyuan.feima.dagger.module.ShoppingCardListResponse;
import com.banshengyuan.feima.entity.BroConstant;
import com.banshengyuan.feima.entity.CollectionResponse;
import com.banshengyuan.feima.entity.GoodsInfoResponse;
import com.banshengyuan.feima.entity.SkuProductResponse;
import com.banshengyuan.feima.entity.SpecificationResponse;
import com.banshengyuan.feima.help.DialogFactory;
import com.banshengyuan.feima.help.GlideLoader;
import com.banshengyuan.feima.utils.ValueUtil;
import com.banshengyuan.feima.view.PresenterControl.GoodsDetailControl;
import com.banshengyuan.feima.view.fragment.PhotoChoiceDialog;
import com.banshengyuan.feima.view.fragment.SpecificationDialog;
import com.jakewharton.rxbinding2.view.RxView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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
    @BindView(R.id.goods_detail_enter_shop)
    TextView mGoodsDetailEnterShop;

    public static Intent getIntent(Context context, Integer productId) {
        Intent intent = new Intent(context, GoodDetailActivity.class);
        intent.putExtra("productId", productId);
        return intent;
    }


    @Inject
    GoodsDetailControl.PresenterGoodsDetail mPresenter;

    private SpecificationDialog mDialog;
    private TreeMap<Integer, String> mSelectProMap;
    private TreeMap<Integer, Integer> mSkuProMap;
    private Integer mProductId;
    private GoodsInfoResponse.InfoBean mInfoBean;
    private SkuProductResponse.InfoBean mSkuInfoBean;
    private boolean mDoFlag;
    private List<String> mSkuList = new ArrayList<>();

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
            mGoodsDetailCollection.setImageResource(mInfoBean.is_collected ? R.mipmap.shop_detail_collection : R.mipmap.shop_detail_uncollection);
            mGoodsDetailSummary.setText(TextUtils.isEmpty(mInfoBean.name) ? "未知" : mInfoBean.name);
            mGoodsDetailPrice.setText("￥" + ValueUtil.formatAmount2(mInfoBean.price) + "");
            if ((mInfoBean.freight == 1)) {
                mGoodsDetailDispatchingPrice.setText("快递:" + ValueUtil.formatAmount2(mInfoBean.shipping_price) +
                        ";满" + ValueUtil.formatAmount2(mInfoBean.free_shipping_price) + "包邮  ");
            } else {
                mGoodsDetailDispatchingPrice.setText("快递:免邮  ");
            }


            GoodsInfoResponse.InfoBean.StoreBean store = mInfoBean.store;
            if (store != null) {
                mGoodsDetailAddress.setText(TextUtils.isEmpty(store.location) ? "未知" : store.location);
                mGoodsDetailShopName.setText(TextUtils.isEmpty(store.name) ? "未知" : store.name);
            }
            if (!TextUtils.isEmpty(mInfoBean.content)) {
                ValueUtil.setHtmlContent(this, mInfoBean.content, mGoodsDetailHtml);
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
    public void closeSpecificationDialog(TreeMap<Integer, String> selectProMap, TreeMap<Integer, Integer> skuProMap,
                                         String content, GoodsInfoResponse.InfoBean infoBean, boolean doFlag) {
        commonContent(selectProMap, skuProMap, content, infoBean, doFlag);
    }

    @Override
    public void closeSpecificationDialog2(SkuProductResponse.InfoBean skuInfoBean, TreeMap<Integer, String> selectProMap,
                                          TreeMap<Integer, Integer> skuProMap, String content, GoodsInfoResponse.InfoBean infoBean, boolean doFlag) {
        mSkuInfoBean = skuInfoBean;
        commonContent(selectProMap, skuProMap, content, infoBean, doFlag);
    }

    private void commonContent(TreeMap<Integer, String> selectProMap, TreeMap<Integer, Integer> skuProMap, String content, GoodsInfoResponse.InfoBean infoBean, boolean doFlag) {
        mDoFlag = doFlag;
        mInfoBean = infoBean;
        mSelectProMap = selectProMap;
        mSkuProMap = skuProMap;
        mGoodsDetailSpecification.setText(content);
    }

    @Override
    public void buyButtonListener(SkuProductResponse.InfoBean skuInfoBean, Integer count) {
        ShoppingCardListResponse response = new ShoppingCardListResponse();
        List<ShoppingCardListResponse.ListBeanX> orderConfirm = new ArrayList<>();
        List<ShoppingCardListResponse.ListBeanX.ListBean> productList = new ArrayList<>();
        ShoppingCardListResponse.ListBeanX product = new ShoppingCardListResponse.ListBeanX();
        if (mInfoBean.store != null) {
            product.stoer_name = mInfoBean.store.name;
            product.store_id = mInfoBean.store.id;
        }

        ShoppingCardListResponse.ListBeanX.ShopFreightConfigBean freightConfigBean = new ShoppingCardListResponse.ListBeanX.ShopFreightConfigBean();
        freightConfigBean.freight = mInfoBean.freight;
        freightConfigBean.free_shipping_price = mInfoBean.free_shipping_price;
        freightConfigBean.shipping_price = mInfoBean.shipping_price;
        product.shop_freight_config = freightConfigBean;

        ShoppingCardListResponse.ListBeanX.ListBean productInfo = new ShoppingCardListResponse.ListBeanX.ListBean();
        productInfo.number = count;
        productInfo.goods_id = mInfoBean.id;
        if (skuInfoBean == null) {
            productInfo.goods_img = mInfoBean.top_img.get(0);
            productInfo.goods_name = mInfoBean.name;
            productInfo.goods_sku = mInfoBean.main_sku;
            productInfo.goods_price = mInfoBean.price;
        } else {
            productInfo.goods_img = skuInfoBean.img;
            productInfo.goods_name = skuInfoBean.name;
            productInfo.goods_sku = skuInfoBean.sku;
            productInfo.goods_price = skuInfoBean.price;
        }
        productList.add(productInfo);
        product.list = productList;
        orderConfirm.add(product);
        response.list = orderConfirm;
        startActivity(PayActivity.getIntent(this, response, 1));
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
                showToast("该设备暂无打电话功能");
            }
        });
        RxView.clicks(mToolbarRightIcon).throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> showToast("该功能暂未开放"));
        RxView.clicks(mGoodsDetailCollection).subscribe(v -> {
            if (mBuProcessor.isValidLogin()) {
                if (mInfoBean.store != null) {
                    mPresenter.requestGoodsCollection(mInfoBean.store.id + "", "goods");
                }
            } else {
                switchToLogin();
            }

        });

        RxView.clicks(mGoodsDetailSpecification).throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> {
            if (mBuProcessor.isValidLogin()) {
                showSpecificationDialog(1);
            } else {
                switchToLogin();
            }
        });
        RxView.clicks(mGoodsDetailBuy).throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> {
            if (mBuProcessor.isValidLogin()) {
                showSpecificationDialog(2);
            } else {
                switchToLogin();
            }
        });
        RxView.clicks(mGoodsDetailAdd).throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> {
            if (mBuProcessor.isValidLogin()) {
                showSpecificationDialog(3);
            } else {
                switchToLogin();
            }
        });

        RxView.clicks(mGoodsDetailComment).throttleFirst(1, TimeUnit.SECONDS).subscribe(
                o -> startActivity(GoodsCommentActivity.getIntent(this, mProductId)));

        RxView.clicks(mGoodsDetailEnterShop).throttleFirst(1, TimeUnit.SECONDS).subscribe(
                o -> startActivity(ShopProductDetailActivity.getActivityDetailIntent(this, mInfoBean.store.id)));

    }

    private void showSpecificationDialog(Integer addOrBugFlag) {
        mDialog = SpecificationDialog.newInstance();
        mDialog.setContent(mInfoBean, addOrBugFlag, mImageLoaderHelper, this, mDialog, mSkuProMap, mSelectProMap, mSkuInfoBean, mDoFlag);
        mDialog.setListener(this);
        DialogFactory.showDialogFragment(getSupportFragmentManager(), mDialog, PhotoChoiceDialog.TAG);

    }

    private void initData() {
        mPresenter.requestGoodInfo(mProductId);
    }


    @Override
    public void addToShoppingCard(String sku, Integer count) {
        mPresenter.requestAddShoppingCard(mProductId + "", sku, count);
    }

    @Override
    public void addShoppingCardSuccess() {
        showToast("添加购物车成功");
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(BroConstant.UPDATE_SHOPPING_CARD_INFO));
    }

    @Override
    public void checkProductId(TreeMap<Integer, Integer> mProMap) {
        mSkuList.clear();

        String skuName = "";
        for (Map.Entry<Integer, Integer> stringStringEntry : mProMap.entrySet()) {
            mSkuList.add(stringStringEntry.getValue().toString());
        }

        for (GoodsInfoResponse.InfoBean.BindSpecBean bindSpecBean : mInfoBean.bind_spec) {
            String[] array = bindSpecBean.spec_id.split("_");
            if (Arrays.asList(array).containsAll(mSkuList)) {
                skuName = bindSpecBean.sku_name;
            }
        }
        if (!TextUtils.isEmpty(skuName)) {
            mPresenter.requestUniqueGoodInfo(mProductId, skuName);
        }

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
        showToast("服务器异常");
    }

    private void initializeInjector() {
        DaggerGoodsDetailActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .goodsDetailActivityModule(new GoodsDetailActivityModule(GoodDetailActivity.this, this))
                .build().inject(this);
    }
}
