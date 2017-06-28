package com.dispatching.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dispatching.feima.R;
import com.dispatching.feima.dagger.component.DaggerOrderDetailActivityComponent;
import com.dispatching.feima.dagger.module.OrderDetailActivityModule;
import com.dispatching.feima.entity.MyOrdersResponse;
import com.dispatching.feima.view.PresenterControl.OrderDetailControl;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dispatching.feima.R.id.toolbar;

/**
 * Created by helei on 2017/4/27.
 * OrderDetailActivity
 */

public class OrderDetailActivity extends BaseActivity implements OrderDetailControl.OrderDetailView {
    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(toolbar)
    Toolbar mToolbar;
    @Inject
    OrderDetailControl.PresenterOrderDetail mPresenter;
    @BindView(R.id.order_detail_status)
    TextView mOrderDetailStatus;
    @BindView(R.id.order_detail_complete_time)
    TextView mOrderDetailCompleteTime;
    @BindView(R.id.order_detail_shop_name)
    TextView mOrderDetailShopName;
    @BindView(R.id.order_detail_product_pic)
    ImageView mOrderDetailProductPic;
    @BindView(R.id.order_detail_product_name)
    TextView mOrderDetailProductName;
    @BindView(R.id.order_detail_product_price)
    TextView mOrderDetailProductPrice;
    @BindView(R.id.order_detail_product_count)
    TextView mOrderDetailProductCount;
    @BindView(R.id.order_detail_product_total_amount)
    TextView mOrderDetailProductTotalAmount;
    @BindView(R.id.order_detail_price)
    TextView mOrderDetailPrice;
    @BindView(R.id.order_detail_dispatch_price)
    TextView mOrderDetailDispatchPrice;
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
    private MyOrdersResponse mOrder;

    public static Intent getOrderDetailIntent(Context context, MyOrdersResponse order) {
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
        supportActionBar(mToolbar, true);
        mMiddleName.setText("订单详情");
        parseIntent();
        initView();
    }

    private void parseIntent() {
        mOrder = (MyOrdersResponse) getIntent().getSerializableExtra("orderDetail");
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

    }

    private void initializeInjector() {
        DaggerOrderDetailActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .orderDetailActivityModule(new OrderDetailActivityModule(OrderDetailActivity.this, this))
                .build().inject(this);
    }
}
