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
import com.banshengyuan.feima.dagger.component.DaggerBlockDetailFragmentComponent;
import com.banshengyuan.feima.dagger.module.BlockDetailActivityModule;
import com.banshengyuan.feima.dagger.module.BlockDetailFragmentModule;
import com.banshengyuan.feima.entity.ProductResponse;
import com.banshengyuan.feima.view.PresenterControl.BlockDetailControl;
import com.banshengyuan.feima.view.activity.BlockActivity;
import com.banshengyuan.feima.view.activity.BlockDetailActivity;
import com.banshengyuan.feima.view.adapter.BlockDetailFairAdapter;
import com.banshengyuan.feima.view.adapter.BlockDetailShopAdapter;
import com.banshengyuan.feima.view.adapter.CommonItemAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by helei on 2017/5/3.
 * PendingOrderFragment
 */

public class BlockDetailFragment extends BaseFragment implements BlockDetailControl.BlockDetailView {


    public static BlockDetailFragment newInstance() {
        return new BlockDetailFragment();
    }

    @BindView(R.id.block_detail_hot)
    RecyclerView mBlockDetailHot;
    @BindView(R.id.block_detail_fair)
    RecyclerView mBlockDetailFair;
    @BindView(R.id.block_detail_shop)
    RecyclerView mBlockDetailShop;
    @Inject
    BlockDetailControl.PresenterBlockDetail mPresenter;

    private Unbinder unbinder;
    private CommonItemAdapter mCommonItemAdapter;
    private BlockDetailFairAdapter mBlockDetailFairAdapter;
    private BlockDetailShopAdapter mBlockDetailShopAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_block_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initAdapter();
        return view;
    }

    private void initView() {
        mBlockDetailHot.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBlockDetailFair.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBlockDetailShop.setLayoutManager(new LinearLayoutManager(getActivity()));

        mCommonItemAdapter =  new CommonItemAdapter(null,getActivity());
        mBlockDetailFairAdapter =  new BlockDetailFairAdapter(null,getActivity());
        mBlockDetailShopAdapter =  new BlockDetailShopAdapter(null,getActivity());
        mBlockDetailHot.setAdapter(mCommonItemAdapter);
        mBlockDetailFair.setAdapter(mBlockDetailFairAdapter);
        mBlockDetailShop.setAdapter(mBlockDetailShopAdapter);

        mCommonItemAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()){
                case R.id.adapter_fair_more:
                    startActivity(BlockActivity.getIntent(getActivity(),0));
                    break;
            }
        });
        mBlockDetailFairAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()){
                case R.id.adapter_fair_more:
                    startActivity(BlockActivity.getIntent(getActivity(),1));
                    break;
            }
        });
        mBlockDetailShopAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()){
                case R.id.adapter_fair_more:
                    startActivity(BlockActivity.getIntent(getActivity(),2));
                    break;
            }
        });
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
        mBlockDetailFairAdapter.setNewData(mList);
        mBlockDetailShopAdapter.setNewData(mList);
        mCommonItemAdapter.setNewData(mList);

    }

    private void initAdapter() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
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

    private void initialize() {
        DaggerBlockDetailFragmentComponent.builder()
                .applicationComponent(((DaggerApplication) getActivity().getApplication()).getApplicationComponent())
                .blockDetailActivityModule(new BlockDetailActivityModule((AppCompatActivity) getActivity(), this))
                .blockDetailFragmentModule(new BlockDetailFragmentModule((BlockDetailActivity) getActivity())).build()
                .inject(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
