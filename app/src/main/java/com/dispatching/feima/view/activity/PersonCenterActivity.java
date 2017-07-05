package com.dispatching.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dispatching.feima.R;
import com.dispatching.feima.entity.IntentConstant;
import com.dispatching.feima.utils.ToastUtils;
import com.dispatching.feima.view.customview.timepickview.DatePicker;
import com.dispatching.feima.view.customview.timepickview.util.ConvertUtils;
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
    @BindView(R.id.update_person_info)
    Button mUpdatePersonInfo;
    @BindView(R.id.person_name)
    TextView mPersonName;
    @BindView(R.id.person_sex)
    TextView mPersonSex;
    @BindView(R.id.person_birthday_date)
    TextView mPersonBirthdayDate;

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
        String sex = mSharePreferenceUtil.getStringValue("sex");
        if (TextUtils.isEmpty(sex)) {
            mPersonSex.setText(getString(R.string.app_choice));
        } else {
            mPersonSex.setText(sex + "  ");
        }
        String birthday = mSharePreferenceUtil.getStringValue("birthday");
        if (TextUtils.isEmpty(birthday)) {
            mPersonSex.setText(getString(R.string.app_choice));
        } else {
            mPersonSex.setText(birthday + "  ");
        }
        RxView.clicks(mPersonCenterHead).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> requestChoicePic());
        RxView.clicks(mPersonCenterName).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> requestPersonName());
        RxView.clicks(mPersonCenterSex).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> requestPersonSex());
        RxView.clicks(mPersonCenterBirthday).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> requestPersonBirthday());
        RxView.clicks(mUpdatePersonInfo).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> requestUpdatePersonInfo());
    }

    private void requestChoicePic() {
        ToastUtils.showShortToast("头像");
    }

    private void requestPersonName() {
       startActivity(PersonNameActivity.getIntent(this));
    }

    private void requestPersonSex() {
        startActivityForResult(SexChoiceActivity.getIntent(this, mPersonSex.getText().toString()), IntentConstant.ORDER_POSITION_ONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentConstant.ORDER_POSITION_ONE && resultCode == RESULT_OK) {
            if (data != null) {
                String sex = data.getStringExtra("sex");
//                mSharePreferenceUtil.setStringValue("sex", sex);
                mPersonSex.setText(sex + "  ");
            }

        }

    }

    private void requestPersonBirthday() {
        final DatePicker picker = new DatePicker(this);
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setTopPadding(ConvertUtils.toPx(this, 10));
        picker.setRangeEnd(2117, 1, 1);
        picker.setRangeStart(1917, 1, 1);
        picker.setSelectedItem(2017, 1, 1);
        picker.setResetWhileWheel(false);
        picker.setOnDatePickListener((DatePicker.OnYearMonthDayPickListener) (year, month, day)
                -> {
            String date = year + "-" + month + "-" + day;
//            mSharePreferenceUtil.setStringValue("birthday", date);
            mPersonBirthdayDate.setText(date + "  ");
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }

    private void requestUpdatePersonInfo() {

    }
}
