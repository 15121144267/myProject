package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerFairProductDetailActivityComponent;
import com.banshengyuan.feima.dagger.module.FairProductDetailActivityModule;
import com.banshengyuan.feima.entity.Constant;
import com.banshengyuan.feima.entity.ExChangeResponse;
import com.banshengyuan.feima.entity.HotFairDetailResponse;
import com.banshengyuan.feima.entity.HotFairStateResponse;
import com.banshengyuan.feima.entity.HotFariJoinActionRequest;
import com.banshengyuan.feima.entity.HotFariJoinActionResponse;
import com.banshengyuan.feima.entity.HotFariStateRequest;
import com.banshengyuan.feima.help.DialogFactory;
import com.banshengyuan.feima.utils.LogUtils;
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

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.join)
    Button join;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fairproduct_detail);
        ButterKnife.bind(this);
        initializeInjector();
       /* supportActionBar(mToolbar, true);
        mMiddleName.setText("新增收货地址");*/
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

    }

    private void initData() {
        HotFariStateRequest hotFariStateRequest = new HotFariStateRequest();
        hotFariStateRequest.setId(fId);
        hotFariStateRequest.setToken(Constant.TOKEN);
        hotFariStateRequest.setFlag(Constant.requestFlag);
        mPresenter.requestHotFairState(fId, hotFariStateRequest); //热闹-报名订单状态查询
        mPresenter.requestHotFairDetail(fId);//根据id查看热闹详情
    }

    /**
     * 报名参加
     * 查看二维码
     *
     * @param view
     */
    public void join(View view) {
        if (hotFairStateResponse.getStatus().equals("2")) {
            startActivity(ActionCodeActivity.getIntent(FairProductDetailActivity.this,hotFairDetailResponse,hotFairStateResponse.getQrcode()));
        } else {
            HotFariJoinActionRequest hotFariJoinActionRequest = new HotFariJoinActionRequest();
            hotFariJoinActionRequest.setId(fId);
            hotFariJoinActionRequest.setToken(Constant.TOKEN);
            hotFariJoinActionRequest.setFlag(Constant.requestFlag);

            JoinActionDialog joinActionDialog = JoinActionDialog.newInstance();
            joinActionDialog.setData(mPresenter, fId, hotFariJoinActionRequest);

            DialogFactory.showDialogFragment(getSupportFragmentManager(), joinActionDialog, CommonDialog.TAG);
        }
    }

    private void initializeInjector() {
        DaggerFairProductDetailActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .fairProductDetailActivityModule(new FairProductDetailActivityModule(FairProductDetailActivity.this, this))
                .build().inject(this);
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void getHotFairDetailSuccess(HotFairDetailResponse response) {
        hotFairDetailResponse = response;
//        if (response != null) {
//
//        }
    }

    @Override
    public void getHotFairStateSuccess(HotFairStateResponse response) {
        hotFairStateResponse = response;
        if (response != null) {
            if (response.getStatus().equals("2")) {//付款完成
                join.setText("查看二维码");
                join.setBackgroundColor(Color.parseColor("#212121"));
                join.setTextColor(Color.WHITE);
            } else {
                join.setText("报名参加");
                join.setBackgroundResource(R.drawable.button_style_blue6_rightangle);
                join.setTextColor(Color.parseColor("#212121"));
            }
        }
    }

    @Override
    public void getHotFairJoinActionSuccess(HotFariJoinActionResponse response) {
        if (response != null) {
            showToast("报名成功");
            if (!TextUtils.isEmpty(response.getOrder_sn())) {//判断订单号是否为空
                //直接唤起支付
            }
        }
    }
}
