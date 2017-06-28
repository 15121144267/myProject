package com.dispatching.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dispatching.feima.R;
import com.dispatching.feima.utils.ToastUtils;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by helei on 2017/4/27.
 * PersonCenterActivity
 */

public class PersonCenterActivity extends BaseActivity {
    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.person_center_head)
    LinearLayout mPersonCenterHead;
    @BindView(R.id.person_center_name)
    LinearLayout mPersonCenterName;
    @BindView(R.id.person_center_sex)
    LinearLayout mPersonCenterSex;
    @BindView(R.id.person_center_birthday)
    LinearLayout mPersonCenterBirthday;

    public static Intent getPersonIntent(Context context) {
        return new Intent(context, PersonCenterActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        ButterKnife.bind(this);
        supportActionBar(mToolbar, true);
        mMiddleName.setText(R.string.user_person_center);
        initView();
    }

    private void initView() {
        RxView.clicks(mPersonCenterHead).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> requestChoicePic());
        RxView.clicks(mPersonCenterName).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> requestPersonName());
        RxView.clicks(mPersonCenterSex).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> requestPersonSex());
        RxView.clicks(mPersonCenterBirthday).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> requestPersonBirthday());
    }

    private void requestChoicePic() {
        ToastUtils.showShortToast("头像");
    }

    private void requestPersonName() {
        ToastUtils.showShortToast("名字");
    }

    private void requestPersonSex() {
        ToastUtils.showShortToast("性别");
    }

    private void requestPersonBirthday() {
        ToastUtils.showShortToast("生日");
    }
}
