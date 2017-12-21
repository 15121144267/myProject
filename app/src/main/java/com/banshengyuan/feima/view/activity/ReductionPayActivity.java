package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerReductionPayActivityComponent;
import com.banshengyuan.feima.dagger.module.ReductionPayActivityModule;
import com.banshengyuan.feima.entity.BroConstant;
import com.banshengyuan.feima.entity.MyCoupleResponse;
import com.banshengyuan.feima.entity.OrderConfirmedResponse;
import com.banshengyuan.feima.entity.PayConstant;
import com.banshengyuan.feima.entity.PayResponse;
import com.banshengyuan.feima.help.DialogFactory;
import com.banshengyuan.feima.help.PayZFBHelper;
import com.banshengyuan.feima.help.WXPayHelp.PayWXHelper;
import com.banshengyuan.feima.listener.MyTextWatchListener;
import com.banshengyuan.feima.utils.ValueUtil;
import com.banshengyuan.feima.view.PresenterControl.ReductionPayControl;
import com.banshengyuan.feima.view.fragment.PayMethodDialog;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/5.
 * WelcomeActivity
 */

public class ReductionPayActivity extends BaseActivity implements ReductionPayControl.ReductionPayView, PayMethodDialog.PayMethodClickListener {

    public static Intent getIntent(Context context, Integer shopId) {
        Intent intent = new Intent(context, ReductionPayActivity.class);
        intent.putExtra("shopId", shopId);
        return intent;
    }

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.activity_reduction_all_price)
    EditText mActivityReductionAllPrice;
    @BindView(R.id.activity_reduction_reduce_price)
    EditText mActivityReductionReducePrice;
    @BindView(R.id.activity_reduction_coupon_size)
    TextView mActivityReductionCouponSize;
    @BindView(R.id.activity_reduction_final_price)
    TextView mActivityReductionFinalPrice;
    @BindView(R.id.activity_reduction_pay)
    Button mActivityReductionPay;

    @Inject
    ReductionPayControl.PresenterReductionPay mPresenter;
    private Integer mShopId;
    private MyCoupleResponse mResponse;
    private double mAllPrice;
    private double mNotCutPrice;
    private double mFinalPrice;
    private double mCouponPrice;
    private OrderConfirmedResponse mOrderConfirmedResponse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reduction_pay);
        initializeInjector();
        ButterKnife.bind(this);
        supportActionBar(mToolbar, true);
        initView();
        initData();
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
    public void getPayRequestSuccess(OrderConfirmedResponse response) {
        mOrderConfirmedResponse = response;
        mActivityReductionCouponSize.setEnabled(false);
        mActivityReductionAllPrice.setEnabled(false);
        mActivityReductionReducePrice.setEnabled(false);
        showDialog();
    }

    @Override
    public void clickRechargeBtn(Integer payType) {
        if (mOrderConfirmedResponse != null) {
            switch (payType) {
                case 1:
                    mPresenter.requestPayInfo(mOrderConfirmedResponse, payType, 3);
                    break;
                case 2:
                    mPresenter.requestPayInfo(mOrderConfirmedResponse, payType, 3);
                    break;
            }
        }
    }

    @Override
    public void orderPaySuccess() {
        finish();
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
           finish();
        }
    }

    @Override
    public void getCouponListRequestSuccess(MyCoupleResponse response) {
        if (response.getList() != null) {
            if (response.getList().size() > 0) {
                mActivityReductionCouponSize.setText("优惠券数量(" + response.getList().size() + ")");
                mActivityReductionCouponSize.setEnabled(true);
                mResponse = response;
            } else {
                mActivityReductionCouponSize.setText("无可用");
                mActivityReductionCouponSize.setEnabled(false);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            MyCoupleResponse.ListBean mCheckData = (MyCoupleResponse.ListBean) data.getSerializableExtra("mCheckData");
            if (mCheckData != null) {
                mActivityReductionCouponSize.setText(mCheckData.getName());
                countFinalPrice(mCheckData);
            }
        }
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

    private void initData() {
        mPresenter.requestCouponList(mShopId + "", "1");
    }

    private void initView() {
        mMiddleName.setText("优惠买单");
        mShopId = getIntent().getIntExtra("shopId", 0);
        RxView.clicks(mActivityReductionCouponSize).throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> {
            double price = mAllPrice - mNotCutPrice;
            if (price < 0) {
                showToast("请输入正确金额");
                return;
            }
            if (price != 0) {
                mActivityReductionReducePrice.setEnabled(false);
                mActivityReductionAllPrice.setEnabled(false);
            }
            startActivityForResult(CouponActivity.getIntent(this, mResponse, price), 1);
        });

        mActivityReductionAllPrice.addTextChangedListener(new MyTextWatchListener() {
            @Override
            public void onMyTextChanged(CharSequence s) {
                if (!TextUtils.isEmpty(s)) {
                    mAllPrice = Double.parseDouble(s.toString());
                } else {
                    mAllPrice = 0;
                }
                if (!(mAllPrice >= mNotCutPrice)) {
                    mActivityReductionPay.setEnabled(false);
                }
                countPrice();
            }
        });

        mActivityReductionReducePrice.addTextChangedListener(new MyTextWatchListener() {
            @Override
            public void onMyTextChanged(CharSequence s) {
                if (!TextUtils.isEmpty(s)) {
                    mNotCutPrice = Double.parseDouble(s.toString());
                } else {
                    mNotCutPrice = 0;
                }
                if (mAllPrice == 0) {
                    showToast("请输入消费总额");
                    mActivityReductionPay.setEnabled(false);
                    return;
                }

                if (!(mAllPrice >= mNotCutPrice)) {
                    showToast("不参与优惠金额不能大于消费总额");
                    mActivityReductionPay.setEnabled(false);
                } else {
                    mActivityReductionPay.setEnabled(true);
                }
                countPrice();
            }
        });

        RxView.clicks(mActivityReductionPay).throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> requestPay());
    }

    private void showDialog() {
        PayMethodDialog payMethodDialog = PayMethodDialog.newInstance();
        payMethodDialog.setListener(this);
        DialogFactory.showDialogFragment(getSupportFragmentManager(), payMethodDialog, PayMethodDialog.TAG);
    }

    private void requestPay() {
        if (TextUtils.isEmpty(mActivityReductionAllPrice.getText())) {
            showToast("请输入金额");
            return;
        }
        if (mOrderConfirmedResponse == null) {
            mPresenter.requestPay(mShopId + "", mActivityReductionAllPrice.getText().toString(), mCouponPrice + "", mFinalPrice + "");
        } else {
            showDialog();
        }

    }

    private void countPrice() {
        mCouponPrice = 0;
        mFinalPrice = (mAllPrice - mNotCutPrice - mCouponPrice) + mNotCutPrice;
        mActivityReductionFinalPrice.setText("￥" + ValueUtil.formatAmount3(mFinalPrice) + "");
    }

    private void countFinalPrice(MyCoupleResponse.ListBean mCheckData) {
        if (mCheckData.getType() == 1) {
            mCouponPrice = mCheckData.getEnd_val();
            mFinalPrice = (mAllPrice - mNotCutPrice - mCheckData.getEnd_val()) + mNotCutPrice;
        } else {
            mCouponPrice = (mAllPrice - mNotCutPrice) * mCheckData.getEnd_val();
            mFinalPrice = ((mAllPrice - mNotCutPrice) * mCheckData.getEnd_val()) + mNotCutPrice;
        }
        mActivityReductionFinalPrice.setText("￥" + ValueUtil.formatAmount3(mFinalPrice) + "");
        mActivityReductionCouponSize.setText("￥" + ValueUtil.formatAmount3(mCouponPrice) + "");
    }

    private void initializeInjector() {
        DaggerReductionPayActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .reductionPayActivityModule(new ReductionPayActivityModule(ReductionPayActivity.this, this))
                .build().inject(this);
    }
}
