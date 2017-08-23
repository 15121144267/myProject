package com.dispatching.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.dispatching.feima.DaggerApplication;
import com.dispatching.feima.R;
import com.dispatching.feima.dagger.component.DaggerGoodsDetailActivityComponent;
import com.dispatching.feima.dagger.module.GoodsDetailActivityModule;
import com.dispatching.feima.entity.AddShoppingCardRequest;
import com.dispatching.feima.entity.BroConstant;
import com.dispatching.feima.entity.GoodsInfoResponse;
import com.dispatching.feima.entity.OrderConfirmedRequest;
import com.dispatching.feima.entity.PayCreateRequest;
import com.dispatching.feima.entity.ShopDetailResponse;
import com.dispatching.feima.entity.SpecificationResponse;
import com.dispatching.feima.help.DialogFactory;
import com.dispatching.feima.help.GlideLoader;
import com.dispatching.feima.help.HtmlHelp.MxgsaTagHandler;
import com.dispatching.feima.help.HtmlHelp.URLImageParser;
import com.dispatching.feima.utils.ValueUtil;
import com.dispatching.feima.view.PresenterControl.GoodsDetailControl;
import com.dispatching.feima.view.fragment.SpecificationDialog;
import com.jakewharton.rxbinding2.view.RxView;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    @BindView(R.id.goods_detail_check_shopping_card)
    ImageView mGoodsDetailCheckShoppingCard;
    @BindView(R.id.goods_detail_add)
    Button mGoodsDetailAdd;

    @Inject
    GoodsDetailControl.PresenterGoodsDetail mPresenter;

    private List<String> mImageList;
    private ShopDetailResponse.ProductsBean mGoodsInfo;
    private SpecificationResponse mSpecificationResponse;
    private SpecificationResponse mResponse;
    private StringBuilder mButter;
    private SpecificationResponse.ProductsBean mProduct;
    private SpecificationDialog mSpecificationDialog;
    private HashMap<String, String> mHashMap;
    private SpecificationResponse.ProductsBean.ProductSpecificationBean mProductSpecification;
    private String mCount;
    private SpecificationResponse.ProductsBean mProductsBean;
    private List<String> mSizeList;
    private List<String> mColorList;
    private List<String> mZipperList;
    private AMapLocation mLocationInfo;

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
        mSpecificationResponse = data;
        mProduct = mSpecificationResponse.products.get(0);
    }

    @Override
    public void getGoodsInfoSuccess(GoodsInfoResponse data) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mGoodsDetailLinear.setText(Html.fromHtml(data.detailText, Html.FROM_HTML_MODE_LEGACY,
                    new URLImageParser(mGoodsDetailLinear, this), new MxgsaTagHandler(this)));
        } else {
            mGoodsDetailLinear.setText(Html.fromHtml(data.detailText,
                    new URLImageParser(mGoodsDetailLinear, this), new MxgsaTagHandler(this)));
        }
    }

    @Override
    public void closeSpecificationDialog(HashMap<String, String> hashMap, String count, List<String> colorList, List<String> zipperList, List<String> sizeList) {
        mSizeList = sizeList;
        mColorList = colorList;
        mZipperList = zipperList;
        mCount = count;
        if (mProduct.specificationList != null && mProduct.specificationList.size() > 0) {
            if (hashMap != null) {
                mButter = new StringBuilder();
                mHashMap = hashMap;
                for (Map.Entry<String, String> stringStringEntry : hashMap.entrySet()) {
                    switch (stringStringEntry.getKey()) {
                        case "color":
                            mButter.append("颜色:").append(stringStringEntry.getValue()).append(" ");
                            break;
                        case "size":
                            mButter.append("尺寸:").append(stringStringEntry.getValue()).append(" ");
                            break;
                        case "zipper":
                            mButter.append("有无拉链:").append(stringStringEntry.getValue()).append(" ");
                            break;
                    }
                }
                mGoodsSpecification.setText(mButter.toString() + " 数量 x" + count);
            }
        } else {
            mGoodsSpecification.setText("数量 x" + count);
        }

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
        mGoodsInfo = (ShopDetailResponse.ProductsBean) getIntent().getSerializableExtra("goodsInfo");
        mLocationInfo = ((DaggerApplication) getApplicationContext()).getaMapLocation();
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
        RxView.clicks(mGoodsDetailButton).throttleFirst(1, TimeUnit.SECONDS).subscribe(v -> requestBugOrAddGoods(2));
        RxView.clicks(mGoodsSpecification).throttleFirst(1, TimeUnit.SECONDS).subscribe(v -> requestGoodsSpecification(1));
        RxView.clicks(mGoodsDetailAdd).throttleFirst(1, TimeUnit.SECONDS).subscribe(v -> requestBugOrAddGoods(3));
        RxView.clicks(mGoodsDetailCheckShoppingCard).throttleFirst(1, TimeUnit.SECONDS).subscribe(v -> switchToShoppingCard());
        mCollapsingToolbarLayout.setTitle("商品详情");
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);
    }

    private void initData() {
        mPresenter.requestGoodInfo(mGoodsInfo.pid);
        mPresenter.requestGoodsSpecification(mGoodsInfo.pid);
    }

    private void requestBugOrAddGoods(Integer flag) {
        if (TextUtils.isEmpty(mGoodsSpecification.getText())) {
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
        }
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
        AddShoppingCardRequest request = new AddShoppingCardRequest();
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
        mPresenter.requestAddShoppingCard(request);
    }

    @Override
    public void addShoppingCardSuccess() {
        showToast("添加购物车成功");
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(BroConstant.UPDATE_SHOPPING_CARD_INFO));
    }

    @Override
    public void checkProductId(HashMap<String, String> hashMap) {
        List<SpecificationResponse.ProductsBean.ProductSpecificationBean> productSpecification = mProduct.productSpecification;
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
        }
    }

    @Override
    public void getUniqueGoodInfoSuccess(SpecificationResponse data) {
        mResponse = data;
        mProductsBean = mResponse.products.get(0);
        mSpecificationDialog.setData(mProductsBean);
    }

    private void checkProductId(Integer count) {
        if (mProduct.specificationList != null && mProduct.specificationList.size() > 0) {
            if (mProductsBean != null) {
                mProductsBean.saleCount = count;
                payOrderCreate(mProductsBean);
            } else {
                showToast("请稍后重试");
            }
        } else {
            mProductsBean = mProduct;
            payOrderCreate(mProductsBean);
        }
    }

    private void payOrderCreate(SpecificationResponse.ProductsBean mProductsBean) {
        PayCreateRequest request = new PayCreateRequest();
        List<OrderConfirmedRequest> payCreate = new ArrayList<>();

        OrderConfirmedRequest orderCreateRequest = new OrderConfirmedRequest();
        List<OrderConfirmedRequest.ProductsBean> list = new ArrayList<>();

        OrderConfirmedRequest.ProductsBean productsBean = new OrderConfirmedRequest.ProductsBean();
        productsBean.productName = mProductsBean.name;
        productsBean.sequence = mProductsBean.sequence + "";
        productsBean.number = String.valueOf(mProductsBean.saleCount);
        productsBean.specification = mProductsBean.specification;
        productsBean.productId = mProductsBean.pid;
        productsBean.price = mProductsBean.finalPrice;
        productsBean.picture = mProductsBean.picture;
        list.add(productsBean);

        orderCreateRequest.products = list;

        List<OrderConfirmedRequest.AccountsBean> accountList = new ArrayList<>();
        OrderConfirmedRequest.AccountsBean accountsBean = new OrderConfirmedRequest.AccountsBean();
        accountsBean.sequence = 0;
        accountsBean.accountId = "123456";
        accountsBean.number = "1";
        accountsBean.name = "运费";
        accountsBean.type = "1";
        accountsBean.price = "500";
        accountList.add(accountsBean);
        orderCreateRequest.accounts = accountList;

        orderCreateRequest.shopName = mBuProcessor.getShopInfo().storeName;
        orderCreateRequest.source = "android";
        orderCreateRequest.customerOrder = "BSY_" + System.currentTimeMillis();
        orderCreateRequest.amount = 1000;
        orderCreateRequest.type = 1;
        orderCreateRequest.payType = 1;
        orderCreateRequest.userId = mBuProcessor.getUserId();
        orderCreateRequest.payChannel = "";
        if (mLocationInfo != null) {
            orderCreateRequest.longitude = String.valueOf(mLocationInfo.getLongitude());
            orderCreateRequest.latitude = String.valueOf(mLocationInfo.getLatitude());
        }
        orderCreateRequest.status = 1;
        orderCreateRequest.shopId = mProductsBean.companyId + mBuProcessor.getShopInfo().storeCode;
        orderCreateRequest.partition = "";
        orderCreateRequest.remark = "";
        orderCreateRequest.payChannelName = "";
        orderCreateRequest.companyId = mProductsBean.companyId;
        payCreate.add(orderCreateRequest);
        request.orders = payCreate;
        startActivity(PayActivity.getIntent(this, request));
    }

   /* private void showEmptyDialog() {
        SpecificationEmptyDialog dialog = SpecificationEmptyDialog.newInstance();
        DialogFactory.showDialogFragment(getSupportFragmentManager(), dialog, SpecificationEmptyDialog.TAG);
    }*/

    private void requestGoodsSpecification(Integer addOrBugFlag) {
        if (mProduct != null) {
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
            if (mBuProcessor.getShopInfo() != null) {
                mSpecificationDialog.setStoreCode(mBuProcessor.getShopInfo().storeCode);
            } else {
                mSpecificationDialog.setStoreCode(mBuProcessor.getShopResponse().storeCode);
            }

            mSpecificationDialog.setListener(this);
            DialogFactory.showDialogFragment(getSupportFragmentManager(), mSpecificationDialog, SpecificationDialog.TAG);
        }

    }

    private void initializeInjector() {
        DaggerGoodsDetailActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .goodsDetailActivityModule(new GoodsDetailActivityModule(GoodDetailActivity.this, this))
                .build().inject(this);
    }
}
