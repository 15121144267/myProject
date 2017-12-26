package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerShopDetailActivityComponent;
import com.banshengyuan.feima.dagger.module.ShopDetailActivityModule;
import com.banshengyuan.feima.entity.GoodsCommentResponse;
import com.banshengyuan.feima.view.PresenterControl.ShopDetailControl;
import com.banshengyuan.feima.view.adapter.GoodsCommentAdapter;
import com.example.mylibrary.adapter.BaseQuickAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/30.
 * GoodsCommentActivity
 */

public class GoodsCommentActivity extends BaseActivity implements ShopDetailControl.ShopDetailView, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.activity_goods_comment)
    RecyclerView mActivityGoodsComment;

    public static Intent getIntent(Context context, Integer goodsId) {
        Intent intent = new Intent(context, GoodsCommentActivity.class);
        intent.putExtra("goodsId", goodsId);
        return intent;
    }


    @Inject
    ShopDetailControl.PresenterShopDetail mPresenter;
    private Integer mGoodsId;
    private GoodsCommentAdapter mAdapter;
    private Integer mPage = 1;
    private Integer mPageSize = 10;
    private GoodsCommentResponse mResponse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_comment);
        ButterKnife.bind(this);
        supportActionBar(mToolbar, true);
        initializeInjector();
        initView();
        initData();
    }

    @Override
    public void getGoodsCommentSuccess(GoodsCommentResponse response) {
        mResponse = response;
        if (response.list != null && response.list.size() > 0) {
            mAdapter.addData(response.list);
            mAdapter.loadMoreComplete();
        } else {
            mAdapter.loadMoreEnd();
        }
    }

    @Override
    public void onLoadMoreRequested() {
        if (mResponse.has_next_page) {
            mPresenter.requestGoodsComment(mGoodsId, ++mPage, mPageSize);
        } else {
            mAdapter.loadMoreEnd(true);
        }
    }

    @Override
    public void loadFail() {
        mAdapter.loadMoreFail();
    }

    @Override
    public void loadError(Throwable throwable) {
        showErrMessage(throwable);
        mAdapter.loadMoreFail();
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
        mMiddleName.setText("小伙伴们说");
        mGoodsId = getIntent().getIntExtra("goodsId", 0);
        mActivityGoodsComment.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new GoodsCommentAdapter(null, this, mImageLoaderHelper);
        mAdapter.setOnLoadMoreListener(this, mActivityGoodsComment);
        mActivityGoodsComment.setAdapter(mAdapter);
    }


    private void initData() {
        mPresenter.requestGoodsComment(1, mPage, mPageSize);
    }

    private void initializeInjector() {
        DaggerShopDetailActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .shopDetailActivityModule(new ShopDetailActivityModule(GoodsCommentActivity.this, this))
                .build().inject(this);
    }
}
