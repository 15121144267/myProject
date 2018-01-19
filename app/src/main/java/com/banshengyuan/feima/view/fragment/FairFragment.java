package com.banshengyuan.feima.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.banshengyuan.feima.DaggerApplication;
import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerDiscoverFragmentComponent;
import com.banshengyuan.feima.dagger.module.DiscoverFragmentModule;
import com.banshengyuan.feima.dagger.module.MainActivityModule;
import com.banshengyuan.feima.entity.FairListResponse;
import com.banshengyuan.feima.entity.RecommendBrandResponse;
import com.banshengyuan.feima.view.PresenterControl.FairControl;
import com.banshengyuan.feima.view.activity.BrandFairActivity;
import com.banshengyuan.feima.view.activity.FairDetailActivity;
import com.banshengyuan.feima.view.activity.MainActivity;
import com.banshengyuan.feima.view.adapter.FairProductAdapter;
import com.banshengyuan.feima.view.adapter.RecommendBrandAdapter;
import com.banshengyuan.feima.view.customview.ClearEditText;

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

public class FairFragment extends BaseFragment implements FairControl.FairView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.send_fragment_fair)
    RecyclerView mSendFragmentFair;
    @BindView(R.id.send_fragment_product)
    RecyclerView mSendFragmentProduct;
    @BindView(R.id.recommend_search)
    ClearEditText mRecommendSearch;
    @BindView(R.id.search_layout)
    LinearLayout mSearchLayout;
    @BindView(R.id.refresh_lay_out)
    SwipeRefreshLayout mRefreshLayOut;

    public static FairFragment newInstance() {
        return new FairFragment();
    }

    @Inject
    FairControl.PresenterFair mPresenter;

    private Unbinder unbind;
    private RecommendBrandAdapter mAdapter;
    private FairProductAdapter mFairProductAdapter;
    private List<RecommendBrandResponse> mList;
    private boolean firstFlag, secondFlag;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fair, container, false);
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
    public void onRefresh() {
        firstFlag = false;
        secondFlag = false;
        initData();
    }

    @Override
    public void getFairBrandComplete() {
        firstFlag = true;
        dismissLoading();
    }

    @Override
    public void getFairListComplete() {
        secondFlag = true;
        dismissLoading();
    }

    @Override
    public void getFairBrandSuccess(RecommendBrandResponse response) {
        if (response.list != null && response.list.size() > 0) {
            mList = new ArrayList<>();
            mList.add(response);
            mAdapter.setNewData(mList);
        } else {
            mSendFragmentFair.setVisibility(View.GONE);
        }

    }

    @Override
    public void getFairBrandFail() {
        mSendFragmentFair.setVisibility(View.GONE);
    }

    @Override
    public void getFairListSuccess(FairListResponse response) {
        List<FairListResponse.CategoryBean> list = response.category;
        if (list != null && list.size() > 0) {
            mFairProductAdapter.setNewData(list);
        } else {
            mSendFragmentProduct.setVisibility(View.GONE);
        }
    }

    @Override
    public void getFairListFail() {
        mSendFragmentProduct.setVisibility(View.GONE);
    }

    private void initData() {
        //请求品牌布局
        mPresenter.requestFairBrand();
        //请求一级市集列表
        mPresenter.requestFairList();
    }

    private void initView() {
        mRefreshLayOut.setOnRefreshListener(this);
        mSendFragmentFair.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSendFragmentProduct.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new RecommendBrandAdapter(null, getActivity(), mImageLoaderHelper);
        mFairProductAdapter = new FairProductAdapter(null, getActivity(), mImageLoaderHelper);
        mSendFragmentFair.setAdapter(mAdapter);
        mSendFragmentProduct.setAdapter(mFairProductAdapter);
        mSendFragmentFair.setNestedScrollingEnabled(false);
        mSendFragmentProduct.setNestedScrollingEnabled(false);

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.adapter_fair_more:
                    startActivity(BrandFairActivity.getIntent(getActivity()));
                    break;
            }
        });

        mFairProductAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            FairListResponse.CategoryBean bean = mFairProductAdapter.getItem(position);
            if (bean == null) return;
            switch (view.getId()) {
                case R.id.adapter_fair_more:
                    startActivity(FairDetailActivity.getIntent(getActivity(), 2, bean.id));
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
        checkUpDataFinish();
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

    private void checkUpDataFinish() {
        if (firstFlag && secondFlag) {
            if (mRefreshLayOut.isRefreshing()) {
                mRefreshLayOut.setRefreshing(false);
            }
        }
    }

    private void initialize() {
        DaggerDiscoverFragmentComponent.builder()
                .applicationComponent(((DaggerApplication) getActivity().getApplication()).getApplicationComponent())
                .mainActivityModule(new MainActivityModule((AppCompatActivity) getActivity()))
                .discoverFragmentModule(new DiscoverFragmentModule(this, (MainActivity) getActivity()))
                .build()
                .inject(this);
    }
}
