package com.banshengyuan.feima.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.banshengyuan.feima.DaggerApplication;
import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerShopProductDetailFragmentComponent;
import com.banshengyuan.feima.dagger.module.ShopProductDetailActivityModule;
import com.banshengyuan.feima.dagger.module.ShopProductDetailFragmentModule;
import com.banshengyuan.feima.entity.CollectionResponse;
import com.banshengyuan.feima.entity.ShopDetailCouponListResponse;
import com.banshengyuan.feima.entity.ShopDetailProductListResponse;
import com.banshengyuan.feima.entity.StoreDetailResponse;
import com.banshengyuan.feima.view.PresenterControl.ShopProductDetailControl;
import com.banshengyuan.feima.view.activity.ShopProductDetailActivity;
import com.banshengyuan.feima.view.adapter.CouponListAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by helei on 2017/5/3.
 * TrendsFragment
 */

public class CouponListFragment extends BaseFragment implements ShopProductDetailControl.ShopProductDetailView {


    @BindView(R.id.fragment_coupon_list)
    RecyclerView mFragmentCouponList;

    public static CouponListFragment newInstance() {
        return new CouponListFragment();
    }

    @Inject
    ShopProductDetailControl.PresenterShopProductDetail mPresenter;

    private Unbinder unbind;
    private CouponListAdapter mAdapter;
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
        View view = inflater.inflate(R.layout.fragment_coupon, container, false);
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
    public void getCouponInfoSuccess() {
        showToast("领取成功");
    }

    @Override
    public void getStoreCouponListSuccess(ShopDetailCouponListResponse response) {
        List<ShopDetailCouponListResponse.ListBean> listBeen = response.list;
        if (listBeen != null && listBeen.size() > 0) {
            mAdapter.setNewData(listBeen);
        } else {
            mFragmentCouponList.setVisibility(View.GONE);
        }
    }

    @Override
    public void getStoreCouponListFail() {
        mFragmentCouponList.setVisibility(View.GONE);
    }

    private void initData() {
        mPresenter.requestStoreCouponList(mShopId);
    }

    private void initView() {
        mFragmentCouponList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new CouponListAdapter(null, getActivity());
        mFragmentCouponList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            ShopDetailCouponListResponse.ListBean bean = (ShopDetailCouponListResponse.ListBean) adapter.getItem(position);
            if(bean!=null){
                mPresenter.requestCouponInfo(bean.id);
            }
        });
    }

    @Override
    public void getStoreDetailSuccess(StoreDetailResponse response) {

    }

    @Override
    public void getStoreDetailFail() {

    }

    @Override
    public void getStoreProductListSuccess(ShopDetailProductListResponse response) {

    }

    @Override
    public void getStoreProductListFail() {

    }

    @Override
    public void getCollectionSuccess(CollectionResponse response) {

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
