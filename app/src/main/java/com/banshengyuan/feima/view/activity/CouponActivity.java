package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.CheckBox;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.MyCoupleResponse;
import com.banshengyuan.feima.view.adapter.ChoiceCouponAdapter;

import java.util.List;

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

    public static Intent getIntent(Context context, MyCoupleResponse mCoupleResponse, double price) {
        Intent intent = new Intent(context, CouponActivity.class);
        intent.putExtra("mCoupleResponse", mCoupleResponse);
        intent.putExtra("price", price);
        return intent;
    }

    private ChoiceCouponAdapter mAdapter;
    private MyCoupleResponse mCoupleResponse;
    private double mPrice;
    private MyCoupleResponse.ListBean mCheckData;

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
        mPrice = getIntent().getDoubleExtra("price", 0.0);
        mCouponCommonList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ChoiceCouponAdapter(null, this);
        mCouponCommonList.setAdapter(mAdapter);

        mCoupleResponse = (MyCoupleResponse) getIntent().getSerializableExtra("mCoupleResponse");
        if (mCoupleResponse != null && mCoupleResponse.getList() != null) {
            for (MyCoupleResponse.ListBean listBean : mCoupleResponse.getList()) {
                if (listBean.getType() == 2 && mPrice != 0) {
                    listBean.isVisiable = true;
                }else if(listBean.getType() == 1 && mPrice >= listBean.getStart_val()){
                    listBean.isVisiable = true;
                }
            }
            mAdapter.setNewData(mCoupleResponse.getList());
        }

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            MyCoupleResponse.ListBean bean = (MyCoupleResponse.ListBean) adapter.getItem(position);
            CheckBox checkBox = view.findViewById(R.id.adapter_couple_check);
            if (bean != null) {
                switch (view.getId()) {
                    case R.id.adapter_couple_check:
                        if (!checkBox.isChecked()) {
                            checkBox.setChecked(true);
                            return;
                        }

                        List<MyCoupleResponse.ListBean> list = adapter.getData();
                        for (int i = 0; i < list.size(); i++) {
                            if (i == position) {
                                list.get(i).isCheck = true;
                                mCheckData = list.get(i);
                                mAdapter.setData(i, mCheckData);
                            } else {
                                if (list.get(i).isCheck) {
                                    list.get(i).isCheck = false;
                                    mAdapter.setData(i, list.get(i));
                                }
                            }
                        }
                        if (mCheckData != null) {
                            Intent intent = new Intent();
                            intent.putExtra("mCheckData", mCheckData);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                        break;
                }
            }
        });
    }

}
