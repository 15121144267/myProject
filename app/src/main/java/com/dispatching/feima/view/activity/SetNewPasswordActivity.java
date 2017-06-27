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
import com.dispatching.feima.dagger.component.DaggerSetNewPasswordActivityComponent;
import com.dispatching.feima.dagger.module.SetNewPasswordActivityModule;
import com.dispatching.feima.view.PresenterControl.SetNewPasswordControl;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/26.
 */

public class SetNewPasswordActivity extends BaseActivity implements SetNewPasswordControl.SetNewPasswordView{
    public static Intent getNewPasswordIntent(Context context){
        return new Intent(context,SetNewPasswordActivity.class);
    }

    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.setting_password)
    TextInputLayout mSettingPassword;
    @BindView(R.id.setting_password_again)
    TextInputLayout mSettingPasswordAgain;
    @BindView(R.id.setting_for_sure)
    Button mSettingForSure;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_password);
        ButterKnife.bind(this);
        initializeInjector();
        supportActionBar(mToolbar, true);
        mMiddleName.setText("新密码");
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

    private void initView() {
        RxView.clicks(mSettingForSure).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> switchSetForSure());
    }

    private void switchSetForSure() {
        startActivity(LoginActivity.getLoginIntent(this));
    }

    private void initializeInjector() {
        DaggerSetNewPasswordActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .setNewPasswordActivityModule(new SetNewPasswordActivityModule(SetNewPasswordActivity.this, this))
                .build().inject(this);
    }
}
