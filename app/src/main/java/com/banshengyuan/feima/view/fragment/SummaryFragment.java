package com.banshengyuan.feima.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by helei on 2017/5/3.
 * TrendsFragment
 */

public class SummaryFragment extends BaseFragment implements ShopProductDetailControl.ShopProductDetailView {

    @BindView(R.id.fragment_summary_text)
    TextView mFragmentSummaryText;

    public static SummaryFragment newInstance() {
        return new SummaryFragment();
    }

    @Inject
    ShopProductDetailControl.PresenterShopProductDetail mPresenter;

    private Unbinder unbind;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summary, container, false);
        unbind = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData() {

    }

    private void initView() {

    }

    @Override
    public void loadError(Throwable throwable) {

    }

    public void setSummaryText(String summary) {
        mFragmentSummaryText.setText(summary);
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
    public void getCouponInfoSuccess() {

    }

    @Override
    public void getStoreCouponListSuccess(ShopDetailCouponListResponse response) {

    }

    @Override
    public void getStoreCouponListFail() {

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
