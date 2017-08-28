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

public class ClassifySearchActivity extends BaseActivity implements ClassifySearchControl.ClassifySearchView, ClearEditText.setOnMyEditorActionListener {

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

    public static Intent getIntent(Context context, String shopId, String nodeId) {
        Intent intent = new Intent(context, ClassifySearchActivity.class);
        intent.putExtra("shopId", shopId);
        intent.putExtra("nodeId", nodeId);
        return intent;
    }

    @Inject
    ClassifySearchControl.PresenterClassifySearch mPresenter;

    private final String[] modules = {"销量", "价格", "新品"};
    private View mView;
    private ImageView mTabItemPriceLow;
    private ImageView mTabItemPriceUp;
    private TextView mTabItemPriceGoods;
    private ClassifySearchListAdapter mAdapter;
    private String mShopId;
    private String mNodeId;
    private String mSearchName;

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
    public void onMyEditorAction() {
        mSearchName = mSearchGoods.getEditText().trim();

        if (TextUtils.isEmpty(mSearchName)) {
            showToast("搜索栏不能为空");
        } else {

        }
    }

    @Override
    public void onMyTouchAction() {

    }

    private void initView() {
        mSearchHistoryLayout.setVisibility(View.GONE);
        mSearchGoodsLayout.setVisibility(View.VISIBLE);
        mShopId = getIntent().getStringExtra("shopId");
        mNodeId = getIntent().getStringExtra("nodeId");
        mSearchGoods.setEditHint("搜索");

        mSearchProductList.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new ClassifySearchListAdapter(null, this);
        mSearchProductList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
                    ClassifySearchListResponse.DataBean bean = (ClassifySearchListResponse.DataBean) adapter.getItem(position);
                    ShopDetailResponse.ProductsBean productsBean = new ShopDetailResponse.ProductsBean();
                    productsBean.saleCount = bean.resultModel.saleCount;
                    productsBean.finalPrice = bean.resultModel.finalPrice;
                    productsBean.barcode = bean.resultModel.barcode;
                    productsBean.category = bean.resultModel.category;
                    productsBean.categoryName = bean.resultModel.categoryName;
                    productsBean.companyId = bean.resultModel.companyId;
                    productsBean.customerCode = bean.resultModel.customerCode;
                    productsBean.name = bean.resultModel.name;
                    productsBean.originalPrice = bean.resultModel.originalPrice;
                    productsBean.picture = bean.resultModel.picture;
                    productsBean.pid = bean.resultModel.pid;
                    productsBean.saleCount = bean.resultModel.saleCount;
                    productsBean.sellTimeName = bean.resultModel.sellTimeName;
                    productsBean.specification = bean.resultModel.specification;
                    productsBean.status = bean.resultModel.status;
                    productsBean.type = bean.resultModel.type;
                    productsBean.unit = bean.resultModel.unit;
                    List<ShopDetailResponse.ProductsBean.ProductSpecificationBean> list = new ArrayList<>();
                    for (ClassifySearchListResponse.DataBean.ResultModelBean.ProductSpecificationBean productSpecificationBean : bean.resultModel.productSpecification) {
                        ShopDetailResponse.ProductsBean.ProductSpecificationBean productBean = new ShopDetailResponse.ProductsBean.ProductSpecificationBean();
                        productBean.size = productSpecificationBean.size;
                        productBean.zipper = productSpecificationBean.zipper;
                        productBean.color = productSpecificationBean.color;
                        productBean.productId = productSpecificationBean.productId;
                        list.add(productBean);
                    }
                    productsBean.productSpecification = list;

                    List<ShopDetailResponse.ProductsBean.SpecificationListBean> list2 = new ArrayList<>();
                    for (ClassifySearchListResponse.DataBean.ResultModelBean.SpecificationListBean specificationListBean : bean.resultModel.specificationList) {
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
        mPresenter.requestClassifySearchRequest(mShopId, mNodeId, 2, "saleCount", 1);
    }

    @Override
    public void getProductListSuccess(ClassifySearchListResponse response) {
        if (response.data != null && response.data.size() > 0) {
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
        mPresenter.requestClassifySearchRequest(mShopId, mNodeId, 2, "saleCount", 2);
    }

    private void sortGoodsByNewProduct() {
        mPresenter.requestClassifySearchRequest(mShopId, mNodeId, 2, "pid", 2);
    }

    private void sortGoodsByPrice(Integer flag) {
        mPresenter.requestClassifySearchRequest(mShopId, mNodeId, 2, "finalPrice", flag);
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

    private void initializeInjector() {
        DaggerClassifySearchActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .classifySearchActivityModule(new ClassifySearchActivityModule(ClassifySearchActivity.this, this))
                .build().inject(this);
    }
}
