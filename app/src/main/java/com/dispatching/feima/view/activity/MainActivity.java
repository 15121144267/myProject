package com.dispatching.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dispatching.feima.BuildConfig;
import com.dispatching.feima.R;
import com.dispatching.feima.dagger.HasComponent;
import com.dispatching.feima.dagger.component.DaggerMainActivityComponent;
import com.dispatching.feima.dagger.component.MainActivityComponent;
import com.dispatching.feima.dagger.module.MainActivityModule;
import com.dispatching.feima.view.PresenterControl.MainControl;
import com.dispatching.feima.view.adapter.MyFragmentAdapter;
import com.dispatching.feima.view.fragment.CompletedOrderFragment;
import com.dispatching.feima.view.fragment.PendingOrderFragment;
import com.dispatching.feima.view.fragment.SendingOrderFragment;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxCompoundButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainControl.MainView,
        NavigationView.OnNavigationItemSelectedListener, HasComponent<MainActivityComponent> {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.nv_sliding_menu)
    NavigationView mNvSlidingMenu;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    private TextView mPersonAccount;
    private TextView mPersonNumber;
    private TextView mPersonStatus;
    private SwitchCompat mPersonStatusControl;

    private ActionBarDrawerToggle mDrawerToggle;
    private String[] modules = {"待取货", "配送中", "已完成"};


    private MainActivityComponent mActivityComponent;
    private MainControl.PresenterMain mPresenter;
    private String mUserToken;
    private String mUserId;
    private String mVersion;
    private List<Fragment> mFragments;

    public static Intent getMainIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        supportActionBar(mToolbar, false);
        initializeInjector();

        mPresenter = mActivityComponent.getPresenterMain();
        mPresenter.setView(this);

        mFragments = new ArrayList<>();
        mFragments.add(new PendingOrderFragment());
        mFragments.add(new SendingOrderFragment());
        mFragments.add(new CompletedOrderFragment());

        mUserToken = mBuProcessor.getUserToken();
        mUserId = mBuProcessor.getUserId();
        mVersion = BuildConfig.VERSION_NAME;

        initView();

    }

    public void changeTabView(int position, int count) {
        TabLayout.Tab tab = mTabLayout.getTabAt(position);
        if(tab!=null){
            switch (tab.getPosition()){
                case 0:
                    tab.setText(count ==0?modules[position]:modules[position]+"("+count+")");
                    break;
                case 1:
                    tab.setText(count ==0?modules[position]:modules[position]+"("+count+")");
                    break;
                case 2:
                    tab.setText(count ==0?modules[position]:modules[position]+"("+count+")");
                    break;
            }
        }
    }

    private void initView() {
        MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager(), mFragments, modules);
        mViewpager.setOffscreenPageLimit(2);
        mViewpager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewpager);

        View view = mNvSlidingMenu.getHeaderView(0);
        LinearLayout topLinearLayout = (LinearLayout) view.findViewById(R.id.person_top);
        mNvSlidingMenu.setItemTextColor(null);
        mNvSlidingMenu.setItemIconTintList(null);
        RxView.clicks(topLinearLayout).throttleFirst(1, TimeUnit.SECONDS).subscribe(v -> requestPersonActivity());
        mPersonAccount = (TextView) view.findViewById(R.id.person_count);
        mPersonNumber = (TextView) view.findViewById(R.id.person_number);
        mPersonStatus = (TextView) view.findViewById(R.id.user_status);
        mPersonStatusControl = (SwitchCompat) view.findViewById(R.id.user_status_control);
        RxCompoundButton.checkedChanges(mPersonStatusControl).subscribe(
                aBoolean -> requestChange(aBoolean));

        mMiddleName.setText(R.string.app_name);
        mNvSlidingMenu.setNavigationItemSelectedListener(this);
        mDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout, R.string.toolbar_des, R.string.toolbar_des);
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);


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
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item);
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
        showBaseToast(message);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public MainActivityComponent getComponent() {
        return mActivityComponent;
    }

    private void initializeInjector() {
        mActivityComponent = DaggerMainActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .mainActivityModule(new MainActivityModule(this))
                .build();
    }
}
