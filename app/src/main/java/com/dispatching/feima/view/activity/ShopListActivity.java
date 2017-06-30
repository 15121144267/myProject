package com.dispatching.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dispatching.feima.R;
import com.dispatching.feima.dagger.component.DaggerShopListActivityComponent;
import com.dispatching.feima.dagger.module.ShopListActivityModule;
import com.dispatching.feima.entity.ShopListResponse;
import com.dispatching.feima.view.PresenterControl.ShopListControl;
import com.dispatching.feima.view.adapter.ShopListAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by lei.he on 2017/6/29.
 */

public class ShopListActivity extends BaseActivity implements ShopListControl.ShopListView, BGABanner.Adapter<ImageView, String> {
    public static Intent getIntent(Context context) {
        return new Intent(context, ShopListActivity.class);
    }

    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.shop_banner)
    BGABanner mShopBanner;
    @BindView(R.id.shop_list)
    RecyclerView mShopList;
    @Inject
    ShopListControl.PresenterShopList mPresenter;
    private ShopListAdapter mAdapter;
    private List<ShopListResponse> mList;

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
    public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
        Glide.with(itemView.getContext())
                .load(model)
                .placeholder(R.mipmap.holder)
                .error(R.mipmap.holder)
                .dontAnimate()
                .centerCrop()
                .into(itemView);
    }

    private void initView() {
        mList = new ArrayList<>();
        mShopBanner.setAdapter(this);
        mShopList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ShopListAdapter(null, this);
        mShopList.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((adapter, view, position) ->
                startActivity(ShopDetailActivity.getIntent(this))
        );
    }

    private void initData() {
        for (int i = 0; i < 3; i++) {
            ShopListResponse response = new ShopListResponse();
            response.shopDesContent = i + "轻轻的我来了";
            response.shopLocation = i + "F L2046";
            mList.add(response);
        }
        mAdapter.setNewData(mList);
    }

    private void initializeInjector() {
        DaggerShopListActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .shopListActivityModule(new ShopListActivityModule(ShopListActivity.this, this))
                .build().inject(this);
    }

}
