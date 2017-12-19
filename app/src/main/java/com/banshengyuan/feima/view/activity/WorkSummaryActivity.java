package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerWorkSummaryComponent;
import com.banshengyuan.feima.dagger.module.WorkSummaryActivityModule;
import com.banshengyuan.feima.entity.CollectionResponse;
import com.banshengyuan.feima.entity.FairContentDetailResponse;
import com.banshengyuan.feima.entity.FairPraiseResponse;
import com.banshengyuan.feima.listener.AppBarStateChangeListener;
import com.banshengyuan.feima.utils.ValueUtil;
import com.banshengyuan.feima.view.PresenterControl.WorkSummaryControl;
import com.banshengyuan.feima.view.adapter.WorkSummaryAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by helei on 2017/4/27.
 * WorkSummaryActivity
 */

public class WorkSummaryActivity extends BaseActivity implements WorkSummaryControl.WorkSummaryView, NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.block_detail_background)
    ImageView mBlockDetailBackground;
    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar_right_icon)
    ImageView mToolbarRightIcon;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.fair_detail_title)
    TextView mFairDetailTitle;
    @BindView(R.id.fair_detail_read_count)
    TextView mFairDetailReadCount;
    @BindView(R.id.fair_detail_product_list)
    RecyclerView mFairDetailProductList;
    @BindView(R.id.fair_detail_comment)
    TextView mFairDetailComment;
    @BindView(R.id.fair_detail_collection)
    TextView mFairDetailCollection;
    @BindView(R.id.fair_detail_praise)
    TextView mFairDetailPraise;
    @BindView(R.id.fair_detail_product)
    TextView mFairDetailProduct;
    @BindView(R.id.fair_detail_bottom_layout)
    LinearLayout mFairDetailBottomLayout;
    @BindView(R.id.fair_product_list)
    NavigationView mFairProductList;
    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;

    public static Intent getSummaryIntent(Context context, Integer fairId) {
        Intent intent = new Intent(context, WorkSummaryActivity.class);
        intent.putExtra("fairId", fairId);
        return intent;
    }

    @Inject
    WorkSummaryControl.PresenterWorkSummary mPresenter;
    private WorkSummaryAdapter mAdapter;
    private Integer mFairId;
    private FairContentDetailResponse.InfoBean mInfoBean;
    private List<FairContentDetailResponse.DetailBean> mDetailBeanList;
    private Integer mProductCount = 0;
    private View mHeadView;
    private TextView mHeadTextView;
    private Menu mMenu;
    private BaseQuickAdapter<FairContentDetailResponse.DetailBean.ProductBean, BaseViewHolder> mBaseAdapter;
    private Integer mItemPosition;
    private FairContentDetailResponse.DetailBean.ProductBean itemBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_summary);
        initializeInjector();
        ButterKnife.bind(this);
        supportActionBar(mToolbar, true);
        initView();
        initData();
    }

    @Override
    public void getFairPraiseSuccess(FairPraiseResponse response) {
        if (response.status == 1) {
            ValueUtil.setTextDrawable(this, mFairDetailPraise, R.mipmap.praise_icon_check, 2);
        } else {
            ValueUtil.setTextDrawable(this, mFairDetailPraise, R.mipmap.praise_icon, 2);
        }
    }

    @Override
    public void getFairCollectionSuccess(CollectionResponse response) {
        if (response.status == 1) {
            ValueUtil.setTextDrawable(this, mFairDetailCollection, R.mipmap.shop_detail_collection, 2);
        } else {
            ValueUtil.setTextDrawable(this, mFairDetailCollection, R.mipmap.shop_detail_uncollection, 2);
        }

    }

    @Override
    public void getGoodCollectionSuccess(CollectionResponse response) {
        if (response.status == 1) {
            itemBean.isCollection = true;
        } else {
            itemBean.isCollection = false;
        }
        mBaseAdapter.setData(mItemPosition, itemBean);
    }

    @Override
    public void requestCollection(BaseQuickAdapter<FairContentDetailResponse.DetailBean.ProductBean, BaseViewHolder> adapter,
                                  Integer position, FairContentDetailResponse.DetailBean.ProductBean bean) {
        mBaseAdapter = adapter;
        mItemPosition = position;
        itemBean = bean;
        mPresenter.requestGoodCollection(bean.id);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        startActivity(GoodDetailActivity.getIntent(this, item.getItemId()));
        return false;
    }

    @Override
    public void getFairDetailSuccess(FairContentDetailResponse response) {
        if (response.info != null) {
            mInfoBean = response.info;
            mFairDetailTitle.setText(mInfoBean.name);
            mFairDetailReadCount.setText(" " + mInfoBean.read_count + "");
            mFairDetailComment.setText("" + mInfoBean.comment_count + " 评论");
            mFairDetailCollection.setText("" + mInfoBean.collected_count + " 收藏");
            mFairDetailPraise.setText("" + mInfoBean.zan_count + " 赞");
            mImageLoaderHelper.displayImage(this, mInfoBean.cover_img, mBlockDetailBackground);

        }
        if (response.detail != null) {
            mDetailBeanList = response.detail;
            mAdapter.setNewData(response.detail);
            for (FairContentDetailResponse.DetailBean detailBean : response.detail) {
                if (detailBean.product != null) {
                    mProductCount += detailBean.product.size();
                    for (int i = 0; i < detailBean.product.size(); i++) {
                        MenuItem item = mMenu.add(i, detailBean.product.get(i).id, i, detailBean.product.get(i).name);
                        Glide.with(this).load(detailBean.product.get(i).image).asBitmap()
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                        BitmapDrawable drawable = new BitmapDrawable(null, resource);
                                        item.setIcon(drawable);
                                    }
                                });

                    }
                }

            }
            mFairDetailProduct.setText("" + mProductCount + " 产品");
            mHeadTextView.setText("" + mProductCount + " 产品");
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

    private void initView() {
        mFairId = getIntent().getIntExtra("fairId", 0);
        mHeadView = mFairProductList.getHeaderView(0);
        mHeadTextView = (TextView) mHeadView.findViewById(R.id.product_count_title);
        mFairDetailProductList.setNestedScrollingEnabled(false);
        mToolbarRightIcon.setVisibility(View.VISIBLE);
        mFairProductList.setNavigationItemSelectedListener(this);
        mFairProductList.setItemIconTintList(null);
        mMenu = mFairProductList.getMenu();
        mFairDetailProductList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new WorkSummaryAdapter(null, WorkSummaryActivity.this, mImageLoaderHelper, this);
        mFairDetailProductList.setAdapter(mAdapter);

        RxView.clicks(mFairDetailCollection).subscribe(o -> {
            if (mBuProcessor.isValidLogin()) {
                mPresenter.requestFairCollection(mFairId);
            } else {
                switchToLogin();
            }
        });

        RxView.clicks(mFairDetailProduct).subscribe(o -> {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.END)) {
                mDrawerLayout.closeDrawer(GravityCompat.END);
            } else {
                mDrawerLayout.openDrawer(GravityCompat.END);
            }
        });

        RxView.clicks(mFairDetailPraise).subscribe(o -> {
            if (mBuProcessor.isValidLogin()) {
                mPresenter.requestPraise(mFairId);
            } else {
                switchToLogin();
            }
        });

        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, AppBarStateChangeListener.State state) {
                if (state == State.EXPANDED) {
                    //展开状态
                    mMiddleName.setVisibility(View.GONE);
                    mToolbar.setNavigationIcon(R.mipmap.arrow_left);
                    mToolbarRightIcon.setImageResource(R.mipmap.share_white);
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    mToolbar.setNavigationIcon(R.drawable.vector_arrow_left);
                    mToolbarRightIcon.setImageResource(R.mipmap.common_share);
                    mMiddleName.setVisibility(View.VISIBLE);
                    mMiddleName.setText(TextUtils.isEmpty(mInfoBean.name) ? "未知" : mInfoBean.name);
                } else {
                    //中间状态
                    mMiddleName.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initData() {
        mPresenter.requestFairDetail(mFairId);
    }

    private void initializeInjector() {
        DaggerWorkSummaryComponent.builder()
                .applicationComponent(getApplicationComponent())
                .workSummaryActivityModule(new WorkSummaryActivityModule(WorkSummaryActivity.this, this))
                .build().inject(this);
    }
}
