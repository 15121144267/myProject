package com.dispatching.feima.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dispatching.feima.R;
import com.dispatching.feima.dagger.component.MainActivityComponent;
import com.dispatching.feima.entity.BroConstant;
import com.dispatching.feima.entity.IntentConstant;
import com.dispatching.feima.entity.MyOrders;
import com.dispatching.feima.entity.OrderDeliveryResponse;
import com.dispatching.feima.listener.OnItemClickListener;
import com.dispatching.feima.view.PresenterControl.SendingOrderControl;
import com.dispatching.feima.view.activity.MainActivity;
import com.dispatching.feima.view.activity.OrderDetailActivity;
import com.dispatching.feima.view.adapter.BaseQuickAdapter;
import com.dispatching.feima.view.adapter.PullToRefreshAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by helei on 2017/5/3.
 * SendingOrderFragment
 */

public class SendingOrderFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, SendingOrderControl.SendingOrderView {
    @Inject
    SendingOrderControl.PresenterSendingOrder mPresenter;
    @BindView(R.id.sending_rv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.sending_swipeLayout)
    SwipeRefreshLayout mSwipeLayout;
    @BindView(R.id.empty_swipeLayout)
    SwipeRefreshLayout mEmptySwipeLayout;

    private PullToRefreshAdapter mSendingAdapter;
    private Integer mPosition;
    private String mUserToken;
    private String mUserId;
    private boolean mBroFlag = false;
    private Unbinder unbind;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sending_order, container, false);
        unbind = ButterKnife.bind(this, view);
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
        mPresenter.requestSendingOrder(mUserToken, mUserId);
    }

    @Override
    public void getSendingOrderSuccess(OrderDeliveryResponse response) {
        if (response != null && response.orders != null) {
            List<MyOrders> myOrders = response.orders;
            if(myOrders.size()>0){
                mSwipeLayout.setVisibility(View.VISIBLE);
                mEmptySwipeLayout.setVisibility(View.GONE);
            }else {
                mSwipeLayout.setVisibility(View.GONE);
                mEmptySwipeLayout.setVisibility(View.VISIBLE);
            }
            mSendingAdapter.setNewData(myOrders);
            ((MainActivity) getActivity()).changeTabView(IntentConstant.ORDER_POSITION_TWO, myOrders.size());
        }
        if (mBroFlag) {
            LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(new Intent(BroConstant.COMPLETE_DELIVERY));
            mBroFlag = false;
        }
    }

    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSendingAdapter = new PullToRefreshAdapter(getActivity(), null);
        mSendingAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mRecyclerView.setAdapter(mSendingAdapter);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                startActivityForResult(OrderDetailActivity.getOrderDetailIntent(getActivity(), mSendingAdapter.getItem(position), IntentConstant.ORDER_POSITION_TWO), IntentConstant.ORDER_POSITION_TWO);
            }
        });
        mSendingAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                    mPosition = position;
                    MyOrders order = (MyOrders) adapter.getItem(position);
                    mPresenter.requestCompleteOrder(mUserToken, mUserId, order.deliveryId);
                }
        );
        mSwipeLayout.setOnRefreshListener(this);
        mEmptySwipeLayout.setOnRefreshListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && data.getBooleanExtra(IntentConstant.ORDER_DETAIL_FLASH, false)) {
            mBroFlag = true;
            mPresenter.requestSendingOrder(mUserToken, mUserId);
        }

    }

    @Override
    public void updateOrderStatusSuccess() {
        mSendingAdapter.remove(mPosition);
        int count = mSendingAdapter.getItemCount();
        if(count == 0){
            mSwipeLayout.setVisibility(View.GONE);
            mEmptySwipeLayout.setVisibility(View.VISIBLE);
        }
        ((MainActivity) getActivity()).changeTabView(IntentConstant.ORDER_POSITION_TWO,count);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(new Intent(BroConstant.COMPLETE_DELIVERY));
    }

    @Override
    public void getOrderComplete() {
        mSwipeLayout.setRefreshing(false);
        mEmptySwipeLayout.setRefreshing(false);
        dismissLoading();
    }

    @Override
    public void getOrderError(Throwable throwable) {
        mEmptySwipeLayout.setVisibility(View.VISIBLE);
        mSwipeLayout.setRefreshing(false);
        mEmptySwipeLayout.setRefreshing(false);
        showErrMessage(throwable);
    }


    @Override
    protected void addFilter() {
        mFilter.addAction(BroConstant.TAKE_DELIVERY);
    }

    @Override
    protected void onReceivePro(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action) {
            case BroConstant.TAKE_DELIVERY:
                mPresenter.requestSendingOrder(mUserToken, mUserId);
                break;
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.requestSendingOrder(mUserToken, mUserId);
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
    public void onDestroyView() {
        super.onDestroyView();
        unbind.unbind();
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
