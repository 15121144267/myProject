package com.dispatching.feima.view.activity;

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

import com.dispatching.feima.R;
import com.dispatching.feima.dagger.component.DaggerLoginActivityComponent;
import com.dispatching.feima.dagger.component.LoginActivityComponent;
import com.dispatching.feima.dagger.module.LoginActivityModule;
import com.dispatching.feima.entity.LoginResponse;
import com.dispatching.feima.entity.SpConstant;
import com.dispatching.feima.listener.MyTextWatchListener;
import com.dispatching.feima.service.CustomerService;
import com.dispatching.feima.utils.ToastUtils;
import com.dispatching.feima.utils.ValueUtil;
import com.dispatching.feima.view.PresenterControl.LoginControl;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by helei on 2017/4/26.
 * 登录页面
 */

public class LoginActivity extends BaseActivity implements LoginControl.LoginView {
    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.login_password)
    TextInputLayout mLoginPassword;
    @BindView(R.id.login_userName)
    TextInputLayout mLoginUserName;
    @BindView(R.id.login_submit)
    Button mLoginSubmit;
    @BindView(R.id.login_identifying_code)
    TextView mLoginIdentifyingCode;

    public static Intent getLoginIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    private LoginActivityComponent mActivityComponent;
    private LoginControl.PresenterLogin mPresenterLogin;
    private String myPhone;
    private String mUserName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeInjector();
        ButterKnife.bind(this);
        mPresenterLogin = mActivityComponent.getPresenterLogin();
        mPresenterLogin.setView(this);
        mMiddleName.setText(R.string.app_login);

        EditText editText = mLoginUserName.getEditText();
        RxView.clicks(mLoginSubmit).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> requestLogin());
        RxView.clicks(mLoginIdentifyingCode).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> requestVerifyCode());
        if (editText != null)
            editText.addTextChangedListener(new MyTextWatchListener() {
                @Override
                public void onMyTextChanged(CharSequence s, int start, int before, int count) {
                    String phone = s.toString();
                    if (!ValueUtil.isMobileNO(phone)) {
                        mLoginSubmit.setEnabled(false);
                        mLoginIdentifyingCode.setEnabled(false);
                    } else {
                        myPhone = phone;
                        mLoginSubmit.setEnabled(true);
                        mLoginIdentifyingCode.setEnabled(true);
                    }

                }
            });
        mUserName = mSharePreferenceUtil.getStringValue(SpConstant.USER_NAME);
        if (editText!=null&&!TextUtils.isEmpty(mUserName)){
            editText.setText(mUserName);
            editText.setSelection(mUserName.length());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //setIntent(intent);
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
        ToastUtils.showShortToast(message);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenterLogin.onDestroy();
    }

    @Override
    public void setButtonEnable(boolean enable, Long time) {
        mLoginIdentifyingCode.setEnabled(enable);
        if (enable) {
            mLoginIdentifyingCode.setText(getString(R.string.text_verify_code));
        } else {
            mLoginIdentifyingCode.setText("重新发送(" + String.valueOf(time) + ")");
        }
    }

    private void requestVerifyCode() {
        mPresenterLogin.onRequestVerifyCode(myPhone);
    }

    private void requestLogin() {
        String verifyCode = mLoginPassword.getEditText().getText().toString();
        if (TextUtils.isEmpty(verifyCode)) {
            showToast(getString(R.string.login_password_empty));
            return;
        }
        //mPresenterLogin.onRequestLogin(myPhone, verifyCode);
        if (TextUtils.isEmpty(mUserName)) {
            mSharePreferenceUtil.setStringValue(SpConstant.USER_NAME, "15121144267");
            startService(CustomerService.newIntent(getApplicationContext()));
        }
        startActivity(MainActivity.getMainIntent(this));
        finish();
    }

    @Override
    public void loginSuccess(LoginResponse loginResponse) {
        mBuProcessor.setUserId(loginResponse.uId);
        mBuProcessor.setUserToken(loginResponse.token);
    }

    private void initializeInjector() {
        mActivityComponent = DaggerLoginActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .loginActivityModule(new LoginActivityModule(this))
                .build();
    }
}
