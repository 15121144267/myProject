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
    private boolean mFirstOpen = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);
        mFirstOpen = mSharePreferenceUtil.getBooleanValue("isFirstOpen", true);
        if (mFirstOpen) {
            startActivity(GuideActivity.getIntent(this));
            finish();
            return;
        }

        String userId = mSharePreferenceUtil.getStringValue(SpConstant.USER_NAME);
        if(TextUtils.isEmpty(userId)&&!mFirstOpen){
            startActivity(LoginActivity.getLoginIntent(this));
            finish();
        }else {
            startActivity(MainActivity.getMainIntent(this));
        }
    }
}
