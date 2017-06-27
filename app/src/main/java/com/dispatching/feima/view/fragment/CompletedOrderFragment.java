package com.dispatching.feima.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dispatching.feima.DaggerApplication;
import com.dispatching.feima.R;
import com.dispatching.feima.dagger.component.DaggerFragmentComponent;
import com.dispatching.feima.dagger.module.FragmentModule;
import com.dispatching.feima.dagger.module.MainActivityModule;
import com.dispatching.feima.entity.BroConstant;
import com.dispatching.feima.entity.OrderDeliveryResponse;
import com.dispatching.feima.view.PresenterControl.CompletedOrderControl;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by helei on 2017/5/3.
 * CompletedOrderFragment
 */

public class CompletedOrderFragment extends BaseFragment implements CompletedOrderControl.CompletedOrderView {

    @BindView(R.id.person_order)
    Button mPersonOrder;
    @BindView(R.id.person_address)
    Button mPersonAddress;
    @BindView(R.id.person_info)
    Button mPersonInfo;

    public static CompletedOrderFragment newInstance() {
//        PendingOrderFragment pendingOrderFragment = new PendingOrderFragment();
        return new CompletedOrderFragment();
    }


    @Inject
    CompletedOrderControl.PresenterCompletedOrder mPresenter;

    private Unbinder unbind;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_complete_order, container, false);
        unbind = ButterKnife.bind(this, view);
        initView();
        initAdapter();
        return view;
    }

    private void initView() {
        RxView.clicks(mPersonOrder).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> requestOrder());
        RxView.clicks(mPersonAddress).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> requestAddress());
        RxView.clicks(mPersonInfo).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> requestInfo());
    }

    private void requestInfo() {
        showToast("1");
    }

    private void requestAddress() {
        showToast("2");
    }

    private void requestOrder() {
        showToast("3");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void getCompletedOrderSuccess(OrderDeliveryResponse response) {

    }

    @Override
    protected void addFilter() {
        mFilter.addAction(BroConstant.COMPLETE_DELIVERY);
    }

    @Override
    protected void onReceivePro(Context context, Intent intent) {

    }

    private void initAdapter() {

    }

    @Override
    public void getOrderComplete() {

    }

    @Override
    public void getOrderError(Throwable throwable) {
        showErrMessage(throwable);
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
                .fragmentModule(new FragmentModule(this)).build()
                .inject(this);
    }
}
