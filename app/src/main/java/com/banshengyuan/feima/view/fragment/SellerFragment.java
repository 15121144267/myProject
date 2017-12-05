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
import com.banshengyuan.feima.entity.FairUnderLineResponse;
import com.banshengyuan.feima.entity.StoreListResponse;
import com.banshengyuan.feima.view.PresenterControl.SellerControl;
import com.banshengyuan.feima.view.activity.MainActivity;
import com.banshengyuan.feima.view.activity.ShopBlockActivity;
import com.banshengyuan.feima.view.adapter.GallerySellerAdapter;
import com.banshengyuan.feima.view.adapter.SellerStoreAdapter;
import com.banshengyuan.feima.view.customview.ClearEditText;
import com.banshengyuan.feima.view.customview.recycleviewgallery.CardScaleHelper;
import com.banshengyuan.feima.view.customview.recycleviewgallery.SpeedRecyclerView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by helei on 2017/5/3.
 * SendingOrderFragment
 */

public class SellerFragment extends BaseFragment implements SellerControl.SellerView, GallerySellerAdapter.SellerClickListener {


    @BindView(R.id.recommend_search)
    ClearEditText mRecommendSearch;
    @BindView(R.id.search_layout)
    LinearLayout mSearchLayout;

    public static SellerFragment newInstance() {
        return new SellerFragment();
    }

    @BindView(R.id.shop_top_gallery)
    SpeedRecyclerView mShopTopGallery;
    @BindView(R.id.shop_bottom_products)
    RecyclerView mShopBottomProducts;
    @Inject
    SellerControl.PresenterSeller mPresenter;
    private Unbinder unbind;
    private SellerStoreAdapter mProductAdapter;
    private CardScaleHelper mCardScaleHelper;
    private GallerySellerAdapter mGallerySellerAdapter;
    private FairUnderLineResponse mBlockBean;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sellers, container, false);
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
    public void getBlockListSuccess(FairUnderLineResponse response) {
        List<FairUnderLineResponse.ListBean> listBeen = response.list;
        if (listBeen != null && listBeen.size() > 0) {
            mBlockBean = response;
            mGallerySellerAdapter.setNewData(listBeen);
        } else {
            mShopTopGallery.setVisibility(View.GONE);
        }

    }

    @Override
    public void getBlockListFail() {
        mShopTopGallery.setVisibility(View.GONE);
    }

    @Override
    public void getStoreListSuccess(StoreListResponse response) {
        List<StoreListResponse.CategoryBean> list = response.category;
        if (list != null && list.size() > 0) {
            mProductAdapter.setNewData(list);
        } else {
            mShopBottomProducts.setVisibility(View.GONE);
        }
    }

    @Override
    public void getStoreListFail() {
        mShopBottomProducts.setVisibility(View.GONE);
    }

    private void initData() {
        //请求街区
        if (mLocationInfo != null) {
            mPresenter.requestBlockList(mLocationInfo.getLongitude(), mLocationInfo.getLatitude());
        } else {
            mPresenter.requestBlockList(0, 0);
        }
        //请求店铺列表
        mPresenter.requestStoreList();

    }

    @Override
    public void sellerClickItemListener(int position) {
        for (int i = 0; i < mBlockBean.list.size(); i++) {
            mBlockBean.list.get(i).select_position = i == position;
        }

        startActivity(ShopBlockActivity.getActivityDetailIntent(getActivity(), mBlockBean, mBlockBean.list.get(position), 0));
    }

    private void initView() {
        mShopTopGallery.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mGallerySellerAdapter = new GallerySellerAdapter(getActivity(), null, mImageLoaderHelper);
        mShopTopGallery.setAdapter(mGallerySellerAdapter);
        mGallerySellerAdapter.setOnItemListener(this);
        mCardScaleHelper = new CardScaleHelper();
        mCardScaleHelper.setCurrentItemPos(0);
        mCardScaleHelper.setScale(1f);
        mCardScaleHelper.setShowLeftCardWidth(15);
        mCardScaleHelper.setPagePadding(3);
        mCardScaleHelper.attachToRecyclerView(mShopTopGallery);

        mShopBottomProducts.setLayoutManager(new LinearLayoutManager(getActivity()));
        mProductAdapter = new SellerStoreAdapter(null, getActivity(), mImageLoaderHelper);
        mShopBottomProducts.setAdapter(mProductAdapter);
        mProductAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            StoreListResponse.CategoryBean categoryBean = (StoreListResponse.CategoryBean) adapter.getItem(position);
            if (categoryBean == null) return;
            switch (view.getId()) {
                case R.id.adapter_fair_more:
                    for (int i = 0; i < mBlockBean.list.size(); i++) {
                        mBlockBean.list.get(i).select_position = false;
                    }
                    startActivity(ShopBlockActivity.getActivityDetailIntent(getActivity(), mBlockBean, null, categoryBean.id));
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
