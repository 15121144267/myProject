package com.dispatching.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dispatching.feima.R;
import com.dispatching.feima.dagger.component.DaggerMainActivityComponent;
import com.dispatching.feima.dagger.component.MainActivityComponent;
import com.dispatching.feima.dagger.module.MainActivityModule;
import com.dispatching.feima.entity.DataServer;
import com.dispatching.feima.listener.OnItemClickListener;
import com.dispatching.feima.listener.TabCheckListener;
import com.dispatching.feima.utils.ToastUtils;
import com.dispatching.feima.view.PresenterControl.MainControl;
import com.dispatching.feima.view.adapter.BaseQuickAdapter;
import com.dispatching.feima.view.adapter.PullToRefreshAdapter;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxCompoundButton;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainControl.MainView, BaseQuickAdapter.RequestLoadMoreListener,
        NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.nv_sliding_menu)
    NavigationView mNvSlidingMenu;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeLayout;
    @BindView(R.id.middle_name)
    TextView mMiddleName;
    private ImageView mPersonIcon;
    private TextView mPersonAccount;
    private TextView mPersonNumber;
    private TextView mPersonStatus;
    private SwitchCompat mPersonStatusControl;
    private PullToRefreshAdapter pullToRefreshAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private static String[] modules = {"待取货", "配送中", "已完成"};
    private boolean isErr = false;
    private static final int TOTAL_COUNTER = 18;
    private int mCurrentCounter = 0;
    private boolean mLoadMoreEndGone = false;
    private static final int PAGE_SIZE = 6;
    private MainActivityComponent mActivityComponent;
    private MainControl.PresenterMain mPresenter;

    public static Intent getMainIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initializeInjector();
        mPresenter = mActivityComponent.getPresenterMain();
        mPresenter.setView(this);
        initView();
        initAdapter();
    }

    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        pullToRefreshAdapter = new PullToRefreshAdapter();
        pullToRefreshAdapter.setOnLoadMoreListener(this, mRecyclerView);
        pullToRefreshAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mRecyclerView.setAdapter(pullToRefreshAdapter);
        mCurrentCounter = pullToRefreshAdapter.getData().size();
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                startActivity(OrderDetailActivity.getOrderDetailIntent(MainActivity.this));
            }
        });
    }

    private void initView() {
        View view = mNvSlidingMenu.getHeaderView(0);
        LinearLayout topLinearLayout = (LinearLayout) view.findViewById(R.id.person_top);
        mNvSlidingMenu.setItemTextColor(null);
        mNvSlidingMenu.setItemIconTintList(null);
        RxView.clicks(topLinearLayout).throttleFirst(1, TimeUnit.SECONDS).subscribe(v -> requestPersonActivity());
        mPersonIcon = (ImageView) view.findViewById(R.id.person_icon);
        mPersonAccount = (TextView) view.findViewById(R.id.person_count);
        mPersonNumber = (TextView) view.findViewById(R.id.person_number);
        mPersonStatus = (TextView) view.findViewById(R.id.user_status);
        mPersonStatusControl = (SwitchCompat) view.findViewById(R.id.user_status_control);
        RxCompoundButton.checkedChanges(mPersonStatusControl).subscribe(
                aBoolean -> requestChange(aBoolean));
        mTabLayout.addOnTabSelectedListener(new TabCheckListener() {
            @Override
            public void onMyTabSelected(TabLayout.Tab tab) {
                tabSelected(tab);
            }
        });
        mMiddleName.setText(R.string.app_name);
        mNvSlidingMenu.setNavigationItemSelectedListener(this);
        mSwipeLayout.setOnRefreshListener(this);
        supportActionBar(mToolbar, false);
        mDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout, R.string.toolbar_des, R.string.toolbar_des);
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        for (String module : modules) {
            mTabLayout.addTab(mTabLayout.newTab().setText(module));
        }
    }

    private void tabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                //获取服务区推的订单 抢单成功存数据库
                showToast("0");
                mPresenter.requestOrderInfo();
                break;
            case 1:
                //获取数据库存储数据 配送完成存储数据库 查询一天的数据
                pullToRefreshAdapter.setNewData(DataServer.getSampleData(PAGE_SIZE));
                break;
            case 2:
                //获取数据库存储数据
                pullToRefreshAdapter.setNewData(DataServer.getSampleData(PAGE_SIZE));
                break;
        }
    }

    private void requestChange(boolean isFlag) {
        if (isFlag) {
            mPersonStatus.setText(R.string.user_status_open);
        } else {
            mPersonStatus.setText(R.string.user_status_close);
        }
    }

    private void requestPersonActivity() {
        startActivity(PersonCenterActivity.getPersonIntent(this));
    }

    @Override
    public void onRefresh() {
        pullToRefreshAdapter.setEnableLoadMore(false);
        //网络请求
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pullToRefreshAdapter.setNewData(DataServer.getSampleData(PAGE_SIZE));
                isErr = false;
                mCurrentCounter = PAGE_SIZE;
                mSwipeLayout.setRefreshing(false);
                pullToRefreshAdapter.setEnableLoadMore(true);
            }
        }, 2_000);
    }

    @Override
    public void onLoadMoreRequested() {
        mSwipeLayout.setEnabled(false);
        if (pullToRefreshAdapter.getData().size() < PAGE_SIZE) {
            pullToRefreshAdapter.loadMoreEnd(true);
        } else {
            if (mCurrentCounter >= TOTAL_COUNTER) {
                pullToRefreshAdapter.loadMoreEnd(mLoadMoreEndGone);//true is gone,false is visible
            } else {
                if (!isErr) {
                    pullToRefreshAdapter.addData(DataServer.getSampleData(PAGE_SIZE));
                    mCurrentCounter = pullToRefreshAdapter.getData().size();
                    pullToRefreshAdapter.loadMoreComplete();
                } else {
                    isErr = true;
                    Toast.makeText(MainActivity.this, "网络出错", Toast.LENGTH_LONG).show();
                    pullToRefreshAdapter.loadMoreFail();

                }
            }
            mSwipeLayout.setEnabled(true);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.my_notice:
                startActivity(NoticeCenterActivity.getNoticeIntent(this));
                break;
            case R.id.my_summary:
                startActivity(WorkSummaryActivity.getSummaryIntent(this));
                break;
            case R.id.my_setting:
                startActivity(SettingActivity.getSettingIntent(this));
                break;
        }
        return false;
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
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void showToast(String message) {
        ToastUtils.showLongToast(message);
    }

    @Override
    public Context getContext() {
        return this;
    }

    private void initializeInjector() {
        mActivityComponent = DaggerMainActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .mainActivityModule(new MainActivityModule(this))
                .build();
    }
}
