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
import com.dispatching.feima.listener.OnItemClickListener;
import com.dispatching.feima.utils.ToastUtils;
import com.dispatching.feima.view.adapter.BaseQuickAdapter;
import com.dispatching.feima.view.adapter.NoticeAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by helei on 2017/4/27.
 */

public class NoticeCenterActivity extends BaseActivity {

    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.notice_all)
    RecyclerView mRecyclerView;
    private NoticeAdapter mNoticeAdapter;
    public static Intent getNoticeIntent(Context context) {
        Intent intent = new Intent(context, NoticeCenterActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        ButterKnife.bind(this);
        supportActionBar(mToolbar,true);
        mMiddleName.setText(R.string.user_notice);
        initAdapter();
    }

    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mNoticeAdapter = new NoticeAdapter();
        mRecyclerView.setAdapter(mNoticeAdapter);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                ToastUtils.showShortToast(position+"");
            }
        });
    }


}
