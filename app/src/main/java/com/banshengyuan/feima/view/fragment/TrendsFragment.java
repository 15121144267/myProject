package com.banshengyuan.feima.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.banshengyuan.feima.DaggerApplication;
import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerExchangeFragmentComponent;
import com.banshengyuan.feima.dagger.module.ExchangeFragmentModule;
import com.banshengyuan.feima.dagger.module.FairDetailActivityModule;
import com.banshengyuan.feima.entity.FairDetailProductListResponse;
import com.banshengyuan.feima.view.PresenterControl.TrendsControl;
import com.banshengyuan.feima.view.activity.FairDetailActivity;
import com.banshengyuan.feima.view.activity.GoodDetailActivity;
import com.banshengyuan.feima.view.adapter.FairDetailProductListAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by helei on 2017/5/3.
 * TrendsFragment
 */

public class TrendsFragment extends BaseFragment implements TrendsControl.TrendsView {
    @BindView(R.id.fragment_trends_list_last)
    RecyclerView mFragmentTrendsListLast;

    public static TrendsFragment newInstance() {
        return new TrendsFragment();
    }

    @Inject
    TrendsControl.PresenterTrends mPresenter;

    private Unbinder unbind;
    private FairDetailProductListAdapter mAdapter;
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
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
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
    public void getProductListSuccess(FairDetailProductListResponse response) {
        List<FairDetailProductListResponse.ListBean> list = response.list;
        if (list != null && list.size() > 0) {
            mAdapter.setNewData(list);
        }
    }

    @Override
    public void getProductListFail() {

    }

    private void initData() {
        //请求产品列表
        mPresenter.requestProductList(mFairId);
    }

    private void initView() {
        mFragmentTrendsListLast.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mAdapter = new FairDetailProductListAdapter(null, getActivity(), mImageLoaderHelper);
        mFragmentTrendsListLast.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            FairDetailProductListResponse.ListBean bean = (FairDetailProductListResponse.ListBean) adapter.getItem(position);
            if (bean != null) {
                startActivity(GoodDetailActivity.getIntent(getActivity(),bean.id));
            }
        });
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
