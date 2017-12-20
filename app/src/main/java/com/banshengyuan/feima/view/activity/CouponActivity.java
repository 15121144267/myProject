package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.MyCoupleResponse;
import com.banshengyuan.feima.view.adapter.ChoiceCouponAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by helei on 2017/4/27.
 * PersonCenterActivity
 */

public class CouponActivity extends BaseActivity {


    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.coupon_common_list)
    RecyclerView mCouponCommonList;

    public static Intent getIntent(Context context, MyCoupleResponse mCoupleResponse,double price) {
        Intent intent = new Intent(context, CouponActivity.class);
        intent.putExtra("mCoupleResponse", mCoupleResponse);
        intent.putExtra("price", price);
        return intent;
    }

    private ChoiceCouponAdapter mAdapter;
    private MyCoupleResponse mCoupleResponse;
    private double mPrice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);
        ButterKnife.bind(this);
        supportActionBar(mToolbar, true);
        mMiddleName.setText("使用优惠券");
        initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {
        mPrice = getIntent().getDoubleExtra("price",0.0);
        mCouponCommonList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ChoiceCouponAdapter(null, this);
        mCouponCommonList.setAdapter(mAdapter);

        mCoupleResponse = (MyCoupleResponse) getIntent().getSerializableExtra("mCoupleResponse");
        if (mCoupleResponse != null && mCoupleResponse.getList() != null) {
            mAdapter.setNewData(mCoupleResponse.getList());
        }
    }

}
