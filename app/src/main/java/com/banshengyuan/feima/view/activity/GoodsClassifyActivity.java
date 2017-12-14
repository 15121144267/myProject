package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerGoodsClassifyActivityComponent;
import com.banshengyuan.feima.dagger.module.GoodsClassifyActivityModule;
import com.banshengyuan.feima.entity.AllFairListResponse;
import com.banshengyuan.feima.view.PresenterControl.GoodsClassifyControl;
import com.banshengyuan.feima.view.adapter.GoodsClassifyAdapter;
import com.example.mylibrary.adapter.BaseQuickAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/26.
 * GoodsClassifyActivity
 */

public class GoodsClassifyActivity extends BaseActivity implements GoodsClassifyControl.GoodsClassifyView {

    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.goods_classify_list)
    RecyclerView mGoodsClassifyList;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, GoodsClassifyActivity.class);
        return intent;
    }

    @Inject
    GoodsClassifyControl.PresenterGoodsClassify mPresenter;
    private GoodsClassifyAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_classify);
        ButterKnife.bind(this);
        initializeInjector();
        supportActionBar(mToolbar, true);
        mMiddleName.setText("市集分类");
        initView();
        initData();
    }

    @Override
    public void getAllFairListSuccess(AllFairListResponse response) {
        List<AllFairListResponse.ListBean> listBean = response.list;
        if (listBean != null && listBean.size() > 0) {
            mAdapter.setNewData(listBean);
        } else {
            mGoodsClassifyList.setVisibility(View.GONE);
        }
    }

    @Override
    public void getAllFairListFail() {
        mGoodsClassifyList.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        AllFairListResponse.ListBean.List1Bean dataBean = (AllFairListResponse.ListBean.List1Bean) adapter.getItem(position);
        if (dataBean != null) {
            if (dataBean.flag != null) {
                switch (dataBean.flag) {
                    case "品牌市集":
                        startActivity(FairDetailActivity.getIntent(this, 1, dataBean.id));
                        break;
                    case "线下市集":
                        startActivity(UnderLineFairActivity.getActivityDetailIntent(this, dataBean.id));
                        break;
                    case "其他分类":
                        startActivity(FairDetailActivity.getIntent(this, 2, dataBean.id));
                        break;
                }
            }

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
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    private void initView() {
        mGoodsClassifyList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new GoodsClassifyAdapter(null, GoodsClassifyActivity.this, this);
        mGoodsClassifyList.setAdapter(mAdapter);

    }

    private void initData() {
        mPresenter.requestAllFairList();
    }

    private void initializeInjector() {
        DaggerGoodsClassifyActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .goodsClassifyActivityModule(new GoodsClassifyActivityModule(GoodsClassifyActivity.this, this))
                .build().inject(this);
    }
}
