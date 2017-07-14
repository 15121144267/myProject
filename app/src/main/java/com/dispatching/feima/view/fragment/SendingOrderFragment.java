package com.dispatching.feima.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dispatching.feima.DaggerApplication;
import com.dispatching.feima.R;
import com.dispatching.feima.dagger.component.DaggerFragmentComponent;
import com.dispatching.feima.dagger.module.FragmentModule;
import com.dispatching.feima.dagger.module.MainActivityModule;
import com.dispatching.feima.entity.BroConstant;
import com.dispatching.feima.entity.OrderDeliveryResponse;
import com.dispatching.feima.view.PresenterControl.SendingOrderControl;
import com.dispatching.feima.view.activity.ActivityDetailActivity;
import com.dispatching.feima.view.activity.MainActivity;
import com.dispatching.feima.view.adapter.ActivitiesAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by helei on 2017/5/3.
 * SendingOrderFragment
 */

public class SendingOrderFragment extends BaseFragment implements SendingOrderControl.SendingOrderView {


    @BindView(R.id.activities_recycle_view)
    RecyclerView mActivitiesRecycleView;

    public static SendingOrderFragment newInstance() {
//        PendingOrderFragment pendingOrderFragment = new PendingOrderFragment();
        return new SendingOrderFragment();
    }

    @Inject
    SendingOrderControl.PresenterSendingOrder mPresenter;
    private List<Drawable> mList;
    private ActivitiesAdapter mAdapter;
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

    private void initData() {
        Drawable[] productDrawable = {
                ContextCompat.getDrawable(getActivity(), R.mipmap.activties_first),
                ContextCompat.getDrawable(getActivity(), R.mipmap.activities_second),
                ContextCompat.getDrawable(getActivity(), R.mipmap.activties_third),
        };
        Collections.addAll(mList, productDrawable);
        mAdapter.setNewData(mList);
    }

    private void initView() {
        mList = new ArrayList<>();
        mActivitiesRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new ActivitiesAdapter(null, getActivity());
        mActivitiesRecycleView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
                    switch (position) {
                        case 0:
                            startActivity(ActivityDetailActivity.getActivityDetailIntent(getActivity(), 1));
                            break;
                        case 1:
                            startActivity(ActivityDetailActivity.getActivityDetailIntent(getActivity(), 2));
                            break;
                        case 2:
                            startActivity(ActivityDetailActivity.getActivityDetailIntent(getActivity(), 3));
                            break;
                    }
                }
        );
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void getSendingOrderSuccess(OrderDeliveryResponse response) {

    }

    private void initAdapter() {

    }


    @Override
    public void updateOrderStatusSuccess() {

    }

    @Override
    public void getOrderComplete() {

    }

    @Override
    public void getOrderError(Throwable throwable) {

    }


    @Override
    protected void addFilter() {
        mFilter.addAction(BroConstant.TAKE_DELIVERY);
    }

    @Override
    protected void onReceivePro(Context context, Intent intent) {

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
        DaggerFragmentComponent.builder()
                .applicationComponent(((DaggerApplication) getActivity().getApplication()).getApplicationComponent())
                .mainActivityModule(new MainActivityModule((AppCompatActivity) getActivity()))
                .fragmentModule(new FragmentModule(this,(MainActivity)getActivity())).build()
                .inject(this);
    }
}
