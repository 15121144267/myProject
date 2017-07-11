package com.dispatching.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.dispatching.feima.R;
import com.dispatching.feima.dagger.component.DaggerMyOrderActivityComponent;
import com.dispatching.feima.dagger.module.MyOrderActivityModule;
import com.dispatching.feima.entity.MyOrdersResponse;
import com.dispatching.feima.view.PresenterControl.MyOrderControl;
import com.dispatching.feima.view.adapter.MyOrdersAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/27.
 * MyOrderActivity
 */

public class MyOrderActivity extends BaseActivity implements MyOrderControl.MyOrderView {

    public static Intent getIntent(Context context) {
        return new Intent(context, MyOrderActivity.class);
    }

    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.my_orders)
    RecyclerView mMyOrders;
    private MyOrdersAdapter mAdapter;
    private List<MyOrdersResponse> mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        ButterKnife.bind(this);
        initializeInjector();
        supportActionBar(mToolbar, true);
        mMiddleName.setText("我的订单");
        initView();
        initData();
    }

    private void initData() {
        for (int i = 0; i < 3; i++) {
            MyOrdersResponse response = new MyOrdersResponse();
            response.productcount = i;
            response.productDes1 = "2017年最新款,最新欧美风 V领模式巴拉巴拉";
            response.productDes2 = "尺码：M 颜色：白色";
            response.productName = "文艺复古连衣裙";
            response.productPrice = 250;
            response.shopName = "淘宝店";
            mList.add(response);
        }
        mAdapter.setNewData(mList);
    }

    private void initView() {
        mList = new ArrayList<>();
        mMyOrders.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyOrdersAdapter(null, this,mImageLoaderHelper);
        mMyOrders.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((adapter, view, position) ->{
                    MyOrdersResponse response = (MyOrdersResponse) adapter.getItem(position);
                    startActivity(OrderDetailActivity.getOrderDetailIntent(this,response));
                }

        );
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                    switch (view.getId()) {
                        case R.id.order_pull_off:
                            showToast("" + position);
                            break;
                        case R.id.order_pull_sure:
                            showToast("" + position);
                            break;
                    }
                }
        );
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
