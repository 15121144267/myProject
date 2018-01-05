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
import com.banshengyuan.feima.view.activity.GoodDetailActivity;
import com.banshengyuan.feima.view.activity.SearchActivity;
import com.banshengyuan.feima.view.adapter.SearchProductAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by helei on 2017/5/3.
 * TrendsFragment
 */

public class SearchProductFragment extends BaseFragment implements SearchControl.SearchView {
    @BindView(R.id.fragment_search_product)
    RecyclerView mFragmentSearchProduct;

    public static SearchProductFragment newInstance() {
        return new SearchProductFragment();
    }

    @Inject
    SearchControl.PresenterSearch mPresenter;

    private Unbinder unbind;
    private SearchProductAdapter mAdapter;
    private String mSearchName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_product, container, false);
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
    public void getSearchProductListSuccess(SearchResultResponse response) {
        mAdapter.setNewData(response.list);
    }

    @Override
    public void getSearchProductListFail(String des) {

    }

    private void initData() {
        mPresenter.requestSearchProductList(mSearchName, "goods");
    }

    private void initView() {
        mFragmentSearchProduct.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new SearchProductAdapter(null, getActivity(), mImageLoaderHelper);
        mFragmentSearchProduct.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            SearchResultResponse.ListBean bean = (SearchResultResponse.ListBean) adapter.getItem(position);
            if (bean != null) {
                startActivity(GoodDetailActivity.getIntent(getActivity(), bean.id));
            }
        });
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
    public void getSearchStreetListSuccess(SearchResultResponse response) {

    }

    @Override
    public void getSearchFairListFail(String des) {

    }

    @Override
    public void getSearchStoreListFail(String des) {

    }

    @Override
    public void getSearchStreetListFail(String des) {

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
        DaggerSearchFragmentComponent.builder()
                .applicationComponent(((DaggerApplication) getActivity().getApplication()).getApplicationComponent())
                .searchActivityModule(new SearchActivityModule((AppCompatActivity) getActivity(), this))
                .searchFragmentModule(new SearchFragmentModule((SearchActivity) getActivity())).build()
                .inject(this);
    }
}
