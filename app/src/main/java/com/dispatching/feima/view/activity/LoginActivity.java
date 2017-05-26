package com.dispatching.feima.view.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
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
import com.dispatching.feima.help.DialogFactory;
import com.dispatching.feima.listener.MyTextWatchListener;
import com.dispatching.feima.service.CustomerService;
import com.dispatching.feima.utils.AppDeviceUtil;
import com.dispatching.feima.utils.ValueUtil;
import com.dispatching.feima.view.PresenterControl.LoginControl;
import com.dispatching.feima.view.fragment.CommonDialog;
import com.jakewharton.rxbinding2.view.RxView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by helei on 2017/4/26.
 * 登录页面
 */

public class LoginActivity extends BaseActivity implements LoginControl.LoginView ,CommonDialog.CommonDialogListener{
    private static final int DIALOG_TYPE_ORDER_INVALID = 1;

    public static Intent getLoginIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

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

    private LoginActivityComponent mActivityComponent;
    private LoginControl.PresenterLogin mPresenterLogin;
    private String myPhone;
    private String mVerifyCode;
    private String mUserId;
    private RxPermissions mPermission;
    private boolean flag = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeInjector();
        ButterKnife.bind(this);
        mPresenterLogin = mActivityComponent.getPresenterLogin();
        mPermission = new RxPermissions(this);
        mPresenterLogin.setView(this);
        mMiddleName.setText(R.string.app_login);
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

    @Override
    public void loginSuccess(LoginResponse loginResponse) {
        mBuProcessor.setUserId(loginResponse.uId);
        mBuProcessor.setUserToken(loginResponse.token);
        mSharePreferenceUtil.setStringValue(SpConstant.USER_NAME, myPhone);
        mSharePreferenceUtil.setStringValue(SpConstant.USER_ID, loginResponse.uId);
        if (TextUtils.isEmpty(mUserId)) {
            startService(CustomerService.newIntent(getApplicationContext()));
        }
        startActivity(MainActivity.getMainIntent(this));
        finish();
    }

    @Override
    public void commonDialogBtnOkListener(int type, int position) {
        switchSetting();
    }

    private void initView() {
        EditText editText = mLoginUserName.getEditText();
        RxView.clicks(mLoginSubmit).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> requestLogin());
        RxView.clicks(mLoginIdentifyingCode).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> requestVerifyCode());
        if (editText != null)
            editText.addTextChangedListener(new MyTextWatchListener() {
                @Override
                public void onMyTextChanged(CharSequence s) {
                    String phone = s.toString();
                    if (ValueUtil.isMobilePhone(phone)) {
                        mLoginSubmit.setEnabled(false);
                        mLoginIdentifyingCode.setEnabled(false);
                    } else {
                        myPhone = phone;
                        mLoginSubmit.setEnabled(true);
                        mLoginIdentifyingCode.setEnabled(true);
                    }

                }
            });
        String mUserName = mSharePreferenceUtil.getStringValue(SpConstant.USER_NAME);
        mUserId = mSharePreferenceUtil.getStringValue(SpConstant.USER_ID);
        if (editText != null && !TextUtils.isEmpty(mUserName)) {
            editText.setText(mUserName);
            editText.setSelection(mUserName.length());
        }
    }

    private void requestVerifyCode() {
        mPresenterLogin.onRequestVerifyCode(myPhone);
    }

    private void requestLogin() {
        EditText editText = mLoginPassword.getEditText();
        if (editText != null) {
            mVerifyCode = editText.getText().toString();
        }

        if (TextUtils.isEmpty(mVerifyCode)) {
            showToast(getString(R.string.login_password_empty));
            return;
        }

        mPermission.request(Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE).subscribe(permission -> {
            if(permission){
                if (flag){
                    mAMapLocationClient.setLocationOption(mAMapLocationClientOption);
                    mAMapLocationClient.startLocation();
                }
                mPresenterLogin.onRequestLogin(myPhone, mVerifyCode);
            }else {
                flag = true;
                showDialog();
            }
        });


    }

    private void showDialog(){
        CommonDialog commonDialog = CommonDialog.newInstance();
        commonDialog.setContent(getString(R.string.login_check_permission));
        commonDialog.setDialogCancleBtnDismiss();
        commonDialog.setListener(this, DIALOG_TYPE_ORDER_INVALID);
        DialogFactory.showDialogFragment(getSupportFragmentManager(), commonDialog, CommonDialog.TAG);
    }

    private void switchSetting(){
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", AppDeviceUtil.getPackageName(this), null);
        intent.setData(uri);
        startActivity(intent);
    }

    private void initializeInjector() {
        mActivityComponent = DaggerLoginActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .loginActivityModule(new LoginActivityModule(this))
                .build();
    }
}
