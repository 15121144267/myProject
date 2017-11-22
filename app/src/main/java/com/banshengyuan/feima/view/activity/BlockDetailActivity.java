package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerBlockDetailActivityComponent;
import com.banshengyuan.feima.dagger.module.BlockDetailActivityModule;
import com.banshengyuan.feima.entity.BlockDetailResponse;
import com.banshengyuan.feima.entity.BlockFairListResponse;
import com.banshengyuan.feima.entity.BlockHotListResponse;
import com.banshengyuan.feima.entity.BlockStoreListResponse;
import com.banshengyuan.feima.help.GlideLoader;
import com.banshengyuan.feima.listener.AppBarStateChangeListener;
import com.banshengyuan.feima.view.PresenterControl.BlockDetailControl;
import com.banshengyuan.feima.view.adapter.BlockDetailFairAdapter;
import com.banshengyuan.feima.view.adapter.BlockDetailShopAdapter;
import com.banshengyuan.feima.view.adapter.CommonItemAdapter;
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
    @BindView(R.id.block_detail_hot)
    RecyclerView mBlockDetailHot;
    @BindView(R.id.block_detail_fair)
    RecyclerView mBlockDetailFair;
    @BindView(R.id.block_detail_shop)
    RecyclerView mBlockDetailShop;
    @BindView(R.id.block_detail_name)
    TextView mBlockDetailName;
    @BindView(R.id.block_detail_summary)
    TextView mBlockDetailSummary;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.middle_name)
    TextView mMiddleName;


    public static Intent getIntent(Context context, Integer id) {
        Intent intent = new Intent(context, BlockDetailActivity.class);
        intent.putExtra("blockId", id);
        return intent;
    }

    @Inject
    BlockDetailControl.PresenterBlockDetail mPresenter;
    private CommonItemAdapter mCommonItemAdapter;
    private BlockDetailFairAdapter mBlockDetailFairAdapter;
    private BlockDetailShopAdapter mBlockDetailShopAdapter;
    private Integer mBlockId;
    private BlockDetailResponse.InfoBean mInfoBean;
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
        mBlockId = getIntent().getIntExtra("blockId", 0);
        mBlockDetailHot.setLayoutManager(new LinearLayoutManager(this));
        mBlockDetailFair.setLayoutManager(new LinearLayoutManager(this));
        mBlockDetailShop.setLayoutManager(new LinearLayoutManager(this));
        mCommonItemAdapter = new CommonItemAdapter(null, this, mImageLoaderHelper);
        mBlockDetailFairAdapter = new BlockDetailFairAdapter(null, this, mImageLoaderHelper);
        mBlockDetailShopAdapter = new BlockDetailShopAdapter(null, this, mImageLoaderHelper);
        mBlockDetailHot.setAdapter(mCommonItemAdapter);
        mBlockDetailFair.setAdapter(mBlockDetailFairAdapter);
        mBlockDetailShop.setAdapter(mBlockDetailShopAdapter);

        mCommonItemAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.adapter_fair_more:
                    startActivity(BlockActivity.getIntent(this, 0,mBlockId));
                    break;
            }
        });
        mBlockDetailFairAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.adapter_fair_more:
                    startActivity(BlockActivity.getIntent(this, 1,mBlockId));
                    break;
            }
        });
        mBlockDetailShopAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.adapter_fair_more:
                    startActivity(BlockActivity.getIntent(this, 2,mBlockId));
                    break;
            }
        });

        mToolbarRightIcon.setVisibility(View.VISIBLE);
        mBlockDetailBanner.isAutoPlay(false);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    //展开状态
                    mMiddleName.setVisibility(View.GONE);
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    mMiddleName.setVisibility(View.VISIBLE);
                    mMiddleName.setText(TextUtils.isEmpty(mInfoBean.name) ? "未知" : mInfoBean.name);
                } else {
                    //中间状态
                    mMiddleName.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void getBlockDetailSuccess(BlockDetailResponse response) {
        mInfoBean = response.info;
        if (mInfoBean != null) {
            mBlockDetailName.setText(TextUtils.isEmpty(mInfoBean.name) ? "未知" : mInfoBean.name);
            mBlockDetailSummary.setText(TextUtils.isEmpty(mInfoBean.summary) ? "未知" : mInfoBean.summary);
            List<String> list = mInfoBean.top_img;
            if (list != null && list.size() > 0) {
                mBlockDetailBanner.setImages(list).setImageLoader(new GlideLoader()).start();
            } else {
                mBlockDetailBanner.setBackground(ContextCompat.getDrawable(this, R.color.white));
            }
        }
    }

    @Override
    public void getBlockDetailFail() {
        mBlockDetailBanner.setBackground(ContextCompat.getDrawable(this, R.color.white));
    }

    @Override
    public void getHotListSuccess(BlockHotListResponse response) {
        List<BlockHotListResponse.ListBean> list = response.list;
        if (list != null && list.size() > 0) {
            List<BlockHotListResponse> listResponses = new ArrayList<>();
            listResponses.add(response);
            mCommonItemAdapter.setNewData(listResponses);
        } else {
            mBlockDetailHot.setVisibility(View.GONE);
        }
    }

    @Override
    public void getHotListFail() {
        mBlockDetailHot.setVisibility(View.GONE);
    }

    @Override
    public void getFairListSuccess(BlockFairListResponse response) {
        List<BlockFairListResponse.ListBean> list = response.list;
        if (list != null && list.size() > 0) {
            List<BlockFairListResponse> listResponses = new ArrayList<>();
            listResponses.add(response);
            mBlockDetailFairAdapter.setNewData(listResponses);
        } else {
            mBlockDetailFair.setVisibility(View.GONE);
        }
    }

    @Override
    public void getFairListFail() {
        mBlockDetailFair.setVisibility(View.GONE);
    }

    @Override
    public void getStoreListSuccess(BlockStoreListResponse response) {
        List<BlockStoreListResponse.ListBean> list = response.list;
        if (list != null && list.size() > 0) {
            List<BlockStoreListResponse> listResponses = new ArrayList<>();
            listResponses.add(response);
            mBlockDetailShopAdapter.setNewData(listResponses);
        } else {
            mBlockDetailShop.setVisibility(View.GONE);
        }
    }

    @Override
    public void getStoreListFail() {
        mBlockDetailShop.setVisibility(View.GONE);
    }

    private void initData() {

        //请求街区详情
        mPresenter.requestBlockDetail(mBlockId);
        //请求热闹列表
        mPresenter.requestHotList(mBlockId);
        //请求市集列表
        mPresenter.requestFairList(mBlockId);
        //请求店铺列表
        mPresenter.requestStoreList(mBlockId);

    }

    private void initializeInjector() {
        DaggerBlockDetailActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .blockDetailActivityModule(new BlockDetailActivityModule(BlockDetailActivity.this, this))
                .build().inject(this);
    }
}
