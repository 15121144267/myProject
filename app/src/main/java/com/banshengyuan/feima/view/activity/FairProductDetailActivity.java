package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.aries.ui.view.radius.RadiusTextView;
import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerFairProductDetailActivityComponent;
import com.banshengyuan.feima.dagger.module.FairProductDetailActivityModule;
import com.banshengyuan.feima.entity.BroConstant;
import com.banshengyuan.feima.entity.HotFairDetailResponse;
import com.banshengyuan.feima.entity.HotFairStateResponse;
import com.banshengyuan.feima.entity.IntentConstant;
import com.banshengyuan.feima.entity.OrderConfirmedResponse;
import com.banshengyuan.feima.help.DialogFactory;
import com.banshengyuan.feima.listener.AppBarStateChangeListener;
import com.banshengyuan.feima.utils.LogUtils;
import com.banshengyuan.feima.utils.TimeUtil;
import com.banshengyuan.feima.view.PresenterControl.FairProductDetailControl;
import com.banshengyuan.feima.view.fragment.CommonDialog;
import com.banshengyuan.feima.view.fragment.JoinActionDialog;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

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
    TextView join;
    @BindView(R.id.fair_detail_title)
    TextView fairDetailTitle;
    @BindView(R.id.fair_detail_date)
    TextView fairDetailDate;
    @BindView(R.id.fair_detail_place)
    TextView fairDetailPlace;
    @BindView(R.id.fair_detail_webcontent)
    WebView fairDetailWebcontent;
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
    private int mStatus;//0未开始  1进行中  2已结束
    private int mIsSignUp;//1：已报名   0：未报名
    private String yelloColoor = "#fffc00";
    private String yelloTextColoor = "#212121";
    private String grayColor = "#eeeeee";
    private String grayTextColor = "#666666";
    private int salePrice;
    private int btnState = -1;//0报名参加 1去付款  2 查看二维码 3已结束

    public static Intent getIntent(Context context, String fId) {
        Intent intent = new Intent(context, FairProductDetailActivity.class);
        intent.putExtra("fId", fId);
        return intent;
    }

    @Inject
    FairProductDetailControl.PresenterFairProductDetail mPresenter;

    private String fId;
    private HotFairDetailResponse hotFairDetailResponse = null;//热闹详情
    private String token;
    private String mOrderSn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @Override
    void onReceivePro(Context context, Intent intent) {
        if (intent.getAction().equals(BroConstant.PAY_TO_EXCHANGEDETAIL_ACTIVITY)) {
            SystemClock.sleep(500);//后台状态反应时间
            mPresenter.requestHotFairState(fId, mOrderSn, token); //热闹-报名订单状态查询
        }
        super.onReceivePro(context, intent);
    }

    @Override
    void addFilter() {
        super.addFilter();
        mFilter.addAction(BroConstant.PAY_TO_EXCHANGEDETAIL_ACTIVITY);
    }

    private void initView() {
        fairDetailWebcontent.getSettings().setAllowFileAccess(true);
        fairDetailWebcontent.getSettings().setJavaScriptEnabled(true);
        fairDetailWebcontent.getSettings().setDomStorageEnabled(true);
        mToolbarRightIcon.setVisibility(View.VISIBLE);
        RxView.clicks(mToolbarRightIcon).throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> showToast("该功能暂未开放"));
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
        if (mBuProcessor.isValidLogin()) {
            mPresenter.requestHotFairDetail(fId, token);//根据id查看热闹详情
        } else {
            mPresenter.requestHotFairDetail(fId, "");//根据id查看热闹详情
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

        switch (btnState) {
            case 0://报名参加
                JoinActionDialog joinActionDialog = JoinActionDialog.newInstance();
                joinActionDialog.setData(mPresenter, fId, token, hotFairDetailResponse, mBuProcessor.getUserPhone());
                DialogFactory.showDialogFragment(getSupportFragmentManager(), joinActionDialog, CommonDialog.TAG);
                break;
            case 1://已报名 未付款
                startActivity(FinalPayActivity.getIntent(FairProductDetailActivity.this, mOrderSn, 2, "ExchangeFragment"));
                break;
            case 2://查看二维码
                startActivity(ActionCodeActivity.getIntent(FairProductDetailActivity.this, hotFairDetailResponse, mOrderSn));//hotFairDetailResponse没更新 mOrderSn传过去
                break;
            default:
                break;
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
            salePrice = infoBean.getSales_price();
            fairDetailTitle.setText(infoBean.getName());
            String date = infoBean.getStart_time() != 0 ? TimeUtil.transferLongToDate(TimeUtil.TIME_YYMMDD_CH, (long) infoBean.getStart_time()) : "未知";
            String place = TextUtils.isEmpty(infoBean.getStreet_name()) ? "未知" : infoBean.getStreet_name();
            fairDetailDate.setText("时间：" + date);
            fairDetailPlace.setText("地点：" + place);

            fairDetailCollection.setImageResource(infoBean.getIs_collection() == 1 ? R.mipmap.shop_detail_collection : R.mipmap.shop_detail_uncollection);
            fairDetailWebcontent.loadDataWithBaseURL(null, response.getInfo().getContent(), "text/html", "utf-8", null);
            mImageLoaderHelper.displayImage(this, infoBean.getCover_img(), mFairDetailBanner);

            mStatus = infoBean.getStatus();
            mIsSignUp = infoBean.getIs_sign_up();
            mOrderSn = infoBean.getOrder_sn();

            if (mStatus == 2) {
                timeOutAction();
                join.setText("已结束");
                btnState = 3;
            } else {
                timeIngAction();
                if (mIsSignUp == 1) {
                    mPresenter.requestHotFairState(fId, mOrderSn, token); //热闹-报名订单状态查询
                } else {
                    join.setText("报名参加");
                    btnState = 0;
                }
            }
        }
    }

    @Override
    public void getHotFairStateSuccess(HotFairStateResponse response) {
        if (mIsSignUp == 1 && salePrice == 0) {//免费热闹
            join.setText("查看二维码");
            btnState = 2;
        } else {
            if (response.getStatus().equals("2")) {//付款完成
                join.setText("查看二维码");
                btnState = 2;
            } else {
                join.setText("去付款");
                btnState = 1;
            }
        }

    }

    @Override
    public void getHotFairJoinActionSuccess(OrderConfirmedResponse response) {
        //直接唤起支付
        Double totalFee = response.total_fee;
        mOrderSn = response.order_sn;
        mIsSignUp = 1;
        mPresenter.requestHotFairState(fId, mOrderSn, token); //热闹-报名订单状态查询
        if (totalFee != 0) {//totalFee 不为0  就是收费报名
            startActivity(FinalPayActivity.getIntent(FairProductDetailActivity.this, mOrderSn, 2, "ExchangeFragment"));
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

    @OnClick({R.id.join,  R.id.fair_detail_collection})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.join:
                if (mStatus != 2) {
                    join();
                }
                break;
            case R.id.fair_detail_collection:
                if (!mBuProcessor.isValidLogin()) {
                    switchToLogin2();
                    return;
                }
                mPresenter.requestHotFairCollection(String.valueOf(hotFairDetailResponse.getInfo().getId()), token);
                break;
        }
    }

    private void switchToLogin2() {
        startActivityForResult(LoginActivity.getLoginIntent(FairProductDetailActivity.this), IntentConstant.ORDER_POSITION_ONE);
    }

    private void timeOutAction() {
        join.setTextColor(Color.parseColor(grayTextColor));
        join.setBackgroundColor(Color.parseColor(grayColor));
    }

    private void timeIngAction() {
        join.setTextColor(Color.parseColor(yelloTextColoor));
        join.setBackgroundColor(Color.parseColor(yelloColoor));
    }

}
