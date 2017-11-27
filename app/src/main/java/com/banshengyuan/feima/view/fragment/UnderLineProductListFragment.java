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
import com.banshengyuan.feima.dagger.component.DaggerUnderLineFairFragmentComponent;
import com.banshengyuan.feima.dagger.module.UnderLineFairActivityModule;
import com.banshengyuan.feima.dagger.module.UnderLineFairFragmentModule;
import com.banshengyuan.feima.entity.BlockDetailFairListResponse;
import com.banshengyuan.feima.entity.BlockDetailResponse;
import com.banshengyuan.feima.entity.BlockStoreListResponse;
import com.banshengyuan.feima.view.PresenterControl.UnderLineFairControl;
import com.banshengyuan.feima.view.activity.UnderLineFairActivity;
import com.banshengyuan.feima.view.adapter.FairDetailNewAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by helei on 2017/5/3.
 * TrendsFragment
 */

public class UnderLineProductListFragment extends BaseFragment implements UnderLineFairControl.UnderLineFairView {
    @BindView(R.id.fragment_trends_list_last)
    RecyclerView mFragmentTrendsListLast;

    public static UnderLineProductListFragment newInstance() {
        return new UnderLineProductListFragment();
    }

    @Inject
    UnderLineFairControl.PresenterUnderLineFair mPresenter;

    private Unbinder unbind;
    private FairDetailNewAdapter mAdapter;
    private Integer mBlcokId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
        mBlcokId = ((UnderLineFairActivity) getActivity()).getBlockId();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trends, container, false);
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
       /* List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.header);
        list.add(R.mipmap.header);
        list.add(R.mipmap.header);
        list.add(R.mipmap.header);
        list.add(R.mipmap.header);
        list.add(R.mipmap.header);
        mAdapter.setNewData(list);*/
    }

    private void initView() {
        mFragmentTrendsListLast.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mAdapter = new FairDetailNewAdapter(null, getActivity(), mImageLoaderHelper);
        mFragmentTrendsListLast.setAdapter(mAdapter);
    }

    @Override
    public void getBlockFairListSuccess(BlockDetailFairListResponse response) {

    }

    @Override
    public void getBlockFairListFail(String des) {

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
        DaggerUnderLineFairFragmentComponent.builder()
                .applicationComponent(((DaggerApplication) getActivity().getApplication()).getApplicationComponent())
                .underLineFairActivityModule(new UnderLineFairActivityModule((AppCompatActivity) getActivity(), this))
                .underLineFairFragmentModule(new UnderLineFairFragmentModule((UnderLineFairActivity) getActivity())).build()
                .inject(this);
    }
}
