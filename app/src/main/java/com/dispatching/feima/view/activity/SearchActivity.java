package com.dispatching.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.dispatching.feima.R;
import com.dispatching.feima.dagger.component.DaggerSearchActivityComponent;
import com.dispatching.feima.dagger.module.SearchActivityModule;
import com.dispatching.feima.view.PresenterControl.SearchControl;
import com.dispatching.feima.view.customview.ClearEditText;
import com.jakewharton.rxbinding2.view.RxView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/5.
 * WelcomeActivity
 */

public class SearchActivity extends BaseActivity implements SearchControl.SearchView,ClearEditText.setOnMyEditorActionListener {

    @BindView(R.id.search_goods_list)
    RecyclerView mSearchGoodsList;
    @BindView(R.id.search_goods)
    ClearEditText mSearchGoods;
    @BindView(R.id.search_goods_cancel)
    TextView mSearchGoodsCancel;

    public static Intent getIntent(Context context,Integer type) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra("searchType",type);
        return intent;
    }

    @Inject
    SearchControl.PresenterSearch mPresenter;


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

    }

    @Override
    public void onMyTouchAction() {

    }

    private void initView() {
        mSearchGoods.setOnMyEditorActionListener(this);
        RxView.clicks(mSearchGoodsCancel).subscribe(o -> onBackPressed());
    }

    private void initData() {

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
