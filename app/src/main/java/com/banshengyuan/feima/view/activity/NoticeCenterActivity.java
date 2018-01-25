package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerNoticeCenterActivityComponent;
import com.banshengyuan.feima.dagger.module.NoticeCenterActivityModule;
import com.banshengyuan.feima.database.OrderNotice;
import com.banshengyuan.feima.entity.NoticeResponse;
import com.banshengyuan.feima.entity.QueryParam;
import com.banshengyuan.feima.utils.TimeUtil;
import com.banshengyuan.feima.view.PresenterControl.NoticeCenterControl;
import com.banshengyuan.feima.view.adapter.NoticeAdapter;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by helei on 2017/4/27.
 * NoticeCenterActivity
 * 我的通知
 */

public class NoticeCenterActivity extends BaseActivity implements NoticeCenterControl.NoticeCenterView, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.notice_all)
    RecyclerView mRecyclerView;
    private int page = 1;
    private final int pageSize = 10;
    private String token;
    private List<NoticeResponse.ListBean> mList = new ArrayList<>();

    @Inject
    NoticeCenterControl.PresenterNoticeCenter mPresenter;

    public static Intent getIntent(Context context) {
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
        mNoticeAdapter = new NoticeAdapter(null, getContext());
        mNoticeAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mNoticeAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
        mRecyclerView.setAdapter(mNoticeAdapter);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
//                mPresenter.updateNoticeDB((OrderNotice) adapter.getItem(position));
            }
        });
    }

    @Override
    public void updateSuccess() {
        mNoticeAdapter.notifyDataSetChanged();
    }

    @Override
    public void queryNoticeListSuccess(NoticeResponse noticeResponse) {
        mList = noticeResponse.getList();
        if (mList.size() > 0) {
            mNoticeAdapter.addData(mList);
            mNoticeAdapter.loadMoreComplete();
        } else {
            mNoticeAdapter.loadMoreEnd();
        }

    }

    private void initData(Calendar calendar) {
        token = mBuProcessor.getUserToken();
        QueryParam param = new QueryParam();
        param.today = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, +1);
        param.tomorrow = calendar.getTime();
//        mPresenter.requestDbNotices(param);

        mPresenter.requestNoticeList(page, pageSize, token);
    }

    private void initializeInjector() {
        DaggerNoticeCenterActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .noticeCenterActivityModule(new NoticeCenterActivityModule(NoticeCenterActivity.this, this))
                .build().inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void onLoadMoreRequested() {
        if (mList.size() < pageSize) {
            mNoticeAdapter.loadMoreEnd();
        } else {
            mPresenter.requestNoticeList(++page, pageSize, token);
        }
    }
}
