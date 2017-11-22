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
import com.banshengyuan.feima.dagger.component.DaggerExchangeFragmentComponent;
import com.banshengyuan.feima.dagger.module.ExchangeFragmentModule;
import com.banshengyuan.feima.dagger.module.FairDetailActivityModule;
import com.banshengyuan.feima.entity.CommentListResponse;
import com.banshengyuan.feima.view.PresenterControl.CommentControl;
import com.banshengyuan.feima.view.activity.FairDetailActivity;
import com.banshengyuan.feima.view.adapter.CommentAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by helei on 2017/5/3.
 * TrendsFragment
 */

public class CommentFragment extends BaseFragment implements CommentControl.CommentView {
    @BindView(R.id.fragment_trends_list_last)
    RecyclerView mFragmentTrendsListLast;

    public static CommentFragment newInstance() {
        return new CommentFragment();
    }

    @Inject
    CommentControl.PresenterComment mPresenter;

    private Unbinder unbind;
    private CommentAdapter mAdapter;
    private Integer mFairId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
        mFairId = ((FairDetailActivity) getActivity()).getFairId();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
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
    public void getCommentListSuccess(CommentListResponse response) {
        List<CommentListResponse.ListBean> list = response.list;
        if (list != null && list.size() > 0) {
            mAdapter.setNewData(list);
        } else {
            mFragmentTrendsListLast.setVisibility(View.GONE);
        }
    }

    @Override
    public void getCommentListFail() {
        mFragmentTrendsListLast.setVisibility(View.GONE);
    }

    private void initData() {
        mPresenter.requestCommentList(mFairId);
    }

    private void initView() {
        mFragmentTrendsListLast.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new CommentAdapter(null, getActivity(),mImageLoaderHelper);
        mFragmentTrendsListLast.setAdapter(mAdapter);
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
        DaggerExchangeFragmentComponent.builder()
                .applicationComponent(((DaggerApplication) getActivity().getApplication()).getApplicationComponent())
                .fairDetailActivityModule(new FairDetailActivityModule((AppCompatActivity) getActivity()))
                .exchangeFragmentModule(new ExchangeFragmentModule(this, (AppCompatActivity) getActivity()))
                .build()
                .inject(this);
    }
}
