package com.banshengyuan.feima.view.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.banshengyuan.feima.DaggerApplication;
import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerDiscoverFragmentComponent;
import com.banshengyuan.feima.dagger.module.DiscoverFragmentModule;
import com.banshengyuan.feima.dagger.module.MainActivityModule;
import com.banshengyuan.feima.entity.MainProducts;
import com.banshengyuan.feima.view.PresenterControl.ProductControl;
import com.banshengyuan.feima.view.activity.MainActivity;
import com.banshengyuan.feima.view.adapter.ProductAdapter;
import com.banshengyuan.feima.view.customview.banner.CBViewHolderCreator;
import com.banshengyuan.feima.view.customview.banner.ConvenientBanner;
import com.banshengyuan.feima.view.customview.banner.NetworkImageHolderView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.banshengyuan.feima.R.id.convenientBanner;

/**
 * Created by helei on 2017/5/3.
 * SendingOrderFragment
 */

public class ProductFragment extends BaseFragment implements ProductControl.ProductView {


    @BindView(convenientBanner)
    ConvenientBanner mConvenientBanner;
    @BindView(R.id.product_products)
    RecyclerView mProductProducts;

    public static ProductFragment newInstance() {
        return new ProductFragment();
    }

    @Inject
    ProductControl.PresenterProduct mPresenter;

    private Unbinder unbind;
    private List<MainProducts> mList;
    private List<List> mList1;
    private String[] productNames = {"品牌", "活动", "商户", "停车", "魔门音乐", "空中花市", "会员积分", "更多"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    private void initData() {
        mList = new ArrayList<>();
        mList1 = new ArrayList<>();

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
        mList1.add(mList);
        mList1.add(mList);
        mConvenientBanner.setCanLoop(false);
        mConvenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, mList1).setPageIndicator(new int[]{R.drawable.shape_line2, R.drawable.shape_line_banner});
    }

    private void initView() {
        mProductProducts.setLayoutManager(new LinearLayoutManager(getActivity()));
        mProductProducts.setAdapter(new ProductAdapter(null,getActivity()));
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
        DaggerDiscoverFragmentComponent.builder()
                .applicationComponent(((DaggerApplication) getActivity().getApplication()).getApplicationComponent())
                .mainActivityModule(new MainActivityModule((AppCompatActivity) getActivity()))
                .discoverFragmentModule(new DiscoverFragmentModule(this, (MainActivity) getActivity()))
                .build()
                .inject(this);
    }
}
