package com.dispatching.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dispatching.feima.R;
import com.dispatching.feima.dagger.component.DaggerPayActivityComponent;
import com.dispatching.feima.dagger.module.PayActivityModule;
import com.dispatching.feima.entity.AddressResponse;
import com.dispatching.feima.entity.IntentConstant;
import com.dispatching.feima.entity.MyPayOrderRequest;
import com.dispatching.feima.entity.OrderConfirmedRequest;
import com.dispatching.feima.entity.OrderConfirmedResponse;
import com.dispatching.feima.entity.PayConstant;
import com.dispatching.feima.entity.PayResponse;
import com.dispatching.feima.entity.SpecificationResponse;
import com.dispatching.feima.entity.UpdateOrderStatusResponse;
import com.dispatching.feima.help.DialogFactory;
import com.dispatching.feima.help.PayZFBHelper;
import com.dispatching.feima.help.WXPayHelp.PayWXHelper;
import com.dispatching.feima.utils.ValueUtil;
import com.dispatching.feima.view.PresenterControl.PayControl;
import com.dispatching.feima.view.adapter.PayGoodsListAdapter;
import com.dispatching.feima.view.fragment.PayMethodDialog;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/27.
 * MyOrderActivity
 */

public class PayActivity extends BaseActivity implements PayControl.PayView, PayMethodDialog.PayMethodClickListener {

    public static Intent getIntent(Context context, SpecificationResponse specificationResponse) {
        Intent intent = new Intent(context, PayActivity.class);
        intent.putExtra("specificationResponse", specificationResponse);
        return intent;
    }

    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.pay_order_list)
    RecyclerView mPayOrderList;
    @BindView(R.id.pay_order)
    Button mPayOrder;
    @Inject
    PayControl.PresenterPay mPresenter;
    private PayGoodsListAdapter mAdapter;
    private View mHeadView;
    private View mFootView;
    private List<MyPayOrderRequest> mList;
    private TextView mPayOrderId;
    private TextView mPayOrderName;
    private TextView mPayOrderPhone;
    private TextView mPayOrderAddress;
    private TextView mDispatchPrice;
    private LinearLayout mAddressLinearLayout;
    private LinearLayout mPayOrderIdLayout;
    private TextView mFinalPrice;
    private OrderConfirmedResponse mResponse;
    private SpecificationResponse mProductSpecification;
    private long mOrderId;
    private AddressResponse.DataBean mDataBean;

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
        mResponse = response;
        mOrderId = response.oid;
      /*  mPayOrderIdLayout.setVisibility(View.VISIBLE);
        mPayOrderId.setText(String.valueOf(mResponse.oid));*/
        PayMethodDialog payMethodDialog = PayMethodDialog.newInstance();
        payMethodDialog.setListener(this);
        DialogFactory.showDialogFragment(getSupportFragmentManager(), payMethodDialog, PayMethodDialog.TAG);
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
        if (mResponse != null) {
            switch (payType) {
                case PayConstant.PAY_TYPE_WX:
                    mPresenter.requestPayInfo(mOrderId, PayConstant.PAY_TYPE_WX);
                    break;
                case PayConstant.PAY_TYPE_ZFB:
                    mPresenter.requestPayInfo(mOrderId, PayConstant.PAY_TYPE_ZFB);
                    break;
            }
        }
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
        mPresenter.updateOrderStatus(String.valueOf(mOrderId));
    }

    @Override
    public void updateOrderStatusSuccess(UpdateOrderStatusResponse response) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        this.finish();
    }

    private void initData() {

    }

    private void initView() {
        mProductSpecification = (SpecificationResponse) getIntent().getSerializableExtra("specificationResponse");
        mList = new ArrayList<>();
        mPayOrderList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new PayGoodsListAdapter(null, this, mImageLoaderHelper);
        mPayOrderList.setAdapter(mAdapter);
        if (mProductSpecification != null) {
            addHeadView();
            addFootView();
            mAdapter.setNewData(mProductSpecification.products);
        }

        mAdapter.setOnItemClickListener((adapter, view, position) ->
                showToast(position + "")
        );

        RxView.clicks(mPayOrder).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> requestPay());
    }

    private void requestPay() {
        if (TextUtils.isEmpty(mPayOrderName.getText()) && TextUtils.isEmpty(mPayOrderAddress.getText())) {
            showToast("请选择收获地址");
            return;
        }
        if (mOrderId == 0) {
            OrderConfirmedRequest request = new OrderConfirmedRequest();
            request.address = mDataBean.address + mDataBean.area;
            request.phone = mDataBean.receiverPhone;
            request.userName = (String) mDataBean.receiverName;
            mPresenter.requestOrderConfirmed(request, mProductSpecification);
        } else {
            PayMethodDialog payMethodDialog = PayMethodDialog.newInstance();
            payMethodDialog.setListener(this);
            DialogFactory.showDialogFragment(getSupportFragmentManager(), payMethodDialog, PayMethodDialog.TAG);
        }

    }

    private void addHeadView() {
        mHeadView = LayoutInflater.from(this).inflate(R.layout.head_pay_view, (ViewGroup) mPayOrderList.getParent(), false);
        mAdapter.addHeaderView(mHeadView);
        mPayOrderId = (TextView) mHeadView.findViewById(R.id.pay_order_id);
        mPayOrderName = (TextView) mHeadView.findViewById(R.id.pay_order_name);
        mPayOrderPhone = (TextView) mHeadView.findViewById(R.id.pay_order_phone);
        mPayOrderAddress = (TextView) mHeadView.findViewById(R.id.pay_order_address);
        mAddressLinearLayout = (LinearLayout) mHeadView.findViewById(R.id.pay_order_address_layout);
        mPayOrderIdLayout = (LinearLayout) mHeadView.findViewById(R.id.pay_order_conf_id);
        mAddressLinearLayout.setOnClickListener(v -> startActivityForResult(AddressActivity.getIntent(this, "payActivity"), 1));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentConstant.ORDER_POSITION_TWO && resultCode == RESULT_OK) {
            if (data != null) {
                mDataBean = (AddressResponse.DataBean) data.getSerializableExtra("addressDataBean");
                if (mDataBean != null) {
                    mPayOrderPhone.setVisibility(View.VISIBLE);
                    mPayOrderPhone.setText(mDataBean.receiverPhone);
                    mPayOrderName.setText((String) mDataBean.receiverName);
                    mPayOrderAddress.setText(mDataBean.address + mDataBean.area);
                }
            }
        }

    }

    private void addFootView() {
        mFootView = LayoutInflater.from(this).inflate(R.layout.foot_pay_view, (ViewGroup) mPayOrderList.getParent(), false);
        mAdapter.addFooterView(mFootView);
        mDispatchPrice = (TextView) mFootView.findViewById(R.id.pay_order_dispatch_price);
        mFinalPrice = (TextView) mFootView.findViewById(R.id.pay_order_price);
        Integer allPrice = 0;
        for (SpecificationResponse.ProductsBean product : mProductSpecification.products) {
            allPrice += product.finalPrice*product.saleCount;
        }
        mFinalPrice.setText(ValueUtil.formatAmount(allPrice + 500));
    }

    private void initializeInjector() {
        DaggerPayActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .payActivityModule(new PayActivityModule(PayActivity.this, this))
                .build().inject(this);
    }
}
