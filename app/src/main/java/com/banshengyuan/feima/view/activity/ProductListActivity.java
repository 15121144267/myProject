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
import com.banshengyuan.feima.dagger.component.DaggerProductListActivityComponent;
import com.banshengyuan.feima.dagger.module.ProductListActivityModule;
import com.banshengyuan.feima.entity.AllProductSortResponse;
import com.banshengyuan.feima.entity.ProductCategoryResponse;
import com.banshengyuan.feima.view.PresenterControl.ProductListControl;
import com.banshengyuan.feima.view.adapter.ProductCategoryListAdapter;
import com.banshengyuan.feima.view.adapter.ProductListSortAdapter;

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

    public static Intent getIntent(Context context, AllProductSortResponse allProductSortResponse, Integer categoryId) {
        Intent intent = new Intent(context, ProductListActivity.class);
        intent.putExtra("allProductSortResponse", allProductSortResponse);
        intent.putExtra("categoryId", categoryId);
        return intent;
    }

    @Inject
    ProductListControl.PresenterProductList mPresenter;
    private ProductListSortAdapter mProductListSortAdapter;
    private ProductCategoryListAdapter mProductAdapter;
    private List<AllProductSortResponse.ListBean> mList;
    private AllProductSortResponse mAllProductSortResponse;
    private Integer mCategoryId;
    private Integer mOriginPosition;

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

    @Override
    public void getProductListSuccess(ProductCategoryResponse response) {
        if (response.list != null && response.list.size() > 0) {
            mProductList.setVisibility(View.VISIBLE);
            mProductAdapter.setNewData(response.list);
        } else {
            mProductList.setVisibility(View.GONE);
        }
    }

    @Override
    public void getProductListFail(String des) {
        mProductList.setVisibility(View.GONE);
    }

    private void initData() {
        mPresenter.requestProductList(mCategoryId);
    }

    private void initView() {
        mAllProductSortResponse = (AllProductSortResponse) getIntent().getSerializableExtra("allProductSortResponse");
        mList = mAllProductSortResponse.list;
        mCategoryId = getIntent().getIntExtra("categoryId", 0);

        mMiddleName.setText("产品列表");
        mProductListSort.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mProductList.setLayoutManager(new LinearLayoutManager(this));
        mProductListSortAdapter = new ProductListSortAdapter(null, this);
        mProductAdapter = new ProductCategoryListAdapter(null, this, mImageLoaderHelper);
        mProductListSort.setAdapter(mProductListSortAdapter);
        mProductList.setAdapter(mProductAdapter);

        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).id == mCategoryId) {
                mOriginPosition = i;
                mList.get(i).isRed = true;
            }
        }
        mProductListSortAdapter.setNewData(mList);
        mProductListSort.getLayoutManager().smoothScrollToPosition(mProductListSort, null, mOriginPosition);
        mProductListSortAdapter.setOnItemClickListener((adapter, view, position) -> {
            AllProductSortResponse.ListBean item = (AllProductSortResponse.ListBean) adapter.getItem(position);
            for (int i = 0; i < mList.size(); i++) {
                mList.get(i).isRed = i == position;
            }
            mProductListSortAdapter.setNewData(mList);
            mProductListSort.getLayoutManager().smoothScrollToPosition(mProductListSort, null, position);
            if (item != null) {
                mPresenter.requestProductList(item.id);
            }

        });

        mProductAdapter.setOnItemClickListener((adapter, view, position) -> {
            ProductCategoryResponse.ListBean bean = (ProductCategoryResponse.ListBean) adapter.getItem(position);
            if (bean != null) {
                startActivity(GoodDetailActivity.getIntent(this, bean.id));
            }

        });

    }

    private void initializeInjector() {
        DaggerProductListActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .productListActivityModule(new ProductListActivityModule(ProductListActivity.this, this))
                .build().inject(this);
    }
}
