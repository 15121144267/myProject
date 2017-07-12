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
import com.dispatching.feima.entity.MyOrdersResponse;
import com.dispatching.feima.entity.PayConstant;
import com.dispatching.feima.help.DialogFactory;
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

    public static Intent getIntent(Context context) {
        return new Intent(context, PayActivity.class);
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
    private List<MyOrdersResponse> mList;

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

    private void initData() {
        for (int i = 0; i < 2; i++) {
            MyOrdersResponse response = new MyOrdersResponse();
            response.productcount = i;
            response.productDes1 = "2017年最新款,最新欧美风 V领模式巴拉巴拉";
            response.productDes2 = "尺码：M 颜色：白色";
            response.productName = "文艺复古连衣裙";
            response.productPrice = 250;
            response.shopName = "淘宝店";
            mList.add(response);
        }
        mAdapter.setNewData(mList);
        if (mList.size() > 0) {
            addHeadView();
            addFootView();
        }

    }

    private void initView() {
        mList = new ArrayList<>();
        mPayOrderList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new PayGoodsListAdapter(null, this, mImageLoaderHelper);
        mPayOrderList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) ->
                showToast(position + "")
        );
        RxView.clicks(mPayOrder).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> requestPay());
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
    public void clickRechargeBtn(String payType) {
        switch (payType){
            case PayConstant.PAY_TYPE_WX:
                showToast("微信");
                break;
            case PayConstant.PAY_TYPE_ZFB:
                showToast("支付宝");
                break;
        }
    }

    private void requestPay() {
        PayMethodDialog payMethodDialog = PayMethodDialog.newInstance();
        payMethodDialog.setListener(this);
        DialogFactory.showDialogFragment(getSupportFragmentManager(), payMethodDialog, PayMethodDialog.TAG);
    }


    private void addHeadView() {
        mHeadView = LayoutInflater.from(this).inflate(R.layout.head_pay_view, (ViewGroup) mPayOrderList.getParent(), false);
        mAdapter.addHeaderView(mHeadView);
    }

    private void addFootView() {
        mFootView = LayoutInflater.from(this).inflate(R.layout.foot_pay_view, (ViewGroup) mPayOrderList.getParent(), false);
        mAdapter.addFooterView(mFootView);
    }

    private void initializeInjector() {
        DaggerPayActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .payActivityModule(new PayActivityModule(PayActivity.this, this))
                .build().inject(this);
    }
}
