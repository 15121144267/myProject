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
import com.banshengyuan.feima.dagger.component.DaggerMainFragmentComponent;
import com.banshengyuan.feima.dagger.module.MainActivityModule;
import com.banshengyuan.feima.dagger.module.MainFragmentModule;
import com.banshengyuan.feima.entity.HotFairResponse;
import com.banshengyuan.feima.view.PresenterControl.HotControl;
import com.banshengyuan.feima.view.activity.MainActivity;
import com.banshengyuan.feima.view.adapter.HotFairAdapter;

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

public class MainHotFragment extends BaseFragment implements HotControl.HotView {
    @BindView(R.id.main_hot_recycle_view)
    RecyclerView mMainHotRecycleView;

    public static MainHotFragment newInstance() {
        return new MainHotFragment();
    }

    @Inject
    HotControl.PresenterHot mPresenter;

    private Unbinder unbind;
    private HotFairAdapter mHotFairAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
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
        List<HotFairResponse> list2 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            HotFairResponse response = new HotFairResponse();
            response.name = "魔兽世界" + i;
            list2.add(response);
        }
        mHotFairAdapter.setNewData(list2);
    }

    private void initView() {
        mMainHotRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mHotFairAdapter = new HotFairAdapter(null,getActivity());
        mMainHotRecycleView.setAdapter(mHotFairAdapter);
        mMainHotRecycleView.setNestedScrollingEnabled(false);
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
        DaggerMainFragmentComponent.builder()
                .applicationComponent(((DaggerApplication) getActivity().getApplication()).getApplicationComponent())
                .mainActivityModule(new MainActivityModule((AppCompatActivity) getActivity()))
                .mainFragmentModule(new MainFragmentModule(this, (MainActivity) getActivity()))
                .build()
                .inject(this);
    }
}
