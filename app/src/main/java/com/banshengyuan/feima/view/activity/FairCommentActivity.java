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
import com.banshengyuan.feima.dagger.component.DaggerClassifySearchActivityComponent;
import com.banshengyuan.feima.dagger.module.ClassifySearchActivityModule;
import com.banshengyuan.feima.entity.ClassifySearchListResponse;
import com.banshengyuan.feima.view.PresenterControl.ClassifySearchControl;
import com.banshengyuan.feima.view.adapter.ClassifySearchListAdapter;
import com.example.mylibrary.adapter.BaseQuickAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/5.
 * WelcomeActivity
 */

public class FairCommentActivity extends BaseActivity implements ClassifySearchControl.ClassifySearchView, BaseQuickAdapter.RequestLoadMoreListener {


    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.activity_fair_comment)
    RecyclerView mActivityFairComment;

    public static Intent getIntent(Context context, Integer fairId) {
        Intent intent = new Intent(context, FairCommentActivity.class);
        intent.putExtra("fairId", fairId);
        return intent;
    }

    @Inject
    ClassifySearchControl.PresenterClassifySearch mPresenter;

    private Integer mPage = 1;
    private final Integer mPageSize = 10;
    private Integer mFairId;
    private ClassifySearchListAdapter mAdapter;
    private ClassifySearchListResponse mResponse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fair_comment);
        ButterKnife.bind(this);
        initializeInjector();
        supportActionBar(mToolbar, true);
        initView();
        initData();
    }


    @Override
    public void onLoadMoreRequested() {
        if (mResponse.has_next_page) {
            mPresenter.requestCommentList(mFairId, ++mPage, mPageSize);
        } else {
            mAdapter.loadMoreEnd(true);
        }
    }

    @Override
    public void getCommentListSuccess(ClassifySearchListResponse response) {
        mResponse = response;
        if (response.list != null && response.list.size() > 0) {
            mAdapter.addData(response.list);
            mAdapter.loadMoreComplete();
        } else {
            mAdapter.loadMoreEnd();
        }
    }

    private void initView() {
        mFairId = getIntent().getIntExtra("fairId", 0);
        mMiddleName.setText("全部评论");
        mActivityFairComment.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ClassifySearchListAdapter(null, this, mImageLoaderHelper);
        mAdapter.setOnLoadMoreListener(this, mActivityFairComment);
        mActivityFairComment.setAdapter(mAdapter);
    }

    private void initData() {
        mPresenter.requestCommentList(mFairId, mPage, mPageSize);
    }

    @Override
    public void loadError(Throwable error) {
        showErrMessage(error);
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

    private void initializeInjector() {
        DaggerClassifySearchActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .classifySearchActivityModule(new ClassifySearchActivityModule(FairCommentActivity.this, this))
                .build().inject(this);
    }
}
