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
import com.banshengyuan.feima.dagger.component.DaggerUnderLineFairFragmentComponent;
import com.banshengyuan.feima.dagger.module.UnderLineFairActivityModule;
import com.banshengyuan.feima.dagger.module.UnderLineFairFragmentModule;
import com.banshengyuan.feima.entity.BlockDetailFairListResponse;
import com.banshengyuan.feima.entity.BlockDetailProductListResponse;
import com.banshengyuan.feima.entity.BlockDetailResponse;
import com.banshengyuan.feima.entity.BlockStoreListResponse;
import com.banshengyuan.feima.entity.BroConstant;
import com.banshengyuan.feima.entity.FairUnderLineResponse;
import com.banshengyuan.feima.view.PresenterControl.UnderLineFairControl;
import com.banshengyuan.feima.view.activity.UnderLineFairActivity;
import com.banshengyuan.feima.view.adapter.BlockFairListAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by helei on 2017/5/3.
 * PendingOrderFragment
 */

public class UnderLineFairFragment extends BaseFragment implements UnderLineFairControl.UnderLineFairView {


    @BindView(R.id.fragment_block_common)
    RecyclerView mFragmentBlockCommon;

    public static UnderLineFairFragment newInstance() {
        return new UnderLineFairFragment();
    }


    @Inject
    UnderLineFairControl.PresenterUnderLineFair mPresenter;

    private Unbinder unbinder;
    private BlockFairListAdapter mAdapter;
    private Integer mBlockId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
        mBlockId = ((UnderLineFairActivity) getActivity()).getBlockId();
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    @Override
    public void getBlockFairListSuccess(BlockDetailFairListResponse response) {
        List<BlockDetailFairListResponse.ListBean> listBean = response.list;
        if (listBean != null && listBean.size() > 0) {
            mAdapter.setNewData(listBean);
        } else {
            mFragmentBlockCommon.setVisibility(View.GONE);
        }
    }

    @Override
    public void getFairUnderLineSuccess(FairUnderLineResponse fairUnderLineResponse) {

    }

    @Override
    public void getFairUnderLineFail() {

    }

    @Override
    public void getBlockFairListFail(String des) {
        showToast(des);
        mFragmentBlockCommon.setVisibility(View.GONE);
    }

    @Override
    void addFilter() {
        super.addFilter();
        mFilter.addAction(BroConstant.BLOCKDETAIL_UPDATE);
    }

    @Override
    void onReceivePro(Context context, Intent intent) {
        super.onReceivePro(context, intent);
        if (intent.getAction().equals(BroConstant.BLOCKDETAIL_UPDATE)) {
            mBlockId = intent.getIntExtra("blockId", 0);
            initData();
        }
    }

    private void initView() {
        mFragmentBlockCommon.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new BlockFairListAdapter(null, getActivity(), mImageLoaderHelper);
        mFragmentBlockCommon.setAdapter(mAdapter);
    }


    private void initData() {
        //请求街区下市集
        mPresenter.requestBlockFairList(mBlockId);
    }

    @Override
    public void getBlockDetailSuccess(BlockDetailResponse response) {

    }

    @Override
    public void getBlockDetailFail(String des) {

    }

    @Override
    public void getStoreListSuccess(BlockStoreListResponse response) {

    }

    @Override
    public void getStoreListFail() {

    }

    @Override
    public void getProductListSuccess(BlockDetailProductListResponse response) {

    }

    @Override
    public void getProductListFail(String des) {

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
        DaggerUnderLineFairFragmentComponent.builder()
                .applicationComponent(((DaggerApplication) getActivity().getApplication()).getApplicationComponent())
                .underLineFairActivityModule(new UnderLineFairActivityModule((AppCompatActivity) getActivity(), this))
                .underLineFairFragmentModule(new UnderLineFairFragmentModule((UnderLineFairActivity) getActivity())).build()
                .inject(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
