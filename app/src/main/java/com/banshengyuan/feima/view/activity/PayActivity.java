package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerPayActivityComponent;
import com.banshengyuan.feima.dagger.module.PayActivityModule;
import com.banshengyuan.feima.dagger.module.ShoppingCardListResponse;
import com.banshengyuan.feima.entity.AddressResponse;
import com.banshengyuan.feima.entity.IntentConstant;
import com.banshengyuan.feima.entity.OrderConfirmedResponse;
import com.banshengyuan.feima.entity.PayConstant;
import com.banshengyuan.feima.entity.PayCreateRequest;
import com.banshengyuan.feima.entity.PayResponse;
import com.banshengyuan.feima.help.PayZFBHelper;
import com.banshengyuan.feima.help.WXPayHelp.PayWXHelper;
import com.banshengyuan.feima.listener.TabCheckListener;
import com.banshengyuan.feima.utils.ValueUtil;
import com.banshengyuan.feima.view.PresenterControl.PayControl;
import com.banshengyuan.feima.view.adapter.PayGoodsListAdapter;
import com.banshengyuan.feima.view.fragment.PayMethodDialog;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/27.
 * MyOrderActivity
 */

public class PayActivity extends BaseActivity implements PayControl.PayView, PayMethodDialog.PayMethodClickListener {

    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.pay_tab_layout)
    TabLayout mPayTabLayout;
    @BindView(R.id.pay_order_list)
    RecyclerView mPayOrderList;
    @BindView(R.id.pay_price)
    TextView mPayPrice;
    @BindView(R.id.pay_order)
    TextView mPayOrder;
    @BindView(R.id.pay_address_name)
    TextView mPayAddressName;
    @BindView(R.id.pay_address_phone)
    TextView mPayAddressPhone;
    @BindView(R.id.pay_address_detail)
    TextView mPayAddressDetail;
    @BindView(R.id.pay_choose_address)
    LinearLayout mPayChooseAddress;
    @BindView(R.id.pay_order_address_layout)
    LinearLayout mPayOrderAddressLayout;

    public static Intent getIntent(Context context, ShoppingCardListResponse response) {
        Intent intent = new Intent(context, PayActivity.class);
        intent.putExtra("ShoppingCardListResponse", response);
        return intent;
    }


    @Inject
    PayControl.PresenterPay mPresenter;
    private final String[] modules = {"物流到家", "门店自提"};
    private PayGoodsListAdapter mAdapter;
    private TextView mPayOrderName;
    private TextView mPayOrderPhone;
    private TextView mPayOrderAddress;
    private ShoppingCardListResponse mOrderConfirm;
    private PayCreateRequest mProductSpecification;
    private AddressResponse.ListBean mDataBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        initializeInjector();
        supportActionBar(mToolbar, true);
        mMiddleName.setText("确认支付");
        initView();
        initData();
    }

    @Override
    public void orderConfirmedSuccess(OrderConfirmedResponse response) {
       /* mResponse = response;
        PayMethodDialog payMethodDialog = PayMethodDialog.newInstance();
        payMethodDialog.setListener(this);
        DialogFactory.showDialogFragment(getSupportFragmentManager(), payMethodDialog, PayMethodDialog.TAG);*/
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

    @Override
    public void clickRechargeBtn(String payType) {
        /*if (mResponse != null) {
            switch (payType) {
                case PayConstant.PAY_TYPE_WX:
                    mPresenter.requestPayInfo(mResponse, PayConstant.PAY_TYPE_WX);
                    break;
                case PayConstant.PAY_TYPE_ZFB:
                    mPresenter.requestPayInfo(mResponse, PayConstant.PAY_TYPE_ZFB);
                    break;
            }
        }*/
    }

    @Override
    public void orderPayInfoSuccess(PayResponse response) {
        if (PayConstant.PAY_TYPE_WX.equals(String.valueOf(response.pay_ebcode))) {
            PayWXHelper.getInstance().pay(response.pay_order, this);
        } else {
            PayZFBHelper.getInstance().pay(response.biz_content, this);
        }
    }

    @Override
    public void orderPaySuccess() {
        /*PayAccessRequest request = new PayAccessRequest();
        List<PayAccessRequest.OrdersBean> list = new ArrayList<>();
        for (String s : mResponse.data) {
            PayAccessRequest.OrdersBean order = new PayAccessRequest.OrdersBean();
            order.orderId = s;
            list.add(order);
        }
        request.orders = list;
        mPresenter.updateOrderStatus(request);*/
    }

    @Override
    public void updateOrderStatusSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        this.finish();
    }

    private void initData() {

    }

    private void initView() {

        for (int i = 0; i < modules.length; i++) {
            mPayTabLayout.addTab(mPayTabLayout.newTab().setText(modules[i]));
        }
        ValueUtil.setIndicator(mPayTabLayout, 20, 20);
        mOrderConfirm = (ShoppingCardListResponse) getIntent().getSerializableExtra("ShoppingCardListResponse");
        mPayOrderList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new PayGoodsListAdapter(null, this, mImageLoaderHelper);
        mPayOrderList.setAdapter(mAdapter);
        if (mOrderConfirm != null) {
            mAdapter.setNewData(mOrderConfirm.list);
        }

        mAdapter.setOnItemClickListener((adapter, view, position) ->
                showToast(position + "")
        );

        RxView.clicks(mPayOrder).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> requestPay());

        mPayTabLayout.addOnTabSelectedListener(new TabCheckListener() {
            @Override
            public void onMyTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        mPayOrderAddressLayout.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        mPayOrderAddressLayout.setVisibility(View.GONE);
                        break;
                }
            }
        });
    }

    private void requestPay() {
      /*  if (TextUtils.isEmpty(mPayOrderName.getText()) && TextUtils.isEmpty(mPayOrderAddress.getText())) {
            showToast("请选择收获地址");
            return;
        }
        if (mResponse == null) {
            for (OrderConfirmedRequest request : mProductSpecification.orders) {
                request.address = mDataBean.getAddress() + mDataBean.getArea();
                request.phone = mDataBean.getMobile();
                request.userName = (String) mDataBean.getName();
            }

            mPresenter.requestOrderConfirmed(mProductSpecification);
        } else {
            PayMethodDialog payMethodDialog = PayMethodDialog.newInstance();
            payMethodDialog.setListener(this);
            DialogFactory.showDialogFragment(getSupportFragmentManager(), payMethodDialog, PayMethodDialog.TAG);
        }*/

    }

    private void addHeadView() {
      /*  View mHeadView = LayoutInflater.from(this).inflate(R.layout.head_pay_view, (ViewGroup) mPayOrderList.getParent(), false);
        mAdapter.addHeaderView(mHeadView);
        mPayOrderName = (TextView) mHeadView.findViewById(R.id.pay_order_name);
        mPayOrderPhone = (TextView) mHeadView.findViewById(R.id.pay_order_phone);
        mPayOrderAddress = (TextView) mHeadView.findViewById(R.id.pay_order_address);
        LinearLayout mAddressLinearLayout = (LinearLayout) mHeadView.findViewById(R.id.pay_order_address_layout);
        mAddressLinearLayout.setOnClickListener(v -> startActivityForResult(AddressActivity.getIntent(this, "payActivity"), 1));*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentConstant.ORDER_POSITION_TWO && resultCode == RESULT_OK) {
            if (data != null) {
                mDataBean = (AddressResponse.ListBean) data.getSerializableExtra("addressDataBean");
                if (mDataBean != null) {
                    mPayOrderPhone.setVisibility(View.VISIBLE);
                    mPayOrderPhone.setText(mDataBean.getMobile());
                    mPayOrderName.setText((String) mDataBean.getName());
                    mPayOrderAddress.setText(mDataBean.getAddress() + mDataBean.getArea());
                }
            }
        }

    }

    private void addFootView() {
      /*  View mFootView = LayoutInflater.from(this).inflate(R.layout.foot_pay_view, (ViewGroup) mPayOrderList.getParent(), false);
        mAdapter.addFooterView(mFootView);
        TextView mDispatchPrice = (TextView) mFootView.findViewById(R.id.pay_order_dispatch_price);
        TextView mFinalPrice = (TextView) mFootView.findViewById(R.id.pay_order_price);
        Integer allPrice = 0;
        Integer dispatchingPrice = 0;
        for (OrderConfirmedRequest request : mProductSpecification.orders) {
            for (OrderConfirmedRequest.ProductsBean product : request.products) {
                allPrice += product.price * Integer.valueOf(product.number);
            }
            for (OrderConfirmedRequest.AccountsBean account : request.accounts) {
                dispatchingPrice += Integer.valueOf(account.price);
            }
        }

        mDispatchPrice.setText(ValueUtil.formatAmount(dispatchingPrice));
        mFinalPrice.setText(ValueUtil.formatAmount(allPrice + dispatchingPrice));*/
    }

    private void initializeInjector() {
        DaggerPayActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .payActivityModule(new PayActivityModule(PayActivity.this, this))
                .build().inject(this);
    }
}
