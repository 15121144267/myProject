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
import com.dispatching.feima.utils.TimeUtil;
import com.dispatching.feima.utils.ValueUtil;
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
        MyOrdersResponse.OrdersBean.ProductsBean product = mOrder.products.get(0);
        MyOrdersResponse.OrdersBean.AccountsBean accounts = mOrder.accounts.get(0);
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
        mOrderDetailCompleteTime.setText(TimeUtil.stringTimeToFormat(String.valueOf(mOrder.gmtCreate),TimeUtil.TIME_MMDD_HHMMSS1));
        mOrderDetailShopName.setText(mOrder.shopName);
        String price = "￥" + ValueUtil.formatAmount(product.finalPrice);
        String amount = "x" + product.productNumber;
        String totalPrice = "￥" + ValueUtil.formatAmount(product.finalPrice * product.productNumber);
        String dispatchPrice = "￥"+ ValueUtil.formatAmount(accounts.price);
        String finalPrice = "￥"+ ValueUtil.formatAmount(product.finalPrice * product.productNumber)+ ValueUtil.formatAmount(accounts.price);
        mOrderDetailProductName.setText(price);
        mOrderDetailProductCount.setText(amount);
        mOrderDetailProductTotalAmount.setText(totalPrice);
        mOrderDetailPrice.setText(totalPrice);
        mOrderDetailDispatchPrice.setText(dispatchPrice);
        mImageLoaderHelper.displayRoundedCornerImage(this, product.picture, mOrderDetailProductPic, 6);
        mOrderDetailPayPrice.setText(finalPrice);
        mOrderDetailOrderId.setText(String.valueOf(mOrder.oid));
        mOrderDetailBeginTime.setText(TimeUtil.stringTimeToFormat(String.valueOf(mOrder.gmtCreate),TimeUtil.TIME_YYMMDD_HHMMSS));
    }

    private void initializeInjector() {
        DaggerOrderDetailActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .orderDetailActivityModule(new OrderDetailActivityModule(OrderDetailActivity.this, this))
                .build().inject(this);
    }
}
