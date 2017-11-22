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
import com.banshengyuan.feima.dagger.component.DaggerExchangeFragmentComponent;
import com.banshengyuan.feima.dagger.module.ExchangeFragmentModule;
import com.banshengyuan.feima.dagger.module.FairDetailActivityModule;
import com.banshengyuan.feima.entity.FairDetailListResponse;
import com.banshengyuan.feima.view.PresenterControl.FollowControl;
import com.banshengyuan.feima.view.activity.FairDetailActivity;
import com.banshengyuan.feima.view.adapter.FairDetailListAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by helei on 2017/5/3.
 * SendingOrderFragment
 */

public class FollowFragment extends BaseFragment implements FollowControl.FollowView {
    @BindView(R.id.fragment_trends_list_first)
    RecyclerView mFragmentTrendsListFirst;

    public static FollowFragment newInstance() {
        return new FollowFragment();
    }

    @Inject
    FollowControl.PresenterFollow mPresenter;

    private Unbinder unbind;
    private FairDetailListAdapter mAdapter;
    private Integer mFairId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
        mFairId = ((FairDetailActivity) getActivity()).getFairId();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_follow, container, false);
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
    public void getFairListSuccess(FairDetailListResponse response) {
        List<FairDetailListResponse.ListBean> listBeen = response.list;
        if (listBeen != null && listBeen.size() > 0) {
            mAdapter.setNewData(listBeen);
        } else {
            mFragmentTrendsListFirst.setVisibility(View.GONE);
        }
    }

    @Override
    public void getFairListFail() {
        mFragmentTrendsListFirst.setVisibility(View.GONE);
    }

    private void initData() {
        //请求市集列表
        mPresenter.requestFairList(mFairId);
    }

    private void initView() {
        mFragmentTrendsListFirst.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new FairDetailListAdapter(null, getActivity(), mImageLoaderHelper);
        mFragmentTrendsListFirst.setAdapter(mAdapter);
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
        DaggerExchangeFragmentComponent.builder()
                .applicationComponent(((DaggerApplication) getActivity().getApplication()).getApplicationComponent())
                .fairDetailActivityModule(new FairDetailActivityModule((AppCompatActivity) getActivity()))
                .exchangeFragmentModule(new ExchangeFragmentModule(this, (AppCompatActivity) getActivity()))
                .build()
                .inject(this);
    }
}
