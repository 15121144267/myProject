package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerMainActivityComponent;
import com.banshengyuan.feima.dagger.module.MainActivityModule;
import com.banshengyuan.feima.entity.IntentConstant;
import com.banshengyuan.feima.help.BottomNavigationViewHelper;
import com.banshengyuan.feima.help.DialogFactory;
import com.banshengyuan.feima.view.PresenterControl.MainControl;
import com.banshengyuan.feima.view.adapter.MyFragmentAdapter;
import com.banshengyuan.feima.view.fragment.CommonDialog;
import com.banshengyuan.feima.view.fragment.CompletedOrderFragment;
import com.banshengyuan.feima.view.fragment.ExchangeFragment;
import com.banshengyuan.feima.view.fragment.PendingOrderFragment;
import com.banshengyuan.feima.view.fragment.SendingOrderFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainControl.MainView, BottomNavigationView.OnNavigationItemSelectedListener, CommonDialog.CommonDialogListener {
    public static Intent getMainIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    public static final Integer DIALOG_TYPE_EXIT_OK = 1;
    @BindView(R.id.view_swapper)
    ViewPager mViewSwapper;
    @BindView(R.id.view_bottom_navigation)
    BottomNavigationView mViewBottomNavigation;
    @Inject
    MainControl.PresenterMain mPresenter;
    public static final int SWITCH_FIRST_PAGE = 0;
    public static final int SWITCH_SECOND_PAGE = 1;
    public static final int SWITCH_THIRD_PAGE = 2;
    public static final int SWITCH_FORTH_PAGE = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initializeInjector();
        initView();
        initData();
    }

    private void initData() {
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
        //默认停用滑动效果
        BottomNavigationViewHelper.disableShiftMode(mViewBottomNavigation);
        List<Fragment> fragments = new ArrayList<>();
        PendingOrderFragment pendingOrderFragment = PendingOrderFragment.newInstance();
        SendingOrderFragment sendingOrderFragment = SendingOrderFragment.newInstance();
        ExchangeFragment exchangeFragment = ExchangeFragment.newInstance();
        CompletedOrderFragment completedOrderFragment = CompletedOrderFragment.newInstance();

        fragments.add(pendingOrderFragment);
        fragments.add(sendingOrderFragment);
        fragments.add(exchangeFragment);
        fragments.add(completedOrderFragment);
        MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager(), fragments);
        mViewSwapper.setOffscreenPageLimit(fragments.size());
        mViewSwapper.setAdapter(adapter);
        mViewBottomNavigation.setOnNavigationItemSelectedListener(this);

    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case SWITCH_FIRST_PAGE:
                mViewSwapper.setCurrentItem(SWITCH_FIRST_PAGE);
                mViewBottomNavigation.setSelectedItemId(R.id.action_one);
                break;
            case SWITCH_FORTH_PAGE:
                mViewSwapper.setCurrentItem(SWITCH_FORTH_PAGE);
                break;
        }
        return super.handleMessage(msg);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_one:
                mViewSwapper.setCurrentItem(SWITCH_FIRST_PAGE, false);
                break;
            case R.id.action_two:
                mViewSwapper.setCurrentItem(SWITCH_SECOND_PAGE, false);
                break;
            case R.id.action_three:
                mViewSwapper.setCurrentItem(SWITCH_THIRD_PAGE, false);
                break;
            case R.id.action_four:
                if (mBuProcessor.isValidLogin()) {
                    mViewSwapper.setCurrentItem(SWITCH_FORTH_PAGE, false);
                    CompletedOrderFragment.setmHandler(mHandler);
                } else {
                    switchToMyLogin();
                    return false;
                }
                break;
        }
        return true;
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

    @Override
    protected void onNewIntent(Intent intent) {
        mViewBottomNavigation.setSelectedItemId(R.id.action_one);
        super.onNewIntent(intent);
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


    private void switchToMyLogin() {
        startActivityForResult(LoginActivity.getLoginIntent(MainActivity.this), IntentConstant.ORDER_POSITION_ONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IntentConstant.ORDER_POSITION_ONE && resultCode == RESULT_OK) {
            mViewSwapper.setCurrentItem(SWITCH_FORTH_PAGE, false);
            mViewBottomNavigation.setSelectedItemId(R.id.action_four);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
