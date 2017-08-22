package com.dispatching.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.dispatching.feima.BuildConfig;
import com.dispatching.feima.DaggerApplication;
import com.dispatching.feima.R;
import com.dispatching.feima.dagger.component.DaggerShoppingCardActivityComponent;
import com.dispatching.feima.dagger.module.ShoppingCardActivityModule;
import com.dispatching.feima.dagger.module.ShoppingCardListResponse;
import com.dispatching.feima.entity.OrderConfirmedRequest;
import com.dispatching.feima.entity.PayCreateRequest;
import com.dispatching.feima.utils.SpannableStringUtils;
import com.dispatching.feima.utils.ToastUtils;
import com.dispatching.feima.utils.ValueUtil;
import com.dispatching.feima.view.PresenterControl.ShoppingCardControl;
import com.dispatching.feima.view.adapter.ShoppingCardAdapter;
import com.dispatching.feima.view.adapter.ShoppingCardItemAdapter;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/28.
 * AddressActivity
 */

public class ShoppingCardActivity extends BaseActivity implements ShoppingCardControl.ShoppingCardView {

    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.activity_shopping_card_list)
    RecyclerView mActivityShoppingCardList;
    @BindView(R.id.activity_shopping_card_check)
    CheckBox mActivityShoppingCardCheck;
    @BindView(R.id.activity_shopping_card_price)
    TextView mActivityShoppingCardPrice;
    @BindView(R.id.activity_shopping_card_balance)
    TextView mActivityShoppingCardBalance;
    @BindView(R.id.activity_shopping_card_bottom_view)
    LinearLayout mActivityShoppingCardBottomView;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, ShoppingCardActivity.class);
        return intent;
    }

    @Inject
    ShoppingCardControl.PresenterShoppingCard mPresenter;

    private ShoppingCardAdapter mAdapter;
    private View mEmptyView;
    private Button mEmptyButton;
    private final String companyId = "53c69e54-c788-495c-bed3-2dbfc6fd5c61";
    private List<ShoppingCardListResponse.DataBean> mProductList;
    private AMapLocation mLocationInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_card);
        ButterKnife.bind(this);
        initializeInjector();
        supportActionBar(mToolbar, true);
        mMiddleName.setText("我的购物车");
        initView();
        initData();
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
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setChildAdapter(Integer parentPosition, ShoppingCardItemAdapter itemAdapter, CheckBox partnerCheckBox) {
        ShoppingCardListResponse.DataBean mProduct = mProductList.get(parentPosition);
        itemAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.item_shopping_card_check);
            switch (view.getId()) {
                case R.id.item_shopping_card_check:
                    ShoppingCardListResponse.DataBean.ProductsBean childProduct = mProduct.products.get(position);
                    if (!checkBox.isChecked()) {
                        childProduct.childCheckFlag = false;
                        if (mProduct.checkFlag) {
                            partnerCheckBox.setChecked(false);
                            mProduct.checkFlag = false;
                            if (mActivityShoppingCardCheck.isChecked()) {
                                mActivityShoppingCardCheck.setChecked(false);
                            }
                        }
                    } else {
                        childProduct.childCheckFlag = true;
                    }

                    countPrice2(partnerCheckBox, mProduct);
                    itemAdapter.setData(position, childProduct);
                    break;
                case R.id.item_shopping_card_reduce:
                    ToastUtils.showShortToast("减少" + position);
                    break;
                case R.id.item_shopping_card_add:
                    ToastUtils.showShortToast("增加" + position);
                    break;
                case R.id.item_shopping_card_delete:
                    ToastUtils.showShortToast("删除" + position);
                    break;
            }

        });
    }

    @Override
    public void shoppingCardListSuccess(ShoppingCardListResponse response) {
        mProductList = response.data;
        if (mProductList != null && mProductList.size() > 0) {
            mActivityShoppingCardBottomView.setVisibility(View.VISIBLE);
            mAdapter.setNewData(mProductList);
        } else {
            mActivityShoppingCardBottomView.setVisibility(View.GONE);
            mAdapter.setEmptyView(mEmptyView);
        }
    }

    private void initData() {
        mPresenter.requestShoppingCardList(companyId, mBuProcessor.getUserId());
    }

    private void initView() {
        setAllPriceText(0);
        mLocationInfo = ((DaggerApplication) getApplicationContext()).getaMapLocation();
        mEmptyView = LayoutInflater.from(this).inflate(R.layout.empty_view, (ViewGroup) mActivityShoppingCardList.getParent(), false);
        mEmptyButton = (Button) mEmptyView.findViewById(R.id.empty_go_shopping);
        RxView.clicks(mEmptyButton).throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> goForShopping());
        RxView.clicks(mActivityShoppingCardBalance).throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> goForPayShoppingCard());
        mActivityShoppingCardList.setLayoutManager(new LinearLayoutManager(this));
        RxView.clicks(mActivityShoppingCardCheck).subscribe(o -> checkForAll());
        mAdapter = new ShoppingCardAdapter(null, this, ShoppingCardActivity.this, mImageLoaderHelper);
        mActivityShoppingCardList.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            ShoppingCardListResponse.DataBean product = mProductList.get(position);
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.adapter_shopping_card_check);
            switch (view.getId()) {
                case R.id.adapter_shopping_card_check:
                    if (!checkBox.isChecked()) {
                        product.checkFlag = false;
                        for (ShoppingCardListResponse.DataBean.ProductsBean productsBean : product.products) {
                            productsBean.childCheckFlag = false;
                        }
                        if (mActivityShoppingCardCheck.isChecked()) {
                            mActivityShoppingCardCheck.setChecked(false);
                        }
                    } else {
                        product.checkFlag = true;
                        for (ShoppingCardListResponse.DataBean.ProductsBean productsBean : product.products) {
                            productsBean.childCheckFlag = true;
                        }
                    }
                    countPrice();
                    mAdapter.setData(position, product);
                    break;
                case R.id.adapter_shopping_card_edit:
                    TextView editTextView = (TextView) view.findViewById(R.id.adapter_shopping_card_edit);
                    if (editTextView.getText().toString().trim().equals("编辑")) {
                        product.childEditFlag = true;
                        for (ShoppingCardListResponse.DataBean.ProductsBean productsBean : product.products) {
                            productsBean.childEditFlag = true;

                        }
                    } else {
                        product.childEditFlag = false;
                        for (ShoppingCardListResponse.DataBean.ProductsBean productsBean : product.products) {
                            productsBean.childEditFlag = false;
                        }
                    }

                    mAdapter.setData(position, product);
                    break;

            }
        });
    }

    private void goForPayShoppingCard() {
        PayCreateRequest request = new PayCreateRequest();
        List<OrderConfirmedRequest> payCreate = new ArrayList<>();

        for (ShoppingCardListResponse.DataBean dataBean : mProductList) {
            OrderConfirmedRequest orderCreateRequest = new OrderConfirmedRequest();
            List<OrderConfirmedRequest.ProductsBean> list = new ArrayList<>();
            for (ShoppingCardListResponse.DataBean.ProductsBean product : dataBean.products) {
                if (product.childCheckFlag) {
                    OrderConfirmedRequest.ProductsBean productsBean = new OrderConfirmedRequest.ProductsBean();
                    productsBean.productName = product.name;
                    productsBean.sequence = product.sequence + "";
                    productsBean.number = String.valueOf(product.productNumber);
                    productsBean.specification = product.specification;
                    productsBean.productId = product.pid;
                    productsBean.price = product.finalPrice;
                    productsBean.picture = product.picture;
                    list.add(productsBean);
                }

            }
            if (list.size() > 0) {
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

                orderCreateRequest.shopName = "";
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
                orderCreateRequest.shopId = dataBean.linkId;
                orderCreateRequest.partition = "";
                orderCreateRequest.remark = "";
                orderCreateRequest.payChannelName = "";
                orderCreateRequest.companyId = BuildConfig.PARTNER_ID;

                payCreate.add(orderCreateRequest);
            }

        }


        if (payCreate.size() > 0) {
            request.orders = payCreate;
            startActivity(PayActivity.getIntent(this, request));
        } else {
            showToast("您还没有选择宝贝哦");
        }
    }

    private void countPrice2(CheckBox partnerCheckBox, ShoppingCardListResponse.DataBean mProduct) {
        countPrice();
        boolean isAllCheck = true;
        for (ShoppingCardListResponse.DataBean.ProductsBean product : mProduct.products) {
            if (!product.childCheckFlag) {
                isAllCheck = false;
            }
        }
        partnerCheckBox.setChecked(isAllCheck);
    }

    private void countPrice() {
        Integer mAllPrice = 0;
        for (ShoppingCardListResponse.DataBean dataBean : mProductList) {
            for (ShoppingCardListResponse.DataBean.ProductsBean product : dataBean.products) {
                if (product.childCheckFlag) {
                    mAllPrice += product.finalPrice * product.productNumber;
                }
            }
        }
        setAllPriceText(mAllPrice);
    }

    private void setAllPriceText(Integer price) {
        String orderPricePartOne = "合计：";
        String orderPricePartTwo = "￥" + ValueUtil.formatAmount(price);
        SpannableStringBuilder stringBuilder = SpannableStringUtils.getBuilder(orderPricePartTwo)
                .setForegroundColor(ContextCompat.getColor(this, R.color.order_price_color))
                .setSize(18, true)
                .create();
        SpannableStringBuilder stringBuilder2 = SpannableStringUtils.getBuilder(orderPricePartOne)
                .setForegroundColor(ContextCompat.getColor(this, R.color.light_grey_dark))
                .append(stringBuilder).create();
        mActivityShoppingCardPrice.setText(stringBuilder2);
    }

    private void checkForAll() {
        if (!mActivityShoppingCardCheck.isChecked()) {
            for (ShoppingCardListResponse.DataBean dataBean : mProductList) {
                dataBean.checkFlag = false;
                for (ShoppingCardListResponse.DataBean.ProductsBean product : dataBean.products) {
                    product.childCheckFlag = false;
                }
            }
        } else {
            for (ShoppingCardListResponse.DataBean dataBean : mProductList) {
                dataBean.checkFlag = true;
                for (ShoppingCardListResponse.DataBean.ProductsBean product : dataBean.products) {
                    product.childCheckFlag = true;
                }
            }
        }
        countPrice();
        mAdapter.setNewData(mProductList);
    }

    private void goForShopping() {
        showToast("去购物");
    }

    private void initializeInjector() {
        DaggerShoppingCardActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .shoppingCardActivityModule(new ShoppingCardActivityModule(ShoppingCardActivity.this, this))
                .build().inject(this);
    }
}
