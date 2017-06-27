package com.dispatching.feima.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.dispatching.feima.R;
import com.dispatching.feima.dagger.component.DaggerMyOrderActivityComponent;
import com.dispatching.feima.dagger.module.MyOrderActivityModule;
import com.dispatching.feima.view.PresenterControl.MyOrderControl;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/27.
 * MyOrderActivity
 */

public class MyOrderActivity extends BaseActivity implements MyOrderControl.MyOrderView {
    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.my_orders)
    RecyclerView mMyOrders;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        ButterKnife.bind(this);
        initializeInjector();
        supportActionBar(mToolbar, true);
        mMiddleName.setText("新密码");
        initView();
    }

    private void initView() {

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


    private void initializeInjector() {
        DaggerMyOrderActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .myOrderActivityModule(new MyOrderActivityModule(MyOrderActivity.this, this))
                .build().inject(this);
    }
}
