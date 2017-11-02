package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerBlockDetailActivityComponent;
import com.banshengyuan.feima.dagger.module.BlockDetailActivityModule;
import com.banshengyuan.feima.entity.ProductResponse;
import com.banshengyuan.feima.help.GlideLoader;
import com.banshengyuan.feima.view.PresenterControl.BlockDetailControl;
import com.banshengyuan.feima.view.adapter.ProductAdapter;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/28.
 * AddAddressActivity
 */

public class BlockDetailActivity extends BaseActivity implements BlockDetailControl.BlockDetailView {

    @BindView(R.id.block_detail_banner)
    Banner mBlockDetailBanner;
    @BindView(R.id.toolbar_right_icon)
    ImageView mToolbarRightIcon;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.block_detail_product)
    RecyclerView mBlockDetailProduct;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, BlockDetailActivity.class);
        return intent;
    }

    @Inject
    BlockDetailControl.PresenterBlockDetail mPresenter;
    private ProductAdapter mAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_detail);
        ButterKnife.bind(this);
        initializeInjector();
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

    private void initView() {
        mToolbarRightIcon.setVisibility(View.VISIBLE);
        mBlockDetailProduct.setLayoutManager(new LinearLayoutManager(this));
        mAdapter =  new ProductAdapter(null,this,false);
        mBlockDetailProduct.setAdapter(mAdapter);
        mBlockDetailProduct.setNestedScrollingEnabled(false);
    }

    private void initData() {
        List<Integer> mImageList = new ArrayList<>();
        mImageList.add(R.mipmap.header);
        mImageList.add(R.mipmap.header);
        mImageList.add(R.mipmap.header);
        mBlockDetailBanner.isAutoPlay(false);
        mBlockDetailBanner.setImages(mImageList).setImageLoader(new GlideLoader()).start();

        List<ProductResponse> mList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            List<ProductResponse.ProductItemBean> mList1 = new ArrayList<>();
            ProductResponse product = new ProductResponse();
            product.name = "户外运动" + i;
            for (int j = 0; j < 5; j++) {
                ProductResponse.ProductItemBean itemBean = new ProductResponse.ProductItemBean();
                itemBean.content = "魔兽世界" + j;
                itemBean.tip = "少年三国志" + j;
                mList1.add(itemBean);
            }
            product.mList = mList1;
            mList.add(product);
        }
        mAdapter.setNewData(mList);
    }

    private void initializeInjector() {
        DaggerBlockDetailActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .blockDetailActivityModule(new BlockDetailActivityModule(BlockDetailActivity.this, this))
                .build().inject(this);
    }
}
