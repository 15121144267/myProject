package com.dispatching.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dispatching.feima.BuildConfig;
import com.dispatching.feima.R;
import com.dispatching.feima.dagger.component.DaggerShopDetailActivityComponent;
import com.dispatching.feima.dagger.module.ShopDetailActivityModule;
import com.dispatching.feima.entity.ShopDetailBannerResponse;
import com.dispatching.feima.entity.ShopDetailResponse;
import com.dispatching.feima.entity.ShopListResponse;
import com.dispatching.feima.entity.ShopResponse;
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

public class ShopDetailActivity extends BaseActivity implements ShopDetailControl.ShopDetailView, BaseQuickAdapter.RequestLoadMoreListener, ClearEditText.setOnMyEditorActionListener {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public static Intent getIntent(Context context, ShopListResponse.ListBean item) {
        Intent intent = new Intent(context, ShopDetailActivity.class);
        intent.putExtra("shopInfo", item);
        return intent;
    }

    @BindView(R.id.search_goods)
    ClearEditText mSearchGoods;
    @BindView(R.id.shop_detail_tool_right)
    LinearLayout mShopDetailToolRight;
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
    private List<String> mImageList;
    private Integer mSaleCountPagerNo = 1;
    private Integer mPricePagerDownNo = 1;
    private Integer mPricePagerUpNo = 1;
    private Integer mNewProductPagerNo = 1;
    private final Integer mPagerSize = 10;
    private ShopListResponse.ListBean mShopInfo;
    private ShopResponse mShopInfo2;
    private List<ShopDetailResponse.ProductsBean> mList;
    private final String[] modules = {"销量", "价格", "新品"};
    private List<ShopDetailResponse.ProductsBean> mSaleCountGoodsList;
    private List<ShopDetailResponse.ProductsBean> mPriceUpGoodsList;
    private List<ShopDetailResponse.ProductsBean> mPriceDownGoodsList;
    private List<ShopDetailResponse.ProductsBean> mNewProductGoodsList;
    private String mStoreCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        ButterKnife.bind(this);
        supportActionBar(mToolbar, true);
        initializeInjector();
        initView();
        initData();
    }

    @Override
    public void onLoadMoreRequested() {

        if (mList.size() < mPagerSize) {
            mAdapter.loadMoreEnd(true);
        } else {
            switch (mTabLayout.getSelectedTabPosition()) {
                case 0:
                    //销量
                    mPresenter.requestShopGoodsList("saleCount", 2, mStoreCode, ++mSaleCountPagerNo, mPagerSize);
                    break;
                case 1:
                    //价格
                    TabLayout.Tab tab = mTabLayout.getTabAt(1);
                    if (tab != null) {
                        if (tab.getTag() == null) return;
                        if ((Integer) tab.getTag() == 1) {
                            mPresenter.requestShopGoodsList("finalPrice", 1, mStoreCode, ++mPricePagerDownNo, mPagerSize);
                        } else {
                            mPresenter.requestShopGoodsList("finalPrice", 2, mStoreCode, ++mPricePagerUpNo, mPagerSize);
                        }
                    }
                    break;
                case 2:
                    //新品
                    mPresenter.requestShopGoodsList("pid", 2, mStoreCode, ++mNewProductPagerNo, mPagerSize);
                    break;
            }

        }
    }

    @Override
    public void transformShopGoodsListSuccess(List<ShopDetailResponse.ProductsBean> products) {
        mList = products;

        switch (mTabLayout.getSelectedTabPosition()) {
            case 0:
                if (mSaleCountGoodsList.size() == 0) {
                    mAdapter.setNewData(mList);
                } else {
                    mAdapter.addData(mList);
                }
                mSaleCountGoodsList.addAll(products);
                break;
            case 1:
                //价格
                TabLayout.Tab tab = mTabLayout.getTabAt(1);
                if (tab != null) {
                    if (tab.getTag() == null) return;
                    if ((Integer) tab.getTag() == 1) {
                        if (mPriceUpGoodsList.size() == 0) {
                            mAdapter.setNewData(mList);
                        } else {
                            mAdapter.addData(mList);
                        }
                        mPriceUpGoodsList.addAll(products);
                    } else {
                        if (mPriceDownGoodsList.size() == 0) {
                            mAdapter.setNewData(mList);
                        } else {
                            mAdapter.addData(mList);
                        }
                        mPriceDownGoodsList.addAll(products);
                    }
                }
                break;
            case 2:
                if (mNewProductGoodsList.size() == 0) {
                    mAdapter.setNewData(mList);
                } else {
                    mAdapter.addData(mList);
                }
                mNewProductGoodsList.addAll(products);
                break;
        }
        if (products.size() > 0) {
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
    public void getShopBannerSuccess(ShopDetailBannerResponse response) {
        for (ShopDetailBannerResponse.DataBean dataBean : response.data) {
            mImageList.add(dataBean.imageUrl);
        }
        mBanner.setImages(mImageList).setImageLoader(new GlideLoader()).start();
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

    @Override
    public void onMyEditorAction() {


    }

    @Override
    public void onMyTouchAction() {
        hideSoftInput(mSearchGoods);
        startActivity(SearchActivity.getIntent(this, "goods",mStoreCode));
    }

    private void initView() {
        mSearchGoods.setOnMyEditorActionListener(this, true);
        mImageList = new ArrayList<>();
        mShopInfo = (ShopListResponse.ListBean) getIntent().getSerializableExtra("shopInfo");
        mShopInfo2 = (ShopResponse) getIntent().getSerializableExtra("ShopResponse");
        if (mShopInfo2 != null) {
            mBuProcessor.setShopResponse(mShopInfo2);
            mImageLoaderHelper.displayRoundedCornerImage(this, mShopInfo2.businessImages.get(0).imageUrl, mShopDetailShopIcon, 6);
            mStoreCode = mShopInfo2.storeCode;
            mShopDetailShopName.setText(mShopInfo2.fullName);
        }

        if (mShopInfo != null) {
            mBuProcessor.setShopInfo(mShopInfo);
            mStoreCode = mShopInfo.storeCode;
            List<ShopListResponse.ListBean.BusinessImagesBean> shopItemInfo = mShopInfo.businessImages;
            if (shopItemInfo.size() != 0) {
                mImageLoaderHelper.displayRoundedCornerImage(this, shopItemInfo.get(0).imageUrl, mShopDetailShopIcon, 6);
            } else {
                mImageLoaderHelper.displayRoundedCornerImage(this, R.mipmap.freemud_logo, mShopDetailShopIcon, 6);
            }
            mShopDetailShopName.setText(mShopInfo.fullName == null ? "未知" : mShopInfo.fullName);
        }


        mList = new ArrayList<>();
        mSaleCountGoodsList = new ArrayList<>();
        mPriceUpGoodsList = new ArrayList<>();
        mPriceDownGoodsList = new ArrayList<>();
        mNewProductGoodsList = new ArrayList<>();
        mShopDetailRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new ShopDetailAdapter(null, this);
        mShopDetailRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(this, mShopDetailRecyclerView);
        mAdapter.setOnItemClickListener((adapter, view, position) ->
                switchToGoodsDetail((ShopDetailResponse.ProductsBean) adapter.getItem(position))
        );

        mSearchGoods.setLinearBackgroundResource(R.drawable.shape_line_grey);
        mSearchGoods.setEditHint("搜索商品");

        RxView.clicks(mShopDetailToolRight).throttleFirst(1, TimeUnit.SECONDS).subscribe(v -> startActivity(GoodsClassifyActivity.getIntent(this, BuildConfig.PARTNER_ID+"_"+mStoreCode)));

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
                switch (tab.getPosition()) {
                    case 0:
                        mTabItemPriceGoods.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                        sortGoodsBySaleCount();
                        break;
                    case 1:
                        mTabItemPriceGoods.setTextColor(ContextCompat.getColor(getContext(), R.color.light_blue_dark));
                        break;
                    case 2:
                        mTabItemPriceGoods.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                        sortGoodsByNewProduct();
                        break;
                }
            }
        });

    }

    private void sortGoodsBySaleCount() {
        mSaleCountPagerNo = 1;
        mSaleCountGoodsList.clear();
        mPresenter.requestShopGoodsList("saleCount", 2, mStoreCode, mSaleCountPagerNo, mPagerSize);

    }

    private void sortGoodsByNewProduct() {
        mNewProductPagerNo = 1;
        mNewProductGoodsList.clear();
        mPresenter.requestShopGoodsList("pid", 2, mStoreCode, mNewProductPagerNo, mPagerSize);
    }

    private void sortGoodsByPrice(Integer flag) {
        mPricePagerDownNo = 1;
        mPricePagerUpNo = 1;
        mPriceDownGoodsList.clear();
        mPriceUpGoodsList.clear();
        mPresenter.requestShopGoodsList("finalPrice", flag, mStoreCode, 1, mPagerSize);

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
                sortGoodsByPrice(1);

            } else {
                mTabItemPriceUp.setBackground(ContextCompat.getDrawable(getContext(), R.mipmap.price_up));
                mTabItemPriceLow.setBackground(ContextCompat.getDrawable(getContext(), R.mipmap.price_low));
                tab.setTag(1);
                sortGoodsByPrice(2);
            }
        } else {
            //不改变状态
            if ((Integer) tab.getTag() == 1) {
                mTabItemPriceUp.setBackground(ContextCompat.getDrawable(getContext(), R.mipmap.price_up));
                mTabItemPriceLow.setBackground(ContextCompat.getDrawable(getContext(), R.mipmap.price_low));
                tab.setTag(1);
                sortGoodsByPrice(2);
            } else {
                mTabItemPriceUp.setBackground(ContextCompat.getDrawable(getContext(), R.mipmap.price_up_blue));
                mTabItemPriceLow.setBackground(ContextCompat.getDrawable(getContext(), R.mipmap.price_up_dark));
                tab.setTag(2);
                sortGoodsByPrice(1);
            }
        }
    }

    private TabLayout.Tab addOtherView() {
        mView = LayoutInflater.from(this).inflate(R.layout.tab_view, (ViewGroup) mTabLayout.getParent(), false);
        mTabItemPriceGoods = (TextView) mView.findViewById(R.id.good_price);
        mTabItemPriceLow = (ImageView) mView.findViewById(R.id.price_low);
        mTabItemPriceUp = (ImageView) mView.findViewById(R.id.price_up);
        TabLayout.Tab tab = mTabLayout.newTab();
        tab.setCustomView(mView);
        tab.setTag(1);
        return tab;
    }

    private void initData() {
        mPresenter.requestShopGoodsList("saleCount", 2, mStoreCode, 1, mPagerSize);
        mPresenter.requestShopBanner("82133fac-4825-418b-be84-0d6a0310ae73","99999");
    }

    private void initializeInjector() {
        DaggerShopDetailActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .shopDetailActivityModule(new ShopDetailActivityModule(ShopDetailActivity.this, this))
                .build().inject(this);
    }
}
