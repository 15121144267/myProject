package com.banshengyuan.feima.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.banshengyuan.feima.entity.BroConstant;
import com.banshengyuan.feima.entity.MainProducts;
import com.banshengyuan.feima.entity.PersonInfoResponse;
import com.banshengyuan.feima.entity.SpConstant;
import com.banshengyuan.feima.help.DialogFactory;
import com.banshengyuan.feima.listener.AppBarStateChangeListener;
import com.banshengyuan.feima.utils.DataCleanManager;
import com.banshengyuan.feima.view.PresenterControl.CompletedOrderControl;
import com.banshengyuan.feima.view.activity.AddressActivity;
import com.banshengyuan.feima.view.activity.CoupleActivity;
import com.banshengyuan.feima.view.activity.MainActivity;
import com.banshengyuan.feima.view.activity.MyCollectionActivity;
import com.banshengyuan.feima.view.activity.MyOrderActivity;
import com.banshengyuan.feima.view.activity.NoticeCenterActivity;
import com.banshengyuan.feima.view.activity.PersonCenterActivity;
import com.banshengyuan.feima.view.activity.SafeSettingActivity;
import com.banshengyuan.feima.view.activity.ShoppingCardActivity;
import com.banshengyuan.feima.view.adapter.MainProductsAdapter;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.banshengyuan.feima.view.activity.MainActivity.DIALOG_TYPE_EXIT_OK;


/**
 * Created by helei on 2017/5/3.
 * CompletedOrderFragment
 */

