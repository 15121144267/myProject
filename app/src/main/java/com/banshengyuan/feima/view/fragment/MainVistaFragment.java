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
import com.banshengyuan.feima.dagger.component.DaggerMainFragmentComponent;
import com.banshengyuan.feima.dagger.module.MainActivityModule;
import com.banshengyuan.feima.dagger.module.MainFragmentModule;
import com.banshengyuan.feima.entity.VistaListResponse;
import com.banshengyuan.feima.view.PresenterControl.VistaControl;
import com.banshengyuan.feima.view.activity.BlockDetailActivity;
import com.banshengyuan.feima.view.activity.MainActivity;
import com.banshengyuan.feima.view.adapter.VistaFairAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by helei on 2017/5/3.
 * SendingOrderFragment
 */

public class MainVistaFragment extends BaseFragment implements VistaControl.VistaView {
    @BindView(R.id.main_fair_recycle_view)
    RecyclerView mMainFairRecycleView;

    public static MainVistaFragment newInstance() {
        return new MainVistaFragment();
    }

    @Inject
    VistaControl.PresenterVista mPresenter;

    private Unbinder unbind;
    private VistaFairAdapter mHotFairAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vista, container, false);
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
    public void getVistaListSuccess(VistaListResponse vistaListResponse) {
        List<VistaListResponse.ListBean> list = vistaListResponse.list;
        if(list!=null&&list.size()>0){
            mHotFairAdapter.setNewData(list);
        }
    }

    @Override
    public void getVistaListFail() {
        mMainFairRecycleView.setVisibility(View.GONE);
    }

    private void initData() {
        if (mLocationInfo != null) {
            mPresenter.requestVistaList(mLocationInfo.getLongitude(), mLocationInfo.getLatitude());
        } else {
            mPresenter.requestVistaList(0, 0);
        }

    }

    private void initView() {
        mMainFairRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMainFairRecycleView.setNestedScrollingEnabled(false);
        mHotFairAdapter = new VistaFairAdapter(null, getActivity(),mImageLoaderHelper);
        mMainFairRecycleView.setAdapter(mHotFairAdapter);
        mHotFairAdapter.setOnItemClickListener((adapter, view, position) -> {
            VistaListResponse.ListBean bean = (VistaListResponse.ListBean)adapter.getItem(position);
            if(bean!=null){
                startActivity(BlockDetailActivity.getIntent(getActivity(),bean.id));
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
                .build()
                .inject(this);
    }
}
