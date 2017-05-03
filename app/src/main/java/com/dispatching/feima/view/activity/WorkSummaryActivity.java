package com.dispatching.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.dispatching.feima.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by helei on 2017/4/27.
 */

public class WorkSummaryActivity extends BaseActivity {
    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public static Intent getSummaryIntent(Context context) {
        Intent intent = new Intent(context, WorkSummaryActivity.class);
        return intent;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        ButterKnife.bind(this);
        supportActionBar(mToolbar,true);
        mMiddleName.setText(R.string.user_work_res);
    }
}
