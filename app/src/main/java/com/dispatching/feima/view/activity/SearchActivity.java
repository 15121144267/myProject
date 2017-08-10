package com.dispatching.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import com.dispatching.feima.dagger.component.DaggerSearchActivityComponent;
import com.dispatching.feima.dagger.module.SearchActivityModule;
import com.dispatching.feima.entity.SpecificationResponse;
import com.dispatching.feima.listener.TabCheckListener;
import com.dispatching.feima.utils.ValueUtil;
import com.dispatching.feima.view.PresenterControl.SearchControl;
import com.dispatching.feima.view.adapter.SearchHistoryAdapter;
import com.dispatching.feima.view.adapter.ShopDetailAdapter;
import com.dispatching.feima.view.adapter.ShopListAdapter;
import com.dispatching.feima.view.customview.ClearEditText;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/5.
 * WelcomeActivity
 */

public class SearchActivity extends BaseActivity implements SearchControl.SearchView, ClearEditText.setOnMyEditorActionListener {

    @BindView(R.id.search_goods)
    ClearEditText mSearchGoods;
    @BindView(R.id.search_goods_cancel)
    TextView mSearchGoodsCancel;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.search_shops_list_layout)
    RecyclerView mSearchShopsListLayout;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.shop_detail_recyclerView)
    RecyclerView mShopDetailRecyclerView;
    @BindView(R.id.search_goods_layout)
    LinearLayout mSearchGoodsLayout;
    @BindView(R.id.search_history_layout)
    RecyclerView mSearchHistoryLayout;

    public static Intent getIntent(Context context, String type) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra("searchType", type);
        return intent;
    }

    @Inject
    SearchControl.PresenterSearch mPresenter;

    private final String[] modules = {"销量", "价格", "新品"};
    private View mView;
    private ImageView mTabItemPriceLow;
    private ImageView mTabItemPriceUp;
    private TextView mTabItemPriceGoods;
    private ShopDetailAdapter mAdapter;
    private ShopListAdapter mShopListAdapter;
    private SearchHistoryAdapter mSearchHistoryAdapter;

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
        showToast("搜索");
    }

    @Override
    public void onMyTouchAction() {

    }

    private void initView() {
        mSearchGoods.requestFouce();
        mSearchHistoryLayout.setLayoutManager(new LinearLayoutManager(this));
        mSearchHistoryAdapter = new SearchHistoryAdapter(null, this);
        mSearchHistoryLayout.setAdapter(mSearchHistoryAdapter);

        String type = getIntent().getStringExtra("searchType");
        if (!TextUtils.isEmpty(type)) {
            if ("goods".equals(type)) {
                //mSearchGoodsLayout.setVisibility(View.VISIBLE);
                mSearchGoods.setEditHint("搜索商品");
                mShopDetailRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                mAdapter = new ShopDetailAdapter(null, this);
                mShopDetailRecyclerView.setAdapter(mAdapter);

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
                                //sortGoodsBySaleCount(mAllGoodsList);
                                break;
                            case 1:
                                mTabItemPriceGoods.setTextColor(ContextCompat.getColor(getContext(), R.color.light_blue_dark));
                                break;
                            case 2:
                                mTabItemPriceGoods.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                                break;
                        }
                    }
                });
            } else {
                //  mSearchShopsListLayout.setVisibility(View.VISIBLE);
                mSearchGoods.setEditHint("搜索商户");
                mSearchShopsListLayout.setLayoutManager(new LinearLayoutManager(this));
                mShopListAdapter = new ShopListAdapter(null, this, mImageLoaderHelper);
                mSearchShopsListLayout.setAdapter(mAdapter);
            }
        }

        mSearchGoods.setLinearBackgroundResource(R.drawable.shape_line_grey);
        mSearchGoods.setOnMyEditorActionListener(this, false);
        RxView.clicks(mSearchGoodsCancel).subscribe(o -> onBackPressed());
    }

    private void initData() {
        List<SpecificationResponse.ProductsBean.SpecificationListBean> searchHistory = new ArrayList<>();
        String[] strings = {"小梅子店铺", "指甲油幻彩店铺", "木棉落落店", "黑白街", "老铁扎心了"};
        String[] strings2 = {"夏天的衣服", "春天的衣服", "秋天的裤子", "冬天的棉袄", "老司机"};
        List<String> mList = new ArrayList<>();
        List<String> mList2 = new ArrayList<>();
        Collections.addAll(mList, strings);
        Collections.addAll(mList2, strings2);
        SpecificationResponse.ProductsBean.SpecificationListBean bean = new SpecificationResponse.ProductsBean.SpecificationListBean();
        bean.partName = "最近搜索";
        bean.value = mList;
        searchHistory.add(bean);
        SpecificationResponse.ProductsBean.SpecificationListBean bean2 = new SpecificationResponse.ProductsBean.SpecificationListBean();
        bean2.partName = "热门搜索";
        bean2.value = mList2;
        searchHistory.add(bean2);
        mSearchHistoryAdapter.setNewData(searchHistory);
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
                mTabItemPriceUp.setBackground(ContextCompat.getDrawable(getContext(), R.mipmap.price_up_blue));
                mTabItemPriceLow.setBackground(ContextCompat.getDrawable(getContext(), R.mipmap.price_up_dark));
                tab.setTag(2);
                //sortGoodsByPriceDown(mAllGoodsList);

            } else {
                mTabItemPriceUp.setBackground(ContextCompat.getDrawable(getContext(), R.mipmap.price_up));
                mTabItemPriceLow.setBackground(ContextCompat.getDrawable(getContext(), R.mipmap.price_low));
                tab.setTag(1);
                // sortGoodsByPriceUp(mAllGoodsList);
            }
        } else {
            //不改变状态
            if ((Integer) tab.getTag() == 1) {
                mTabItemPriceUp.setBackground(ContextCompat.getDrawable(getContext(), R.mipmap.price_up));
                mTabItemPriceLow.setBackground(ContextCompat.getDrawable(getContext(), R.mipmap.price_low));
                tab.setTag(1);
                //sortGoodsByPriceUp(mAllGoodsList);
            } else {
                mTabItemPriceUp.setBackground(ContextCompat.getDrawable(getContext(), R.mipmap.price_up_blue));
                mTabItemPriceLow.setBackground(ContextCompat.getDrawable(getContext(), R.mipmap.price_up_dark));
                tab.setTag(2);
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

    private void initializeInjector() {
        DaggerSearchActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .searchActivityModule(new SearchActivityModule(SearchActivity.this, this))
                .build().inject(this);
    }
}
