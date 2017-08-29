package com.dispatching.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.dispatching.feima.R;
import com.dispatching.feima.dagger.component.DaggerGoodsClassifyActivityComponent;
import com.dispatching.feima.dagger.module.GoodsClassifyActivityModule;
import com.dispatching.feima.entity.SortListResponse;
import com.dispatching.feima.view.PresenterControl.GoodsClassifyControl;
import com.dispatching.feima.view.adapter.GoodsClassifyAdapter;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/26.
 */

public class GoodsClassifyActivity extends BaseActivity implements GoodsClassifyControl.GoodsClassifyView {

    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.goods_classify_all)
    TextView mGoodsClassifyAll;
    @BindView(R.id.goods_classify_list)
    RecyclerView mGoodsClassifyList;

    public static Intent getIntent(Context context, String shopId) {
        Intent intent = new Intent(context, GoodsClassifyActivity.class);
        intent.putExtra("shopId", shopId);
        return intent;
    }

    @Inject
    GoodsClassifyControl.PresenterGoodsClassify mPresenter;
    private GoodsClassifyAdapter mAdapter;
    private String mShopId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_classify);
        ButterKnife.bind(this);
        initializeInjector();
        supportActionBar(mToolbar, true);
        mMiddleName.setText("商品分类");
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

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        SortListResponse.DataBean.ChildrenBean dataBean = (SortListResponse.DataBean.ChildrenBean) adapter.getItem(position);
        startActivity(ClassifySearchActivity.getIntent(this, mShopId, dataBean.resultModel.nid, 2));
    }

    @Override
    public void sortListSuccess(SortListResponse response) {
        if (response.data.size() > 0) {
            mAdapter.setNewData(response.data);
        }
    }

    private void initView() {
        mShopId = getIntent().getStringExtra("shopId");
        RxView.clicks(mGoodsClassifyAll).throttleFirst(1, TimeUnit.SECONDS).subscribe(v -> onBackPressed());
        mGoodsClassifyList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new GoodsClassifyAdapter(null, GoodsClassifyActivity.this, this);
        mGoodsClassifyList.setAdapter(mAdapter);

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                    switch (view.getId()) {
                        case R.id.adapter_goods_classify_all:
                            SortListResponse.DataBean item = (SortListResponse.DataBean) adapter.getItem(position);
                            startActivity(ClassifySearchActivity.getIntent(this, mShopId, item.resultModel.nid, 1));
                            break;
                    }
                }
        );

    }

    private void initData() {
        mPresenter.requestSortList(mShopId, "01", 2, "saleCount", 1);
    }

    private void initializeInjector() {
        DaggerGoodsClassifyActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .goodsClassifyActivityModule(new GoodsClassifyActivityModule(GoodsClassifyActivity.this, this))
                .build().inject(this);
    }
}
