package com.dispatching.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dispatching.feima.R;
import com.dispatching.feima.dagger.component.DaggerPayActivityComponent;
import com.dispatching.feima.dagger.module.PayActivityModule;
import com.dispatching.feima.entity.MyPayOrderRequest;
import com.dispatching.feima.entity.OrderConfirmedRequest;
import com.dispatching.feima.entity.OrderConfirmedResponse;
import com.dispatching.feima.entity.PayConstant;
import com.dispatching.feima.entity.PayResponse;
import com.dispatching.feima.entity.ShopDetailResponse;
import com.dispatching.feima.entity.ShopListResponse;
import com.dispatching.feima.entity.SpecificationResponse;
import com.dispatching.feima.entity.UpdateOrderStatusResponse;
import com.dispatching.feima.help.DialogFactory;
import com.dispatching.feima.help.PayZFBHelper;
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

    public static Intent getIntent(Context context,SpecificationResponse.ProductsBean.ProductSpecificationBean productSpecification) {
        Intent intent =new Intent(context, PayActivity.class);
        intent.putExtra("productSpecification",productSpecification);
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
    private ShopListResponse.ListBean mShopInfo;
    private TextView PayOrderId;
    private TextView PayOrderName;
    private TextView PayOrderPhone;
    private TextView PayOrderAddress;
    private TextView mDispatchPrice;
    private TextView mfinalPrice;
    private ShopDetailResponse.ProductsBean mGoodInfo;
    private OrderConfirmedResponse mResponse;
    private SpecificationResponse.ProductsBean.ProductSpecificationBean mProductSpecification;
    private long mOrderId;
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
        PayOrderId.setText(String.valueOf(mResponse.oid));
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
        switch (payType) {
            case PayConstant.PAY_TYPE_WX:
                showToast("微信");
                break;
            case PayConstant.PAY_TYPE_ZFB:
                if(mResponse!=null){
                    mPresenter.requestPayInfo(mOrderId,PayConstant.PAY_TYPE_ZFB);
                }
                break;
        }
    }

    @Override
    public void orderPayInfoSuccess(PayResponse response) {
        if (PayConstant.PAY_TYPE_WX.equals(String.valueOf(response.pay_ebcode))){
//            PayWXHelper.getInstance().pay(response.biz_content, view);
        }else{
            PayZFBHelper.getInstance().pay(response.biz_content,this);
        }
    }

    @Override
    public void orderPaySuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        this.finish();
//       mPresenter.updateOrderStatus(mOrderId);
    }

    @Override
    public void updateOrderStatusSuccess(UpdateOrderStatusResponse response) {

    }

    private void initData() {
        mShopInfo = mBuProcessor.getShopInfo();
        mPresenter.requestOrderConfirmed(new OrderConfirmedRequest(),mProductSpecification);
    }

    private void initView() {
        mProductSpecification = (SpecificationResponse.ProductsBean.ProductSpecificationBean) getIntent().getSerializableExtra("productSpecification");
        mGoodInfo = mBuProcessor.getGoodsInfo();
        mList = new ArrayList<>();
        mPayOrderList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new PayGoodsListAdapter(null, this, mImageLoaderHelper);
        mPayOrderList.setAdapter(mAdapter);
        List<ShopDetailResponse.ProductsBean> list = new ArrayList<>();
        if (mGoodInfo != null) {
            list.add(mGoodInfo);
            addHeadView();
            addFootView();
            mAdapter.setNewData(list);
        }

        mAdapter.setOnItemClickListener((adapter, view, position) ->
                showToast(position + "")
        );
        RxView.clicks(mPayOrder).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> requestPay());
    }

    private void requestPay() {
        PayMethodDialog payMethodDialog = PayMethodDialog.newInstance();
        payMethodDialog.setListener(this);
        DialogFactory.showDialogFragment(getSupportFragmentManager(), payMethodDialog, PayMethodDialog.TAG);
    }


    private void addHeadView() {
        mHeadView = LayoutInflater.from(this).inflate(R.layout.head_pay_view, (ViewGroup) mPayOrderList.getParent(), false);
        mAdapter.addHeaderView(mHeadView);
        PayOrderId = (TextView) mHeadView.findViewById(R.id.pay_order_id);
        PayOrderName = (TextView) mHeadView.findViewById(R.id.pay_order_name);
        PayOrderPhone = (TextView) mHeadView.findViewById(R.id.pay_order_phone);
        PayOrderAddress = (TextView) mHeadView.findViewById(R.id.pay_order_address);


    }

    private void addFootView() {
        mFootView = LayoutInflater.from(this).inflate(R.layout.foot_pay_view, (ViewGroup) mPayOrderList.getParent(), false);
        mAdapter.addFooterView(mFootView);
        mDispatchPrice = (TextView) mFootView.findViewById(R.id.pay_order_dispatch_price);
        mfinalPrice = (TextView) mFootView.findViewById(R.id.pay_order_price);
        mfinalPrice.setText(ValueUtil.formatAmount(mGoodInfo.finalPrice + 500));
    }

    private void initializeInjector() {
        DaggerPayActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .payActivityModule(new PayActivityModule(PayActivity.this, this))
                .build().inject(this);
    }
}
