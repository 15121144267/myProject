package com.banshengyuan.feima.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.banshengyuan.feima.DaggerApplication;
import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerMainFragmentComponent;
import com.banshengyuan.feima.dagger.module.MainActivityModule;
import com.banshengyuan.feima.dagger.module.MainFragmentModule;
import com.banshengyuan.feima.entity.ProductResponse;
import com.banshengyuan.feima.entity.RecommendBrandResponse;
import com.banshengyuan.feima.entity.RecommendDiscoverResponse;
import com.banshengyuan.feima.view.PresenterControl.RecommendControl;
import com.banshengyuan.feima.view.activity.BlockDetailActivity;
import com.banshengyuan.feima.view.activity.BrandFairActivity;
import com.banshengyuan.feima.view.activity.MainActivity;
import com.banshengyuan.feima.view.adapter.RecommendBrandAdapter;
import com.banshengyuan.feima.view.adapter.RecommendDiscoverBrandAdapter;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by helei on 2017/5/3.
 * SendingOrderFragment
 */

public class RecommendFragment extends BaseFragment implements RecommendControl.RecommendView {
    @BindView(R.id.recommend_brand_recycle_view)
    RecyclerView mRecommendBrandRecycleView;
    @BindView(R.id.recommend_discover_recycle_view)
    RecyclerView mRecommendDiscoverRecycleView;
    @BindView(R.id.recommend_block_detail_enter)
    LinearLayout mRecommendBlockDetailEnter;

    public static RecommendFragment newInstance() {
        return new RecommendFragment();
    }

    @Inject
    RecommendControl.PresenterRecommend mPresenter;

    private Unbinder unbind;
    private RecommendBrandAdapter mAdapter;
    private RecommendDiscoverBrandAdapter mDiscoverBrandAdapter;
    private List<RecommendBrandResponse> mList;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommed, container, false);
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

        List<ProductResponse> mList = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            List<ProductResponse.ProductItemBean> mList1 = new ArrayList<>();
            ProductResponse product = new ProductResponse();
            product.name = "户外运动" + i;
            for (int j = 0; j < 5; j++) {
                ProductResponse.ProductItemBean itemBean = new ProductResponse.ProductItemBean();
                itemBean.content = "魔兽世界" + j;
                itemBean.tip = "少年三国志" + j;
                mList1.add(itemBean);
            }
            product.mList = mList1;
            mList.add(product);
        }

        mAdapter.setNewData(mList);

        List<RecommendDiscoverResponse> list2 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            RecommendDiscoverResponse response2 = new RecommendDiscoverResponse();
            response2.name = "魔兽世界" + i;
            list2.add(response2);
        }
        mDiscoverBrandAdapter.setNewData(list2);
    }

    private void initView() {

        RxView.clicks(mRecommendBlockDetailEnter).throttleFirst(1, TimeUnit.SECONDS).subscribe(
                o -> startActivity(BlockDetailActivity.getIntent(getActivity())));

        mRecommendBrandRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecommendDiscoverRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new RecommendBrandAdapter(null, getActivity(),true);
        mDiscoverBrandAdapter = new RecommendDiscoverBrandAdapter(null, getActivity());

        mRecommendBrandRecycleView.setAdapter(mAdapter);
        mRecommendDiscoverRecycleView.setAdapter(mDiscoverBrandAdapter);

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.adapter_fair_more:
                    startActivity(BrandFairActivity.getIntent(getActivity()));
                    break;
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
        DaggerMainFragmentComponent.builder()
                .applicationComponent(((DaggerApplication) getActivity().getApplication()).getApplicationComponent())
                .mainActivityModule(new MainActivityModule((AppCompatActivity) getActivity()))
                .mainFragmentModule(new MainFragmentModule(this, (MainActivity) getActivity()))
                .build().inject(this);
    }
}
