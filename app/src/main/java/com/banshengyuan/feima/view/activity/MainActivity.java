package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerMainActivityComponent;
import com.banshengyuan.feima.dagger.module.MainActivityModule;
import com.banshengyuan.feima.help.DialogFactory;
import com.banshengyuan.feima.view.PresenterControl.MainControl;
import com.banshengyuan.feima.view.customview.MainNavigateTabBar;
import com.banshengyuan.feima.view.fragment.CommonDialog;
import com.banshengyuan.feima.view.fragment.CompletedOrderFragment;
import com.banshengyuan.feima.view.fragment.PendingOrderFragment;
import com.banshengyuan.feima.view.fragment.SendingOrderFragment;
import com.banshengyuan.feima.view.fragment.ShoppingCardFragment;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainControl.MainView, CommonDialog.CommonDialogListener, MainNavigateTabBar.OnTabSelectedListener {


    public static Intent getMainIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    public static final Integer DIALOG_TYPE_EXIT_OK = 1;
    private final String first_tag = "发现";
    private final String second_tag = "精选";
    private final String third_tag = " ";
    private final String fourth_tag = "TA";
    private final String fifth_tag = "我的";
    private int mFromIndex;
    private int mTargetIndex;
    private AtomicBoolean isSwitchTab = new AtomicBoolean(false);
    @BindView(R.id.mainTabBar)
    MainNavigateTabBar mNavigateTabBar;

    @Inject
    MainControl.PresenterMain mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initializeInjector();
        mNavigateTabBar.onRestoreInstanceState(savedInstanceState);
        initView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mNavigateTabBar.onSaveInstanceState(outState);
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

    private void initView() {

        mNavigateTabBar.addTab(PendingOrderFragment.class, new MainNavigateTabBar.TabParam(R.mipmap.tab_bar_main, R.mipmap.tab_bar_main_selected, first_tag));
        mNavigateTabBar.addTab(SendingOrderFragment.class, new MainNavigateTabBar.TabParam(R.mipmap.tab_bar_msg, R.mipmap.tab_bar_msg_selected, second_tag));
        mNavigateTabBar.addTab(null, new MainNavigateTabBar.TabParam(0, 0, third_tag));
        mNavigateTabBar.addTab(ShoppingCardFragment.class, new MainNavigateTabBar.TabParam(R.mipmap.tab_bar_quick_consult, R.mipmap.tab_bar_quick_consult_selected, fourth_tag));
        mNavigateTabBar.addTab(CompletedOrderFragment.class, new MainNavigateTabBar.TabParam(R.mipmap.tab_bar_personal, R.mipmap.tab_bar_personal_selected, fifth_tag));
        mNavigateTabBar.setSelectedTabTextColor(getResources().getColor(R.color.text_color_blue));
        mNavigateTabBar.setTabSelectListener(this);
       /* //默认停用滑动效果
        BottomNavigationViewHelper.disableShiftMode(mViewBottomNavigation);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(PendingOrderFragment.newInstance());
        fragments.add(SendingOrderFragment.newInstance());
        fragments.add(ShoppingCardFragment.newInstance());
        fragments.add(CompletedOrderFragment.newInstance());

        MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager(), fragments);
        mViewSwapper.setOffscreenPageLimit(fragments.size());
        mViewSwapper.setAdapter(adapter);
        mViewBottomNavigation.setOnNavigationItemSelectedListener(this);*/
    }

    /*@Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_one:
                mViewSwapper.setCurrentItem(0);
                break;
            case R.id.action_two:
                mViewSwapper.setCurrentItem(1);
                break;
            case R.id.action_three:
                mViewSwapper.setCurrentItem(2);
                break;
            case R.id.action_four:
                mViewSwapper.setCurrentItem(3);
                break;
        }
        return true;
    }*/
    @Override
    public void onTabSelected(int fromIndex, MainNavigateTabBar.ViewHolder holder) {

        isSwitchTab.set(true);
        mFromIndex = fromIndex;
        mTargetIndex = holder.tabIndex;
        synchronized (this) {
            mNavigateTabBar.setCurrentSelectedTab(mTargetIndex);
        }
        isSwitchTab.set(false);
    }

    @Override
    public void commonDialogBtnOkListener(int type, int position) {
        switch (type) {
            case 1:
                finish();
                System.exit(0);
                break;
        }
    }

    public void onClickPublish(View v) {
       showToast("添加集市");
    }

    private void showDialog() {
        CommonDialog commonDialog = CommonDialog.newInstance();
        commonDialog.setContent(getString(R.string.main_exit));
        commonDialog.setListener(this, DIALOG_TYPE_EXIT_OK);
        DialogFactory.showDialogFragment(getSupportFragmentManager(), commonDialog, CommonDialog.TAG);
    }

    private void initializeInjector() {
        DaggerMainActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .mainActivityModule(new MainActivityModule(MainActivity.this, this))
                .build().inject(this);
    }

}
