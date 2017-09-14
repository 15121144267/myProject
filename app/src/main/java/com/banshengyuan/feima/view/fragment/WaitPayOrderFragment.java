package com.banshengyuan.feima.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.banshengyuan.feima.DaggerApplication;
import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerOrderFragmentComponent;
import com.banshengyuan.feima.dagger.module.MyOrderActivityModule;
import com.banshengyuan.feima.dagger.module.OrderFragmentModule;
import com.banshengyuan.feima.entity.MyOrdersResponse;
import com.banshengyuan.feima.view.PresenterControl.WaitPayControl;
import com.banshengyuan.feima.view.activity.MyOrderActivity;
import com.banshengyuan.feima.view.activity.OrderDetailActivity;
import com.banshengyuan.feima.view.adapter.MyOrdersAdapter;
import com.example.mylibrary.adapter.BaseQuickAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by helei on 2017/5/3.
 * SendingOrderFragment
 */

public class WaitPayOrderFragment extends BaseFragment implements WaitPayControl.WaitPayView, BaseQuickAdapter.RequestLoadMoreListener {


    @BindView(R.id.activities_recycle_view)
    RecyclerView mMyOrders;
    private MyOrdersAdapter mAdapter;
    private List<MyOrdersResponse.OrdersBean> mList;
    private Integer mPagerSize = 10;
    private Integer mPagerNo = 1;
    private final Integer mStatus = 3;

    public static WaitPayOrderFragment newInstance() {
        return new WaitPayOrderFragment();
    }

    @Inject
    WaitPayControl.PresenterWaitPay mPresenter;

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
        initView();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onLoadMoreRequested() {
        if (mList.size() < mPagerSize) {
            mAdapter.loadMoreEnd(true);
        } else {
            mPresenter.requestMyOrderList(mStatus,++mPagerNo, mPagerSize);
        }
    }

    @Override
    public void loadFail(Throwable throwable) {
        showErrMessage(throwable);
        mAdapter.loadMoreFail();
    }

    @Override
    public void getMyOrderListSuccess(MyOrdersResponse response) {
        if (response == null) return;
        mList = response.orders;
        if (mList.size() > 0) {
            mAdapter.addData(mList);
            mAdapter.loadMoreComplete();
        } else {
            mAdapter.loadMoreEnd();
        }
    }

    private void initData() {
        mPresenter.requestMyOrderList(mStatus,mPagerNo, mPagerSize);
    }

    private void initView() {
        mMyOrders.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MyOrdersAdapter(null, getActivity(), mImageLoaderHelper);
        mAdapter.setOnLoadMoreListener(this, mMyOrders);
        mMyOrders.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
                    MyOrdersResponse.OrdersBean response = (MyOrdersResponse.OrdersBean) adapter.getItem(position);
                    startActivity(OrderDetailActivity.getOrderDetailIntent(getActivity(), response));
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
        DaggerOrderFragmentComponent.builder()
                .applicationComponent(((DaggerApplication) getActivity().getApplication()).getApplicationComponent())
                .myOrderActivityModule(new MyOrderActivityModule((AppCompatActivity) getActivity()))
                .orderFragmentModule(new OrderFragmentModule(this, (MyOrderActivity) getActivity())).build()
                .inject(this);
    }
}
