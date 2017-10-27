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
import com.banshengyuan.feima.entity.RecommendBrandResponse;
import com.banshengyuan.feima.view.PresenterControl.MainFairControl;
import com.banshengyuan.feima.view.activity.MainActivity;
import com.banshengyuan.feima.view.adapter.HotFairAdapter;
import com.banshengyuan.feima.view.adapter.RecommendBrandAdapter;

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

public class MainFairFragment extends BaseFragment implements MainFairControl.MainFairView {
    @BindView(R.id.fair_offline_hot_recycle_view)
    RecyclerView mFairOfflineHotRecycleView;
    @BindView(R.id.fair_brand_recycle_view)
    RecyclerView mFairBrandRecycleView;
    @BindView(R.id.fair_hot_recycle_view)
    RecyclerView mFairHotRecycleView;

    public static MainFairFragment newInstance() {
        return new MainFairFragment();
    }

    @Inject
    MainFairControl.PresenterFair mPresenter;

    private Unbinder unbind;
    private RecommendBrandAdapter mAdapter;
    private HotFairAdapter mHotFairAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_fair, container, false);
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
        List<RecommendBrandResponse> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            RecommendBrandResponse response = new RecommendBrandResponse();
            response.name = "魔兽世界" + i;
            list.add(response);
        }
        mAdapter.setNewData(list);

        List<HotFairResponse> list2 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            HotFairResponse response = new HotFairResponse();
            response.name = "魔兽世界" + i;
            list2.add(response);
        }
        mHotFairAdapter.setNewData(list2);
    }

    private void initView() {
        mFairOfflineHotRecycleView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        mFairBrandRecycleView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        mFairHotRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new RecommendBrandAdapter(null, getActivity());
        mHotFairAdapter = new HotFairAdapter(null,getActivity());
        mFairOfflineHotRecycleView.setAdapter(mAdapter);
        mFairBrandRecycleView.setAdapter(mAdapter);
        mFairHotRecycleView.setAdapter(mHotFairAdapter);
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