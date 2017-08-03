package com.dispatching.feima.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.dispatching.feima.R;
import com.dispatching.feima.entity.SpConstant;

/**
 * Created by lei.he on 2017/6/5.
 * WelcomeActivity
 */

public class WelcomeActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);
        String userId = mSharePreferenceUtil.getStringValue(SpConstant.USER_ID);
        if(TextUtils.isEmpty(userId)){
           baseSwitchToLoginActivity();
        }else {
            mBuProcessor.setUserId(userId);
            mBuProcessor.setUserToken(mSharePreferenceUtil.getStringValue(SpConstant.USER_TOKEN));
            startActivity(MainActivity.getMainIntent(this));
            finish();
        }
    }
}
