package com.dispatching.feima.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dispatching.feima.BuildConfig;
import com.dispatching.feima.R;
import com.dispatching.feima.dagger.component.MainActivityComponent;
import com.dispatching.feima.entity.BroConstant;
import com.dispatching.feima.entity.IntentConstant;
import com.dispatching.feima.entity.OrderDeliveryResponse;
import com.dispatching.feima.listener.OnItemClickListener;
import com.dispatching.feima.view.PresenterControl.CompletedOrderControl;
import com.dispatching.feima.view.activity.MainActivity;
import com.dispatching.feima.view.activity.OrderDetailActivity;
import com.dispatching.feima.view.adapter.BaseQuickAdapter;
import com.dispatching.feima.view.adapter.PullToRefreshAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by helei on 2017/5/3.
 * CompletedOrderFragment
 */

public class CompletedOrderFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        CompletedOrderControl.CompletedOrderView {
    @Inject
    CompletedOrderControl.PresenterCompletedOrder mPresenter;

    @BindView(R.id.completed_rv_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.completed_swipeLayout)
    SwipeRefreshLayout mSwipeLayout;

    private PullToRefreshAdapter mCompleteAdapter;
    private String mUserToken;
    private String mUserId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_complete_order, container, false);
        ButterKnife.bind(this, view);
        initAdapter();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUserToken = mBuProcessor.getUserToken();
        mUserId = mBuProcessor.getUserId();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.requestCompletedOrder(IntentConstant.ORDER_POSITION_THREE, mUserToken, BuildConfig.VERSION_NAME, mUserId);
    }

    @Override
    public void getCompletedOrderSuccess(OrderDeliveryResponse response) {
        if (response != null && response.orders != null && response.orders.size() > 0) {
            Log.d("completed",response.orders.get(0).deliveryStatus+"");
            mCompleteAdapter.setNewData(response.orders);
            ((MainActivity) getActivity()).changeTabView(IntentConstant.ORDER_POSITION_THREE, response.orders.size());
        }
    }

    @Override
    protected void addFilter() {
        mFilter.addAction(BroConstant.COMPLETE_DELIVERY);
    }

    @Override
    protected void onReceivePro(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action) {
            case BroConstant.COMPLETE_DELIVERY:
                mPresenter.requestCompletedOrder(IntentConstant.ORDER_POSITION_THREE, mUserToken, BuildConfig.VERSION_NAME, mUserId);
                break;
        }
    }

    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCompleteAdapter = new PullToRefreshAdapter(getActivity(), null);
        mCompleteAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mRecyclerView.setAdapter(mCompleteAdapter);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                startActivity(OrderDetailActivity.getOrderDetailIntent(getActivity(), mCompleteAdapter.getItem(position), IntentConstant.ORDER_POSITION_THREE));
            }
        });
        mSwipeLayout.setOnRefreshListener(this);
    }

    @Override
    public void getOrderComplete() {
        mSwipeLayout.setRefreshing(false);
        dismissLoading();
    }

    @Override
    public void getOrderError(Throwable throwable) {
        mSwipeLayout.setRefreshing(false);
        showErrMessage(throwable);
    }

    @Override
    public void onRefresh() {
        mPresenter.requestCompletedOrder(IntentConstant.ORDER_POSITION_THREE, mUserToken, BuildConfig.VERSION_NAME, mUserId);
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
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    private void initialize() {
        this.getComponent(MainActivityComponent.class).inject(this);
        mPresenter.setView(this);
    }
}
