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
import com.banshengyuan.feima.entity.FairDetailStoreListResponse;
import com.banshengyuan.feima.view.PresenterControl.CelebrityControl;
import com.banshengyuan.feima.view.activity.FairDetailActivity;
import com.banshengyuan.feima.view.adapter.FairDetailStoreListAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by helei on 2017/5/3.
 * SendingOrderFragment
 */

public class CelebrityFragment extends BaseFragment implements CelebrityControl.CelebrityView {
    @BindView(R.id.fragment_trends_list_middle)
    RecyclerView mFragmentTrendsListMiddle;

    public static CelebrityFragment newInstance() {
        return new CelebrityFragment();
    }

    @Inject
    CelebrityControl.PresenterCelebrity mPresenter;

    private Unbinder unbind;
    private FairDetailStoreListAdapter mAdapter;
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
        View view = inflater.inflate(R.layout.fragment_celebrity, container, false);
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
    public void getStoreListSuccess(FairDetailStoreListResponse response) {
        List<FairDetailStoreListResponse.ListBean> list = response.list;
        if (list != null && list.size() > 0) {
            mAdapter.setNewData(list);
        } else {
            mFragmentTrendsListMiddle.setVisibility(View.GONE);
        }
    }

    @Override
    public void getStoreListFail() {
        mFragmentTrendsListMiddle.setVisibility(View.GONE);
    }

    private void initData() {
        //请求店铺列表
        mPresenter.requestStoreList(mFairId);
    }

    private void initView() {
        mFragmentTrendsListMiddle.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new FairDetailStoreListAdapter(null,getActivity(),mImageLoaderHelper);
        mFragmentTrendsListMiddle.setAdapter(mAdapter);
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
