package com.dispatching.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.dispatching.feima.R;
import com.dispatching.feima.dagger.component.DaggerMainActivityComponent;
import com.dispatching.feima.dagger.module.MainActivityModule;
import com.dispatching.feima.view.PresenterControl.MainControl;
import com.dispatching.feima.view.adapter.ViewSwapperAdapter;
import com.example.mylibrary.view.AdaptableBottomNavigationView;
import com.example.mylibrary.view.ViewSwapper;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainControl.MainView {


    @BindView(R.id.view_swapper)
    ViewSwapper mViewSwapper;
    @BindView(R.id.view_bottom_navigation)
    AdaptableBottomNavigationView mViewBottomNavigation;

    public static Intent getMainIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Inject
    MainControl.PresenterMain mPresenter;

    private ViewSwapperAdapter viewSwapperAdapter;

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
        viewSwapperAdapter = new ViewSwapperAdapter(getSupportFragmentManager());
        mViewSwapper.setAdapter(viewSwapperAdapter);
        mViewBottomNavigation.setupWithViewSwapper(mViewSwapper);
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
