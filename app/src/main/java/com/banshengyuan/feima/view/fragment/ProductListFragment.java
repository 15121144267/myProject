package com.banshengyuan.feima.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.banshengyuan.feima.DaggerApplication;
import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerShopProductDetailFragmentComponent;
import com.banshengyuan.feima.dagger.module.ShopProductDetailActivityModule;
import com.banshengyuan.feima.dagger.module.ShopProductDetailFragmentModule;
import com.banshengyuan.feima.entity.ShopDetailCouponListResponse;
import com.banshengyuan.feima.entity.ShopDetailProductListResponse;
import com.banshengyuan.feima.entity.StoreDetailResponse;
import com.banshengyuan.feima.view.PresenterControl.ShopProductDetailControl;
import com.banshengyuan.feima.view.activity.ShopProductDetailActivity;
import com.banshengyuan.feima.view.adapter.FairDetailNewAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by helei on 2017/5/3.
 * TrendsFragment
 */

public class ProductListFragment extends BaseFragment implements ShopProductDetailControl.ShopProductDetailView {
    @BindView(R.id.fragment_trends_list_last)
    RecyclerView mFragmentTrendsListLast;

    public static ProductListFragment newInstance() {
        return new ProductListFragment();
    }

    @Inject
    ShopProductDetailControl.PresenterShopProductDetail mPresenter;

    private Unbinder unbind;
    private FairDetailNewAdapter mAdapter;
    private Integer mShopId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
        mShopId = ((ShopProductDetailActivity) getActivity()).getShopId();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trends, container, false);
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
    public void getStoreProductListSuccess(ShopDetailProductListResponse response) {
        List<ShopDetailProductListResponse.ListBean> listBeen = response.list;
        if (listBeen != null && listBeen.size() > 0) {
            mAdapter.setNewData(listBeen);
        }else {
            mFragmentTrendsListLast.setVisibility(View.GONE);
        }
    }

    @Override
    public void getStoreProductListFail() {
        mFragmentTrendsListLast.setVisibility(View.GONE);
    }


    private void initData() {
        mPresenter.requestStoreProductList(mShopId);
    }

    private void initView() {
        mFragmentTrendsListLast.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mAdapter = new FairDetailNewAdapter(null, getActivity(),mImageLoaderHelper);
        mFragmentTrendsListLast.setAdapter(mAdapter);
    }

    @Override
    public void getStoreCouponListSuccess(ShopDetailCouponListResponse response) {

    }

    @Override
    public void getStoreCouponListFail() {

    }

    @Override
    public void getStoreDetailSuccess(StoreDetailResponse response) {

    }

    @Override
    public void getStoreDetailFail() {

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
        DaggerShopProductDetailFragmentComponent.builder()
                .applicationComponent(((DaggerApplication) getActivity().getApplication()).getApplicationComponent())
                .shopProductDetailActivityModule(new ShopProductDetailActivityModule((AppCompatActivity) getActivity(), this))
                .shopProductDetailFragmentModule(new ShopProductDetailFragmentModule((ShopProductDetailActivity) getActivity())).build()
                .inject(this);
    }
}
