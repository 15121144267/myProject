package com.dispatching.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.dispatching.feima.R;
import com.dispatching.feima.dagger.component.DaggerWorkSummaryComponent;
import com.dispatching.feima.dagger.module.WorkSummaryActivityModule;
import com.dispatching.feima.entity.MyOrders;
import com.dispatching.feima.entity.OrderDeliveryResponse;
import com.dispatching.feima.utils.TimeUtil;
import com.dispatching.feima.view.PresenterControl.WorkSummaryControl;
import com.dispatching.feima.view.adapter.WorkSummaryAdapter;

import java.util.ArrayList;
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
        mPresenter.setView(this);
        ButterKnife.bind(this);
        supportActionBar(mToolbar, true);
        mMiddleName.setText(R.string.user_work_res);
        initView();
        initData();
    }

    @Override
    public void getAllOrderSuccess(OrderDeliveryResponse response) {
        List<MyOrders> completedOrder = new ArrayList<>();
        int completeOrderCount = 0;
        List<MyOrders> ordersList = response.orders;
        if (ordersList != null && ordersList.size() > 0) {
            for (MyOrders myOrders : ordersList) {
                if (myOrders.deliveryStatus == 4) {
                    completeOrderCount ++;
                    completedOrder.add(myOrders);
                }
            }
            mAdapter.setNewData(completedOrder);

        }
        mOrderSummary.setText(completeOrderCount+" 单");

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
        mAdapter = new WorkSummaryAdapter(null,getContext());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData() {

        String startTime = TimeUtil.transferLongToDate(TimeUtil.TIME_YYMMDD_HHMMSS1, TimeUtil.getTimesMonthmorning());
        String endTime = TimeUtil.transferLongToDate(TimeUtil.TIME_YYMMDD_HHMMSS1, TimeUtil.getTimesMonthnight());
        mPresenter.requestAllOrderInfo(mBuProcessor.getUserToken(),
                mBuProcessor.getUserId(), startTime, endTime);
    }

    private void initializeInjector() {
        DaggerWorkSummaryComponent.builder()
                .applicationComponent(getApplicationComponent())
                .workSummaryActivityModule(new WorkSummaryActivityModule(this))
                .build().inject(this);
    }
}
