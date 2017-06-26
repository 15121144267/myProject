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
import com.dispatching.feima.dagger.component.DaggerSignActivityComponent;
import com.dispatching.feima.dagger.module.SignActivityModule;
import com.dispatching.feima.entity.SpConstant;
import com.dispatching.feima.listener.MyTextWatchListener;
import com.dispatching.feima.utils.ValueUtil;
import com.dispatching.feima.view.PresenterControl.SignControl;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/26.
 */

public class SignActivity extends BaseActivity implements SignControl.SignView {

    @BindView(R.id.sign_phone)
    TextInputLayout mSignPhone;
    @BindView(R.id.sign_identifying_code)
    TextView mSignIdentifyingCode;
    @BindView(R.id.sign_password)
    TextInputLayout mSignPassword;
    @BindView(R.id.sign_password_again)
    TextInputLayout mSignPasswordAgain;
    @BindView(R.id.sign)
    Button mSign;

    public static Intent getSignIntent(Context context) {
//        Intent intent = new Intent(context, SignActivity.class);
        return new Intent(context, SignActivity.class);
    }

    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
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

    private void initView() {
        EditText editText = mSignPhone.getEditText();
        if (editText != null)
            editText.addTextChangedListener(new MyTextWatchListener() {
                @Override
                public void onMyTextChanged(CharSequence s) {
                    String phone = s.toString();
                    if (ValueUtil.isMobilePhone(phone)) {
                        mSign.setEnabled(false);
                        mSignIdentifyingCode.setEnabled(false);
                    } else {
                        mPhone = phone;
                        mSign.setEnabled(true);
                        mSignIdentifyingCode.setEnabled(true);
                    }
                }
            });
        String mUserName = mSharePreferenceUtil.getStringValue(SpConstant.USER_NAME);
        if (editText != null && !TextUtils.isEmpty(mUserName)) {
            editText.setText(mUserName);
            editText.setSelection(mUserName.length());
        }
    }

    private void initializeInjector() {
        DaggerSignActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .signActivityModule(new SignActivityModule(SignActivity.this, this))
                .build();
    }
}
