package com.banshengyuan.feima.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.banshengyuan.feima.DaggerApplication;
import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerOrderFragmentComponent;
import com.banshengyuan.feima.dagger.module.MyOrderActivityModule;
import com.banshengyuan.feima.dagger.module.OrderFragmentModule;
import com.banshengyuan.feima.entity.BroConstant;
import com.banshengyuan.feima.entity.MyOrdersResponse;
import com.banshengyuan.feima.view.PresenterControl.OrderCompleteControl;
import com.banshengyuan.feima.view.activity.CommentActivity;
import com.banshengyuan.feima.view.activity.MyOrderActivity;
import com.banshengyuan.feima.view.activity.OrderDetailActivity;
import com.banshengyuan.feima.view.adapter.MyOrdersAdapter;
import com.example.mylibrary.adapter.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by helei on 2017/5/3.
 * SendingOrderFragment
 * 待评价
 */

public class OrderCompleteFragment extends BaseFragment implements OrderCompleteControl.OrderCompleteView, BaseQuickAdapter.RequestLoadMoreListener {


    @BindView(R.id.activities_recycle_view)
    RecyclerView mMyOrders;
    private MyOrdersAdapter mAdapter;
    private List<MyOrdersResponse.ListBean> mList;
    private final Integer mPagerSize = 10;
    private Integer mPagerNo = 1;
    private final String mStatus = "3";//1.待付款 2.待收货 3. 待评价
    private int mPos;
    private String mOrderSn = null;
    private View mEmptyView = null;

    public static OrderCompleteFragment newInstance() {
        return new OrderCompleteFragment();
    }

    @Inject
    OrderCompleteControl.PresenterOrderComplete mPresenter;

