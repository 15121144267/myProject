package com.banshengyuan.feima.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.banshengyuan.feima.DaggerApplication;
import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerFragmentComponent;
import com.banshengyuan.feima.dagger.module.FragmentModule;
import com.banshengyuan.feima.dagger.module.MainActivityModule;
import com.banshengyuan.feima.listener.TabCheckListener;
import com.banshengyuan.feima.utils.ValueUtil;
import com.banshengyuan.feima.view.PresenterControl.SendingOrderControl;
import com.banshengyuan.feima.view.activity.GoodsClassifyActivity;
import com.banshengyuan.feima.view.activity.MainActivity;
import com.banshengyuan.feima.view.activity.SearchActivity;
import com.banshengyuan.feima.view.adapter.MyOrderFragmentAdapter;
import com.banshengyuan.feima.view.customview.ClearEditText;
import com.banshengyuan.feima.view.customview.MyNoScrollViewPager;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.HashMap;
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

public class SendingOrderFragment extends BaseFragment implements SendingOrderControl.SendingOrderView, ClearEditText.setOnMyEditorActionListener {

    @BindView(R.id.discover_tab_layout)
    TabLayout mDiscoverTabLayout;
    @BindView(R.id.discover_view_pager)
    MyNoScrollViewPager mDiscoverViewPager;
    @BindView(R.id.sending_enter_classify)
    ImageView mSendingEnterClassify;
    @BindView(R.id.sending_show_search)
    ImageView mSendingShowSearch;
    @BindView(R.id.search_edit)
    ClearEditText mSearchEdit;
    @BindView(R.id.search_layout)
    LinearLayout mSearchLayout;
    @BindView(R.id.search_cancel)
    TextView mSearchCancel;

    public static SendingOrderFragment newInstance() {
        return new SendingOrderFragment();
    }

    private boolean showSearchLayout = true;
    private final String[] modules = {"市集", "产品", "商家"};
    private HashMap<Integer, Integer> mHashMap;
    @Inject
    SendingOrderControl.PresenterSendingOrder mPresenter;
    private Unbinder unbind;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sending_order, container, false);
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
    public void onMyEditorAction() {
        String searchName = mSearchEdit.getEditText().trim();
        startActivity(SearchActivity.getIntent(getActivity(), searchName));
    }

    @Override
    public void onMyTouchAction() {

    }

    private void initData() {

    }

    private void initView() {
        mHashMap = new HashMap<>();
        for (int i = 0; i < modules.length; i++) {
            mHashMap.put(i, 0);
        }
        mSearchEdit.setEditHint("请输入市集、街区、产品、商家");
        mSearchEdit.setOnMyEditorActionListener(this, false);
        List<Fragment> mFragments = new ArrayList<>();
        mFragments.add(FairFragment.newInstance());
        mFragments.add(ProductFragment.newInstance());
        mFragments.add(SellerFragment.newInstance());
        MyOrderFragmentAdapter adapter = new MyOrderFragmentAdapter(getChildFragmentManager(), mFragments, modules);
        mDiscoverViewPager.setOffscreenPageLimit(mFragments.size() - 1);
        mDiscoverViewPager.setAdapter(adapter);
        mDiscoverTabLayout.setupWithViewPager(mDiscoverViewPager);
        ValueUtil.setIndicator(mDiscoverTabLayout, 20, 20);
        RxView.clicks(mSendingEnterClassify).throttleFirst(1, TimeUnit.SECONDS).subscribe(
                o -> startActivity(GoodsClassifyActivity.getIntent(getActivity())));
        RxView.clicks(mSearchCancel).subscribe(o -> {
            mSearchEdit.clearContent();
            mSearchLayout.setVisibility(View.GONE);
        });
        RxView.clicks(mSendingShowSearch).subscribe(o -> showSearchLayout(showSearchLayout));
        mDiscoverTabLayout.addOnTabSelectedListener(new TabCheckListener() {
            @Override
            public void onMyTabSelected(TabLayout.Tab tab) {
                showOrCloseSearchLayout(tab.getPosition());
            }
        });
    }

    private void showOrCloseSearchLayout(Integer position) {
        Integer flag = mHashMap.get(position);
        mSearchLayout.setVisibility(flag == 1 ? View.VISIBLE : View.GONE);
    }

    public void showSearchLayout(boolean flag) {
        if (flag) {
            mSearchLayout.setVisibility(View.VISIBLE);
            mHashMap.put(mDiscoverTabLayout.getSelectedTabPosition(), 1);
        } else {
            mSearchLayout.setVisibility(View.GONE);
            mHashMap.put(mDiscoverTabLayout.getSelectedTabPosition(), 0);
        }
        showSearchLayout = !flag;
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
        DaggerFragmentComponent.builder()
                .applicationComponent(((DaggerApplication) getActivity().getApplication()).getApplicationComponent())
                .mainActivityModule(new MainActivityModule((AppCompatActivity) getActivity()))
                .fragmentModule(new FragmentModule(this, (MainActivity) getActivity())).build()
                .inject(this);
    }
}
