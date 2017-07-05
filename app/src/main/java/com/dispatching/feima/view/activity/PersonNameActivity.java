package com.dispatching.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.dispatching.feima.R;
import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by helei on 2017/4/27.
 * PersonCenterActivity
 */

public class PersonNameActivity extends BaseActivity {


    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_right_text)
    TextView mToolbarRightText;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, PersonNameActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_name);
        ButterKnife.bind(this);
        supportActionBar(mToolbar, true);
        mMiddleName.setText("修改昵称");
        initView();
    }

    private void initView() {
        mToolbarRightText.setVisibility(View.VISIBLE);
        RxView.clicks(mToolbarRightText).subscribe(v->requestSure());
    }

    private void requestSure() {

    }


}
