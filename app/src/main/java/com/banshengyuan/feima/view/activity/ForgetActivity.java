package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerForgetActivityComponent;
import com.banshengyuan.feima.dagger.module.ForgetActivityModule;
import com.banshengyuan.feima.listener.MyTextWatchListener;
import com.banshengyuan.feima.utils.ValueUtil;
import com.banshengyuan.feima.view.PresenterControl.ForgetControl;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/26.
 * ForgetActivity
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
    @BindView(R.id.forget_verity_code)
    TextInputLayout mForgetVerityCode;
    @BindView(R.id.forget_password)
    TextInputLayout mForgetPassword;
    @BindView(R.id.forget_commit)
    Button mForgetCommit;
    private EditText mForgetVerity;
    private EditText mForgetNewPassword;
    private String mPhone;
    private String mVerityCode;
    private String mPassword;
    @Inject
    ForgetControl.PresenterForget mPresenter;

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
        RxView.clicks(mForgetCommit).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> switchSetPasswordActivity());
        RxView.clicks(mForgetIdentifyingCode).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> getVerifyCode());
        EditText editText = mForgetPhone.getEditText();
        mForgetVerity = mForgetVerityCode.getEditText();
        mForgetNewPassword = mForgetPassword.getEditText();
        if (editText != null)
            editText.addTextChangedListener(new MyTextWatchListener() {
                @Override
                public void onMyTextChanged(CharSequence s) {
                    String phone = s.toString();
                    if (ValueUtil.isMobilePhone(phone)) {
                        mForgetCommit.setEnabled(false);
                        mForgetIdentifyingCode.setEnabled(false);
                    } else {
                        mPhone = phone;
                        mForgetCommit.setEnabled(true);
                        mForgetIdentifyingCode.setEnabled(true);
                    }
                }
            });
    }

    @Override
    public void setButtonEnable(boolean enable, long time) {
        mForgetIdentifyingCode.setEnabled(enable);
        if (enable) {
            mForgetIdentifyingCode.setText(getString(R.string.text_verify_code));
        } else {
            mForgetIdentifyingCode.setText("重新发送(" + String.valueOf(time) + ")");
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
    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    private void getVerifyCode() {
        mPresenter.onRequestVerifyCode(mPhone);
    }

    private void switchSetPasswordActivity() {
        if (mForgetVerity != null) {
            mVerityCode = mForgetVerity.getText().toString();
            if (TextUtils.isEmpty(mVerityCode.trim())) {
                showToast("验证码不能为空");
                return;
            }
        }
        if (mForgetNewPassword != null) {
            mPassword = mForgetNewPassword.getText().toString();
            if (TextUtils.isEmpty(mPassword.trim())) {
                showToast("密码不能为空");
                return;
            }
        }
        mPresenter.requestCheckCode(mPhone, mVerityCode, mPassword);

    }

    @Override
    public void checkCodeSuccess() {
//        startActivity(SetNewPasswordActivity.getNewPasswordIntent(this));
        finish();
    }

    private void initializeInjector() {
        DaggerForgetActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .forgetActivityModule(new ForgetActivityModule(ForgetActivity.this, this))
                .build().inject(this);
    }


}
