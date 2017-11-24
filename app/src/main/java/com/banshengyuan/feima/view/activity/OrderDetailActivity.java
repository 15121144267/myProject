package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerOrderDetailActivityComponent;
import com.banshengyuan.feima.dagger.module.OrderDetailActivityModule;
import com.banshengyuan.feima.entity.MyOrdersResponse;
import com.banshengyuan.feima.utils.ValueUtil;
import com.banshengyuan.feima.view.PresenterControl.OrderDetailControl;
import com.banshengyuan.feima.view.adapter.OrdersDetailAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by helei on 2017/4/27.
 * OrderDetailActivity
 */

public class OrderDetailActivity extends BaseActivity implements OrderDetailControl.OrderDetailView {


    @Inject
    OrderDetailControl.PresenterOrderDetail mPresenter;
    @BindView(R.id.middle_name)
    TextView middleName;
    @BindView(R.id.toolbar_right_icon)
    ImageView toolbarRightIcon;
    @BindView(R.id.toolbar_right_text)
    TextView toolbarRightText;
    @BindView(R.id.order_detail_status)
    TextView orderDetailStatus;
    @BindView(R.id.order_detail_complete_time)
    TextView orderDetailCompleteTime;
    @BindView(R.id.order_detail_shop_name)
    TextView orderDetailShopName;
    @BindView(R.id.order_detail_product_list)
    RecyclerView orderDetailProductList;
    @BindView(R.id.order_detail_price)
    TextView orderDetailPrice;
    @BindView(R.id.order_detail_dispatch_price)
    TextView orderDetailDispatchPrice;
    @BindView(R.id.order_detail_should_pay)
    TextView orderDetailShouldPay;
    @BindView(R.id.order_detail_order_id)
    TextView orderDetailOrderId;
    @BindView(R.id.order_detail_copy_orderid)
    TextView orderDetailCopyOrderid;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private MyOrdersResponse.ListBean.OrderItemBean mOrder;
    private MyOrdersResponse.ListBean mListBean;

    public static Intent getOrderDetailIntent(Context context, MyOrdersResponse.ListBean.OrderItemBean order, MyOrdersResponse.ListBean listBean) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra("orderDetail", order);
        intent.putExtra("listBean", listBean);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetail);
        ButterKnife.bind(this);
        supportActionBar(mToolbar, true);
        initializeInjector();
        middleName.setText("订单详情");
        parseIntent();
        initView();
    }

    private void parseIntent() {
        mOrder = getIntent().getParcelableExtra("orderDetail");
        mListBean = getIntent().getParcelableExtra("listBean");
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
        orderDetailProductList.setLayoutManager(new LinearLayoutManager(this));
        OrdersDetailAdapter adapter = new OrdersDetailAdapter(mOrder.getProduct(), this, mImageLoaderHelper);
        orderDetailProductList.setAdapter(adapter);

//        mOrderDetailCompleteTime.setText(TimeUtil.stringTimeToFormat(String.valueOf(mOrder.gmtCreate), TimeUtil.TIME_MMDD_HHMMSS1));
        orderDetailShopName.setText(mOrder.getStore_name());
        Double totalPrice = 0.00;
        Integer shouldPayPrice = 0;

        for (MyOrdersResponse.ListBean.OrderItemBean.ProductBean productsBean : mOrder.getProduct()) {
            totalPrice += Double.parseDouble(productsBean.getPrice()) * productsBean.getNumber();
//                shouldPayPrice += productsBean.originalPrice * productsBean.productNumber;
        }
        String priceTotal = "￥" + ValueUtil.formatAmount(totalPrice);
        orderDetailPrice.setText(priceTotal);

//        if (mOrder.accounts != null) {
//            MyOrdersResponse.OrdersBean.AccountsBean accounts = mOrder.accounts.get(0);
//            String dispatchPrice = "￥" + ValueUtil.formatAmount(accounts.price);
//            String finalPrice = "实付:￥" + ValueUtil.formatAmount(totalPrice + accounts.price);
//            String shouldPayFinalPrice = "应付:￥" + ValueUtil.formatAmount(shouldPayPrice + accounts.price);
//            mOrderDetailDispatchPrice.setText(dispatchPrice);
//            mOrderDetailPayPrice.setText(finalPrice);
//            mOrderDetailShouldPay.setText(shouldPayFinalPrice);
//        }
//
//        mOrderDetailOrderId.setText(String.valueOf(mOrder.oid));
//        mOrderDetailBeginTime.setText(TimeUtil.stringTimeToFormat(String.valueOf(mOrder.gmtCreate), TimeUtil.TIME_YYMMDD_HHMMSS));

    }

    private void initializeInjector() {
        DaggerOrderDetailActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .orderDetailActivityModule(new OrderDetailActivityModule(OrderDetailActivity.this, this))
                .build().inject(this);
    }

    @OnClick(R.id.order_detail_copy_orderid)
    public void onViewClicked() {
        finish();
    }
}
