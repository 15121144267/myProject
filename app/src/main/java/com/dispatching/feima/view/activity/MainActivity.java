package com.dispatching.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.dispatching.feima.R;
import com.dispatching.feima.dagger.component.DaggerMainActivityComponent;
import com.dispatching.feima.dagger.module.MainActivityModule;
import com.dispatching.feima.view.PresenterControl.MainControl;
import com.dispatching.feima.view.adapter.MyFragmentAdapter;
import com.dispatching.feima.view.fragment.CompletedOrderFragment;
import com.dispatching.feima.view.fragment.PendingOrderFragment;
import com.dispatching.feima.view.fragment.SendingOrderFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainControl.MainView ,BottomNavigationView.OnNavigationItemSelectedListener{


    @BindView(R.id.view_swapper)
    ViewPager mViewSwapper;
    @BindView(R.id.view_bottom_navigation)
    BottomNavigationView mViewBottomNavigation;

    public static Intent getMainIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Inject
    MainControl.PresenterMain mPresenter;


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
        List<Fragment> fragments = new ArrayList<>();
        PendingOrderFragment pendingOrderFragment = PendingOrderFragment.newInstance();
        SendingOrderFragment sendingOrderFragment = SendingOrderFragment.newInstance();
        CompletedOrderFragment completedOrderFragment = CompletedOrderFragment.newInstance();
        fragments.add(pendingOrderFragment);
        fragments.add(sendingOrderFragment);
        fragments.add(completedOrderFragment);
        MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager(),fragments);
        mViewSwapper.setOffscreenPageLimit(fragments.size());
        mViewSwapper.setAdapter(adapter);
        mViewBottomNavigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
       switch (item.getItemId()){
           case R.id.action_one:
               mViewSwapper.setCurrentItem(0);
               break;
           case R.id.action_two:
               mViewSwapper.setCurrentItem(1);
               break;
           case R.id.action_three:
               mViewSwapper.setCurrentItem(2);
               break;
       }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    private void showDialog() {
       /* CommonDialog commonDialog = CommonDialog.newInstance();
        commonDialog.setContent(getString(R.string.main_exit));
        commonDialog.setListener(this, DIALOG_TYPE_EXIT_OK);
        DialogFactory.showDialogFragment(getSupportFragmentManager(), commonDialog, CommonDialog.TAG);*/
    }


    private void initializeInjector() {
        DaggerMainActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .mainActivityModule(new MainActivityModule(MainActivity.this, this))
                .build().inject(this);
    }

}
