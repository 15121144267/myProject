package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerShopBlockActivityComponent;
import com.banshengyuan.feima.dagger.module.ShopBlockActivityModule;
import com.banshengyuan.feima.entity.FairUnderLineResponse;
import com.banshengyuan.feima.entity.ShopSortListResponse;
import com.banshengyuan.feima.entity.StoreCategoryListResponse;
import com.banshengyuan.feima.entity.StreetSortListResponse;
import com.banshengyuan.feima.utils.AppDeviceUtil;
import com.banshengyuan.feima.view.PresenterControl.ShopBlockControl;
import com.banshengyuan.feima.view.adapter.ShopListAdapter;
import com.banshengyuan.feima.view.adapter.ShopMenuAdapter;
import com.banshengyuan.feima.view.adapter.StreetMenuAdapter;
import com.banshengyuan.feima.view.customview.CustomPopWindow;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by lei.he on 2017/6/5.
 * WelcomeActivity
 */

public class ShopBlockActivity extends BaseActivity implements ShopBlockControl.ShopBlockView {

    @BindView(R.id.shop_block_blocks_text)
    TextView mShopBlockBlocksText;
    @BindView(R.id.shop_block_blocks_layout)
    LinearLayout mShopBlockBlocksLayout;

    @BindView(R.id.shop_block_shops_layout)
    LinearLayout mShopBlockShopsLayout;
    @BindView(R.id.shop_block_list)
    RecyclerView mShopBlockList;
    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.shop_block_blocks_icon)
    ImageView mShopBlockBlocksIcon;
    @BindView(R.id.shop_block_shop_icon)
    ImageView mShopBlockShopIcon;
    @BindView(R.id.shop_block_shops_text)
    TextView mShopBlockShopsText;

    public static Intent getActivityDetailIntent(Context context, Integer streetId, Integer categoryId) {
        Intent intent = new Intent(context, ShopBlockActivity.class);
        intent.putExtra("streetId", streetId);
        intent.putExtra("categoryId", categoryId);
        return intent;
    }


    @Inject
    ShopBlockControl.PresenterShopBlock mPresenter;
    private int mWith;
    private ShopListAdapter mAdapter;
    private List<ShopSortListResponse.ListBean> mShopSortList;
    private List<StreetSortListResponse.ListBean> mStreetSortList;
    private Integer mStreetId = 0;
    private Integer mCategoryId;
    private CustomPopWindow mCustomPopWindow1;
    private CustomPopWindow mCustomPopWindow2;
    private FairUnderLineResponse.ListBean mListItemBean;
    private ShopSortListResponse mShopListResponse;
    private StreetSortListResponse mStreetSortListResponse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_block);
        initializeInjector();
        ButterKnife.bind(this);
        supportActionBar(mToolbar, true);
        mMiddleName.setText("商家");
        initView();
        initData();

    }

    @Override
    public void getStreetSortListSuccess(StreetSortListResponse response) {
        if (response.list != null && response.list.size() > 0) {
            mStreetSortListResponse = response;
            for (int i = 0; i < response.list.size(); i++) {
                if (response.list.get(i).id == mStreetId) {
                    response.list.get(i).select_position = true;
                    mShopBlockBlocksText.setText("" + response.list.get(i).name + " ");
                }
            }
            mStreetSortList = response.list;
        }
    }

    @Override
    public void getStreetSortListFail(String des) {
        showToast(des);
    }

    @Override
    public void getShopSortListSuccess(ShopSortListResponse response) {
        if (response.list != null && response.list.size() > 0) {
            mShopListResponse = response;
            for (int i = 0; i < response.list.size(); i++) {
                if (response.list.get(i).id == mCategoryId) {
                    response.list.get(i).select_position = true;
                    mShopBlockShopsText.setText("" + response.list.get(i).name + " ");
                }
            }
            mShopSortList = response.list;
        }
    }

    @Override
    public void getShopSortListFail(String des) {
        showToast(des);
    }

    @Override
    public void getShopListSuccess(StoreCategoryListResponse response) {
        mAdapter.setNewData(response.list);
    }

    @Override
    public void getShopListFail(String des) {
        showToast(des);
        mShopBlockList.setVisibility(View.GONE);
    }

    private void initData() {
        mPresenter.requestShopSortList();
        mPresenter.requestStreetSortList();
        updateShopList();
    }

    void updateShopList() {
        mPresenter.requestShopList(mStreetId, mCategoryId);
    }

    private void initView() {
        mStreetId = getIntent().getIntExtra("streetId", 0);
        mCategoryId = getIntent().getIntExtra("categoryId", 0);
        mShopBlockList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ShopListAdapter(null, this, mImageLoaderHelper);
        mShopBlockList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            StoreCategoryListResponse.ListBean bean = (StoreCategoryListResponse.ListBean) adapter.getItem(position);
            if (bean != null) {
                startActivity(ShopProductDetailActivity.getActivityDetailIntent(this, bean.id));
            }
        });

        mShopBlockBlocksLayout.setOnClickListener(v -> {
                    if (mStreetSortListResponse != null) {
                        showStreetPopMenu(mShopBlockBlocksLayout, mStreetSortList);
                        mShopBlockBlocksIcon.setImageResource(R.mipmap.price_up_red);
                        mShopBlockBlocksText.setTextColor(ContextCompat.getColor(this, R.color.light_red));
                    }

                }
        );


        mShopBlockShopsLayout.setOnClickListener(v -> {
                    if (mShopListResponse != null) {
                        showPopMenu(mShopBlockShopsLayout, mShopSortList);
                        mShopBlockShopIcon.setImageResource(R.mipmap.price_up_red);
                        mShopBlockShopsText.setTextColor(ContextCompat.getColor(this, R.color.light_red));
                    }

                }
        );

        mWith = AppDeviceUtil.getDisplayMetrics(this).widthPixels;
    }

    private void showStreetPopMenu(View view, List<StreetSortListResponse.ListBean> list) {
        View contentView = LayoutInflater.from(this).inflate(R.layout.shop_block_menu, (ViewGroup) mShopBlockBlocksLayout.getParent(), false);
        RecyclerView recyclerView = (RecyclerView) contentView.findViewById(R.id.menu_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(ShopBlockActivity.this));
        StreetMenuAdapter adapter = new StreetMenuAdapter(list, ShopBlockActivity.this);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter1, view1, position) -> {
                    for (int i = 0; i < mStreetSortList.size(); i++) {
                        mStreetSortList.get(i).select_position = i == position;
                    }
                    StreetSortListResponse.ListBean listBean = (StreetSortListResponse.ListBean) adapter1.getItem(position);
                    if (listBean != null) {
                        mStreetId = listBean.id;
                        mShopBlockBlocksText.setText(listBean.name+" ");
                        mCustomPopWindow1.dissmiss();
                        updateShopList();
                    }
                }
        );
        mCustomPopWindow1 = new CustomPopWindow.PopupWindowBuilder(ShopBlockActivity.this)
                .setView(contentView)
                .size(mWith / 2, ViewGroup.LayoutParams.WRAP_CONTENT)
                .create()
                .showAsDropDown(view, 0, 0);

        mCustomPopWindow1.getPopupWindow().setOnDismissListener(() -> {
                    mShopBlockBlocksText.setTextColor(ContextCompat.getColor(this, R.color.tab_text_normal));
                    mShopBlockBlocksIcon.setImageResource(R.mipmap.price_down_black);
                }
        );
    }

    private void showPopMenu(View view, List<ShopSortListResponse.ListBean> list) {
        View contentView = LayoutInflater.from(this).inflate(R.layout.shop_block_menu, (ViewGroup) mShopBlockBlocksLayout.getParent(), false);

        RecyclerView recyclerView = (RecyclerView) contentView.findViewById(R.id.menu_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(ShopBlockActivity.this));
        ShopMenuAdapter adapter = new ShopMenuAdapter(list, ShopBlockActivity.this);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter1, view1, position) -> {
                    for (int i = 0; i < mShopSortList.size(); i++) {
                        mShopSortList.get(i).select_position = i == position;
                    }
                    ShopSortListResponse.ListBean listBean = (ShopSortListResponse.ListBean) adapter1.getItem(position);
                    if (listBean != null) {
                        mCategoryId = listBean.id;
                        mShopBlockShopsText.setText(""+listBean.name + " ");
                        mCustomPopWindow2.dissmiss();
                        updateShopList();
                    }
                }
        );

        mCustomPopWindow2 = new CustomPopWindow.PopupWindowBuilder(ShopBlockActivity.this)
                .setView(contentView)
                .size(mWith / 2, ViewGroup.LayoutParams.WRAP_CONTENT)
                .create()
                .showAsDropDown(view, 0, 0);

        mCustomPopWindow2.getPopupWindow().setOnDismissListener(() -> {
                    mShopBlockShopIcon.setImageResource(R.mipmap.price_down_black);
                    mShopBlockShopsText.setTextColor(ContextCompat.getColor(this, R.color.tab_text_normal));
                }
        );
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

    private void initializeInjector() {
        DaggerShopBlockActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .shopBlockActivityModule(new ShopBlockActivityModule(ShopBlockActivity.this, this))
                .build().inject(this);
    }
}
