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
import com.banshengyuan.feima.entity.GoodsClassifyResponse;
import com.banshengyuan.feima.entity.SortListResponse;
import com.banshengyuan.feima.view.PresenterControl.GoodsClassifyControl;
import com.banshengyuan.feima.view.adapter.GoodsClassifyAdapter;
import com.example.mylibrary.adapter.BaseQuickAdapter;

import java.util.ArrayList;
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
       /* SortListResponse.DataBean.ChildrenBean dataBean = (SortListResponse.DataBean.ChildrenBean) adapter.getItem(position);
        if(dataBean!=null){
            startActivity(ClassifySearchActivity.getIntent(this, mShopId, dataBean.resultModel.nid, 2));
        }*/

    }

    @Override
    public void sortListSuccess(SortListResponse response) {

    }

    private void initView() {
        mGoodsClassifyList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new GoodsClassifyAdapter(null,GoodsClassifyActivity.this,this);
        mGoodsClassifyList.setAdapter(mAdapter);

    }

    private void initData() {
        List<String> list1 = new ArrayList<>();
        for (int i = 0; i <5 ; i++) {
            list1.add("花市"+i);
        }
        List<GoodsClassifyResponse>  list2 =new ArrayList<>();
        for (int i = 0; i <4 ; i++) {
            GoodsClassifyResponse response = new GoodsClassifyResponse();
            response.name = "线上街区"+i;
            response.mList = list1;
            list2.add(response);
        }
        mAdapter.setNewData(list2);
    }

    private void initializeInjector() {
        DaggerGoodsClassifyActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .goodsClassifyActivityModule(new GoodsClassifyActivityModule(GoodsClassifyActivity.this, this))
                .build().inject(this);
    }
}
