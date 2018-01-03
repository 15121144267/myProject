package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerFinalPayActivityComponent;
import com.banshengyuan.feima.dagger.module.FinalPayActivityModule;
import com.banshengyuan.feima.entity.BroConstant;
import com.banshengyuan.feima.entity.PayConstant;
import com.banshengyuan.feima.entity.PayResponse;
import com.banshengyuan.feima.help.PayZFBHelper;
import com.banshengyuan.feima.help.WXPayHelp.PayWXHelper;
import com.banshengyuan.feima.view.PresenterControl.FinalPayControl;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/5.
 * WelcomeActivity
 */

public class FinalPayActivity extends BaseActivity implements FinalPayControl.FinalPayView, RadioGroup.OnCheckedChangeListener {


    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.final_pay_pay)
    TextView mFinalPayPay;
    @BindView(R.id.final_pay_zfb)
    RadioButton mFinalPayZfb;
    @BindView(R.id.final_pay_wx)
    RadioButton mFinalPayWx;
    @BindView(R.id.pay_choice_group)
    RadioGroup mPayChoiceGroup;
    private String mActivityType = null;

    public static Intent getIntent(Context context, String order_sn, Integer type) {
        Intent intent = new Intent(context, FinalPayActivity.class);
        intent.putExtra("order_sn", order_sn);
        intent.putExtra("order_type", type);
        return intent;
    }

    public static Intent getIntent(Context context, String order_sn, Integer type, String activityType) {
        Intent intent = new Intent(context, FinalPayActivity.class);
        intent.putExtra("order_sn", order_sn);
        intent.putExtra("order_type", type);
        intent.putExtra("activityType", activityType);
        return intent;
    }

    @Inject
    FinalPayControl.PresenterFinalPay mPresenter;

    private Integer payType = 0;
    private String mOrderSn;
    private Integer mOrderType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_pay);
        ButterKnife.bind(this);
        initializeInjector();
        supportActionBar(mToolbar, true);
        mMiddleName.setText("支付");
        initView();
    }

    @Override
    public void orderPaySuccess() {
        Intent i = getSingTaskMainIntent();
        i.putExtra("codeFlag", 11);
        startActivity(i);
    }

    @Override
    void addFilter() {
        super.addFilter();
        mFilter.addAction(BroConstant.LOCAL_BROADCAST_WX_PAY_SUCCESS);
    }

    //微信支付成功回调
    @Override
    void onReceivePro(Context context, Intent intent) {
        super.onReceivePro(context, intent);
        if (intent.getAction().equals(BroConstant.LOCAL_BROADCAST_WX_PAY_SUCCESS)) {
            if (mActivityType != null) {
                if (mActivityType.equals("OrderFragment")) {
                    LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(BroConstant.ORDER_TO_PAY_OrderFragment));
                } else if (mActivityType.equals("OrderDetailActivity")) {
                    LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(BroConstant.ORDER_TO_PAY_OrderDetailActivity));
                }
                finish();
            } else {
                Intent i = getSingTaskMainIntent();
                i.putExtra("codeFlag", 11);
                startActivity(i);
            }
        }
    }

    @Override
    public void orderPayInfoSuccess(PayResponse response) {
        if (PayConstant.PAY_TYPE_WX.equals(response.pay_ebcode + "")) {
            PayWXHelper.getInstance().pay(response.pay_order, this);
        } else {
            PayZFBHelper.getInstance().pay(response.biz_content, this);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.final_pay_zfb:
                payType = 1;
                break;
            case R.id.final_pay_wx:
                payType = 2;
                break;
        }
    }

    private void initView() {
        mActivityType = getIntent().getStringExtra("activityType");
        mOrderSn = getIntent().getStringExtra("order_sn");
        mOrderType = getIntent().getIntExtra("order_type", 0);
        mPayChoiceGroup.setOnCheckedChangeListener(this);
        RxView.clicks(mFinalPayPay).throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> {
            if (payType != 0) {
                mPresenter.requestPayInfo(mOrderSn, payType, mOrderType);
            } else {
                showToast("请选择支付方式");
            }
        });

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

    private void initializeInjector() {
        DaggerFinalPayActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .finalPayActivityModule(new FinalPayActivityModule(FinalPayActivity.this, this))
                .build().inject(this);
    }
}
