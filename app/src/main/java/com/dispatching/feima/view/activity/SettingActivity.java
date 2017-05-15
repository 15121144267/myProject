package com.dispatching.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.dispatching.feima.BuildConfig;
import com.dispatching.feima.R;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by helei on 2017/4/27.
 * SettingActivity
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.setting_version)
    TextView mSettingVersion;
    @BindView(R.id.quite_login)
    Button mQuiteLogin;

    public static Intent getSettingIntent(Context context) {
        return new Intent(context, SettingActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        supportActionBar(mToolbar, true);
        mMiddleName.setText(R.string.user_setting_center);
        mSettingVersion.setText(BuildConfig.VERSION_NAME);
        RxView.clicks(mQuiteLogin).throttleFirst(1, TimeUnit.SECONDS).subscribe(v->quiteLogin());
    }

    private void quiteLogin() {

    }
}
