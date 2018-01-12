package com.banshengyuan.feima.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.banshengyuan.feima.DaggerApplication;
import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerDiscoverFragmentComponent;
import com.banshengyuan.feima.dagger.module.DiscoverFragmentModule;
import com.banshengyuan.feima.dagger.module.MainActivityModule;
import com.banshengyuan.feima.entity.AllProductSortResponse;
import com.banshengyuan.feima.entity.ProductListResponse;
import com.banshengyuan.feima.view.PresenterControl.ProductControl;
import com.banshengyuan.feima.view.activity.MainActivity;
import com.banshengyuan.feima.view.activity.ProductListActivity;
import com.banshengyuan.feima.view.adapter.ProductAdapter;
import com.banshengyuan.feima.view.customview.ClearEditText;
import com.banshengyuan.feima.view.customview.banner.CBViewHolderCreator;
import com.banshengyuan.feima.view.customview.banner.ConvenientBanner;
import com.banshengyuan.feima.view.customview.banner.NetworkImageHolderView;
import com.example.mylibrary.adapter.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.banshengyuan.feima.R.id.convenientBanner;

/**
 * Created by helei on 2017/5/3.
 * SendingOrderFragment
 */

public class ProductFragment extends BaseFragment implements ProductControl.ProductView {


    @BindView(convenientBanner)
    ConvenientBanner mConvenientBanner;
    @BindView(R.id.product_products)
    RecyclerView mProductProducts;
    @BindView(R.id.recommend_search)
    ClearEditText mRecommendSearch;
    @BindView(R.id.search_layout)
    LinearLayout mSearchLayout;

    public static ProductFragment newInstance() {
        return new ProductFragment();
    }

    @Inject
    ProductControl.PresenterProduct mPresenter;

    private Unbinder unbind;
    private List<List> mListAll;
    private ProductAdapter mAdapter;
    private AllProductSortResponse mAllProductSortResponse;
    private ProductControl.ProductView mView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
        mView = this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        unbind = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void getAllProductSortSuccess(AllProductSortResponse allProductSortResponse) {
        List<AllProductSortResponse.ListBean> list = allProductSortResponse.list;
        if (list != null && list.size() > 0) {
            mAllProductSortResponse = allProductSortResponse;
            mListAll = new ArrayList<>();
            if (list.size() > 8) {
                mListAll.add(list.subList(0, 8));
                mListAll.add(list.subList(8, list.size()));
            } else {
                mListAll.add(list);
            }
            mConvenientBanner.setCanLoop(false);
            mConvenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                @Override
                public NetworkImageHolderView createHolder() {
                    return new NetworkImageHolderView(mImageLoaderHelper, mView);
                }
            }, mListAll).setPointViewVisible(mListAll.size() > 1).setPageIndicator(new int[]{R.drawable.shape_line2, R.drawable.shape_line_banner});
        } else {
            mConvenientBanner.setVisibility(View.GONE);
        }
    }

    @Override
    public void switchToProductList(BaseQuickAdapter adapter, Integer position) {
        AllProductSortResponse.ListBean bean = (AllProductSortResponse.ListBean) adapter.getItem(position);
        if (bean != null) {
            startActivity(ProductListActivity.getIntent(getActivity(), mAllProductSortResponse, bean.id));
        }
    }

    @Override
    public void getAllProductSortFail() {
        mConvenientBanner.setVisibility(View.GONE);
    }

    @Override
    public void getProductListSuccess(ProductListResponse response) {
        List<ProductListResponse.CategoryBean> list = response.category;
        if (list != null && list.size() > 0) {
            mAdapter.setNewData(list);
        } else {
            mProductProducts.setVisibility(View.GONE);
        }
    }

    @Override
    public void getProductListFail() {
        mProductProducts.setVisibility(View.GONE);
    }

    private void initData() {
        //请求全部分类
        mPresenter.requestAllProductSort();
        //请求一级产品列表
        mPresenter.requestProductList();

    }

    private void initView() {
        mProductProducts.setLayoutManager(new LinearLayoutManager(getActivity()));
        mProductProducts.setNestedScrollingEnabled(false);
        mAdapter = new ProductAdapter(null, getActivity(), mImageLoaderHelper);
        mProductProducts.setAdapter(mAdapter);

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            ProductListResponse.CategoryBean categoryBean = (ProductListResponse.CategoryBean) adapter.getItem(position);
            if (categoryBean == null) return;
            switch (view.getId()) {
                case R.id.adapter_fair_more:
                    startActivity(ProductListActivity.getIntent(getActivity(), mAllProductSortResponse, categoryBean.id));
                    break;
            }
        });
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
    public void onDestroyView() {
        super.onDestroyView();
        unbind.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    private void initialize() {
        DaggerDiscoverFragmentComponent.builder()
                .applicationComponent(((DaggerApplication) getActivity().getApplication()).getApplicationComponent())
                .mainActivityModule(new MainActivityModule((AppCompatActivity) getActivity()))
                .discoverFragmentModule(new DiscoverFragmentModule(this, (MainActivity) getActivity()))
                .build()
                .inject(this);
    }
}
