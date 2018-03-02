package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/5.
 * WelcomeActivity
 */

public class AboutActivity extends BaseActivity {

    @BindView(R.id.about_version)
    TextView mAboutVersion;

    public static Intent getIntent(Context context) {
        return new Intent(context, AboutActivity.class);
    }

    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        supportActionBar(mToolbar, true);
        mMiddleName.setText("关于我们");
        mAboutVersion.setText("版本: "+BuildConfig.VERSION_NAME+"");
    }

}
