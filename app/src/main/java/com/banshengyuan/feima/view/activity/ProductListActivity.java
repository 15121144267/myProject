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
import com.banshengyuan.feima.dagger.component.DaggerProductListActivityComponent;
import com.banshengyuan.feima.dagger.module.ProductListActivityModule;
import com.banshengyuan.feima.view.PresenterControl.ProductListControl;
import com.banshengyuan.feima.view.adapter.CollectionProductAdapter;
import com.banshengyuan.feima.view.adapter.ProductListSortAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/5.
 * WelcomeActivity
 */

public class ProductListActivity extends BaseActivity implements ProductListControl.ProductListView {


    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.product_list_sort)
    RecyclerView mProductListSort;
    @BindView(R.id.product_list)
    RecyclerView mProductList;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, ProductListActivity.class);
        return intent;
    }

    @Inject
    ProductListControl.PresenterProductList mPresenter;
    private ProductListSortAdapter mProductListSortAdapter;
    private CollectionProductAdapter mProductAdapter;
    private String[] mStrings = {"全部", "美食", "服饰", "休闲娱乐", "母婴亲子", "护肤彩妆", "运动健身", "家店数码", "鞋包", "文化", "设计感"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
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
        List<String> list = new ArrayList<>();
        Collections.addAll(list, mStrings);
        mProductListSortAdapter.setNewData(list);


        List<Integer> mList = new ArrayList<>();
        mList.add(R.mipmap.main_banner_first);
        mList.add(R.mipmap.main_banner_second);
        mList.add(R.mipmap.main_banner_third);
        mProductAdapter.setNewData(mList);
    }

    private void initView() {
        mMiddleName.setText("产品列表");
        mProductListSort.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mProductList.setLayoutManager(new LinearLayoutManager(this));
        mProductListSortAdapter = new ProductListSortAdapter(null, this);
        mProductAdapter = new CollectionProductAdapter(null,this);
        mProductListSort.setAdapter(mProductListSortAdapter);
        mProductList.setAdapter(mProductAdapter);

    }

    private void initializeInjector() {
        DaggerProductListActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .productListActivityModule(new ProductListActivityModule(ProductListActivity.this, this))
                .build().inject(this);
    }
}
