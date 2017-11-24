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

import com.amap.api.location.AMapLocation;
import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerGoodsDetailActivityComponent;
import com.banshengyuan.feima.dagger.module.GoodsDetailActivityModule;
import com.banshengyuan.feima.entity.BroConstant;
import com.banshengyuan.feima.entity.CollectionResponse;
import com.banshengyuan.feima.entity.GoodsInfoResponse;
import com.banshengyuan.feima.entity.SpecificationResponse;
import com.banshengyuan.feima.help.GlideLoader;
import com.banshengyuan.feima.utils.ValueUtil;
import com.banshengyuan.feima.view.PresenterControl.GoodsDetailControl;
import com.banshengyuan.feima.view.fragment.SpecificationDialog;
import com.jakewharton.rxbinding2.view.RxView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    @BindView(R.id.goods_detail_specification)
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

    public static Intent getIntent(Context context, Integer productId) {
        Intent intent = new Intent(context, GoodDetailActivity.class);
        intent.putExtra("productId", productId);
        return intent;
    }


    @Inject
    GoodsDetailControl.PresenterGoodsDetail mPresenter;

    private StringBuilder mButter;
    private SpecificationDialog mSpecificationDialog;
    private HashMap<String, String> mHashMap;
    private SpecificationResponse.ProductsBean.ProductSpecificationBean mProductSpecification;
    private String mCount;
    private SpecificationResponse.ProductsBean mProductsBean;
    private AMapLocation mLocationInfo;
    private Integer mProductId;
    private GoodsInfoResponse.InfoBean mInfoBean;

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
        mGoodsDetailCollection.setImageResource(response.status==1?R.mipmap.shop_detail_collection:R.mipmap.shop_detail_uncollection);
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
            mGoodsDetailDispatchingPrice.setText("快递:" + ValueUtil.formatAmount2(mInfoBean.freight));
            GoodsInfoResponse.InfoBean.StoreBean store = mInfoBean.store;
            if (store != null) {
                mGoodsDetailAddress.setText(TextUtils.isEmpty(store.location) ? "未知" : store.location);
                mGoodsDetailShopName.setText(TextUtils.isEmpty(store.name) ? "未知" : store.name);
            }
        } else {
            mGoodsDetailBottomLayout.setVisibility(View.GONE);
        }

       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mGoodsDetailLinear.setText(Html.fromHtml(data.detailText, Html.FROM_HTML_MODE_LEGACY,
                    new URLImageParser(mGoodsDetailLinear, this), new MxgsaTagHandler(this)));
        } else {
            mGoodsDetailLinear.setText(Html.fromHtml(data.detailText,
                    new URLImageParser(mGoodsDetailLinear, this), new MxgsaTagHandler(this)));
        }*/
    }

    @Override
    public void getGoodsInfoFail(String data) {
        mGoodsDetailBottomLayout.setVisibility(View.GONE);
    }

    @Override
    public void closeSpecificationDialog(HashMap<String, String> hashMap, String count, List<String> colorList, List<String> zipperList, List<String> sizeList) {


    }

    @Override
    public void buyButtonListener(HashMap<String, String> hashMap, Integer count) {
        checkProductId(count);
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
                showToast("该设备无打电话功能");
            }
        });
        RxView.clicks(mGoodsDetailCollection).subscribe(v -> {
            if(mInfoBean.store!=null){
                mPresenter.requestGoodsCollection(mInfoBean.store.id,"goods");
            }

        });
       /*
        if (mInfoBean.top_img != null && mInfoBean.top_img.size() > 0) {
                mShopDetailDetailBanner.setImages(mInfoBean.top_img).setImageLoader(new GlideLoader()).start();
            }

        RxView.clicks(mGoodsDetailCollection).throttleFirst(1, TimeUnit.SECONDS).subscribe(v -> requestBugOrAddGoods(2));
        RxView.clicks(mGoodsSpecification).throttleFirst(1, TimeUnit.SECONDS).subscribe(v -> requestGoodsSpecification(1));
        RxView.clicks(mGoodsDetailAdd).throttleFirst(1, TimeUnit.SECONDS).subscribe(v -> requestBugOrAddGoods(3));
        RxView.clicks(mGoodsDetailCheckShoppingCard).throttleFirst(1, TimeUnit.SECONDS).subscribe(v -> switchToShoppingCard());
        mCollapsingToolbarLayout.setTitle("商品详情");
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);*/
    }

    private void initData() {
        mPresenter.requestGoodInfo(mProductId);
    }

    private void requestBugOrAddGoods(Integer flag) {
       /* if (TextUtils.isEmpty(mGoodsSpecification.getText())) {
            requestGoodsSpecification(flag);
        } else {
            if (mProduct.specificationList != null && mProduct.specificationList.size() > 0) {
                if (mProduct.specificationList.size() == mHashMap.size()) {
                    bugOrAdd(flag);
                } else {
                    requestGoodsSpecification(flag);
                }
            } else {
                bugOrAdd(flag);
            }
        }*/
    }

    private void bugOrAdd(Integer flag) {
        if (flag == 2) {
            checkProductId(Integer.valueOf(mCount));
        } else if (flag == 3) {
            //添加购物车
            requestGoodsSpecification(flag);
        }
    }

    private void switchToShoppingCard() {
        startActivity(ShoppingCardActivity.getIntent(this));
    }

    @Override
    public void addToShoppingCard(Integer count) {
       /* AddShoppingCardRequest request = new AddShoppingCardRequest();
        request.number = String.valueOf(count);
        request.userId = mBuProcessor.getUserId();
        request.linkName = mBuProcessor.getShopInfo().storeName;
        request.type = "1";
        if (mProductsBean != null) {
            request.name = mProductsBean.name;
            request.productId = mProductsBean.pid;

        } else {
            mProductsBean = mProduct;
            request.name = mProduct.name;
            request.productId = mProduct.pid;
        }
        mPresenter.requestAddShoppingCard(request);*/
    }

    @Override
    public void addShoppingCardSuccess() {
        showToast("添加购物车成功");
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(BroConstant.UPDATE_SHOPPING_CARD_INFO));
    }

    @Override
    public void checkProductId(HashMap<String, String> hashMap) {
        /*List<SpecificationResponse.ProductsBean.ProductSpecificationBean> productSpecification = mProduct.productSpecification;
        for (SpecificationResponse.ProductsBean.ProductSpecificationBean productSpecificationBean : productSpecification) {
            if (productSpecificationBean.size != null) {
                if (productSpecificationBean.size.equals(hashMap.get("size"))) {
                    if (productSpecificationBean.color != null) {
                        if (productSpecificationBean.color.equals(hashMap.get("color"))) {
                            if (productSpecificationBean.zipper != null) {
                                if (productSpecificationBean.zipper.equals(hashMap.get("zipper"))) {
                                    mProductSpecification = productSpecificationBean;
                                }
                            } else {
                                mProductSpecification = productSpecificationBean;
                            }
                        }
                    } else {
                        mProductSpecification = productSpecificationBean;
                    }
                }
            } else {
                if (productSpecificationBean.color != null) {
                    if (productSpecificationBean.color.equals(hashMap.get("color"))) {
                        if (productSpecificationBean.zipper != null) {
                            if (productSpecificationBean.zipper.equals(hashMap.get("zipper"))) {
                                mProductSpecification = productSpecificationBean;
                            }
                        } else {
                            mProductSpecification = productSpecificationBean;
                        }
                    }
                } else {
                    if (productSpecificationBean.zipper != null) {
                        if (productSpecificationBean.zipper.equals(hashMap.get("zipper"))) {
                            mProductSpecification = productSpecificationBean;
                        }
                    }
                }
            }
        }
        if (mProductSpecification != null) {
            mPresenter.requestUniqueGoodInfo(String.valueOf(mProductSpecification.productId));
        }*/
    }

    @Override
    public void getUniqueGoodInfoSuccess(SpecificationResponse data) {
        /*mProductsBean = data.products.get(0);
        mSpecificationDialog.setData(mProductsBean);*/
    }

    private void checkProductId(Integer count) {
        /*if (mProduct.specificationList != null && mProduct.specificationList.size() > 0) {
            if (mProductsBean != null) {
                mProductsBean.saleCount = count;
                payOrderCreate(mProductsBean);
            } else {
                showToast("请稍后重试");
            }
        } else {
            mProductsBean = mProduct;
            payOrderCreate(mProductsBean);
        }*/
    }


   /* private void showEmptyDialog() {
        SpecificationEmptyDialog dialog = SpecificationEmptyDialog.newInstance();
        DialogFactory.showDialogFragment(getSupportFragmentManager(), dialog, SpecificationEmptyDialog.TAG);
    }*/

    private void requestGoodsSpecification(Integer addOrBugFlag) {
       /* if (mProduct != null) {
            mSpecificationDialog = new SpecificationDialog();
            mSpecificationDialog.setInstance(mSpecificationDialog);
            mSpecificationDialog.setGoodsView(this);
            mSpecificationDialog.setLists(mColorList, mZipperList, mSizeList);
            mSpecificationDialog.setVisibilityFlag(addOrBugFlag);
            if (mHashMap != null) {
                mSpecificationDialog.setSpecificationHashMap(mHashMap);
                mSpecificationDialog.setTextContent(mButter.toString());
            }

            if (mProductsBean != null) {
                mSpecificationDialog.setProductBean(mProductsBean);
            }

            mSpecificationDialog.setImageLoadHelper(mImageLoaderHelper);
            mSpecificationDialog.productSpecification(mProduct);
            mSpecificationDialog.setListener(this);
            DialogFactory.showDialogFragment(getSupportFragmentManager(), mSpecificationDialog, SpecificationDialog.TAG);
        }*/

    }

    private void initializeInjector() {
        DaggerGoodsDetailActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .goodsDetailActivityModule(new GoodsDetailActivityModule(GoodDetailActivity.this, this))
                .build().inject(this);
    }
}
