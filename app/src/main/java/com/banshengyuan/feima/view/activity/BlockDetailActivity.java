package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerBlockDetailActivityComponent;
import com.banshengyuan.feima.dagger.module.BlockDetailActivityModule;
import com.banshengyuan.feima.help.GlideLoader;
import com.banshengyuan.feima.view.PresenterControl.BlockDetailControl;
import com.banshengyuan.feima.view.adapter.MyFragmentAdapter;
import com.banshengyuan.feima.view.fragment.BlockDetailFragment;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/28.
 * AddAddressActivity
 */

public class BlockDetailActivity extends BaseActivity implements BlockDetailControl.BlockDetailView {

    @BindView(R.id.block_detail_banner)
    Banner mBlockDetailBanner;
    @BindView(R.id.toolbar_right_icon)
    ImageView mToolbarRightIcon;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.block_detail_view_pager)
    ViewPager mBlockDetailViewPager;


    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, BlockDetailActivity.class);
        return intent;
    }

    @Inject
    BlockDetailControl.PresenterBlockDetail mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_detail);
        ButterKnife.bind(this);
        initializeInjector();
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

    private void initView() {

        List<Fragment> fragments = new ArrayList<>();
        BlockDetailFragment fragment = BlockDetailFragment.newInstance();
        fragments.add(fragment);
        MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager(), fragments);
        mBlockDetailViewPager.setAdapter(adapter);

        mToolbarRightIcon.setVisibility(View.VISIBLE);
    }

    private void initData() {
        List<Integer> mImageList = new ArrayList<>();
        mImageList.add(R.mipmap.header);
        mImageList.add(R.mipmap.header);
        mImageList.add(R.mipmap.header);
        mBlockDetailBanner.isAutoPlay(false);
        mBlockDetailBanner.setImages(mImageList).setImageLoader(new GlideLoader()).start();
    }

    private void initializeInjector() {
        DaggerBlockDetailActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .blockDetailActivityModule(new BlockDetailActivityModule(BlockDetailActivity.this, this))
                .build().inject(this);
    }
}
