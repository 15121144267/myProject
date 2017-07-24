package com.dispatching.feima.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dispatching.feima.DaggerApplication;
import com.dispatching.feima.R;
import com.dispatching.feima.dagger.component.DaggerFragmentComponent;
import com.dispatching.feima.dagger.module.FragmentModule;
import com.dispatching.feima.dagger.module.MainActivityModule;
import com.dispatching.feima.entity.BroConstant;
import com.dispatching.feima.entity.MainProducts;
import com.dispatching.feima.entity.ShopResponse;
import com.dispatching.feima.help.GlideLoader;
import com.dispatching.feima.view.PresenterControl.PendingOrderControl;
import com.dispatching.feima.view.activity.ActivitiesActivity;
import com.dispatching.feima.view.activity.BrandActivity;
import com.dispatching.feima.view.activity.MainActivity;
import com.dispatching.feima.view.activity.MusicActivity;
import com.dispatching.feima.view.activity.PartCarActivity;
import com.dispatching.feima.view.activity.SearchActivity;
import com.dispatching.feima.view.activity.ShopDetailActivity;
import com.dispatching.feima.view.activity.ShopListActivity;
import com.dispatching.feima.view.activity.SkyFlowerActivity;
import com.dispatching.feima.view.adapter.MainProductsAdapter;
import com.dispatching.feima.view.customview.ClearEditText;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by helei on 2017/5/3.
 * PendingOrderFragment
 */

public class PendingOrderFragment extends BaseFragment implements PendingOrderControl.PendingOrderView {


    @BindView(R.id.search_shop)
    ClearEditText mSearchShop;
    @BindView(R.id.products_item)
    RecyclerView mProductsItem;
    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.right_down_drawable)
    ImageView mRightDownDrawable;
    @BindView(R.id.left_drawable)
    ImageView mLeftDrawable;
    @BindView(R.id.right_drawable)
    ImageView mRightDrawable;

    public static PendingOrderFragment newInstance() {
        return new PendingOrderFragment();
    }


    @Inject
    PendingOrderControl.PresenterPendingOrder mPresenter;

    private Unbinder unbinder;
    private MainProductsAdapter mAdapter;
    private List<MainProducts> mList;
    private List<Integer> mImageList;
    private String[] productNames = {"品牌", "活动", "商户", "停车", "魔门音乐", "空中花市", "会员积分", "更多"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pending_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initAdapter();
        return view;
    }

    private void initView() {
        mSearchShop.setOnMyEditorActionListener(() -> {
            hideSoftInput(mSearchShop);
            startActivity(SearchActivity.getIntent(getActivity()));
        });
        mSearchShop.setEditHint("搜索商户");
        mList = new ArrayList<>();
        mImageList = new ArrayList<>();
        mImageList.add(R.mipmap.main_banner_first);
        mImageList.add(R.mipmap.main_banner_second);
        mImageList.add(R.mipmap.main_banner_third);
        mBanner.setImages(mImageList).setImageLoader(new GlideLoader()).start();
        mBanner.setOnBannerListener(this::requestShopId);
        mRightDownDrawable.setOnClickListener(this::imageClick);
        mLeftDrawable.setOnClickListener(this::imageClick1);
        mRightDrawable.setOnClickListener(this::imageClick2);
    }


    private void imageClick(View v) {
        startActivity(MusicActivity.getIntent(getActivity()));
    }

    private void imageClick1(View v) {
        mPresenter.requestShopId("107", 3);
    }

    private void imageClick2(View v) {
        mPresenter.requestShopId("107", 3);
    }

    private void requestShopId(int position) {
        switch (position) {
            case 0:
                startActivity(MusicActivity.getIntent(getActivity()));
                break;
            case 1:
                mPresenter.requestShopId("107", 3);
                break;
            case 2:
                mPresenter.requestShopId("107", 3);
                break;

        }

    }

    @Override
    public void getShopSuccess(ShopResponse response) {
        Intent intent = new Intent(getActivity(), ShopDetailActivity.class);
        intent.putExtra("ShopResponse",response);
        startActivity(intent);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData() {

        Drawable[] productDrawable = {
                ContextCompat.getDrawable(getActivity(), R.mipmap.product_brand),
                ContextCompat.getDrawable(getActivity(), R.mipmap.product_activity),
                ContextCompat.getDrawable(getActivity(), R.mipmap.product_shop),
                ContextCompat.getDrawable(getActivity(), R.mipmap.product_part),
                ContextCompat.getDrawable(getActivity(), R.mipmap.product_music),
                ContextCompat.getDrawable(getActivity(), R.mipmap.product_flower),
                ContextCompat.getDrawable(getActivity(), R.mipmap.product_score),
                ContextCompat.getDrawable(getActivity(), R.mipmap.product_more)
        };


        for (int i = 0; i < productNames.length; i++) {
            MainProducts product = new MainProducts();
            product.productName = productNames[i];
            product.productDrawable = productDrawable[i];
            mList.add(product);
        }
        mAdapter.setNewData(mList);

    }

    private void initAdapter() {

        mProductsItem.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mAdapter = new MainProductsAdapter(mList, getActivity());
        mProductsItem.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
                    switch (position) {
                        case 0:
                            startActivity(BrandActivity.getBrandIntent(getActivity()));
                            break;
                        case 1:
                            startActivity(ActivitiesActivity.getActivitiesIntent(getActivity()));
                            break;
                        case 2:
                            startActivity(ShopListActivity.getIntent(getActivity()));
                            break;
                        case 3:
                            startActivity(PartCarActivity.getIntent(getActivity()));
                            break;
                        case 4:
                            startActivity(MusicActivity.getIntent(getActivity()));
                            break;
                        case 5:
                            startActivity(SkyFlowerActivity.getIntent(getActivity()));
                            break;
                        case 6:
                            break;
                        case 7:
                            break;


                    }
                }
        );
    }


    @Override
    protected void addFilter() {
        mFilter.addAction(BroConstant.PENDING_DELIVERY);
    }

    @Override
    protected void onReceivePro(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action) {
            case BroConstant.PENDING_DELIVERY:

                break;
        }
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
    public void onResume() {
        super.onResume();
        mBanner.startAutoPlay();
    }

    @Override
    public void onPause() {
        super.onPause();
        mBanner.stopAutoPlay();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    private void initialize() {
        DaggerFragmentComponent.builder()
                .applicationComponent(((DaggerApplication) getActivity().getApplication()).getApplicationComponent())
                .mainActivityModule(new MainActivityModule((AppCompatActivity) getActivity()))
                .fragmentModule(new FragmentModule(this, (MainActivity) getActivity())).build()
                .inject(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
