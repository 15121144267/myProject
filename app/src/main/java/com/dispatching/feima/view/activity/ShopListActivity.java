package com.dispatching.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.dispatching.feima.R;
import com.dispatching.feima.dagger.component.DaggerShopListActivityComponent;
import com.dispatching.feima.dagger.module.ShopListActivityModule;
import com.dispatching.feima.entity.ShopListResponse;
import com.dispatching.feima.help.GlideLoader;
import com.dispatching.feima.view.PresenterControl.ShopListControl;
import com.dispatching.feima.view.adapter.ShopListAdapter;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/29.
 */

public class ShopListActivity extends BaseActivity implements ShopListControl.ShopListView, BaseQuickAdapter.RequestLoadMoreListener {


    public static Intent getIntent(Context context) {
        return new Intent(context, ShopListActivity.class);
    }

    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.shop_list)
    RecyclerView mShopList;
    @Inject
    ShopListControl.PresenterShopList mPresenter;
    private ShopListAdapter mAdapter;
    private List<Integer> mImageList;
    private Integer mPagerSize = 10;
    private Integer mPagerNo = 1;
    private List<ShopListResponse.ListBean> mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        ButterKnife.bind(this);
        initializeInjector();
        supportActionBar(mToolbar, true);
        mMiddleName.setText(R.string.app_shop_list);
        initView();
        initData();
    }

    @Override
    public void getShopListSuccess(List<ShopListResponse.ListBean> list) {
        mList = list;
        if (list.size() > 0) {
            mAdapter.addData(mList);
            mAdapter.loadMoreComplete();
        } else {
            mAdapter.loadMoreEnd();
        }
    }

    @Override
    public void loadFail(Throwable throwable) {
        showErrMessage(throwable);
        mAdapter.loadMoreFail();
    }

    @Override
    public void onLoadMoreRequested() {
        if (mList.size() < mPagerSize) {
            mAdapter.loadMoreEnd(true);
        } else {
            mPresenter.requestShopList(++mPagerNo, mPagerSize);
        }
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
    public void onResume() {
        super.onResume();
        mBanner.startAutoPlay();
    }

    @Override
    public void onPause() {
        super.onPause();
        mBanner.stopAutoPlay();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    private void initView() {
        mImageList = new ArrayList<>();
        mImageList.add(R.mipmap.main_banner_first);
        mImageList.add(R.mipmap.main_banner_second);
        mImageList.add(R.mipmap.main_banner_third);
        mBanner.setImages(mImageList).setImageLoader(new GlideLoader()).start();

        mShopList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ShopListAdapter(null, this,mImageLoaderHelper);
        mShopList.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(this, mShopList);
        mAdapter.setOnItemClickListener((adapter, view, position) ->
                startActivity(ShopDetailActivity.getIntent(this,(ShopListResponse.ListBean)adapter.getItem(position)))
        );
    }

    private void initData() {
        mPresenter.requestShopList(mPagerNo, mPagerSize);

    }

    private void initializeInjector() {
        DaggerShopListActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .shopListActivityModule(new ShopListActivityModule(ShopListActivity.this, this))
                .build().inject(this);
    }

}
