package com.banshengyuan.feima.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.banshengyuan.feima.DaggerApplication;
import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerFragmentComponent;
import com.banshengyuan.feima.dagger.module.FragmentModule;
import com.banshengyuan.feima.dagger.module.MainActivityModule;
import com.banshengyuan.feima.entity.BroConstant;
import com.banshengyuan.feima.entity.IntentConstant;
import com.banshengyuan.feima.entity.MainProducts;
import com.banshengyuan.feima.entity.PersonInfoResponse;
import com.banshengyuan.feima.help.DialogFactory;
import com.banshengyuan.feima.listener.AppBarStateChangeListener;
import com.banshengyuan.feima.utils.DataCleanManager;
import com.banshengyuan.feima.utils.ValueUtil;
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

import static android.app.Activity.RESULT_OK;
import static com.banshengyuan.feima.view.activity.MainActivity.DIALOG_TYPE_EXIT_OK;


/**
 * Created by helei on 2017/5/3.
 * CompletedOrderFragment
 * 我的
 */

public class CompletedOrderFragment extends BaseFragment implements CompletedOrderControl.CompletedOrderView, CommonDialog.CommonDialogListener {
    @BindView(R.id.person_list_enter)
    RecyclerView mPersonListEnter;
    @BindView(R.id.person_enter_safe_page)
    TextView mPersonEnterSafePage;
    @BindView(R.id.person_enter_personal_page)
    TextView mPersonEnterPersonalPage;
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
    @BindView(R.id.person_background)
    ImageView mPersonBackground;
    @BindView(R.id.person_icon)
    ImageView mPersonIcon;
    @BindView(R.id.person_name)
    TextView mPersonName;
    @BindView(R.id.person_detail)
    TextView mPersonDetail;
    @BindView(R.id.login_submit)
    Button mLoginSubmit;
    private static Handler mHandler;

    public static CompletedOrderFragment newInstance() {
        return new CompletedOrderFragment();
    }

    @Inject
    CompletedOrderControl.PresenterCompletedOrder mPresenter;

    private Unbinder unbind;
    private PersonInfoResponse mResponse;
    private final String[] productNames = {"购物车", "我的订单", "我的卡券", "我的收藏"};
    private List<MainProducts> mList;
    private MainProductsAdapter mAdapter;
    private int resourceColor = R.drawable.yellow_style;
    public static void setmHandler(Handler handler) {
        mHandler = handler;
    }

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
        RxView.clicks(mPersonEnterPersonalPage).throttleFirst(1, TimeUnit.SECONDS).subscribe(o ->
                startActivityForResult(PersonCenterActivity.getPersonIntent(getActivity(), mResponse), IntentConstant.ORDER_POSITION_ONE));

        RxView.clicks(mPersonEnterSafePage).throttleFirst(1, TimeUnit.SECONDS).subscribe(
                o -> startActivity(SafeSettingActivity.getIntent(getActivity())));

        RxView.clicks(mPersonTips).throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> {
            mPersonTips.setImageResource(R.mipmap.my_message);
            startActivity(NoticeCenterActivity.getIntent(getActivity()));
        });

        RxView.clicks(mPersonAddressPage).throttleFirst(1, TimeUnit.SECONDS).subscribe(
                o -> startActivity(AddressActivity.getIntent(getActivity(), "CompletedOrderFragment")));

        RxView.clicks(mPersonCleanCache).throttleFirst(1, TimeUnit.SECONDS).subscribe(
                o -> showDialog());

        RxView.clicks(mLoginSubmit).throttleFirst(1, TimeUnit.SECONDS).subscribe(
                o -> exitLogin());

        mPersonListEnter.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mAdapter = new MainProductsAdapter(null, getActivity());
        mPersonListEnter.setAdapter(mAdapter);
        mPersonListEnter.setNestedScrollingEnabled(false);
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
                    resourceColor = R.color.text_color_yellow_dark2;
                    ((MainActivity) getActivity()).setBackGroundColor(resourceColor);
                } else {
                    //中间状态
                    mPersonTips.setVisibility(View.VISIBLE);
                    mMiddleName.setVisibility(View.GONE);
                    resourceColor = R.drawable.yellow_style;
                    ((MainActivity) getActivity()).setBackGroundColor(resourceColor);
                }
            }
        });

    }

    /**
     * 退出登陆
     */
    private void exitLogin() {
        showToast("退出成功");
        mPresenter.requestExitLogin(mBuProcessor.getUserToken());
        Message message = new Message();
        message.what = MainActivity.SWITCH_FIRST_PAGE;
        mHandler.sendMessage(message);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentConstant.ORDER_POSITION_ONE && resultCode == RESULT_OK) {
            mPresenter.requestPersonInfo(mBuProcessor.getUserToken());
        }
    }

    public int getResourceColor() {
        return resourceColor;
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
        if (mBuProcessor.isValidLogin()) {
            mPresenter.requestPersonInfo(mBuProcessor.getUserToken());
        }

    }

    private void showDialog() {
        CommonDialog commonDialog = CommonDialog.newInstance();
        commonDialog.setContent(getString(R.string.clean_cache));
        commonDialog.setListener(this, DIALOG_TYPE_EXIT_OK);
        DialogFactory.showDialogFragment(getChildFragmentManager(), commonDialog, CommonDialog.TAG);
    }

    @Override
    public void getPersonInfoSuccess(PersonInfoResponse response) {
        mResponse = response;
        update(response);
    }

    @Override
    public void getExitLoginSuccess() {
        mBuProcessor.clearLoginUser();
    }

    @Override
    void addFilter() {
        super.addFilter();
        mFilter.addAction(BroConstant.UPDATE_PERSON_INFO);
    }

    @Override
    void onReceivePro(Context context, Intent intent) {
        if (intent.getAction().equals(BroConstant.UPDATE_PERSON_INFO)) {
            mPresenter.requestPersonInfo(mBuProcessor.getUserToken());
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
        if (response.getInfo() == null) return;
        PersonInfoResponse.InfoBean infoBean = response.getInfo();
        mPersonDetail.setText(infoBean.getSalt());
        mPersonName.setText(TextUtils.isEmpty(infoBean.getName()) ? "昵称  " : infoBean.getName() + "  ");
        mImageLoaderHelper.displayCircularImage(getActivity(), infoBean.getHead_img() == null ?
                R.mipmap.person_head_icon : infoBean.getHead_img(), mPersonIcon);
        if (infoBean.getSex() == 2) {
            ValueUtil.setTextDrawable(getActivity(), mPersonName, R.mipmap.person_sex_man, 2);
        } else {
            ValueUtil.setTextDrawable(getActivity(), mPersonName, R.mipmap.person_sex_women, 2);
        }

    }

    private void initialize() {
        DaggerFragmentComponent.builder()
                .applicationComponent(((DaggerApplication) getActivity().getApplication()).getApplicationComponent())
                .mainActivityModule(new MainActivityModule((AppCompatActivity) getActivity()))
                .fragmentModule(new FragmentModule(this, (MainActivity) getActivity())).build()
                .inject(this);
    }
}
