package com.dispatching.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.dispatching.feima.R;
import com.dispatching.feima.dagger.component.DaggerWorkSummaryComponent;
import com.dispatching.feima.dagger.module.WorkSummaryActivityModule;
import com.dispatching.feima.entity.MyOrders;
import com.dispatching.feima.entity.OrderDeliveryResponse;
import com.dispatching.feima.utils.TimeUtil;
import com.dispatching.feima.view.PresenterControl.WorkSummaryControl;

import java.util.Calendar;
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
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.take_order_count)
    TextView mTakeOrderCount;
    @BindView(R.id.completed_order_count)
    TextView mCompletedOrderCount;
    @Inject
    WorkSummaryControl.PresenterWorkSummary mPresenter;

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
        initData();
    }


    @Override
    public void getAllOrderSuccess(OrderDeliveryResponse response) {
        int takeOrderCount = 0;
        int completeOrderCount = 0;
        List<MyOrders> ordersList = response.orders;
        if (ordersList != null && ordersList.size() > 0) {
            for (MyOrders myOrders : ordersList) {
                if (myOrders.deliveryStatus == 3) {
                    takeOrderCount ++;
                }
                if (myOrders.deliveryStatus == 4) {
                    completeOrderCount ++;
                }
            }
        }
        String transTakeCount =  takeOrderCount  +"单";
        String transCompleteCount =  completeOrderCount  +"单";
        mTakeOrderCount.setText(transTakeCount);
        mCompletedOrderCount .setText(transCompleteCount);
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

    private void initData() {
        Calendar calendar = TimeUtil.getCalendar();
        String startTime = TimeUtil.transferLongToDate(TimeUtil.TIME_YYMMDD_HHMMSS1, calendar.getTimeInMillis());
        calendar.add(Calendar.DAY_OF_MONTH, +1);
        String endTime = TimeUtil.transferLongToDate(TimeUtil.TIME_YYMMDD_HHMMSS1, calendar.getTimeInMillis());
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
