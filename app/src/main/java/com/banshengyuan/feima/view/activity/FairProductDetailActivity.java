package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aries.ui.view.radius.RadiusTextView;
import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerFairProductDetailActivityComponent;
import com.banshengyuan.feima.dagger.module.FairProductDetailActivityModule;
import com.banshengyuan.feima.entity.HotFairDetailResponse;
import com.banshengyuan.feima.entity.HotFairStateResponse;
import com.banshengyuan.feima.entity.IntentConstant;
import com.banshengyuan.feima.entity.OrderConfirmedResponse;
import com.banshengyuan.feima.help.DialogFactory;
import com.banshengyuan.feima.listener.AppBarStateChangeListener;
import com.banshengyuan.feima.utils.SystemStatusManager;
import com.banshengyuan.feima.utils.ValueUtil;
import com.banshengyuan.feima.view.PresenterControl.FairProductDetailControl;
import com.banshengyuan.feima.view.fragment.CommonDialog;
import com.banshengyuan.feima.view.fragment.JoinActionDialog;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lei.he on 2017/6/28.
 * AddAddressActivity
 */

public class FairProductDetailActivity extends BaseActivity implements FairProductDetailControl.FairProductDetailView {

    @BindView(R.id.join)
    RadiusTextView join;
    @BindView(R.id.fair_detail_webcontent)
    TextView fairDetailWebcontent;
    @BindView(R.id.fair_detail_collection)
    ImageView fairDetailCollection;
    @BindView(R.id.fair_detail_banner)
    ImageView mFairDetailBanner;
    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar_right_icon)
    ImageView mToolbarRightIcon;
    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public static Intent getIntent(Context context, String fId) {
        Intent intent = new Intent(context, FairProductDetailActivity.class);
        intent.putExtra("fId", fId);
        return intent;
    }

    @Inject
    FairProductDetailControl.PresenterFairProductDetail mPresenter;

    private String fId;
    private HotFairDetailResponse hotFairDetailResponse = null;//热闹详情
    private HotFairStateResponse hotFairStateResponse = null;
    private String token;
    private String order_sn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemStatusManager.setbannerStatus(this);
        setContentView(R.layout.activity_fairproduct_detail);
        ButterKnife.bind(this);
        supportActionBar(mToolbar, true);
        initializeInjector();
        initView();
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
    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    private void initView() {
        if (getIntent() != null) {
            fId = getIntent().getStringExtra("fId");
        }


        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    //展开状态
                    mMiddleName.setVisibility(View.GONE);
                    mToolbar.setNavigationIcon(R.mipmap.arrow_left);
                    mToolbarRightIcon.setImageResource(R.mipmap.share_white);
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    mToolbar.setNavigationIcon(R.drawable.vector_arrow_left);
                    mToolbarRightIcon.setImageResource(R.mipmap.common_share);
                    mMiddleName.setVisibility(View.VISIBLE);
                    mMiddleName.setText("热闹详情");
                } else {
                    //中间状态
                    mMiddleName.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initData() {
        token = mBuProcessor.getUserToken();
//        token = Constant.TOKEN;
        if (mBuProcessor.isValidLogin()) {
            mPresenter.requestHotFairDetail(fId, token);//根据id查看热闹详情
        } else {
            mPresenter.requestHotFairDetail(fId, null);//根据id查看热闹详情
        }


    }

    /**
     * 报名参加
     * 查看二维码
     */
    private void join() {
        if (!mBuProcessor.isValidLogin()) {
            switchToLogin2();
            return;
        }

        if (hotFairDetailResponse != null) {
            if (hotFairStateResponse != null) {//付款完成
                if (hotFairStateResponse.getStatus().equals("1")) {//已报名 未付款
                    startActivity(FinalPayActivity.getIntent(FairProductDetailActivity.this, order_sn, 2));
                } else {// 已付款
                    startActivity(ActionCodeActivity.getIntent(FairProductDetailActivity.this, hotFairDetailResponse, hotFairStateResponse.getQrcode()));
                }
            } else {//报名参加
                JoinActionDialog joinActionDialog = JoinActionDialog.newInstance();
                joinActionDialog.setData(mPresenter, fId, token, hotFairDetailResponse);

                DialogFactory.showDialogFragment(getSupportFragmentManager(), joinActionDialog, CommonDialog.TAG);
            }
        } else {
            showToast("热闹报名状态获取失败");
        }
    }

    private void initializeInjector() {
        DaggerFairProductDetailActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .fairProductDetailActivityModule(new FairProductDetailActivityModule(FairProductDetailActivity.this, this))
                .build().inject(this);
    }


    @Override
    public void getHotFairDetailSuccess(HotFairDetailResponse response) {
        hotFairDetailResponse = response;
        HotFairDetailResponse.InfoBean infoBean = response.getInfo();
        if (infoBean != null) {
            fairDetailCollection.setImageResource(infoBean.getIs_collection() == 1 ? R.mipmap.shop_detail_collection : R.mipmap.shop_detail_uncollection);
//            RichText.from(response.getInfo().getContent()).into(fairDetailWebcontent);
            ValueUtil.setHtmlContent(this,response.getInfo().getContent(),fairDetailWebcontent);
            mImageLoaderHelper.displayImage(this,infoBean.getCover_img(),mFairDetailBanner);
            if (!TextUtils.isEmpty(response.getInfo().getOrder_sn())) {
                order_sn = response.getInfo().getOrder_sn();
                mPresenter.requestHotFairState(fId, order_sn, token); //热闹-报名订单状态查询
            } else {
                join.setText("报名参加");
            }
        }

    }

    @Override
    public void getHotFairStateSuccess(HotFairStateResponse response) {
        hotFairStateResponse = response;
        if (response.getStatus().equals("2")) {//付款完成
            join.setText("查看二维码");
//                join.setBackgroundColor(Color.parseColor("#212121"));
//                join.setTextColor(Color.WHITE);
        } else {
            join.setText("去付款");
//                join.setBackgroundResource(R.drawable.button_style_blue6_rightangle);
//                join.setTextColor(Color.parseColor("#212121"));
        }
    }

    @Override
    public void getHotFairJoinActionSuccess(OrderConfirmedResponse response) {
        showToast("报名成功");
        if (!TextUtils.isEmpty(response.order_sn)) {//判断订单号是否为空
            //直接唤起支付
            mPresenter.requestHotFairState(fId, response.order_sn, token); //热闹-报名订单状态查询
        }
    }

    @Override
    public void getHotFairCollectionSuccess(int state) {
        fairDetailCollection.setImageResource(state == 1 ? R.mipmap.shop_detail_collection : R.mipmap.shop_detail_uncollection);
        if (state == 1) {
            showToast("收藏成功");
        } else {
            showToast("取消收藏");
        }

    }

    @OnClick({R.id.join, R.id.toolbar_right_icon, R.id.fair_detail_collection})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.join:
                join();
                break;
            case R.id.toolbar_right_icon:
                //分享
                break;
            case R.id.fair_detail_collection:
                mPresenter.requestHotFairCollection(String.valueOf(hotFairDetailResponse.getInfo().getId()), token);
                break;
        }
    }

    private void switchToLogin2() {
        startActivityForResult(LoginActivity.getLoginIntent(FairProductDetailActivity.this), IntentConstant.ORDER_POSITION_ONE);
    }

    @OnClick()
    public void onViewClicked() {
    }
}
