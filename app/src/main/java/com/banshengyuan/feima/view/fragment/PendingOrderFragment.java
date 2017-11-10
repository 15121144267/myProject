package com.banshengyuan.feima.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.banshengyuan.feima.DaggerApplication;
import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerFragmentComponent;
import com.banshengyuan.feima.dagger.module.FragmentModule;
import com.banshengyuan.feima.dagger.module.MainActivityModule;
import com.banshengyuan.feima.entity.ShopResponse;
import com.banshengyuan.feima.view.PresenterControl.PendingOrderControl;
import com.banshengyuan.feima.view.activity.GoodsClassifyActivity;
import com.banshengyuan.feima.view.activity.MainActivity;
import com.banshengyuan.feima.view.adapter.MyOrderFragmentAdapter;
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
 * PendingOrderFragment
 */

public class PendingOrderFragment extends BaseFragment implements PendingOrderControl.PendingOrderView {

    @BindView(R.id.main_tab_layout)
    TabLayout mMainTabLayout;
    @BindView(R.id.main_view_pager)
    ViewPager mMainViewPager;
    @BindView(R.id.pending_show_search)
    ImageView mPendingShowSearch;
    @BindView(R.id.pending_enter_classify)
    ImageView mPendingEnterClassify;

    public static PendingOrderFragment newInstance() {
        return new PendingOrderFragment();
    }

    @Inject
    PendingOrderControl.PresenterPendingOrder mPresenter;

    private Unbinder unbinder;
    private boolean showSearchLayout = false;
    private final String[] modules = {"推荐", "市集", "街景", "魔门"};

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
        List<Fragment> mFragments = new ArrayList<>();
        mFragments.add(RecommendFragment.newInstance());
        mFragments.add(MainFairFragment.newInstance());
//        mFragments.add(MainHotFragment.newInstance());
        mFragments.add(MainVistaFragment.newInstance());
        mFragments.add(MagicMusicFragment.newInstance());
        MyOrderFragmentAdapter adapter = new MyOrderFragmentAdapter(getChildFragmentManager(), mFragments, modules);
        mMainViewPager.setOffscreenPageLimit(mFragments.size() - 1);
        mMainViewPager.setAdapter(adapter);
        mMainTabLayout.setupWithViewPager(mMainViewPager);
//        mMainTabLayout.post(() ->   ValueUtil.setIndicator(mMainTabLayout, 35, 35));
        RxView.clicks(mPendingShowSearch).subscribe(o -> showSearchLayout());
        RxView.clicks(mPendingEnterClassify).throttleFirst(1, TimeUnit.SECONDS).subscribe(
                o -> startActivity(GoodsClassifyActivity.getIntent(getActivity())));

    }

    public void showSearchLayout() {
        showSearchLayout = !showSearchLayout;
        switch (mMainTabLayout.getSelectedTabPosition()){
            case 0:
                ((RecommendFragment)getChildFragmentManager().getFragments().get(0)).showSearchLayout(showSearchLayout);
                break;
            case 1:
                ((MainFairFragment)getChildFragmentManager().getFragments().get(1)).showSearchLayout(showSearchLayout);
                break;
            case 2:
                ((MainVistaFragment)getChildFragmentManager().getFragments().get(2)).showSearchLayout(showSearchLayout);
                break;
            case 3:
                ((MagicMusicFragment)getChildFragmentManager().getFragments().get(3)).showSearchLayout(showSearchLayout);
                break;
        }
    }

    private void initData() {


    }

    private void initAdapter() {

    }

    @Override
    public void getShopSuccess(ShopResponse response) {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
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
                .fragmentModule(new FragmentModule(this, (MainActivity) getActivity())).build()
                .inject(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
