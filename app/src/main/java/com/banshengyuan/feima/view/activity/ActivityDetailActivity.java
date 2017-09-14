package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.banshengyuan.feima.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/5.
 * WelcomeActivity
 */

public class ActivityDetailActivity extends BaseActivity {

    public static Intent getActivityDetailIntent(Context context, Integer position) {
        Intent intent = new Intent(context, ActivityDetailActivity.class);
        intent.putExtra("detail_pic", position);
        return intent;
    }

    @BindView(R.id.activity_activities)
    ImageView mActivityActivities;
    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private Integer mPosition;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        supportActionBar(mToolbar, true);
        mMiddleName.setText("活动详情");
        initView();
        initData();
    }

    private void initData() {
        switch (mPosition) {
            case 1:
                mActivityActivities.setBackgroundResource(R.mipmap.activties_first_detail);
                break;
            case 2:
                mActivityActivities.setBackgroundResource(R.mipmap.activities_second_detail);
                break;
            case 3:
                mActivityActivities.setBackgroundResource(R.mipmap.activities_third_detail);
                break;
        }
    }

    private void initView() {
        mPosition = getIntent().getIntExtra("detail_pic",0);
    }
}
