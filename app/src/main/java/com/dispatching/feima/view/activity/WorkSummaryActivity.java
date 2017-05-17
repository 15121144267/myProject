package com.dispatching.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.dispatching.feima.BuildConfig;
import com.dispatching.feima.R;
import com.dispatching.feima.dagger.component.DaggerWorkSummaryComponent;
import com.dispatching.feima.dagger.module.WorkSummaryActivityModule;
import com.dispatching.feima.entity.OrderDeliveryResponse;
import com.dispatching.feima.utils.TimeUtil;
import com.dispatching.feima.view.PresenterControl.WorkSummaryControl;

import java.util.Calendar;

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

    private void initData() {
        Calendar calendar = TimeUtil.getCalendar();
        String startTime = TimeUtil.transferLongToDate(TimeUtil.TIME_YYMMDD_HHMMSS1, calendar.getTimeInMillis());
        calendar.add(Calendar.DAY_OF_MONTH, +1);
        String endTime = TimeUtil.transferLongToDate(TimeUtil.TIME_YYMMDD_HHMMSS1, calendar.getTimeInMillis());
        mPresenter.requestAllOrderInfo(mBuProcessor.getUserToken(), BuildConfig.VERSION_NAME,
                mBuProcessor.getUserId(), startTime, endTime);
    }

    @Override
    public void getAllOrderSuccess(OrderDeliveryResponse response) {

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
        DaggerWorkSummaryComponent.builder()
                .applicationComponent(getApplicationComponent())
                .workSummaryActivityModule(new WorkSummaryActivityModule(this))
                .build().inject(this);
    }
}
