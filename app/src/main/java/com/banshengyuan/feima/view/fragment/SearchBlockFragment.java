package com.banshengyuan.feima.view.fragment;

import android.content.Context;
import android.content.Intent;
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
import com.banshengyuan.feima.dagger.component.DaggerSearchFragmentComponent;
import com.banshengyuan.feima.dagger.module.SearchActivityModule;
import com.banshengyuan.feima.dagger.module.SearchFragmentModule;
import com.banshengyuan.feima.entity.BroConstant;
import com.banshengyuan.feima.entity.SearchResultResponse;
import com.banshengyuan.feima.view.PresenterControl.SearchControl;
import com.banshengyuan.feima.view.activity.SearchActivity;
import com.banshengyuan.feima.view.adapter.FairDetailSellersAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by helei on 2017/5/3.
 * PendingOrderFragment
 */

public class SearchBlockFragment extends BaseFragment implements SearchControl.SearchView {


    @BindView(R.id.fragment_block_common)
    RecyclerView mFragmentBlockCommon;

    public static SearchBlockFragment newInstance() {
        return new SearchBlockFragment();
    }


    @Inject
    SearchControl.PresenterSearch mPresenter;

    private Unbinder unbinder;
    private FairDetailSellersAdapter mAdapter;
    private String mSearchName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
        mSearchName = ((SearchActivity) getActivity()).getSearchName();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_under_line_shop, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    @Override
    public void getSearchStreetListSuccess(SearchResultResponse response) {
        if (response.list != null && response.list.size() > 0) {
            mAdapter.setNewData(response.list);
        } else {
            mFragmentBlockCommon.setVisibility(View.GONE);
        }
    }

    @Override
    public void getSearchStreetListFail(String des) {
        mFragmentBlockCommon.setVisibility(View.GONE);
    }

    private void initView() {
        mFragmentBlockCommon.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new FairDetailSellersAdapter(null, getActivity(),mImageLoaderHelper);
        mFragmentBlockCommon.setAdapter(mAdapter);
    }


    private void initData() {
        mPresenter.requestSearchStreetList(mSearchName, "street");
    }

    @Override
    void addFilter() {
        super.addFilter();
        mFilter.addAction(BroConstant.SEARCH_UPDATE);
    }

    @Override
    void onReceivePro(Context context, Intent intent) {
        super.onReceivePro(context, intent);
        if (intent.getAction().equals(BroConstant.SEARCH_UPDATE)) {
            mSearchName = intent.getStringExtra("broSearchName");
            initData();
        }
    }

    @Override
    public void getSearchFairListSuccess(SearchResultResponse response) {

    }

    @Override
    public void getSearchStoreListSuccess(SearchResultResponse response) {

    }

    @Override
    public void getSearchProductListSuccess(SearchResultResponse response) {

    }

    @Override
    public void getSearchFairListFail(String des) {

    }

    @Override
    public void getSearchStoreListFail(String des) {

    }

    @Override
    public void getSearchProductListFail(String des) {

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
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    private void initialize() {
        DaggerSearchFragmentComponent.builder()
                .applicationComponent(((DaggerApplication) getActivity().getApplication()).getApplicationComponent())
                .searchActivityModule(new SearchActivityModule((AppCompatActivity) getActivity(), this))
                .searchFragmentModule(new SearchFragmentModule((SearchActivity) getActivity())).build()
                .inject(this);
    }


}
