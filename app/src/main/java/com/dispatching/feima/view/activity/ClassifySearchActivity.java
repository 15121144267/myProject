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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dispatching.feima.R;
import com.dispatching.feima.dagger.component.DaggerClassifySearchActivityComponent;
import com.dispatching.feima.dagger.module.ClassifySearchActivityModule;
import com.dispatching.feima.entity.ClassifySearchListResponse;
import com.dispatching.feima.entity.ShopDetailResponse;
import com.dispatching.feima.listener.TabCheckListener;
import com.dispatching.feima.utils.ValueUtil;
import com.dispatching.feima.view.PresenterControl.ClassifySearchControl;
import com.dispatching.feima.view.adapter.ClassifySearchListAdapter;
import com.dispatching.feima.view.customview.ClearEditText;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/5.
 * WelcomeActivity
 */

public class ClassifySearchActivity extends BaseActivity implements ClassifySearchControl.ClassifySearchView, BaseQuickAdapter.RequestLoadMoreListener, ClearEditText.setOnMyEditorActionListener {

    @BindView(R.id.search_goods)
    ClearEditText mSearchGoods;
    @BindView(R.id.search_goods_cancel)
    TextView mSearchGoodsCancel;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.search_product_recyclerView)
    RecyclerView mSearchProductList;
    @BindView(R.id.search_goods_layout)
    LinearLayout mSearchGoodsLayout;
    @BindView(R.id.search_history_layout)
    RecyclerView mSearchHistoryLayout;

    public static Intent getIntent(Context context, String shopId, String nodeId, Integer type) {
        Intent intent = new Intent(context, ClassifySearchActivity.class);
        intent.putExtra("shopId", shopId);
        intent.putExtra("nodeId", nodeId);
        intent.putExtra("searchType", type);
        return intent;
    }

    @Inject
    ClassifySearchControl.PresenterClassifySearch mPresenter;

    private final String[] modules = {"销量", "价格", "新品"};
    private ImageView mTabItemPriceLow;
    private ImageView mTabItemPriceUp;
    private TextView mTabItemPriceGoods;
    private ClassifySearchListAdapter mAdapter;
    private Integer mSearchType;
    private String mShopId;
    private String mNodeId;
    private final Integer mPagerSize = 10;
    private Integer mSaleCountPagerNo = 1;
    private Integer mPricePagerDownNo = 1;
    private Integer mPricePagerUpNo = 1;
    private Integer mNewProductPagerNo = 1;
    private List<ClassifySearchListResponse.DataBean> mSaleCountGoodsList;
    private List<ClassifySearchListResponse.DataBean> mPriceUpGoodsList;
    private List<ClassifySearchListResponse.DataBean> mPriceDownGoodsList;
    private List<ClassifySearchListResponse.DataBean> mNewProductGoodsList;
    private List<ClassifySearchListResponse.DataBean> mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
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
                    mPresenter.requestClassifySearchRequest(mShopId, mNodeId, 2, "saleCount", 1, mSearchType, mPagerSize, ++mSaleCountPagerNo);
                    break;
                case 1:
                    //价格
                    TabLayout.Tab tab = mTabLayout.getTabAt(1);
                    if (tab != null) {
                        if (tab.getTag() == null) return;
                        if ((Integer) tab.getTag() == 1) {
                            mPresenter.requestClassifySearchRequest(mShopId, mNodeId, 2, "finalPrice", 1, mSearchType, mPagerSize, ++mPricePagerDownNo);
                        } else {
                            mPresenter.requestClassifySearchRequest(mShopId, mNodeId, 2, "finalPrice", 2, mSearchType, mPagerSize, ++mPricePagerUpNo);
                        }
                    }
                    break;
                case 2:
                    //新品
                    mPresenter.requestClassifySearchRequest(mShopId, mNodeId, 2, "pid", 2, mSearchType, mPagerSize, ++mNewProductPagerNo);
                    break;
            }

        }
    }

    @Override
    public void onMyEditorAction() {
        String searchName = mSearchGoods.getEditText().trim();
        if (TextUtils.isEmpty(searchName)) {
            showToast("搜索栏不能为空");
        } else {

        }
    }

    @Override
    public void onMyTouchAction() {

    }

    private void initView() {
        mSaleCountGoodsList = new ArrayList<>();
        mPriceUpGoodsList = new ArrayList<>();
        mPriceDownGoodsList = new ArrayList<>();
        mNewProductGoodsList = new ArrayList<>();
        mSearchHistoryLayout.setVisibility(View.GONE);
        mSearchGoodsLayout.setVisibility(View.VISIBLE);
        mShopId = getIntent().getStringExtra("shopId");
        mNodeId = getIntent().getStringExtra("nodeId");
        mSearchType = getIntent().getIntExtra("searchType", 0);
        mSearchGoods.setEditHint("搜索");

        mSearchProductList.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new ClassifySearchListAdapter(null, this);
        mSearchProductList.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(this, mSearchProductList);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
                    ClassifySearchListResponse.DataBean bean = (ClassifySearchListResponse.DataBean) adapter.getItem(position);
                    ShopDetailResponse.ProductsBean productsBean = new ShopDetailResponse.ProductsBean();
                    assert bean != null;
                    productsBean.saleCount = bean.saleCount;
                    productsBean.finalPrice = bean.finalPrice;
                    productsBean.barcode = bean.barcode;
                    productsBean.category = bean.category;
                    productsBean.categoryName = bean.categoryName;
                    productsBean.companyId = bean.companyId;
                    productsBean.customerCode = bean.customerCode;
                    productsBean.name = bean.name;
                    productsBean.originalPrice = bean.originalPrice;
                    productsBean.picture = bean.picture;
                    productsBean.pid = bean.pid;
                    productsBean.saleCount = bean.saleCount;
                    productsBean.sellTimeName = bean.sellTimeName;
                    productsBean.specification = bean.specification;
                    productsBean.status = bean.status;
                    productsBean.type = bean.type;
                    productsBean.unit = bean.unit;
                    List<ShopDetailResponse.ProductsBean.ProductSpecificationBean> list = new ArrayList<>();
                    for (ClassifySearchListResponse.DataBean.ProductSpecificationBean productSpecificationBean : bean.productSpecification) {
                        ShopDetailResponse.ProductsBean.ProductSpecificationBean productBean = new ShopDetailResponse.ProductsBean.ProductSpecificationBean();
                        productBean.size = productSpecificationBean.size;
                        productBean.zipper = productSpecificationBean.zipper;
                        productBean.color = productSpecificationBean.color;
                        productBean.productId = productSpecificationBean.productId;
                        list.add(productBean);
                    }
                    productsBean.productSpecification = list;

                    List<ShopDetailResponse.ProductsBean.SpecificationListBean> list2 = new ArrayList<>();
                    for (ClassifySearchListResponse.DataBean.SpecificationListBean specificationListBean : bean.specificationList) {
                        ShopDetailResponse.ProductsBean.SpecificationListBean specification = new ShopDetailResponse.ProductsBean.SpecificationListBean();
                        specification.partName = specificationListBean.partName;
                        specification.value = specificationListBean.value;
                        list2.add(specification);
                    }
                    productsBean.specificationList = list2;
                    startActivity(GoodDetailActivity.getIntent(this, productsBean));
                }
        );

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

        mSearchGoods.setLinearBackgroundResource(R.drawable.shape_line_grey);
        mSearchGoods.setOnMyEditorActionListener(this, false);
        RxView.clicks(mSearchGoodsCancel).subscribe(o -> onBackPressed());
    }

    private void initData() {
        mPresenter.requestClassifySearchRequest(mShopId, mNodeId, 2, "saleCount", 1, mSearchType, mPagerSize, mSaleCountPagerNo);
    }

    @Override
    public void getProductListSuccess(ClassifySearchListResponse response) {
        mList = response.data;
        if (response.data != null && response.data.size() > 0) {
            switch (mTabLayout.getSelectedTabPosition()) {
                case 0:
                    if (mSaleCountGoodsList.size() == 0) {
                        mAdapter.setNewData(mList);
                    } else {
                        mAdapter.addData(mList);
                    }
                    mSaleCountGoodsList.addAll(mList);
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
                            mPriceUpGoodsList.addAll(mList);
                        } else {
                            if (mPriceDownGoodsList.size() == 0) {
                                mAdapter.setNewData(mList);
                            } else {
                                mAdapter.addData(mList);
                            }
                            mPriceDownGoodsList.addAll(mList);
                        }
                    }
                    break;
                case 2:
                    if (mNewProductGoodsList.size() == 0) {
                        mAdapter.setNewData(mList);
                    } else {
                        mAdapter.addData(mList);
                    }
                    mNewProductGoodsList.addAll(mList);
                    break;
            }
            mAdapter.setNewData(response.data);
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
    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    private void changeStatus(TabLayout.Tab tab) {
        if (tab.getTag() == null) return;
        if (tab.isSelected()) {
            //改变状态
            if ((Integer) tab.getTag() == 1) {
                arrowUiUp();
                tab.setTag(2);
                sortGoodsByPrice(1);

            } else {
                arrowUiDown();
                tab.setTag(1);
                sortGoodsByPrice(2);
            }
        } else {
            //不改变状态
            if ((Integer) tab.getTag() == 1) {
                arrowUiDown();
                tab.setTag(1);
                sortGoodsByPrice(2);
            } else {
                arrowUiUp();
                sortGoodsByPrice(1);
            }
        }
    }

    private void arrowUiUp() {
        mTabItemPriceUp.setBackground(ContextCompat.getDrawable(getContext(), R.mipmap.price_up_blue));
        mTabItemPriceLow.setBackground(ContextCompat.getDrawable(getContext(), R.mipmap.price_up_dark));
    }

    private void arrowUiDown() {
        mTabItemPriceUp.setBackground(ContextCompat.getDrawable(getContext(), R.mipmap.price_up));
        mTabItemPriceLow.setBackground(ContextCompat.getDrawable(getContext(), R.mipmap.price_low));
    }

    private void sortGoodsBySaleCount() {
        mSaleCountGoodsList.clear();
        mSaleCountPagerNo = 1;
        mPresenter.requestClassifySearchRequest(mShopId, mNodeId, 2, "saleCount", 2, mSearchType, mPagerSize, mSaleCountPagerNo);
    }

    private void sortGoodsByNewProduct() {
        mNewProductPagerNo = 1;
        mNewProductGoodsList.clear();
        mPresenter.requestClassifySearchRequest(mShopId, mNodeId, 2, "pid", 2, mSearchType, mPagerSize, mNewProductPagerNo);
    }

    private void sortGoodsByPrice(Integer flag) {
        mPriceDownGoodsList.clear();
        mPriceUpGoodsList.clear();
        mPricePagerDownNo = 1;
        mPricePagerUpNo = 1;
        mPresenter.requestClassifySearchRequest(mShopId, mNodeId, 2, "finalPrice", flag, mSearchType, mPagerSize, 1);
    }

    private TabLayout.Tab addOtherView() {
        View mView = LayoutInflater.from(this).inflate(R.layout.tab_view, (ViewGroup) mTabLayout.getParent(), false);
        mTabItemPriceGoods = (TextView) mView.findViewById(R.id.good_price);
        mTabItemPriceLow = (ImageView) mView.findViewById(R.id.price_low);
        mTabItemPriceUp = (ImageView) mView.findViewById(R.id.price_up);
        TabLayout.Tab tab = mTabLayout.newTab();
        tab.setCustomView(mView);
        tab.setTag(1);
        return tab;
    }

    private void initializeInjector() {
        DaggerClassifySearchActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .classifySearchActivityModule(new ClassifySearchActivityModule(ClassifySearchActivity.this, this))
                .build().inject(this);
    }
}
