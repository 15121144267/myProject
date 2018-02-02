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
import com.banshengyuan.feima.dagger.component.DaggerCollectionFragmentComponent;
import com.banshengyuan.feima.dagger.module.CollectionActivityModule;
import com.banshengyuan.feima.dagger.module.CollectionFragmentModule;
import com.banshengyuan.feima.entity.CollectionShopResponse;
import com.banshengyuan.feima.view.PresenterControl.CollectionShopControl;
import com.banshengyuan.feima.view.activity.MyCollectionActivity;
import com.banshengyuan.feima.view.activity.ShopProductDetailActivity;
import com.banshengyuan.feima.view.adapter.CollectionShopAdapter;
import com.example.mylibrary.adapter.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by helei on 2017/5/3.
 * 我的收藏-商家
 */

public class CollectionShopFragment extends BaseFragment implements CollectionShopControl.CollectionShopView, BaseQuickAdapter.RequestLoadMoreListener {

    public static CollectionShopFragment newInstance() {
        return new CollectionShopFragment();
    }

    @BindView(R.id.coupon_common_list)
    RecyclerView mCouponCommonList;
    private Unbinder unbind;
    private List<CollectionShopResponse.ListBean> mList = new ArrayList<>();
    private CollectionShopAdapter mAdapter;
    private Integer mPagerSize = 10;
    private Integer mPagerNo = 1;
    private View mEmptyView = null;
    @Inject
    CollectionShopControl.PresenterCollectionShop mPresenter;
    private String token;

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
        mPresenter.requestCollectionShopList(mPagerNo, mPagerSize, token);
    }

    private void initView() {
        mCouponCommonList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new CollectionShopAdapter(null, getActivity(), mImageLoaderHelper);
        mAdapter.setOnLoadMoreListener(this, mCouponCommonList);
        mCouponCommonList.setAdapter(mAdapter);

        mEmptyView = LayoutInflater.from(getActivity()).inflate(R.layout.empty_view, (ViewGroup) mCouponCommonList.getParent(), false);
        ImageView imageView = (ImageView) mEmptyView.findViewById(R.id.empty_icon);
        mImageLoaderHelper.displayImage(getActivity(),R.mipmap.empty_collection_view,imageView);
        TextView emptyContent = (TextView) mEmptyView.findViewById(R.id.empty_content);
        emptyContent.setVisibility(View.VISIBLE);
        emptyContent.setText(R.string.connection_shop_empty_view);
        Button emptyButton = (Button) mEmptyView.findViewById(R.id.empty_text);
        emptyButton.setVisibility(View.GONE);

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            CollectionShopResponse.ListBean bean = (CollectionShopResponse.ListBean) adapter.getItem(position);
            if (bean != null) {
                startActivity(ShopProductDetailActivity.getActivityDetailIntent(getActivity(), bean.getId()));
            }
        });
    }

    @Override
    public void loadFail(Throwable throwable) {
        showErrMessage(throwable);
        mAdapter.loadMoreFail();
        if (mPagerNo > 1) mPagerNo--;
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
        DaggerCollectionFragmentComponent.builder()
                .applicationComponent(((DaggerApplication) getActivity().getApplication()).getApplicationComponent())
                .collectionActivityModule(new CollectionActivityModule((AppCompatActivity) getActivity()))
                .collectionFragmentModule(new CollectionFragmentModule(this, (MyCollectionActivity) getActivity()))
                .build()
                .inject(this);
    }

    @Override
    public void getMyCollectionListSuccess(CollectionShopResponse response) {
        mList = response.getList();
        if (mPagerNo == 1) {
            if (mList!= null && mList.size()>0) {
                mAdapter.setNewData(mList);
            } else {
                mAdapter.setNewData(null);
                mAdapter.setEmptyView(mEmptyView);
            }
        } else {
            mAdapter.addData(mList);
            mAdapter.loadMoreComplete();
        }
    }

    @Override
    public void onLoadMoreRequested() {
        if (mPagerNo == 1 && mList.size() < mPagerSize) {
            mAdapter.loadMoreEnd(true);
        } else {
            if (mList.size() < mPagerSize) {
                mAdapter.loadMoreEnd();
            } else {
                mPresenter.requestCollectionShopList(++mPagerNo, mPagerSize, token);
            }
        }
    }
}
