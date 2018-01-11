package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerSetNewPasswordActivityComponent;
import com.banshengyuan.feima.dagger.module.SetNewPasswordActivityModule;
import com.banshengyuan.feima.entity.SetPasswordRequest;
import com.banshengyuan.feima.view.PresenterControl.SetNewPasswordControl;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/26.
 * SetNewPasswordActivity
 */

public class SetNewPasswordActivity extends BaseActivity implements SetNewPasswordControl.SetNewPasswordView {


    public static Intent getNewPasswordIntent(Context context) {
        Intent intent = new Intent(context, SetNewPasswordActivity.class);
        return intent;
    }

    @BindView(R.id.toolbar_right_text)
    TextView mToolbarRightText;
    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.original_password)
    TextInputLayout originalPassword;
    @BindView(R.id.setting_password)
    TextInputLayout mSettingPassword;
    @BindView(R.id.setting_password_again)
    TextInputLayout mSettingPasswordAgain;
    /* @BindView(R.id.setting_for_sure)
     Button mSettingForSure;*/
    private EditText mPasswordEdit;
    private EditText mPasswordAgainEdit;
    @Inject
    SetNewPasswordControl.PresenterSetNewPassword mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_password);
        ButterKnife.bind(this);
        initializeInjector();
        supportActionBar(mToolbar, true);
        mMiddleName.setText("修改密码");
        initView();
    }

    @Override
    public void setPasswordSuccess() {
        showToast("修改成功");
        mBuProcessor.clearLoginUser();
        LoginActivity.setClearActivity("clearActivity");
        startActivity(LoginActivity.getLoginIntent(this));
        finish();
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
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public Context getContext() {
        return this;
    }

    private void initView() {
        mToolbarRightText.setVisibility(View.VISIBLE);
        mToolbarRightText.setText("完成");
        mPasswordEdit = mSettingPassword.getEditText();
        mPasswordAgainEdit = mSettingPasswordAgain.getEditText();
        TextChange textChange = new TextChange();
        mPasswordEdit.addTextChangedListener(textChange);
        mPasswordAgainEdit.addTextChangedListener(textChange);
        RxView.clicks(mToolbarRightText).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> switchSetForSure());
    }

    private void switchSetForSure() {
        String originalPwd = originalPassword.getEditText().getText().toString();
        String password = mPasswordEdit.getText().toString();
        String passwordAgain = mPasswordAgainEdit.getText().toString();
        if (TextUtils.isEmpty(originalPwd)) {
            showToast("原始密码不能为空");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            showToast("新密码不能为空");
            return;
        }
        if (!password.equals(passwordAgain)) {
            showToast("密码不一致,请重新输入");
            return;
        }
        SetPasswordRequest request = new SetPasswordRequest();
        request.old_password = originalPwd;
        request.password = password;
        request.confirm_password = passwordAgain;
        request.token = mBuProcessor.getUserToken();
        mPresenter.onRequestForSure(request);

    }

    private void initializeInjector() {
        DaggerSetNewPasswordActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .setNewPasswordActivityModule(new SetNewPasswordActivityModule(SetNewPasswordActivity.this, this))
                .build().inject(this);
    }

    class TextChange implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
           /* if (mPasswordEdit.length() > 0 && mPasswordAgainEdit.length() > 0) {
                mSettingForSure.setEnabled(true);
            } else {
                mSettingForSure.setEnabled(false);
            }*/
        }
    }
}
