package com.dispatching.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dispatching.feima.R;
import com.dispatching.feima.dagger.component.DaggerShopDetailActivityComponent;
import com.dispatching.feima.dagger.module.ShopDetailActivityModule;
import com.dispatching.feima.entity.ShopDetailResponse;
import com.dispatching.feima.help.GlideLoader;
import com.dispatching.feima.listener.TabCheckListener;
import com.dispatching.feima.utils.ValueUtil;
import com.dispatching.feima.view.PresenterControl.ShopDetailControl;
import com.dispatching.feima.view.adapter.ShopDetailAdapter;
import com.dispatching.feima.view.customview.ClearEditText;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.jakewharton.rxbinding2.view.RxView;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/30.
 */

public class ShopDetailActivity extends BaseActivity implements ShopDetailControl.ShopDetailView, BaseQuickAdapter.RequestLoadMoreListener {


    public static Intent getIntent(Context context) {
        return new Intent(context, ShopDetailActivity.class);
    }

    @BindView(R.id.search_goods)
    ClearEditText mSearchGoods;
    @BindView(R.id.shop_detail_tool_left)
    ImageView mShopDetailToolLeft;
    @BindView(R.id.shop_detail_tool_right)
    ImageView mShopDetailToolRight;
    @BindView(R.id.shop_detail_shop_icon)
    ImageView mShopDetailShopIcon;
    @BindView(R.id.shop_detail_shop_name)
    TextView mShopDetailShopName;

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.shop_detail_recyclerView)
    RecyclerView mShopDetailRecyclerView;
    @BindView(R.id.banner)
    Banner mBanner;
    @Inject
    ShopDetailControl.PresenterShopDetail mPresenter;

    private View mView;
    private ImageView mTabItemPriceLow;
    private ImageView mTabItemPriceUp;
    private TextView mTabItemPriceGoods;
    private ShopDetailAdapter mAdapter;
    private List<Integer> mImageList;
    private Integer mPagerNo = 1;
    private final Integer mPagerSize = 8;
    private List<ShopDetailResponse.ProductsBean> mList;
    private final String[] modules = {"销量", "价格", "新品"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        ButterKnife.bind(this);
        initializeInjector();
        initView();
        initData();
    }

    @Override
    public void onLoadMoreRequested() {
        if (mList.size() < mPagerSize) {
            mAdapter.loadMoreEnd(true);
        } else {
            mPresenter.requestShopGoodsList(++mPagerNo, mPagerSize);
        }
    }

    @Override
    public void transformShopGoodsListSuccess(List<ShopDetailResponse.ProductsBean> products) {
        mList = products;
        if (products.size() > 0) {
            mAdapter.addData(mList);
            mAdapter.loadMoreComplete();
        } else {
            mAdapter.loadMoreEnd();
        }
    }

    @Override
    public void loadFail(Throwable throwable) {
        showErrMessage(throwable);
        mAdapter.loadMoreFail();
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
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    private void initView() {
        mImageList = new ArrayList<>();
        mImageList.add(R.mipmap.main_banner_first);
        mImageList.add(R.mipmap.main_banner_second);
        mImageList.add(R.mipmap.main_banner_third);
        mBanner.setImages(mImageList).setImageLoader(new GlideLoader()).start();
        mList = new ArrayList<>();
        mImageLoaderHelper.displayRoundedCornerImage(this, R.mipmap.neo, mShopDetailShopIcon, 6);
        mShopDetailRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new ShopDetailAdapter(null, this);
        mShopDetailRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(this, mShopDetailRecyclerView);
        mAdapter.setOnItemClickListener((adapter, view, position) ->
                switchToGoodsDetail((ShopDetailResponse.ProductsBean) adapter.getItem(position))
        );

        mSearchGoods.setLinearBackgroundResource(R.drawable.shape_line_grey);
        mSearchGoods.setEditHint("搜索商品");

        RxView.clicks(mShopDetailToolLeft).throttleFirst(1, TimeUnit.SECONDS).subscribe(v -> onBackPressed());
        RxView.clicks(mShopDetailToolRight).throttleFirst(1, TimeUnit.SECONDS).subscribe(v -> showToast("点我啊"));

        mTabLayout.addTab(mTabLayout.newTab().setText(modules[0]));
        mTabLayout.addTab(addOtherView());
        mTabLayout.addTab(mTabLayout.newTab().setText(modules[2]));
        ValueUtil.setIndicator(mTabLayout, 40, 40);
        TabLayout.Tab tab = mTabLayout.getTabAt(1);
        if (tab != null) {
            if (tab.getCustomView() != null) {
                View view = (View) tab.getCustomView().getParent();
                view.setOnClickListener(v -> changeStatus(tab));
            }
        }
        mTabLayout.addOnTabSelectedListener(new TabCheckListener() {
            @Override
            public void onMyTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 1) {
                    mTabItemPriceGoods.setTextColor(ContextCompat.getColor(getContext(), R.color.light_blue_dark));
                } else {
                    mTabItemPriceGoods.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                }
            }
        });


    }

    private void switchToGoodsDetail(ShopDetailResponse.ProductsBean goodsInfo) {
        startActivity(GoodDetailActivity.getIntent(this, goodsInfo));
    }

    private void changeStatus(TabLayout.Tab tab) {
        if (tab.getTag() == null) return;
        if (tab.isSelected()) {
            //改变状态
            if ((Integer) tab.getTag() == 1) {
                mTabItemPriceUp.setBackground(ContextCompat.getDrawable(getContext(), R.mipmap.price_up_blue));
                mTabItemPriceLow.setBackground(ContextCompat.getDrawable(getContext(), R.mipmap.price_up_dark));
                tab.setTag(2);
            } else {
                mTabItemPriceUp.setBackground(ContextCompat.getDrawable(getContext(), R.mipmap.price_up));
                mTabItemPriceLow.setBackground(ContextCompat.getDrawable(getContext(), R.mipmap.price_low));
                tab.setTag(1);
            }
        } else {
            //不改变状态
            if ((Integer) tab.getTag() == 1) {
                mTabItemPriceUp.setBackground(ContextCompat.getDrawable(getContext(), R.mipmap.price_up));
                mTabItemPriceLow.setBackground(ContextCompat.getDrawable(getContext(), R.mipmap.price_low));
                tab.setTag(1);
            } else {
                mTabItemPriceUp.setBackground(ContextCompat.getDrawable(getContext(), R.mipmap.price_up_blue));
                mTabItemPriceLow.setBackground(ContextCompat.getDrawable(getContext(), R.mipmap.price_up_dark));
                tab.setTag(2);
            }
        }
    }

    private TabLayout.Tab addOtherView() {
        mView = LayoutInflater.from(this).inflate(R.layout.tab_view, null);
        mTabItemPriceGoods = (TextView) mView.findViewById(R.id.good_price);
        mTabItemPriceLow = (ImageView) mView.findViewById(R.id.price_low);
        mTabItemPriceUp = (ImageView) mView.findViewById(R.id.price_up);
        TabLayout.Tab tab = mTabLayout.newTab();
        tab.setCustomView(mView);
        tab.setTag(1);
        return tab;
    }

    private void initData() {
        mPresenter.requestShopGoodsList(mPagerNo, mPagerSize);
    }

    private void initializeInjector() {
        DaggerShopDetailActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .shopDetailActivityModule(new ShopDetailActivityModule(ShopDetailActivity.this, this))
                .build().inject(this);
    }
}
