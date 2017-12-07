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
import com.banshengyuan.feima.dagger.component.DaggerCouponFragmentComponent;
import com.banshengyuan.feima.dagger.module.CoupleActivityModule;
import com.banshengyuan.feima.dagger.module.CouponFragmentModule;
import com.banshengyuan.feima.entity.Constant;
import com.banshengyuan.feima.entity.MyCoupleResponse;
import com.banshengyuan.feima.view.PresenterControl.CouponNotAvailableControl;
import com.banshengyuan.feima.view.activity.CoupleActivity;
import com.banshengyuan.feima.view.adapter.CouponAdapter;
import com.example.mylibrary.adapter.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by helei on 2017/5/3.
 * SendingOrderFragment
 * 已使用
 */

public class CouponNotAvailableFragment extends BaseFragment implements CouponNotAvailableControl.CouponNotAvailableView , BaseQuickAdapter.RequestLoadMoreListener{
    public static CouponNotAvailableFragment newInstance() {
        return new CouponNotAvailableFragment();
    }

    @Inject
    CouponNotAvailableControl.PresenterCouponNotAvailable mPresenter;
    @BindView(R.id.coupon_common_list)
    RecyclerView mCouponCommonList;

    private CouponAdapter mAdapter;
    private Unbinder unbind;
    private List<MyCoupleResponse.ListBean> mList = new ArrayList<>();
    private String state = "2";//券状态 1未使用 2已使用 3已过期
    private int page = 1;
    private int pageSize = 10;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coupon_common, container, false);
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
//        mList = new ArrayList<>();
//        mList.add(R.mipmap.main_banner_second);
//        mAdapter.setNewData(mList);

        mPresenter.requestUsedCouponList(state, page, pageSize, Constant.TOKEN);
    }

    private void initView() {
        mCouponCommonList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new CouponAdapter(null, getActivity());
        mCouponCommonList.setAdapter(mAdapter);
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
        DaggerCouponFragmentComponent.builder()
                .applicationComponent(((DaggerApplication) getActivity().getApplication()).getApplicationComponent())
                .coupleActivityModule(new CoupleActivityModule((AppCompatActivity) getActivity()))
                .couponFragmentModule(new CouponFragmentModule(this, (CoupleActivity) getActivity()))
                .build()
                .inject(this);
    }

    @Override
    public void getUsedCoupleListSuccess(MyCoupleResponse myCoupleResponse) {
        if (myCoupleResponse.getList().size() > 0) {
            mList = myCoupleResponse.getList();
            mAdapter.setNewData(mList);
        }
    }

    @Override
    public void onLoadMoreRequested() {
        if (mList.size() < pageSize) {
            mAdapter.loadMoreEnd(true);
        } else {
            mPresenter.requestUsedCouponList(state, ++page, pageSize, Constant.TOKEN);
        }
    }
}
