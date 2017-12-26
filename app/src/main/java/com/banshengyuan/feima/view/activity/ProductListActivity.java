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
import com.banshengyuan.feima.entity.AllProductSortResponse;
import com.banshengyuan.feima.entity.ProductCategoryResponse;
import com.banshengyuan.feima.view.PresenterControl.ProductListControl;
import com.banshengyuan.feima.view.adapter.ProductCategoryListAdapter;
import com.banshengyuan.feima.view.adapter.ProductListSortAdapter;
import com.example.mylibrary.adapter.BaseQuickAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/5.
 * WelcomeActivity
 */

public class ProductListActivity extends BaseActivity implements ProductListControl.ProductListView, BaseQuickAdapter.RequestLoadMoreListener {


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
    private Integer mPage = 1;
    private Integer mPageSize = 10;
    private ProductCategoryResponse mResponse;

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
    public void onLoadMoreRequested() {
        if (mResponse.has_next_page) {
            mPresenter.requestProductList(mCategoryId, ++mPage, mPageSize);
        } else {
            mProductAdapter.loadMoreEnd(true);
        }
    }

    @Override
    public void getProductListSuccess(ProductCategoryResponse response) {
        mResponse = response;
        if (response.list != null && response.list.size() > 0) {
            mProductAdapter.addData(response.list);
            mProductAdapter.loadMoreComplete();
        } else {
            mProductAdapter.loadMoreEnd();
        }
    }

    @Override
    public void getProductListFail(String des) {
        mProductAdapter.loadMoreFail();
    }

    @Override
    public void loadError(Throwable throwable) {
        showErrMessage(throwable);
        mProductAdapter.loadMoreFail();
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
        mPresenter.requestProductList(mCategoryId, mPage, mPageSize);
    }

    private void initView() {
        mAllProductSortResponse = (AllProductSortResponse) getIntent().getSerializableExtra("allProductSortResponse");

        mCategoryId = getIntent().getIntExtra("categoryId", 0);

        mMiddleName.setText("产品列表");
        mProductListSort.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mProductList.setLayoutManager(new LinearLayoutManager(this));
        mProductListSortAdapter = new ProductListSortAdapter(null, this);
        mProductAdapter = new ProductCategoryListAdapter(null, this, mImageLoaderHelper);
        mProductListSort.setAdapter(mProductListSortAdapter);
        mProductList.setAdapter(mProductAdapter);
        mProductAdapter.setOnLoadMoreListener(this, mProductList);

        if (mAllProductSortResponse != null) {
            mList = mAllProductSortResponse.list;
            for (int i = 0; i < mList.size(); i++) {
                if (mList.get(i).id == mCategoryId) {
                    mOriginPosition = i;
                    mList.get(i).isRed = true;
                }
            }
            mProductListSortAdapter.setNewData(mList);
        }


        if (mOriginPosition != null) {
            mProductListSort.getLayoutManager().smoothScrollToPosition(mProductListSort, null, mOriginPosition);
        }

        mProductListSortAdapter.setOnItemClickListener((adapter, view, position) -> {
            AllProductSortResponse.ListBean item = (AllProductSortResponse.ListBean) adapter.getItem(position);
            mPage = 1;
            mProductAdapter.setNewData(null);
            for (int i = 0; i < mList.size(); i++) {
                mList.get(i).isRed = i == position;
            }
            mProductListSortAdapter.setNewData(mList);
            mProductListSort.getLayoutManager().smoothScrollToPosition(mProductListSort, null, position);
            if (item != null) {
                mCategoryId = item.id;
                mPresenter.requestProductList(item.id, mPage, mPageSize);
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
