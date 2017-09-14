package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.MainProducts;
import com.banshengyuan.feima.view.adapter.BrandAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/5.
 * WelcomeActivity
 */

public class BrandActivity extends BaseActivity {
    public static Intent getBrandIntent(Context context) {
        return new Intent(context, BrandActivity.class);
    }

    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar_right_icon)
    ImageView mToolbarRightIcon;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.brand_recycle_view)
    RecyclerView mBrandRecycleView;
    private String[] productNames = {"LMS 服装店", "卷福餐厅", "大创生活馆", "敬请期待"};
    private List<MainProducts> mList;
    private BrandAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand);
        ButterKnife.bind(this);
        supportActionBar(mToolbar, true);
        mMiddleName.setText("品牌");
        mToolbarRightIcon.setVisibility(View.VISIBLE);
        initView();
        initData();
    }

    private void initData() {
        Drawable[] productDrawable = {
                ContextCompat.getDrawable(this, R.mipmap.brand_lms),
                ContextCompat.getDrawable(this, R.mipmap.brand_fujuan),
                ContextCompat.getDrawable(this, R.mipmap.brand_dachuang),
                ContextCompat.getDrawable(this, R.mipmap.brand_more),
        };
        for (int i = 0; i < productNames.length; i++) {
            MainProducts product = new MainProducts();
            product.productName = productNames[i];
            product.productDrawable = productDrawable[i];
            mList.add(product);
        }
        mAdapter.setNewData(mList);

    }

    private void initView() {
        mList = new ArrayList<>();
        mBrandRecycleView.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new BrandAdapter(null, this);
        mBrandRecycleView.setAdapter(mAdapter);
    }
}
