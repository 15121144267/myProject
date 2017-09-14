package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.utils.ValueUtil;
import com.banshengyuan.feima.view.customview.timepickview.ClearEditText2;
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
    @BindView(R.id.change_name)
    ClearEditText2 mChangeName;

    public static Intent getIntent(Context context,String name) {
        Intent intent = new Intent(context, PersonNameActivity.class);
        intent.putExtra("name",name);
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
        String name = getIntent().getStringExtra("name");
        if(!TextUtils.isEmpty(name)){
            mChangeName.setEditText(name);
        }
        mToolbarRightText.setVisibility(View.VISIBLE);
        RxView.clicks(mToolbarRightText).subscribe(v -> requestSure());
    }

    private void requestSure() {

        String name = mChangeName.getEditText();
        if(ValueUtil.checkSpecialString1(name)){
            Intent intent = new Intent();
            intent.putExtra("name",name);
            setResult(RESULT_OK,intent);
            finish();
        }else {
            showBaseToast("不符合");
        }
    }


}
