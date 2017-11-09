package com.banshengyuan.feima.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.banshengyuan.feima.DaggerApplication;
import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerFragmentComponent;
import com.banshengyuan.feima.dagger.module.FragmentModule;
import com.banshengyuan.feima.dagger.module.MainActivityModule;
import com.banshengyuan.feima.entity.OrderDeliveryResponse;
import com.banshengyuan.feima.utils.ValueUtil;
import com.banshengyuan.feima.view.PresenterControl.SendingOrderControl;
import com.banshengyuan.feima.view.activity.MainActivity;
import com.banshengyuan.feima.view.adapter.MyOrderFragmentAdapter;

import java.util.ArrayList;
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


    @BindView(R.id.discover_tab_layout)
    TabLayout mDiscoverTabLayout;
    @BindView(R.id.discover_view_pager)
    ViewPager mDiscoverViewPager;

    public static SendingOrderFragment newInstance() {
        return new SendingOrderFragment();
    }
    private final String[] modules = {"市集", "产品", "商家"};
    @Inject
    SendingOrderControl.PresenterSendingOrder mPresenter;
    //private ActivitiesAdapter mAdapter;
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

    }

    private void initView() {
        List<Fragment> mFragments = new ArrayList<>();
        mFragments.add(FairFragment.newInstance());
        mFragments.add(ProductFragment.newInstance());
        mFragments.add(SellerFragment.newInstance());
        MyOrderFragmentAdapter adapter = new MyOrderFragmentAdapter(getChildFragmentManager(), mFragments, modules);
        mDiscoverViewPager.setOffscreenPageLimit(mFragments.size() - 1);
        mDiscoverViewPager.setAdapter(adapter);
        mDiscoverTabLayout.setupWithViewPager(mDiscoverViewPager);
        ValueUtil.setIndicator(mDiscoverTabLayout,20,20);
    }

    @Override
    public void getSendingOrderSuccess(OrderDeliveryResponse response) {

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
                .fragmentModule(new FragmentModule(this, (MainActivity) getActivity())).build()
                .inject(this);
    }
}