public class CompletedOrderFragment extends BaseFragment implements CompletedOrderControl.CompletedOrderView, CommonDialog.CommonDialogListener {
    @BindView(R.id.person_list_enter)
    RecyclerView mPersonListEnter;
    @BindView(R.id.person_enter_safe_page)
    TextView mPersonEnterSafePage;
    @BindView(R.id.person_address_page)
    TextView mPersonAddressPage;
    @BindView(R.id.person_cache)
    TextView mPersonCache;
    @BindView(R.id.person_clean_cache)
    LinearLayout mPersonCleanCache;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.person_tips)
    ImageView mPersonTips;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.middle_name)
    TextView mMiddleName;
   /*
    @BindView(R.id.person_address)
    TextView mPersonAddress;
    @BindView(R.id.person_info)
    TextView mPersonInfo;
    @BindView(R.id.person_icon)
    ImageView mPersonIcon;
    @BindView(R.id.person_name)
    TextView mPersonName;
    @BindView(R.id.person_detail)
    TextView mPersonDetail;

    @BindView(R.id.move_image)
    ImageView mMoveImage;
    @BindView(R.id.scroll_layout)
    MoveScrollView mScrollLayout;*/

    public static CompletedOrderFragment newInstance() {
        return new CompletedOrderFragment();
    }

    @Inject
    CompletedOrderControl.PresenterCompletedOrder mPresenter;

    private Unbinder unbind;
    private PersonInfoResponse mResponse;
    private String[] productNames = {"购物车", "我的订单", "我的卡券", "我的收藏"};
    private List<MainProducts> mList;
    private MainProductsAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_complete_order, container, false);
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
    public void commonDialogBtnOkListener(int type, int position) {
        showToast("清除成功");
        DataCleanManager.clearAllCache(getActivity());
        try {
            mPersonCache.setText(DataCleanManager.getTotalCacheSize(getActivity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        RxView.clicks(mPersonEnterSafePage).throttleFirst(1, TimeUnit.SECONDS).subscribe(
                o -> startActivity(SafeSettingActivity.getIntent(getActivity())));

        RxView.clicks(mPersonTips).throttleFirst(1, TimeUnit.SECONDS).subscribe(
                o -> startActivity(NoticeCenterActivity.getIntent(getActivity())));

        RxView.clicks(mPersonAddressPage).throttleFirst(1, TimeUnit.SECONDS).subscribe(
                o -> startActivity(AddressActivity.getIntent(getActivity(), "CompletedOrderFragment")));

        RxView.clicks(mPersonCleanCache).throttleFirst(1, TimeUnit.SECONDS).subscribe(
                o -> showDialog());
        mPersonListEnter.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mAdapter = new MainProductsAdapter(null, getActivity());
        mPersonListEnter.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            switch (position) {
                case 0:
                    startActivity(ShoppingCardActivity.getIntent(getActivity()));
                    break;
                case 1:
                    startActivity(MyOrderActivity.getIntent(getActivity()));
                    break;
                case 2:
                    startActivity(CoupleActivity.getIntent(getActivity()));
                    break;
                case 3:
                    startActivity(MyCollectionActivity.getIntent(getActivity()));
                    break;
            }
        });
        /*mResponse = mBuProcessor.getPersonInfo();
        update(mResponse);
        mScrollLayout.setMoveImageView(mMoveImage);
        RxView.clicks(mPersonOrder).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> requestOrder());
        RxView.clicks(mPersonAddress).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> requestAddress());
        RxView.clicks(mPersonInfo).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> requestInfo());*/
        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    //展开状态
                    mPersonTips.setVisibility(View.VISIBLE);
                    mMiddleName.setVisibility(View.GONE);
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    mPersonTips.setVisibility(View.GONE);
                    mMiddleName.setVisibility(View.VISIBLE);
                } else {
                    //中间状态
                    mPersonTips.setVisibility(View.VISIBLE);
                    mMiddleName.setVisibility(View.GONE);
                }
            }
        });

    }

    private void initData() {
        mList = new ArrayList<>();
        try {
            mPersonCache.setText(DataCleanManager.getTotalCacheSize(getActivity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Drawable[] productDrawable = {
                ContextCompat.getDrawable(getActivity(), R.mipmap.my_shopping_card),
                ContextCompat.getDrawable(getActivity(), R.mipmap.my_order),
                ContextCompat.getDrawable(getActivity(), R.mipmap.my_coupon),
                ContextCompat.getDrawable(getActivity(), R.mipmap.my_collection),
        };
        for (int i = 0; i < productNames.length; i++) {
            MainProducts product = new MainProducts();
            product.productName = productNames[i];
            product.productDrawable = productDrawable[i];
            mList.add(product);
        }
        mAdapter.setNewData(mList);

    }

    private void showDialog() {
        CommonDialog commonDialog = CommonDialog.newInstance();
        commonDialog.setContent(getString(R.string.clean_cache));
        commonDialog.setListener(this, DIALOG_TYPE_EXIT_OK);
        DialogFactory.showDialogFragment(getChildFragmentManager(), commonDialog, CommonDialog.TAG);
    }

    private void requestInfo() {
        startActivity(PersonCenterActivity.getPersonIntent(getActivity(), mResponse));
    }

    private void requestAddress() {
        startActivity(AddressActivity.getIntent(getActivity(), "completedOrderFragment"));
    }

    private void requestOrder() {
        startActivity(MyOrderActivity.getIntent(getActivity()));
    }

    @Override
    public void getPersonInfoSuccess(PersonInfoResponse response) {
        mResponse = response;
        update(response);
    }

    @Override
    void addFilter() {
        super.addFilter();
        mFilter.addAction(BroConstant.UPDATE_PERSON_INFO);
    }

    @Override
    void onReceivePro(Context context, Intent intent) {
        if (intent.getAction().equals(BroConstant.UPDATE_PERSON_INFO)) {
            mPresenter.requestPersonInfo(mSharePreferenceUtil.getStringValue(SpConstant.USER_NAME));
        }
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

    private void update(PersonInfoResponse response) {
        /*if (response == null) return;
        mPersonName.setText(response.nickName == null ? "未知  " : response.nickName + "  ");
        mImageLoaderHelper.displayCircularImage(getActivity(), response.avatarUrl == null ?
                R.mipmap.person_head_icon : response.avatarUrl, mPersonIcon);
        Drawable drawable = ContextCompat.getDrawable(getActivity(), R.mipmap.person_sex_man);
        Drawable drawable2 = ContextCompat.getDrawable(getActivity(), R.mipmap.person_sex_women);
        drawable.setBounds(0, 0, drawable.getMinimumHeight(), drawable.getMinimumHeight());
        drawable2.setBounds(0, 0, drawable2.getMinimumHeight(), drawable2.getMinimumHeight());
        if (response.sex == 2) {
            mPersonName.setCompoundDrawables(null, null, drawable, null);
        } else {
            mPersonName.setCompoundDrawables(null, null, drawable2, null);
        }*/

    }

    private void initialize() {
        DaggerFragmentComponent.builder()
                .applicationComponent(((DaggerApplication) getActivity().getApplication()).getApplicationComponent())
                .mainActivityModule(new MainActivityModule((AppCompatActivity) getActivity()))
                .fragmentModule(new FragmentModule(this, (MainActivity) getActivity())).build()
                .inject(this);
    }
}
