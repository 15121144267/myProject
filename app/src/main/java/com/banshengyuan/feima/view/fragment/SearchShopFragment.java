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
import com.banshengyuan.feima.view.activity.ShopProductDetailActivity;
import com.banshengyuan.feima.view.adapter.SearchShopAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by helei on 2017/5/3.
 * PendingOrderFragment
 */

public class SearchShopFragment extends BaseFragment implements SearchControl.SearchView {


    @BindView(R.id.fragment_block_common)
    RecyclerView mFragmentBlockCommon;

    public static SearchShopFragment newInstance() {
        return new SearchShopFragment();
    }


    @Inject
    SearchControl.PresenterSearch mPresenter;

    private Unbinder unbinder;
    private SearchShopAdapter mAdapter;
    private String mSearchName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_block, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void getSearchListSuccess(SearchResultResponse response) {
        mAdapter.setNewData(response.list);
    }


    private void initView() {
        mFragmentBlockCommon.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new SearchShopAdapter(null, getActivity(), mImageLoaderHelper);
        mFragmentBlockCommon.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            SearchResultResponse.ListBean bean = (SearchResultResponse.ListBean) adapter.getItem(position);
            if (bean != null) {
                startActivity(ShopProductDetailActivity.getActivityDetailIntent(getActivity(), bean.id));
            }
        });
    }


    private void initData() {
        mPresenter.requestSearchStoreList(mSearchName, "store");
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
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initialize() {
        DaggerSearchFragmentComponent.builder()
                .applicationComponent(((DaggerApplication) getActivity().getApplication()).getApplicationComponent())
                .searchActivityModule(new SearchActivityModule((AppCompatActivity) getActivity(), this))
                .searchFragmentModule(new SearchFragmentModule((SearchActivity) getActivity())).build()
                .inject(this);
    }


}
