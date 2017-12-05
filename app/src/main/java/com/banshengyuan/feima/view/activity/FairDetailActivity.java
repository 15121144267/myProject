package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerFairDetailActivityComponent;
import com.banshengyuan.feima.dagger.module.FairDetailActivityModule;
import com.banshengyuan.feima.entity.FairListResponse;
import com.banshengyuan.feima.entity.RecommendBrandResponse;
import com.banshengyuan.feima.listener.AppBarStateChangeListener;
import com.banshengyuan.feima.utils.ValueUtil;
import com.banshengyuan.feima.view.PresenterControl.FairDetailControl;
import com.banshengyuan.feima.view.adapter.MyOrderFragmentAdapter;
import com.banshengyuan.feima.view.fragment.CelebrityFragment;
import com.banshengyuan.feima.view.fragment.CommentFragment;
import com.banshengyuan.feima.view.fragment.FollowFragment;
import com.banshengyuan.feima.view.fragment.TrendsFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/5.
 * WelcomeActivity
 */

public class FairDetailActivity extends BaseActivity implements FairDetailControl.FairDetailView {


    public static Intent getIntent(Context context, Integer flag, Integer fairId) {
        Intent intent = new Intent(context, FairDetailActivity.class);
        intent.putExtra("layout_flag", flag);
        intent.putExtra("fairId", fairId);
        return intent;
    }

    public static Intent getIntent(Context context, Integer flag, FairListResponse.CategoryBean bean) {
        Intent intent = new Intent(context, FairDetailActivity.class);
        intent.putExtra("layout_flag", flag);
        intent.putExtra("categoryBean", bean);
        return intent;
    }

    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.fair_detail_middle_name)
    TextView mMiddleName;
    @BindView(R.id.fair_detail_name)
    TextView mFairDetailName;
    @BindView(R.id.fair_detail_summary)
    TextView mFairDetailSummary;
    @BindView(R.id.toolbar_right_icon)
    ImageView mToolbarRightIcon;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.fair_detail_tab_layout)
    TabLayout mFairDetailTabLayout;
    @BindView(R.id.fair_detail_view_pager)
    ViewPager mFairDetailViewPager;

    @Inject
    FairDetailControl.PresenterFairDetail mPresenter;

    private Integer mFairId;
    private RecommendBrandResponse.ListBean mListBean;
    private FairListResponse.CategoryBean mCategoryBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fair_detail);
        ButterKnife.bind(this);
        supportActionBar(mToolbar, true);
        initializeInjector();
        initView();
        initData();
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

    private void initData() {

    }

    private void initView() {
        Integer layoutFlag = getIntent().getIntExtra("layout_flag", 0);
        mCollapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.white));
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.tab_text_normal));
        mCollapsingToolbarLayout.setCollapsedTitleGravity(Gravity.CENTER);

        String[] modules = {};
        List<String> list = new ArrayList<>();
        List<Fragment> mFragments = new ArrayList<>();
        if (layoutFlag == 1) {
            mFairId = getIntent().getIntExtra("fairId",0);
            /*mMiddleName.setText(mListBean.name);
            mFairDetailName.setText(TextUtils.isEmpty(mListBean.name) ? "未知" : mListBean.name);
            mFairDetailSummary.setText(mListBean.summary);*/
            list.add("最新");
            list.add("商家");
            list.add("产品");
            modules = new String[list.size()];
            list.toArray(modules);
            mFragments.add(FollowFragment.newInstance());
            mFragments.add(CelebrityFragment.newInstance());
            mFragments.add(TrendsFragment.newInstance());
//            mPresenter.request
        } else if (layoutFlag == 2) {
            mCategoryBean = (FairListResponse.CategoryBean) getIntent().getSerializableExtra("categoryBean");
            mFairId = mCategoryBean.id;
            mMiddleName.setText(mCategoryBean.name);
            mFairDetailName.setText(TextUtils.isEmpty(mCategoryBean.name) ? "未知" : mCategoryBean.name);
            mFairDetailSummary.setText(mCategoryBean.summary);
            list.add("市集");
            list.add("产品");
            list.add("点评");
            modules = new String[list.size()];
            list.toArray(modules);
            mFragments.add(FollowFragment.newInstance());
            mFragments.add(TrendsFragment.newInstance());
            mFragments.add(CommentFragment.newInstance());
        }

        MyOrderFragmentAdapter adapter = new MyOrderFragmentAdapter(getSupportFragmentManager(), mFragments, modules);
        mFairDetailViewPager.setOffscreenPageLimit(mFragments.size() - 1);
        mFairDetailViewPager.setAdapter(adapter);
        mFairDetailTabLayout.setupWithViewPager(mFairDetailViewPager);
        ValueUtil.setIndicator(mFairDetailTabLayout, 40, 40);


        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    //展开状态
                    mMiddleName.setVisibility(View.GONE);
                    mToolbar.setNavigationIcon(R.mipmap.arrow_left);
                    mToolbarRightIcon.setImageResource(R.mipmap.share_white);
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    mMiddleName.setVisibility(View.VISIBLE);
                    mToolbar.setNavigationIcon(R.drawable.vector_arrow_left);
                    mToolbarRightIcon.setImageResource(R.mipmap.common_share);
                } else {
                    //中间状态
                    mMiddleName.setVisibility(View.GONE);
                }
            }
        });
    }

    public Integer getFairId() {
        return mFairId;
    }

    private void initializeInjector() {
        DaggerFairDetailActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .fairDetailActivityModule(new FairDetailActivityModule(FairDetailActivity.this, this))
                .build().inject(this);
    }
}
