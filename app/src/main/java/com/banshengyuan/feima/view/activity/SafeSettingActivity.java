package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/5.
 * WelcomeActivity
 */

public class SafeSettingActivity extends BaseActivity {


    @BindView(R.id.safe_change_password)
    TextView mSafeChangePassword;

    public static Intent getIntent(Context context) {
        return new Intent(context, SafeSettingActivity.class);
    }

    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_right_icon)
    ImageView mToolbarRightIcon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe_setting);
        ButterKnife.bind(this);
        supportActionBar(mToolbar, true);
        mMiddleName.setText("安全设置");
        initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {
        RxView.clicks(mSafeChangePassword).throttleFirst(1, TimeUnit.SECONDS).subscribe(o ->
                startActivity(SetNewPasswordActivity.getNewPasswordIntent(this)));
    }
}
