package com.banshengyuan.feima.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.BroConstant;
import com.banshengyuan.feima.view.adapter.MyOrderFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by helei on 2017/5/3.
 * SendingOrderFragment
 */

public class ExchangeFragment extends BaseFragment {

    @BindView(R.id.exchange_tab_layout)
    TabLayout mExchangeTabLayout;
    @BindView(R.id.exchange_view_pager)
    ViewPager mExchangeViewPager;

    public static ExchangeFragment newInstance() {
        return new ExchangeFragment();
    }

    private Unbinder unbind;
    private final String[] modules = {"动态", "关注", "红人"};
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exchange, container, false);
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
    void addFilter() {
        mFilter.addAction(BroConstant.UPDATE_SHOPPING_CARD_INFO);
    }

    @Override
    void onReceivePro(Context context, Intent intent) {
        if (intent.getAction().equals(BroConstant.UPDATE_SHOPPING_CARD_INFO)) {
            initData();
        }
    }

   /* @Override
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
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbind.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        mPresenter.onDestroy();
    }


    private void initData() {

    }

    private void initView() {
        List<Fragment> mFragments = new ArrayList<>();
        mFragments.add(TrendsFragment.newInstance());
        mFragments.add(FollowFragment.newInstance());
        mFragments.add(CelebrityFragment.newInstance());
        MyOrderFragmentAdapter adapter = new MyOrderFragmentAdapter(getChildFragmentManager(), mFragments, modules);
        mExchangeViewPager.setOffscreenPageLimit(mFragments.size() - 1);
        mExchangeViewPager.setAdapter(adapter);
        mExchangeTabLayout.setupWithViewPager(mExchangeViewPager);
    }

    private void initialize() {
       /* DaggerFragmentComponent.builder()
                .applicationComponent(((DaggerApplication) getActivity().getApplication()).getApplicationComponent())
                .mainActivityModule(new MainActivityModule((AppCompatActivity) getActivity()))
                .fragmentModule(new FragmentModule(this, (MainActivity) getActivity())).build()
                .inject(this);*/
    }
}
