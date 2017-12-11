package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerSignActivityComponent;
import com.banshengyuan.feima.dagger.module.SignActivityModule;
import com.banshengyuan.feima.listener.MyTextWatchListener;
import com.banshengyuan.feima.utils.ValueUtil;
import com.banshengyuan.feima.view.PresenterControl.SignControl;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/26.
 * SignActivity
 */

public class SignActivity extends BaseActivity implements SignControl.SignView {


    @BindView(R.id.sign_phone)
    TextInputLayout mSignPhone;
    @BindView(R.id.sign_identifying_code)
    TextView mSignIdentifyingCode;
    @BindView(R.id.sign_password)
    TextInputLayout mSignPassword;
    @BindView(R.id.sign)
    Button mSign;
    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.sign_verityCode)
    TextInputLayout mSignVerityCode;
    @BindView(R.id.sign_check_content)
    CheckBox mSignCheckContent;
    private EditText mEditTextPassword;
    private EditText mEditTextVerityCode;

    public static Intent getSignIntent(Context context) {
//        Intent intent = new Intent(context, SignActivity.class);
        return new Intent(context, SignActivity.class);
    }


    @Inject
    SignControl.PresenterSign mPresenter;

    private String mPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        ButterKnife.bind(this);
        initializeInjector();
        supportActionBar(mToolbar, true);
        mMiddleName.setText(R.string.app_sign);
        initView();
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
        RxView.clicks(mSignIdentifyingCode).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> requestVerityCode());
        RxView.clicks(mSign).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> requestSign());
        EditText mPhoneEditText = mSignPhone.getEditText();
        mEditTextPassword = mSignPassword.getEditText();
        mEditTextVerityCode = mSignVerityCode.getEditText();

        if (mPhoneEditText != null)
            mPhoneEditText.addTextChangedListener(new MyTextWatchListener() {
                @Override
                public void onMyTextChanged(CharSequence s) {
                    String phone = s.toString();
                    if (ValueUtil.isMobilePhone(phone)) {
                        mSignIdentifyingCode.setEnabled(false);
                        mSign.setEnabled(false);
                    } else {
                        mPhone = phone;
                        mSignIdentifyingCode.setEnabled(true);
                        mSign.setEnabled(true);
                    }
                }
            });

    }

    private void requestSign() {
        String password = null;
        String verityCode = null;
        if (mEditTextPassword != null) {
            password = mEditTextPassword.getText().toString();
            if (TextUtils.isEmpty(password)) {
                showToast("密码不能为空");
                return;
            }
        }
        if (mEditTextVerityCode != null) {
            verityCode = mEditTextVerityCode.getText().toString();
            if (TextUtils.isEmpty(verityCode)) {
                showToast("验证码不能为空");
                return;
            }
        }
        if(!mSignCheckContent.isChecked()){
            showToast("请勾选用户协议");
            return;
        }
        mPresenter.onRequestSign(mPhone, password, verityCode);
    }

    @Override
    public void signUpSuccess() {
        showToast(getString(R.string.sign_up_success));
        finish();
    }

    @Override
    public void setButtonEnable(boolean enable, Long integer) {
        mSignIdentifyingCode.setEnabled(enable);
        if (enable) {
            mSignIdentifyingCode.setText(getString(R.string.text_verify_code));
        } else {
            mSignIdentifyingCode.setText("重新发送(" + String.valueOf(integer) + ")");
        }
    }

    private void requestVerityCode() {
        mPresenter.onRequestVerifyCode(mPhone);
    }

    private void initializeInjector() {
        DaggerSignActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .signActivityModule(new SignActivityModule(SignActivity.this, this))
                .build().inject(this);
    }
}
