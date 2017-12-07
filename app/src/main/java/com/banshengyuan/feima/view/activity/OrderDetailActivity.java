package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aries.ui.view.radius.RadiusTextView;
import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerOrderDetailActivityComponent;
import com.banshengyuan.feima.dagger.module.OrderDetailActivityModule;
import com.banshengyuan.feima.entity.MyOrdersResponse;
import com.banshengyuan.feima.utils.ValueUtil;
import com.banshengyuan.feima.view.PresenterControl.OrderDetailControl;
import com.banshengyuan.feima.view.adapter.OrdersDetailAdapter;

import java.util.ArrayList;
import java.util.List;

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
    RadiusTextView orderDetailCopyOrderid;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.address_map_icon)
    ImageView addressMapIcon;
    @BindView(R.id.order_right_btn)
    RadiusTextView orderRightBtn;
    @BindView(R.id.order_left_btn)
    RadiusTextView orderLeftBtn;
    private MyOrdersResponse.ListBean mListBean = null;
    private MyOrdersResponse.ListBean orderItemBean = null;
    List<MyOrdersResponse.ListBean.ProductBean> mProductBeen = new ArrayList<>();

    Integer totalPrice = 0;
    Integer transPrice = 0;
    Integer shouldPrice = 0;

    public static Intent getOrderDetailIntent(Context context, MyOrdersResponse.ListBean listBean) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
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
        initView();
    }

    private void parseIntent() {
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
        parseIntent();
        orderDetailProductList.setLayoutManager(new LinearLayoutManager(this));
        if (orderItemBean != null) {
            OrdersDetailAdapter adapter = new OrdersDetailAdapter(orderItemBean.getProduct(), this, mImageLoaderHelper);
            orderDetailProductList.setAdapter(adapter);

            orderDetailShopName.setText(orderItemBean.getStore_name());

            for (MyOrdersResponse.ListBean.ProductBean productBean : orderItemBean.getProduct()) {
                if (productBean != null) {
                    totalPrice += productBean.getPrice() * productBean.getNumber();
                }
            }
        }

        String priceTotal = "￥" + ValueUtil.formatAmount(totalPrice);
        orderDetailPrice.setText(priceTotal);

        shouldPrice = totalPrice + transPrice;
        orderDetailShouldPay.setText("￥" + ValueUtil.formatAmount(shouldPrice));
    }

    private void initializeInjector() {
        DaggerOrderDetailActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .orderDetailActivityModule(new OrderDetailActivityModule(OrderDetailActivity.this, this))
                .build().inject(this);
    }

    @OnClick({R.id.call_business_iv, R.id.order_detail_copy_orderid, R.id.order_right_btn, R.id.order_left_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.call_business_iv:
                try {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:123456789"));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.order_detail_copy_orderid:
                showToast("copy");
                break;
            case R.id.order_right_btn:
                showToast("right");
                break;
            case R.id.order_left_btn:
                showToast("left");
                break;
        }
    }
}
