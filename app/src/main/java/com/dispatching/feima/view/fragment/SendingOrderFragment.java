package com.dispatching.feima.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by helei on 2017/5/3.
 * SendingOrderFragment
 */

public class SendingOrderFragment extends BaseFragment implements SendingOrderControl.SendingOrderView {


    public static SendingOrderFragment newInstance() {
//        PendingOrderFragment pendingOrderFragment = new PendingOrderFragment();
        return new SendingOrderFragment();
    }
    @Inject
    SendingOrderControl.PresenterSendingOrder mPresenter;

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
                .applicationComponent(((DaggerApplication)getActivity().getApplication()).getApplicationComponent())
                .mainActivityModule(new MainActivityModule((AppCompatActivity) getActivity()))
                .fragmentModule(new FragmentModule(this)).build()
                .inject(this);
    }
}
