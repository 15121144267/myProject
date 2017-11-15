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
import android.widget.LinearLayout;

import com.banshengyuan.feima.DaggerApplication;
import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerFragmentComponent;
import com.banshengyuan.feima.dagger.module.FragmentModule;
import com.banshengyuan.feima.dagger.module.MainActivityModule;
import com.banshengyuan.feima.entity.ShopResponse;
import com.banshengyuan.feima.utils.ValueUtil;
import com.banshengyuan.feima.view.PresenterControl.PendingOrderControl;
import com.banshengyuan.feima.view.activity.GoodsClassifyActivity;
import com.banshengyuan.feima.view.activity.MainActivity;
import com.banshengyuan.feima.view.activity.SearchActivity;
import com.banshengyuan.feima.view.adapter.MyOrderFragmentAdapter;
import com.banshengyuan.feima.view.customview.ClearEditText;
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

public class PendingOrderFragment extends BaseFragment implements PendingOrderControl.PendingOrderView, ClearEditText.setOnMyEditorActionListener {

    @BindView(R.id.main_tab_layout)
    TabLayout mMainTabLayout;
    @BindView(R.id.main_view_pager)
    ViewPager mMainViewPager;
    @BindView(R.id.pending_show_search)
    ImageView mPendingShowSearch;
    @BindView(R.id.pending_enter_classify)
    ImageView mPendingEnterClassify;
    @BindView(R.id.search_edit)
    ClearEditText mSearchEdit;
    @BindView(R.id.search_layout)
    LinearLayout mSearchLayout;

    public static PendingOrderFragment newInstance() {
        return new PendingOrderFragment();
    }

    @Inject
    PendingOrderControl.PresenterPendingOrder mPresenter;

    private Unbinder unbinder;
    private boolean showSearchLayout = true;
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

    @Override
    public void onMyEditorAction() {

    }

    @Override
    public void onMyTouchAction() {
        hideSoftInput(mSearchEdit);
        startActivity(SearchActivity.getIntent(getActivity()));
    }

    private void initView() {
        List<Fragment> mFragments = new ArrayList<>();
        mFragments.add(RecommendFragment.newInstance());
        mFragments.add(MainFairFragment.newInstance());
        mFragments.add(MainVistaFragment.newInstance());
        mFragments.add(MagicMusicFragment.newInstance());
        MyOrderFragmentAdapter adapter = new MyOrderFragmentAdapter(getChildFragmentManager(), mFragments, modules);
        mMainViewPager.setOffscreenPageLimit(mFragments.size() - 1);
        mMainViewPager.setAdapter(adapter);
        mMainTabLayout.setupWithViewPager(mMainViewPager);
        ValueUtil.setIndicator(mMainTabLayout, 10, 10);
        RxView.clicks(mPendingShowSearch).subscribe(o -> showSearchLayout(showSearchLayout));
        RxView.clicks(mPendingEnterClassify).throttleFirst(1, TimeUnit.SECONDS).subscribe(
                o -> startActivity(GoodsClassifyActivity.getIntent(getActivity())));
        mSearchEdit.setOnMyEditorActionListener(this,true);
    }

    public void showSearchLayout(boolean flag) {
       /* if(flag){
            AniCreator.getInstance().apply_animation_translate(mSearchLayout, AniCreator.ANIMATION_MODE_DROPDOWN, View.VISIBLE, false, null);
        }else {
            AniCreator.getInstance().apply_animation_translate(mSearchLayout, AniCreator.ANIMATION_MODE_DROPDOWN_RESEVERD, View.GONE, false, null);
        }*/

        mSearchLayout.setVisibility(flag ? View.VISIBLE : View.GONE);
        showSearchLayout = !flag;
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
