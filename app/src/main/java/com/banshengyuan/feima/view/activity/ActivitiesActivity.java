package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.view.adapter.ActivitiesAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/5.
 * WelcomeActivity
 */

public class ActivitiesActivity extends BaseActivity {
    public static Intent getActivitiesIntent(Context context) {
        return new Intent(context, ActivitiesActivity.class);
    }

    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.activities_recycle_view)
    RecyclerView mActivityRecycleView;
    private List<Drawable> mList;
    private ActivitiesAdapter mAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);
        ButterKnife.bind(this);
        supportActionBar(mToolbar, true);
        mMiddleName.setText("商家");
        initView();
        initData();
    }

    private void initData() {
        Drawable[] productDrawable = {
                ContextCompat.getDrawable(this, R.mipmap.activties_first),
                ContextCompat.getDrawable(this, R.mipmap.activities_second),
                ContextCompat.getDrawable(this, R.mipmap.activties_third),
        };
        Collections.addAll(mList,productDrawable);
        mAdapter.setNewData(mList);
    }

    private void initView() {
        mList = new ArrayList<>();
        mActivityRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ActivitiesAdapter(null,this);
        mActivityRecycleView.setAdapter(mAdapter);

    }
}
