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
import com.banshengyuan.feima.dagger.component.DaggerUnderLineFairFragmentComponent;
import com.banshengyuan.feima.dagger.module.UnderLineFairActivityModule;
import com.banshengyuan.feima.dagger.module.UnderLineFairFragmentModule;
import com.banshengyuan.feima.view.PresenterControl.UnderLineFairControl;
import com.banshengyuan.feima.view.activity.UnderLineFairActivity;
import com.banshengyuan.feima.view.adapter.CollectionFairAdapter;

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

public class UnderLineFairFragment extends BaseFragment implements UnderLineFairControl.UnderLineFairView {


    @BindView(R.id.fragment_block_common)
    RecyclerView mFragmentBlockCommon;

    public static UnderLineFairFragment newInstance() {
        return new UnderLineFairFragment();
    }


    @Inject
    UnderLineFairControl.PresenterUnderLineFair mPresenter;

    private Unbinder unbinder;
    private CollectionFairAdapter mAdapter;
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initView() {
        mFragmentBlockCommon.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new CollectionFairAdapter(null, getActivity(),mImageLoaderHelper);
        mFragmentBlockCommon.setAdapter(mAdapter);
    }


    private void initData() {
        List<Integer> mList = new ArrayList<>();
        mList.add(R.mipmap.main_banner_first);
        mList.add(R.mipmap.main_banner_second);
        mList.add(R.mipmap.main_banner_third);
        mAdapter.setNewData(mList);
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
