package com.dispatching.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.dispatching.feima.R;
import com.dispatching.feima.dagger.component.DaggerNoticeCenterActivityComponent;
import com.dispatching.feima.dagger.module.NoticeCenterActivityModule;
import com.dispatching.feima.database.OrderNotice;
import com.dispatching.feima.entity.QueryParam;
import com.dispatching.feima.listener.OnItemClickListener;
import com.dispatching.feima.utils.TimeUtil;
import com.dispatching.feima.view.PresenterControl.NoticeCenterControl;
import com.dispatching.feima.view.adapter.BaseQuickAdapter;
import com.dispatching.feima.view.adapter.NoticeAdapter;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by helei on 2017/4/27.
 * NoticeCenterActivity
 */

public class NoticeCenterActivity extends BaseActivity implements NoticeCenterControl.NoticeCenterView {

    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.notice_all)
    RecyclerView mRecyclerView;

    @Inject
    NoticeCenterControl.PresenterNoticeCenter mPresenter;

    public static Intent getNoticeIntent(Context context) {
        return new Intent(context, NoticeCenterActivity.class);
    }

    private NoticeAdapter mNoticeAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        ButterKnife.bind(this);
        initializeInjector();
        supportActionBar(mToolbar, true);
        mMiddleName.setText(R.string.user_notice);
        initAdapter();
        Calendar calendar = TimeUtil.getCalendar();
        initData(calendar);

    }

    @Override
    public void querySuccess(List<OrderNotice> list) {
        dismissLoading();
        Collections.sort(list,(o1,o2)->{
            if(o1.getOrderTime().before(o2.getOrderTime())){
                return 1;
            }
            return -1;
        });
        mNoticeAdapter.setNewData(list);
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

    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mNoticeAdapter = new NoticeAdapter(null,getContext());
        mRecyclerView.setAdapter(mNoticeAdapter);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                mPresenter.updateNoticeDB((OrderNotice)adapter.getItem(position));
            }
        });
    }

    @Override
    public void updateSuccess() {
        mNoticeAdapter.notifyDataSetChanged();
    }

    private void initData(Calendar calendar) {
        QueryParam param = new QueryParam();
        param.today = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, +1);
        param.tomorrow = calendar.getTime();
        mPresenter.requestDbNotices(param);
    }

    private void initializeInjector() {
        DaggerNoticeCenterActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .noticeCenterActivityModule(new NoticeCenterActivityModule(NoticeCenterActivity.this,this))
                .build().inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
