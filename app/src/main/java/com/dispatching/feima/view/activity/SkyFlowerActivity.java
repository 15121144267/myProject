package com.dispatching.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dispatching.feima.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/5.
 * WelcomeActivity
 */

public class SkyFlowerActivity extends BaseActivity {


    public static Intent getIntent(Context context) {
        return new Intent(context, SkyFlowerActivity.class);
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
        setContentView(R.layout.activity_sky_flower);
        ButterKnife.bind(this);
        supportActionBar(mToolbar, true);
        mToolbarRightIcon.setVisibility(View.VISIBLE);
        mMiddleName.setText("空中花园");
        initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {

    }
}
