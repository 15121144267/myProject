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
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dispatching.feima.R;
import com.dispatching.feima.dagger.HasComponent;
import com.dispatching.feima.dagger.component.DaggerMainActivityComponent;
import com.dispatching.feima.dagger.component.MainActivityComponent;
import com.dispatching.feima.dagger.module.MainActivityModule;
import com.dispatching.feima.entity.BroConstant;
import com.dispatching.feima.entity.QueryParam;
import com.dispatching.feima.entity.SpConstant;
import com.dispatching.feima.help.DialogFactory;
import com.dispatching.feima.utils.TimeUtil;
import com.dispatching.feima.view.PresenterControl.MainControl;
import com.dispatching.feima.view.adapter.MyFragmentAdapter;
import com.dispatching.feima.view.fragment.CommonDialog;
import com.dispatching.feima.view.fragment.CompletedOrderFragment;
import com.dispatching.feima.view.fragment.PendingOrderFragment;
import com.dispatching.feima.view.fragment.SendingOrderFragment;
import com.jakewharton.rxbinding2.widget.RxCompoundButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dispatching.feima.R.id.my_notice;

public class MainActivity extends BaseActivity implements MainControl.MainView,
        NavigationView.OnNavigationItemSelectedListener, HasComponent<MainActivityComponent>, CommonDialog.CommonDialogListener {

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
    public static final int DIALOG_TYPE_EXIT_OK = 1;
    //    private TextView mPersonNumber;
    private TextView mPersonStatus;
    private ActionBarDrawerToggle mDrawerToggle;
    private final String[] modules = {"待取货", "配送中", "已完成"};
    private MainActivityComponent mActivityComponent;
    private MainControl.PresenterMain mPresenter;
    //private String mUserToken;
    //private String mUserId;
    //private String mVersion;
    private List<Fragment> mFragments;
    private TextView noticeCount;
    private QueryParam mQueryParam;

    public static Intent getMainIntent(Context context) {
        return new Intent(context, MainActivity.class);
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
        initData();
        mFragments = new ArrayList<>();
        mFragments.add(new PendingOrderFragment());
        mFragments.add(new SendingOrderFragment());
        mFragments.add(new CompletedOrderFragment());

        initView();

    }

    private void initData() {
        String uid = mSharePreferenceUtil.getStringValue(SpConstant.USER_ID);
        String token = mSharePreferenceUtil.getStringValue(SpConstant.USER_TOKEN);
        if (TextUtils.isEmpty(mBuProcessor.getUserId()) && uid != null) {
            mBuProcessor.setUserId(uid);
            mBuProcessor.setUserToken(token);
        }
        mQueryParam = new QueryParam();
        Calendar calendar = TimeUtil.getCalendar();
        mQueryParam.today = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, +1);
        mQueryParam.tomorrow = calendar.getTime();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mViewpager.setCurrentItem(0);
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(new Intent(BroConstant.PENDING_DELIVERY));
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
            case my_notice:
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
    public void onBackPressed() {
        showDialog();
    }

    @Override
    public void commonDialogBtnOkListener(int type, int position) {
        finish();
        System.exit(0);
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

    public void changeTabView(int position, int count) {
        TabLayout.Tab tab = mTabLayout.getTabAt(position);
        if (tab != null) {
            switch (tab.getPosition()) {
                case 0:
                    tab.setText(count == 0 ? modules[position] : modules[position] + "(" + count + ")");
                    break;
                case 1:
                    tab.setText(count == 0 ? modules[position] : modules[position] + "(" + count + ")");
                    break;
                case 2:
                    tab.setText(count == 0 ? modules[position] : modules[position] + "(" + count + ")");
                    break;
            }
        }
    }

    private void initView() {
        MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager(), mFragments, modules);
        mViewpager.setOffscreenPageLimit(2);
        mViewpager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewpager);
        View actionView = mNvSlidingMenu.getMenu().findItem(R.id.my_notice).getActionView();
        noticeCount = (TextView) actionView.findViewById(R.id.notice_tip);
        View view = mNvSlidingMenu.getHeaderView(0);
        // LinearLayout topLinearLayout = (LinearLayout) view.findViewById(R.id.person_top);
        mNvSlidingMenu.setItemTextColor(null);
        mNvSlidingMenu.setItemIconTintList(null);
        //RxView.clicks(topLinearLayout).throttleFirst(1, TimeUnit.SECONDS).subscribe(v -> requestPersonActivity());
        TextView personAccount = (TextView) view.findViewById(R.id.person_count);
        //mPersonNumber = (TextView) view.findViewById(R.id.person_number);
        mPersonStatus = (TextView) view.findViewById(R.id.user_status);
        SwitchCompat mPersonStatusControl = (SwitchCompat) view.findViewById(R.id.user_status_control);
        personAccount.setText(mSharePreferenceUtil.getStringValue(SpConstant.USER_NAME));
        RxCompoundButton.checkedChanges(mPersonStatusControl).subscribe(
                this::requestChange);

        mMiddleName.setText(R.string.main_tool_name);
        mNvSlidingMenu.setNavigationItemSelectedListener(this);
        mDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout, R.string.toolbar_des, R.string.toolbar_des);
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.requestNoticeCount(mQueryParam);
    }

    @Override
    public void querySuccess(Integer count) {
        if (count == 0) {
            noticeCount.setVisibility(View.GONE);
        } else {
            noticeCount.setVisibility(View.VISIBLE);
            noticeCount.setText(String.valueOf(count));
        }
    }

    private void requestChange(boolean isFlag) {
        if (isFlag) {
            mPersonStatus.setText(R.string.user_status_open);
        } else {
            mPersonStatus.setText(R.string.user_status_close);
        }
    }

    private void showDialog() {
        CommonDialog commonDialog = CommonDialog.newInstance();
        commonDialog.setContent(getString(R.string.main_exit));
        commonDialog.setListener(this, DIALOG_TYPE_EXIT_OK);
        DialogFactory.showDialogFragment(getSupportFragmentManager(), commonDialog, CommonDialog.TAG);
    }

    /*private void requestPersonActivity() {
        startActivity(PersonCenterActivity.getPersonIntent(this));
    }*/

    private void initializeInjector() {
        mActivityComponent = DaggerMainActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .mainActivityModule(new MainActivityModule(this))
                .build();
    }

}
