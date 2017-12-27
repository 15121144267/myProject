package com.banshengyuan.feima.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.banshengyuan.feima.DaggerApplication;
import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerCouponFragmentComponent;
import com.banshengyuan.feima.dagger.module.CoupleActivityModule;
import com.banshengyuan.feima.dagger.module.CouponFragmentModule;
import com.banshengyuan.feima.entity.Constant;
import com.banshengyuan.feima.entity.MyCoupleResponse;
import com.banshengyuan.feima.view.PresenterControl.CouponPastAvailableControl;
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
 */

public class CouponPastFragment extends BaseFragment implements CouponPastAvailableControl.CouponPastAvailableView, BaseQuickAdapter.RequestLoadMoreListener {

    public static CouponPastFragment newInstance() {
        return new CouponPastFragment();
    }

    @Inject
    CouponPastAvailableControl.PresenterCouponPastAvailable mPresenter;
    @BindView(R.id.coupon_common_list)
    RecyclerView mCouponCommonList;

    private CouponAdapter mAdapter;
    private Unbinder unbind;
    private List<MyCoupleResponse.ListBean> mList = new ArrayList<>();
    private String state = "3";//券状态 1未使用 2已使用 3已过期
    private int page = 1;
    private int pageSize = 10;
    private String token;
    private View mEmptyView = null;

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
        token = mBuProcessor.getUserToken();
//        token = Constant.TOKEN;
        mPresenter.requestExpiredCouponList(state, page, pageSize, token);
    }

    private void initView() {
        mCouponCommonList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new CouponAdapter(null, getActivity());
        mAdapter.setOnLoadMoreListener(this,mCouponCommonList);
        mCouponCommonList.setAdapter(mAdapter);

        mEmptyView = LayoutInflater.from(getActivity()).inflate(R.layout.empty_view, (ViewGroup) mCouponCommonList.getParent(), false);
        ImageView imageView = (ImageView) mEmptyView.findViewById(R.id.empty_icon);
        imageView.setImageResource(R.mipmap.empty_couple_view);
        TextView emptyContent = (TextView) mEmptyView.findViewById(R.id.empty_content);
        emptyContent.setVisibility(View.GONE);
        emptyContent.setText(R.string.couple_expired_empty_view);
        Button emptyButton = (Button) mEmptyView.findViewById(R.id.empty_text);
        emptyButton.setVisibility(View.GONE);
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
    public void getExpiredCoupleListSuccess(MyCoupleResponse myCoupleResponse) {
        mList = myCoupleResponse.getList();
        if (page == 1 && mList.size() == 0) {
            mAdapter.setEmptyView(mEmptyView);
            return;
        }
        if (mList.size() > 0) {
            mAdapter.addData(mList);
            mAdapter.loadMoreComplete();
        } else {
            mAdapter.loadMoreEnd();
        }
    }

    @Override
    public void onLoadMoreRequested() {
        if(page==1 && mList.size() < pageSize){
            mAdapter.loadMoreEnd(true);
        }else {
            if (mList.size() < pageSize) {
                mAdapter.loadMoreEnd();
            } else {
                mPresenter.requestExpiredCouponList(state, ++page, pageSize, token);
            }
        }
    }
}
