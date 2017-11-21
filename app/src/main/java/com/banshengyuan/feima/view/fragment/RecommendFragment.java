package com.banshengyuan.feima.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.banshengyuan.feima.DaggerApplication;
import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerMainFragmentComponent;
import com.banshengyuan.feima.dagger.module.MainActivityModule;
import com.banshengyuan.feima.dagger.module.MainFragmentModule;
import com.banshengyuan.feima.entity.RecommendBottomResponse;
import com.banshengyuan.feima.entity.RecommendBrandResponse;
import com.banshengyuan.feima.entity.RecommendTopResponse;
import com.banshengyuan.feima.utils.ValueUtil;
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

    @BindView(R.id.recommend_block_detail_distance)
    TextView mRecommendBlockDetailDistance;
    @BindView(R.id.recommend_block_detail_name)
    TextView mRecommendBlockDetailName;
    @BindView(R.id.recommend_block_detail_top_icon)
    ImageView mRecommendBlockDetailTopIcon;

    public static RecommendFragment newInstance() {
        return new RecommendFragment();
    }

    @Inject
    RecommendControl.PresenterRecommend mPresenter;

    private Unbinder unbind;
    private RecommendBrandAdapter mAdapter;
    private RecommendDiscoverBrandAdapter mDiscoverBrandAdapter;
    private List<RecommendBrandResponse> mList;
    private RecommendTopResponse.InfoBean mInfoBean;
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

    @Override
    public void getRecommendTopSuccess(RecommendTopResponse recommendTopResponse) {
        mInfoBean = recommendTopResponse.info;
        if (mInfoBean != null) {
            mImageLoaderHelper.displayRoundedCornerImage(getActivity(), mInfoBean.cover_img, mRecommendBlockDetailTopIcon, 4);
            if (mInfoBean.distance.equals("0")) {
                mRecommendBlockDetailDistance.setText("  距离未知,请开启权限");
            } else {
                mRecommendBlockDetailDistance.setText("  距您" + ValueUtil.formatDistance(Float.parseFloat(mInfoBean.distance)));
            }
            mRecommendBlockDetailName.setText(mInfoBean.name);
        }

    }

    @Override
    public void getRecommendBrandSuccess(RecommendBrandResponse recommendBrandResponse) {
        mList = new ArrayList<>();
        mList.add(recommendBrandResponse);
        mAdapter.setNewData(mList);
    }

    @Override
    public void getRecommendBrandFail() {
        mRecommendBrandRecycleView.setVisibility(View.GONE);
    }

    @Override
    public void getRecommendBottomSuccess(RecommendBottomResponse recommendBottomResponse) {
        List<RecommendBottomResponse.ListBean> listBeen = recommendBottomResponse.list;
        if (listBeen != null && listBeen.size() > 0) {
            mDiscoverBrandAdapter.setNewData(listBeen);
        }
    }

    @Override
    public void getRecommendBottomFail() {
        mRecommendDiscoverRecycleView.setVisibility(View.GONE);
    }

    private void initData() {
        //请求推荐页头布局
        if (mLocationInfo != null) {
            mPresenter.requestRecommendTop(mLocationInfo.getLongitude(), mLocationInfo.getLatitude());
        } else {
            mPresenter.requestRecommendTop(0, 0);
        }
        //请求推荐页品牌布局
        mPresenter.requestRecommendBrand();

        //请求推荐页发现精彩
        mPresenter.requestRecommendBottom();
    }

    private void initView() {

        RxView.clicks(mRecommendBlockDetailTopIcon).throttleFirst(1, TimeUnit.SECONDS).subscribe(
                o -> startActivity(BlockDetailActivity.getIntent(getActivity(),mInfoBean.id)));

        mRecommendBrandRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecommendDiscoverRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new RecommendBrandAdapter(null, getActivity(), mImageLoaderHelper);
        mDiscoverBrandAdapter = new RecommendDiscoverBrandAdapter(null, getActivity(), mImageLoaderHelper);

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
