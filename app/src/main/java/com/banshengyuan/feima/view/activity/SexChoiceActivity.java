package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by helei on 2017/4/27.
 * PersonCenterActivity
 */

public class SexChoiceActivity extends BaseActivity {


    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.person_sex_man_choice)
    ImageView mPersonSexManChoice;
    @BindView(R.id.person_sex_man_layout)
    LinearLayout mPersonSexManLayout;
    @BindView(R.id.person_sex_women_choice)
    ImageView mPersonSexWomenChoice;
    @BindView(R.id.person_sex_women_layout)
    LinearLayout mPersonSexWomenLayout;

    public static Intent getIntent(Context context, String sex) {
        Intent intent = new Intent(context, SexChoiceActivity.class);
        intent.putExtra("sex", sex);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sex_choice);
        ButterKnife.bind(this);
        supportActionBar(mToolbar, true);
        mMiddleName.setText("性别修改");
        parseIntent();
        initView();
    }

    private void parseIntent() {
        String sex = getIntent().getStringExtra("sex");
        switch (sex) {
            case "男":
                mPersonSexManChoice.setVisibility(View.VISIBLE);
                mPersonSexWomenChoice.setVisibility(View.GONE);
                break;
            case "女":
                mPersonSexManChoice.setVisibility(View.GONE);
                mPersonSexWomenChoice.setVisibility(View.VISIBLE);
                break;
            default:
                mPersonSexManChoice.setVisibility(View.GONE);
                mPersonSexWomenChoice.setVisibility(View.GONE);
        }
    }

    private void initView() {
        RxView.clicks(mPersonSexManLayout).subscribe(v -> choiceSex(true));
        RxView.clicks(mPersonSexWomenLayout).subscribe(v -> choiceSex(false));
    }

    private void choiceSex(boolean isManSex) {
        Intent intent = new Intent();
        if (isManSex) {
            mPersonSexManChoice.setVisibility(View.VISIBLE);
            mPersonSexWomenChoice.setVisibility(View.GONE);
            intent.putExtra("sex","男");
        } else {
            mPersonSexManChoice.setVisibility(View.GONE);
            mPersonSexWomenChoice.setVisibility(View.VISIBLE);
            intent.putExtra("sex","女");
        }
        setResult(RESULT_OK,intent);
        finish();
    }


}
