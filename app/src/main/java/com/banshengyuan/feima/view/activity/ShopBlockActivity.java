package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerShopBlockActivityComponent;
import com.banshengyuan.feima.dagger.module.ShopBlockActivityModule;
import com.banshengyuan.feima.view.PresenterControl.ShopBlockControl;
import com.banshengyuan.feima.view.adapter.ShopMenuAdapter;
import com.banshengyuan.feima.view.customview.CustomPopWindow;

import java.util.ArrayList;
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
    @BindView(R.id.shop_block_shops_text)
    TextView mShopBlockShopsText;
    @BindView(R.id.shop_block_shops_layout)
    LinearLayout mShopBlockShopsLayout;
    @BindView(R.id.shop_block_list)
    RecyclerView mShopBlockList;
    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public static Intent getActivityDetailIntent(Context context) {
        Intent intent = new Intent(context, ShopBlockActivity.class);
        return intent;
    }


    @Inject
    ShopBlockControl.PresenterShopBlock mPresenter;
    private int mWith;
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

    private void initData() {

    }

    private void initView() {
        mShopBlockShopsLayout.setOnClickListener(v -> {
                    List<String> list = new ArrayList<>();
                    for (int i = 0; i < 4; i++) {
                        list.add("魔兽世界" + i);
                    }
                    showPopMenu(mShopBlockShopsLayout, list);
                }
        );
        mShopBlockBlocksLayout.setOnClickListener(v -> {
                    List<String> list = new ArrayList<>();
                    for (int i = 0; i < 4; i++) {
                        list.add("魔兽世界" + i);
                    }
                    showPopMenu(mShopBlockBlocksLayout, list);
                }
        );

        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        mWith= outMetrics.widthPixels;
    }

    private void showPopMenu(View view, List<String> list) {
        View contentView = LayoutInflater.from(this).inflate(R.layout.shop_block_menu, null);
        //处理popWindow 显示内容
        RecyclerView recyclerView = (RecyclerView) contentView.findViewById(R.id.menu_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(ShopBlockActivity.this));
        ShopMenuAdapter adapter = new ShopMenuAdapter(list, ShopBlockActivity.this);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter1, view1, position) ->
                showToast("" + position)
        );
        //创建并显示popWindow
        CustomPopWindow mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(ShopBlockActivity.this)
                .setView(contentView)
                .size(mWith/2, ViewGroup.LayoutParams.WRAP_CONTENT)
                .create()
                .showAsDropDown(view, 0, 0);


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