    private Unbinder unbind;
    private String token;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_order, container, false);
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
    void onReceivePro(Context context, Intent intent) {
        if (intent.getAction().equals(BroConstant.ORDER_TO_ORDERDETAIL) ||
                intent.getAction().equals(BroConstant.ORDER_REFRESH_ORDERFRAGMENT4)) {
            mPagerNo = 1;
            mPresenter.requestMyOrderList(mPagerNo, mPagerSize, mStatus, token);
        }
        super.onReceivePro(context, intent);
    }

    @Override
    void addFilter() {
        super.addFilter();
        mFilter.addAction(BroConstant.ORDER_TO_ORDERDETAIL);
        mFilter.addAction(BroConstant.ORDER_REFRESH_ORDERFRAGMENT4);
    }

    @Override
    public void onLoadMoreRequested() {
        if (mPagerNo == 1 && mList.size() < mPagerSize) {
            mAdapter.loadMoreEnd(true);
        } else {
            if (mList.size() < mPagerSize) {
                mAdapter.loadMoreEnd();
            } else {
                mPresenter.requestMyOrderList(++mPagerNo, mPagerSize, mStatus, token);
            }
        }
    }

    @Override
    public void loadFail(Throwable throwable) {
        showErrMessage(throwable);
        mAdapter.loadMoreFail();
        if (mPagerNo > 1) mPagerNo--;
    }

    @Override
    public void getMyOrderListSuccess(MyOrdersResponse response) {
        mList = response.getList();
        if (mPagerNo == 1) {
            if (mList != null && mList.size() > 0) {
                mAdapter.setNewData(mList);
            } else {
                mAdapter.setNewData(null);
                mAdapter.setEmptyView(mEmptyView);
            }
        } else {
            mAdapter.addData(mList);
            mAdapter.loadMoreComplete();
        }
    }

    @Override
    public void getDeleteOrderSuccess() {
        reFreshOrder();
        showToast("删除成功");
        mAdapter.remove(mPos);
    }

    /**
     * 为了刷新 AllOrderFragment
     */
    private void reFreshOrder() {
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(new Intent(BroConstant.ORDER_REFRESH));
    }

    private void initData() {
        token = mBuProcessor.getUserToken();
        mPresenter.requestMyOrderList(mPagerNo, mPagerSize, mStatus, token);
    }

    private void initView() {
        mMyOrders.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MyOrdersAdapter(null, getActivity(), mImageLoaderHelper);
        mAdapter.setOnLoadMoreListener(this, mMyOrders);
        mMyOrders.setAdapter(mAdapter);

        mEmptyView = LayoutInflater.from(getActivity()).inflate(R.layout.empty_view, (ViewGroup) mMyOrders.getParent(), false);
        ImageView imageView = mEmptyView.findViewById(R.id.empty_icon);
        mImageLoaderHelper.displayImage(getActivity(), R.mipmap.enpty_order_view, imageView);
        TextView emptyContent = mEmptyView.findViewById(R.id.empty_content);
        emptyContent.setVisibility(View.VISIBLE);
        emptyContent.setText(R.string.wait_comment_empty_view);
        Button emptyButton = mEmptyView.findViewById(R.id.empty_text);
        emptyButton.setVisibility(View.GONE);

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                    mPos = position;
                    MyOrdersResponse.ListBean listBean = (MyOrdersResponse.ListBean) adapter.getItem(position);
                    if (listBean != null) {
                        mOrderSn = listBean.getOrder_sn();
                        switch (view.getId()) {
                            case R.id.mime_order_lv:
                                startActivity(OrderDetailActivity.getOrderDetailIntent(getActivity(), mOrderSn));
                                break;
                            case R.id.order_left_btn:
                                if (listBean.getOrder_type() == 1) {
                                    //线上
                                    if (listBean.getPay_status() == 1) {//取消订单
                                    } else if (listBean.getPay_status() == 2) {//false
                                    } else if (listBean.getPay_status() == 3) {//false
                                    } else if (listBean.getPay_status() == 4) {//删除
                                        mPresenter.requestDeleteOrder(mOrderSn, token);
                                    } else if (listBean.getPay_status() == 5) {//删除
                                        mPresenter.requestDeleteOrder(mOrderSn, token);
                                    }
                                } else if (listBean.getOrder_type() == 2) {//2自提订单
                                    if (listBean.getPay_status() == 1) {//取消订单
                                    } else if (listBean.getPay_status() == 2) {//false
                                    } else if (listBean.getPay_status() == 3) {//false
                                    } else if (listBean.getPay_status() == 4) {//删除
                                        mPresenter.requestDeleteOrder(mOrderSn, token);
                                    } else if (listBean.getPay_status() == 5) {//删除
                                        mPresenter.requestDeleteOrder(mOrderSn, token);
                                    }
                                } else if (listBean.getOrder_type() == 3) {
                                    //3线下收款订单
                                    // false
                                }
                                break;
                            case R.id.order_right_btn:
                                if (listBean.getOrder_type() == 1) {
                                    //线上
                                    if (listBean.getPay_status() == 1) {//立即付款
                                    } else if (listBean.getPay_status() == 2) {//确认收货
                                    } else if (listBean.getPay_status() == 3) {//提醒发货
                                    } else if (listBean.getPay_status() == 4) {//去评价
                                        if(listBean.getIs_evaluate()==0){//去评价
                                            startActivity(CommentActivity.getIntent(getActivity(), (ArrayList<MyOrdersResponse.ListBean.ProductBean>) listBean.getProduct(),listBean.getOrder_sn()));
                                        }else {//删除订单
                                            mPresenter.requestDeleteOrder(mOrderSn, token);
                                        }
                                    } else if (listBean.getPay_status() == 5) {//删除
                                        mPresenter.requestDeleteOrder(mOrderSn, token);
                                    }
                                } else if (listBean.getOrder_type() == 2) {
                                    //2自提订单
                                    if (listBean.getPay_status() == 1) {//立即付款
                                    } else if (listBean.getPay_status() == 2) {//确认收货
                                    } else if (listBean.getPay_status() == 3) {//确认收货
                                    } else if (listBean.getPay_status() == 4) {//去评价
                                        if(listBean.getIs_evaluate()==0){//去评价
                                            startActivity(CommentActivity.getIntent(getActivity(), (ArrayList<MyOrdersResponse.ListBean.ProductBean>) listBean.getProduct(),listBean.getOrder_sn()));
                                        }else {//删除订单
                                            mPresenter.requestDeleteOrder(mOrderSn, token);
                                        }
                                    } else if (listBean.getPay_status() == 5) {//删除
                                        mPresenter.requestDeleteOrder(mOrderSn, token);
                                    }
                                } else if (listBean.getOrder_type() == 3) {
                                    //3线下收款订单
                                    //删除
                                    mPresenter.requestDeleteOrder(mOrderSn, token);
                                }
                                break;
                        }
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
