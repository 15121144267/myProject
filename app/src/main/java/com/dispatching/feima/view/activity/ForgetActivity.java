package com.dispatching.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.dispatching.feima.R;
import com.dispatching.feima.dagger.component.DaggerForgetActivityComponent;
import com.dispatching.feima.dagger.module.ForgetActivityModule;
import com.dispatching.feima.view.PresenterControl.ForgetControl;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/26.
 */

public class ForgetActivity extends BaseActivity implements ForgetControl.ForgetView {
    public static Intent getForgetIntent(Context context) {
        return new Intent(context, ForgetActivity.class);
    }

    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.forget_phone)
    TextInputLayout mForgetPhone;
    @BindView(R.id.forget_identifying_code)
    TextView mForgetIdentifyingCode;
    @BindView(R.id.forget_next_step)
    Button mForgetNextStep;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        ButterKnife.bind(this);
        initializeInjector();
        supportActionBar(mToolbar, true);
        mMiddleName.setText(R.string.app_forget);
        initView();
    }

    private void initView() {
        RxView.clicks(mForgetNextStep).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> switchSetPasswordActivity());
        RxView.clicks(mForgetIdentifyingCode).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> getVerifyCode());
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

    private void getVerifyCode() {

    }

    private void switchSetPasswordActivity() {
        startActivity(SetNewPasswordActivity.getNewPasswordIntent(this));
    }

    private void initializeInjector() {
        DaggerForgetActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .forgetActivityModule(new ForgetActivityModule(ForgetActivity.this, this))
                .build().inject(this);
    }


}
