package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerReductionPayActivityComponent;
import com.banshengyuan.feima.dagger.module.ReductionPayActivityModule;
import com.banshengyuan.feima.entity.MyCoupleResponse;
import com.banshengyuan.feima.entity.OrderConfirmedResponse;
import com.banshengyuan.feima.listener.MyTextWatchListener;
import com.banshengyuan.feima.utils.ValueUtil;
import com.banshengyuan.feima.view.PresenterControl.ReductionPayControl;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/5.
 * WelcomeActivity
 */

public class ReductionPayActivity extends BaseActivity implements ReductionPayControl.ReductionPayView {

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
    private double min = 0;
    private double max = 0;

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
    public void getPayRequestSuccess(OrderConfirmedResponse response) {
        if (!TextUtils.isEmpty(response.order_sn)) {
            startActivity(FinalPayActivity.getIntent(this, response.order_sn, 3));
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
            } else if (price == 0) {
                if (mAllPrice != 0) {
                    price = mAllPrice;
                }
            }
            if (price != 0) {
                mActivityReductionReducePrice.setEnabled(false);
                mActivityReductionAllPrice.setEnabled(false);
            }
            startActivityForResult(CouponActivity.getIntent(this, mResponse, price), 1);
        });

        mActivityReductionAllPrice.addTextChangedListener(new MyTextWatchListener() {
            @Override
            public void onMyTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    mAllPrice = Double.parseDouble(s.toString());
                } else {
                    mAllPrice = 0;
                }
                if (!(mAllPrice >= mNotCutPrice)) {
                    mActivityReductionPay.setEnabled(false);
                }
                max = mAllPrice;
                mActivityReductionReducePrice.setFilters(new InputFilter[]{new InputFilter.LengthFilter(s.toString().length())});
                countPrice();
            }
        });

        mActivityReductionReducePrice.addTextChangedListener(new MyTextWatchListener() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
            }

            @Override
            public void onMyTextChanged(CharSequence s, int start, int before, int count) {
                if (mAllPrice == 0) {
                    showToast("请输入消费总额");
                    mActivityReductionPay.setEnabled(false);
                    return;
                }

                if (start >= 0) {//从一输入就开始判断，
                    if (min != -1 && max != -1) {
                        try {
                            double num = Double.parseDouble(s.toString());
                            if (num > max) {
                                s = ValueUtil.getStringOutE(String.valueOf(max));//如果大于max，则内容为max
                                mActivityReductionReducePrice.setText(s);
                                mActivityReductionReducePrice.setSelection(mActivityReductionReducePrice.getText().length());
                                showToast("金额不能超过消费总额");
                            } else if (num < min) {
                                s = String.valueOf(min);//如果小于min,则内容为min
                            }
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                }

                if (!TextUtils.isEmpty(s)) {
                    mNotCutPrice = Double.parseDouble(s.toString());
                } else {
                    mNotCutPrice = 0;
                }
                countPrice();
            }
        });

        RxView.clicks(mActivityReductionPay).throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> requestPay());
    }

    private void requestPay() {
        if (TextUtils.isEmpty(mActivityReductionAllPrice.getText())) {
            showToast("请输入金额");
            return;
        }
        mPresenter.requestPay(mShopId + "", mActivityReductionAllPrice.getText().toString(), mCouponPrice + "", mFinalPrice + "");

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
            if (mAllPrice - mNotCutPrice == 0 && mAllPrice != 0) {
                mCouponPrice = (mAllPrice) * mCheckData.getEnd_val();
                mFinalPrice = ((mAllPrice) * mCheckData.getEnd_val());
            } else {
                mCouponPrice = (mAllPrice - mNotCutPrice) * mCheckData.getEnd_val();
                mFinalPrice = ((mAllPrice - mNotCutPrice) * mCheckData.getEnd_val()) + mNotCutPrice;
            }

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
