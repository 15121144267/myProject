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
import com.banshengyuan.feima.dagger.component.DaggerBlockFragmentComponent;
import com.banshengyuan.feima.dagger.module.BlockActivityModule;
import com.banshengyuan.feima.dagger.module.BlockFragmentModule;
import com.banshengyuan.feima.entity.BlockFairListResponse;
import com.banshengyuan.feima.entity.BlockHotListResponse;
import com.banshengyuan.feima.entity.BlockStoreListResponse;
import com.banshengyuan.feima.view.PresenterControl.BlockControl;
import com.banshengyuan.feima.view.activity.BlockActivity;
import com.banshengyuan.feima.view.adapter.BlockDetailStoreListAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by helei on 2017/5/3.
 * PendingOrderFragment
 */

public class BlockShopFragment extends BaseFragment implements BlockControl.BlockView {


    @BindView(R.id.fragment_block_common)
    RecyclerView mFragmentBlockCommon;

    public static BlockShopFragment newInstance() {
        return new BlockShopFragment();
    }


    @Inject
    BlockControl.PresenterBlock mPresenter;

    private Unbinder unbinder;
    private BlockDetailStoreListAdapter mAdapter;
    private Integer mBlockId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
        mBlockId = ((BlockActivity) getActivity()).getBlockId();
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
    public void getStoreListSuccess(BlockStoreListResponse response) {
        List<BlockStoreListResponse.ListBean> list = response.list;
        if (list != null && list.size() > 0) {
            mAdapter.setNewData(list);
        } else {
            mFragmentBlockCommon.setVisibility(View.GONE);
        }
    }

    @Override
    public void getStoreListFail() {
        mFragmentBlockCommon.setVisibility(View.GONE);
    }


    private void initView() {
        mFragmentBlockCommon.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new BlockDetailStoreListAdapter(null, getActivity(),mImageLoaderHelper);
        mFragmentBlockCommon.setAdapter(mAdapter);
    }


    private void initData() {
        mPresenter.requestStoreList(mBlockId);
    }

    @Override
    public void getFairListSuccess(BlockFairListResponse response) {

    }

    @Override
    public void getFairListFail() {

    }

    @Override
    public void getHotListSuccess(BlockHotListResponse response) {

    }

    @Override
    public void getHotListFail() {

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
        DaggerBlockFragmentComponent.builder()
                .applicationComponent(((DaggerApplication) getActivity().getApplication()).getApplicationComponent())
                .blockActivityModule(new BlockActivityModule((AppCompatActivity) getActivity(), this))
                .blockFragmentModule(new BlockFragmentModule((BlockActivity) getActivity())).build()
                .inject(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
