package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerWorkSummaryComponent;
import com.banshengyuan.feima.dagger.module.WorkSummaryActivityModule;
import com.banshengyuan.feima.entity.MyOrders;
import com.banshengyuan.feima.entity.OrderDeliveryResponse;
import com.banshengyuan.feima.view.PresenterControl.WorkSummaryControl;
import com.banshengyuan.feima.view.adapter.WorkSummaryAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by helei on 2017/4/27.
 * WorkSummaryActivity
 */

public class WorkSummaryActivity extends BaseActivity implements WorkSummaryControl.WorkSummaryView {
    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.order_detail_button)
    Button mOrderSummary;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.order_summary)
    RecyclerView mRecyclerView;
    @Inject
    WorkSummaryControl.PresenterWorkSummary mPresenter;
    private WorkSummaryAdapter mAdapter;

    public static Intent getSummaryIntent(Context context) {
        return new Intent(context, WorkSummaryActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_summary);
        initializeInjector();
        ButterKnife.bind(this);
        supportActionBar(mToolbar, true);
        mMiddleName.setText(R.string.user_work_res);
        initView();
        initData();
    }

    @Override
    public void getAllOrderSuccess(OrderDeliveryResponse response) {
        List<MyOrders> ordersList = response.orders;
        if (ordersList != null) {
            mAdapter.setNewData(ordersList);
            String orderCount = ordersList.size() + "单";
            mOrderSummary.setText(orderCount);
        }


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

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new WorkSummaryAdapter(null, getContext());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData() {

       /* String startTime = TimeUtil.transferLongToDate(TimeUtil.TIME_YYMMDD_HHMMSS1, TimeUtil.getTimesMonthmorning());
        String endTime = TimeUtil.transferLongToDate(TimeUtil.TIME_YYMMDD_HHMMSS1, TimeUtil.getTimesMonthnight());
        mPresenter.requestAllOrderInfo(mBuProcessor.getUserToken(),
                mBuProcessor.getUserId(), startTime, endTime);*/
    }

    private void initializeInjector() {
        DaggerWorkSummaryComponent.builder()
                .applicationComponent(getApplicationComponent())
                .workSummaryActivityModule(new WorkSummaryActivityModule(WorkSummaryActivity.this, this))
                .build().inject(this);
    }
}
