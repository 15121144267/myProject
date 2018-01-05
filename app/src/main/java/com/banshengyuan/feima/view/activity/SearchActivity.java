package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerSearchActivityComponent;
import com.banshengyuan.feima.dagger.module.SearchActivityModule;
import com.banshengyuan.feima.entity.BroConstant;
import com.banshengyuan.feima.entity.SearchResultResponse;
import com.banshengyuan.feima.utils.ValueUtil;
import com.banshengyuan.feima.view.PresenterControl.SearchControl;
import com.banshengyuan.feima.view.adapter.MyOrderFragmentAdapter;
import com.banshengyuan.feima.view.customview.ClearEditText;
import com.banshengyuan.feima.view.fragment.SearchBlockFragment;
import com.banshengyuan.feima.view.fragment.SearchFairFragment;
import com.banshengyuan.feima.view.fragment.SearchProductFragment;
import com.banshengyuan.feima.view.fragment.SearchShopFragment;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/5.
 * SearchActivity
 */

public class SearchActivity extends BaseActivity implements SearchControl.SearchView, ClearEditText.setOnMyEditorActionListener {


    @BindView(R.id.search_goods)
    ClearEditText mSearchGoods;
    @BindView(R.id.search_goods_cancel)
    TextView mSearchGoodsCancel;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.search_tab_layout)
    TabLayout mSearchTabLayout;
    @BindView(R.id.search_view_pager)
    ViewPager mSearchViewPager;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        return intent;
    }

    @Inject
    SearchControl.PresenterSearch mPresenter;

    private final String[] modules = {"市集", "产品", "商家", "街区"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initializeInjector();
        initView();
        initData();
    }

    @Override
    public void onMyEditorAction() {
        String searchName = mSearchGoods.getEditText().trim();
        Intent intent = new Intent(BroConstant.SEARCH_UPDATE);
        intent.putExtra("broSearchName",searchName);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public void onMyTouchAction() {

    }

    private void initView() {
        mSearchGoods.setEditHint("请输入市集、街区、产品、商家");
        mSearchGoods.setLinearBackgroundResource(R.drawable.shape_line_blod2);
        List<Fragment> mFragments = new ArrayList<>();
        mFragments.add(SearchFairFragment.newInstance());
        mFragments.add(SearchProductFragment.newInstance());
        mFragments.add(SearchShopFragment.newInstance());
        mFragments.add(SearchBlockFragment.newInstance());
        MyOrderFragmentAdapter adapter = new MyOrderFragmentAdapter(getSupportFragmentManager(), mFragments, modules);
        mSearchViewPager.setOffscreenPageLimit(mFragments.size() - 1);
        mSearchViewPager.setAdapter(adapter);
        mSearchTabLayout.setupWithViewPager(mSearchViewPager);
        ValueUtil.setIndicator(mSearchTabLayout, 20, 20);

        mSearchGoods.setOnMyEditorActionListener(this, false);
        RxView.clicks(mSearchGoodsCancel).subscribe(o -> onBackPressed());
    }

    private void initData() {

    }

    @Override
    public void getSearchFairListSuccess(SearchResultResponse response) {

    }

    @Override
    public void getSearchStoreListSuccess(SearchResultResponse response) {

    }

    @Override
    public void getSearchStreetListSuccess(SearchResultResponse response) {

    }

    @Override
    public void getSearchProductListSuccess(SearchResultResponse response) {

    }

    @Override
    public void getSearchFairListFail(String des) {

    }

    @Override
    public void getSearchStoreListFail(String des) {

    }

    @Override
    public void getSearchStreetListFail(String des) {

    }

    @Override
    public void getSearchProductListFail(String des) {

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
        DaggerSearchActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .searchActivityModule(new SearchActivityModule(SearchActivity.this, this))
                .build().inject(this);
    }
}
