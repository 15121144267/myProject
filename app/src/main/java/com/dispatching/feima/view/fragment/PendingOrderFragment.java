package com.dispatching.feima.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dispatching.feima.DaggerApplication;
import com.dispatching.feima.R;
import com.dispatching.feima.dagger.component.DaggerFragmentComponent;
import com.dispatching.feima.dagger.module.FragmentModule;
import com.dispatching.feima.dagger.module.MainActivityModule;
import com.dispatching.feima.entity.BroConstant;
import com.dispatching.feima.entity.MainProducts;
import com.dispatching.feima.entity.OrderDeliveryResponse;
import com.dispatching.feima.view.PresenterControl.PendingOrderControl;
import com.dispatching.feima.view.activity.ShopListActivity;
import com.dispatching.feima.view.adapter.MainProductsAdapter;
import com.dispatching.feima.view.customview.ClearEditText;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by helei on 2017/5/3.
 * PendingOrderFragment
 */

public class PendingOrderFragment extends BaseFragment implements PendingOrderControl.PendingOrderView, BGABanner.Adapter<ImageView, String> {

    @BindView(R.id.combo_banner)
    BGABanner mComboBanner;
    @BindView(R.id.search_shop)
    ClearEditText mSearchShop;
    @BindView(R.id.products_item)
    RecyclerView mProductsItem;

    public static PendingOrderFragment newInstance() {
        return new PendingOrderFragment();
    }


    @Inject
    PendingOrderControl.PresenterPendingOrder mPresenter;

    private Unbinder unbinder;
    private MainProductsAdapter mAdapter;
    private List<MainProducts> mList;
    private String[] productNames = {"品牌", "活动", "商户", "停车", "魔门音乐", "空中花园", "会员积分", "更多"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pending_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initAdapter();
        return view;
    }

    private void initView() {
        mSearchShop.setEditHint("搜索商户");
        mComboBanner.setAdapter(this);
        mList = new ArrayList<>();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData() {
        Drawable[] productDrawable = {
                ContextCompat.getDrawable(getActivity(), R.mipmap.product_brand),
                ContextCompat.getDrawable(getActivity(), R.mipmap.product_activity),
                ContextCompat.getDrawable(getActivity(), R.mipmap.product_shop),
                ContextCompat.getDrawable(getActivity(), R.mipmap.product_part),
                ContextCompat.getDrawable(getActivity(), R.mipmap.product_music),
                ContextCompat.getDrawable(getActivity(), R.mipmap.product_flower),
                ContextCompat.getDrawable(getActivity(), R.mipmap.product_score),
                ContextCompat.getDrawable(getActivity(), R.mipmap.product_more)
        };


        for (int i = 0; i < productNames.length; i++) {
            MainProducts product = new MainProducts();
            product.productName = productNames[i];
            product.productDrawable = productDrawable[i];
            mList.add(product);
        }
        mAdapter.setNewData(mList);
    }

    @Override
    public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
        Glide.with(itemView.getContext())
                .load(model)
                .placeholder(R.mipmap.holder)
                .error(R.mipmap.holder)
                .dontAnimate()
                .centerCrop()
                .into(itemView);
    }


    @Override
    public void getPendingOrderSuccess(OrderDeliveryResponse response) {

    }

    @Override
    public void getPendingOrderComplete() {
        dismissLoading();
    }

    @Override
    public void getOrderError(Throwable throwable) {
        showErrMessage(throwable);
    }

    private void initAdapter() {
        mProductsItem.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mAdapter = new MainProductsAdapter(mList, getActivity());
        mProductsItem.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
                    if (position == 2) {
                        startActivity(ShopListActivity.getIntent(getActivity()));
                    } else {
                        showToast(String.valueOf(position));
                    }

                }
        );
    }


    @Override
    protected void addFilter() {
        mFilter.addAction(BroConstant.PENDING_DELIVERY);
    }

    @Override
    protected void onReceivePro(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action) {
            case BroConstant.PENDING_DELIVERY:

                break;
        }
    }


    @Override
    public void updateOrderStatusSuccess() {

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
        DaggerFragmentComponent.builder()
                .applicationComponent(((DaggerApplication) getActivity().getApplication()).getApplicationComponent())
                .mainActivityModule(new MainActivityModule((AppCompatActivity) getActivity()))
                .fragmentModule(new FragmentModule(this)).build()
                .inject(this);
    }

    /*private void showPasswordDialog(String orderId) {
        PasswordDialog passwordDialog = PasswordDialog.newInstance();
        passwordDialog.setContent(orderId);
        passwordDialog.setTitle(getString(R.string.dialog_password_tips));
        passwordDialog.setListener(this);
        DialogFactory.showDialogFragment(getActivity().getSupportFragmentManager(), passwordDialog, PasswordDialog.TAG);
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
