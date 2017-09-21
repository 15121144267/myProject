package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerOrderDetailActivityComponent;
import com.banshengyuan.feima.dagger.module.OrderDetailActivityModule;
import com.banshengyuan.feima.entity.MyOrdersResponse;
import com.banshengyuan.feima.utils.TimeUtil;
import com.banshengyuan.feima.utils.ValueUtil;
import com.banshengyuan.feima.view.PresenterControl.OrderDetailControl;
import com.banshengyuan.feima.view.adapter.OrdersDetailAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by helei on 2017/4/27.
 * OrderDetailActivity
 */

public class OrderDetailActivity extends BaseActivity implements OrderDetailControl.OrderDetailView {

    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar_right_icon)
    ImageView mToolbarRightIcon;
    @BindView(R.id.toolbar_right_text)
    TextView mToolbarRightText;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.order_detail_status)
    TextView mOrderDetailStatus;
    @BindView(R.id.order_detail_complete_time)
    TextView mOrderDetailCompleteTime;
    @BindView(R.id.order_detail_shop_name)
    TextView mOrderDetailShopName;
    @BindView(R.id.order_detail_product_list)
    RecyclerView mOrderDetailProductList;
    @BindView(R.id.order_detail_price)
    TextView mOrderDetailPrice;
    @BindView(R.id.order_detail_dispatch_price)
    TextView mOrderDetailDispatchPrice;
    @BindView(R.id.order_detail_should_pay)
    TextView mOrderDetailShouldPay;
    @BindView(R.id.order_detail_pay_price)
    TextView mOrderDetailPayPrice;
    @BindView(R.id.order_detail_arrive_time)
    TextView mOrderDetailArriveTime;
    @BindView(R.id.order_detail_address)
    TextView mOrderDetailAddress;
    @BindView(R.id.order_detail_dispatch_type)
    TextView mOrderDetailDispatchType;
    @BindView(R.id.order_detail_order_id)
    TextView mOrderDetailOrderId;
    @BindView(R.id.order_detail_begin_time)
    TextView mOrderDetailBeginTime;
    @BindView(R.id.order_detail_pay_type)
    TextView mOrderDetailPayType;
    @BindView(R.id.order_detail_buy_again)
    Button mOrderDetailBuyAgain;

    @Inject
    OrderDetailControl.PresenterOrderDetail mPresenter;
    private MyOrdersResponse.OrdersBean mOrder;

    public static Intent getOrderDetailIntent(Context context, MyOrdersResponse.OrdersBean order) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra("orderDetail", order);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetail);
        ButterKnife.bind(this);
        supportActionBar(mToolbar, true);
        initializeInjector();
        mMiddleName.setText("订单详情");
        parseIntent();
        initView();
    }

    private void parseIntent() {
        mOrder = (MyOrdersResponse.OrdersBean) getIntent().getSerializableExtra("orderDetail");
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
        mOrderDetailProductList.setLayoutManager(new LinearLayoutManager(this));
        OrdersDetailAdapter adapter = new OrdersDetailAdapter(mOrder.products, this, mImageLoaderHelper);
        mOrderDetailProductList.setAdapter(adapter);
        switch (mOrder.status) {
            case 2:
                mOrderDetailStatus.setText("  待支付");
                mOrderDetailBuyAgain.setText("去支付");
                break;
            case 3:
                mOrderDetailStatus.setText("  代发货");
                break;
            case 4:
                mOrderDetailStatus.setText("  配送中");
                break;
            default:
                mOrderDetailStatus.setText("  系统处理中");
        }

        mOrderDetailCompleteTime.setText(TimeUtil.stringTimeToFormat(String.valueOf(mOrder.gmtCreate), TimeUtil.TIME_MMDD_HHMMSS1));
        mOrderDetailShopName.setText(mOrder.shopName);
        Integer totalPrice = 0;
        Integer shouldPayPrice = 0;
        if(mOrder.products!=null){
            for (MyOrdersResponse.OrdersBean.ProductsBean productsBean : mOrder.products) {
                totalPrice += productsBean.finalPrice * productsBean.productNumber;
                shouldPayPrice += productsBean.originalPrice * productsBean.productNumber;
            }
            String priceTotal = "￥" + ValueUtil.formatAmount(totalPrice);
            mOrderDetailPrice.setText(priceTotal);
        }

        if (mOrder.accounts != null) {
            MyOrdersResponse.OrdersBean.AccountsBean accounts = mOrder.accounts.get(0);
            String dispatchPrice = "￥" + ValueUtil.formatAmount(accounts.price);
            String finalPrice = "实付:￥" + ValueUtil.formatAmount(totalPrice + accounts.price);
            String shouldPayFinalPrice = "应付:￥" + ValueUtil.formatAmount(shouldPayPrice + accounts.price);
            mOrderDetailDispatchPrice.setText(dispatchPrice);
            mOrderDetailPayPrice.setText(finalPrice);
            mOrderDetailShouldPay.setText(shouldPayFinalPrice);
        }

        mOrderDetailOrderId.setText(String.valueOf(mOrder.oid));
        mOrderDetailBeginTime.setText(TimeUtil.stringTimeToFormat(String.valueOf(mOrder.gmtCreate), TimeUtil.TIME_YYMMDD_HHMMSS));

    }

    private void initializeInjector() {
        DaggerOrderDetailActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .orderDetailActivityModule(new OrderDetailActivityModule(OrderDetailActivity.this, this))
                .build().inject(this);
    }
}
