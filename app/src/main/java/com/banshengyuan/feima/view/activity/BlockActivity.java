package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerBlockActivityComponent;
import com.banshengyuan.feima.dagger.module.BlockActivityModule;
import com.banshengyuan.feima.utils.ValueUtil;
import com.banshengyuan.feima.view.PresenterControl.BlockControl;
import com.banshengyuan.feima.view.adapter.MyOrderFragmentAdapter;
import com.banshengyuan.feima.view.fragment.BlockFairFragment;
import com.banshengyuan.feima.view.fragment.BlockHotFragment;
import com.banshengyuan.feima.view.fragment.BlockShopFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/5.
 * WelcomeActivity
 */

public class BlockActivity extends BaseActivity implements BlockControl.BlockView {


    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.block_tab_layout)
    TabLayout mBlockTabLayout;
    @BindView(R.id.block_view_pager)
    ViewPager mBlockViewPager;

    public static Intent getIntent(Context context, Integer position) {
        Intent intent = new Intent(context, BlockActivity.class);
        intent.putExtra("tabPosition", position);
        return intent;
    }


    @Inject
    BlockControl.PresenterBlock mPresenter;
    private final String[] modules = {"热门", "市集", "商家"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block);
        initializeInjector();
        ButterKnife.bind(this);
        supportActionBar(mToolbar, true);
        initView();
        initData();
    }

    @Override
    public void showLoading(String msg) {
        showDialogLoading(msg);
    }

    @Override
    public void dismissLoading() {
        dismissDialogLoading();
    }

    @Override
    public void showToast(String message) {
        showBaseToast(message);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    private void initData() {

    }

    private void initView() {
        Integer position = getIntent().getIntExtra("tabPosition", 3);
        mMiddleName.setText("半生缘街区");
        List<Fragment> mFragments = new ArrayList<>();
        mFragments.add(BlockHotFragment.newInstance());
        mFragments.add(BlockFairFragment.newInstance());
        mFragments.add(BlockShopFragment.newInstance());
        MyOrderFragmentAdapter adapter = new MyOrderFragmentAdapter(getSupportFragmentManager(), mFragments, modules);
        mBlockViewPager.setOffscreenPageLimit(mFragments.size() - 1);
        mBlockViewPager.setAdapter(adapter);
        mBlockTabLayout.setupWithViewPager(mBlockViewPager);
        ValueUtil.setIndicator(mBlockTabLayout, 40, 40);
        TabLayout.Tab tab = mBlockTabLayout.getTabAt(position);
        if (tab != null) {
            mBlockViewPager.setCurrentItem(position);
            tab.select();
        }
    }

    private void initializeInjector() {
        DaggerBlockActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .blockActivityModule(new BlockActivityModule(BlockActivity.this, this))
                .build().inject(this);
    }
}
