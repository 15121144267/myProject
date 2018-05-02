package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.banshengyuan.feima.DaggerApplication;
import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerAddAddressActivityComponent;
import com.banshengyuan.feima.dagger.module.AddAddressActivityModule;
import com.banshengyuan.feima.entity.AddAddressRequest;
import com.banshengyuan.feima.entity.AddressResponse;
import com.banshengyuan.feima.entity.IntentConstant;
import com.banshengyuan.feima.utils.ToastUtils;
import com.banshengyuan.feima.utils.ValueUtil;
import com.banshengyuan.feima.view.PresenterControl.AddAddressControl;
import com.banshengyuan.feima.view.customview.citypickerview.widget.CityPicker;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/28.
 * AddAddressActivity
 */

public class AddAddressActivity extends BaseActivity implements AddAddressControl.AddAddressView {

    public static Intent getIntent(Context context, AddressResponse.ListBean bean) {
        Intent intent = new Intent(context, AddAddressActivity.class);
        if (bean != null) {
            intent.putExtra(IntentConstant.ADDRESS_DETAIL, bean);
        }
        return intent;
    }

    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.add_address_name)
    EditText mAddAddressName;
    @BindView(R.id.add_address_tel)
    EditText mAddAddressTel;
    @BindView(R.id.add_address_location)
    LinearLayout mAddAddressLocation;
    @BindView(R.id.add_address_location_text)
    TextView mAddAddressLocationText;
    @BindView(R.id.add_address_location_detail)
    EditText mAddAddressLocationDetail;
    @BindView(R.id.add_address_location_street)
    EditText mAddAddressLocationStreet;
    @BindView(R.id.add_address_time)
    TextView mAddAddressTime;
    @BindView(R.id.add_address_default)
    CheckBox mAddAddressDefault;
    @BindView(R.id.address_save)
    Button mAddressSave;
    private String mProvince;
    private String mCity;
    private String mDistrict;
    private AddAddressRequest request = new AddAddressRequest();
    @Inject
    AddAddressControl.PresenterAddAddress mPresenter;

    private AddressResponse.ListBean bean = null;
    private String token ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);
        initializeInjector();
        supportActionBar(mToolbar, true);
        mMiddleName.setText("新增收货地址");
        initView();
        initData();
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
        AMapLocation aMapLocation = ((DaggerApplication) getApplicationContext()).getMapLocation();
        if (aMapLocation != null) {
            mProvince = aMapLocation.getProvince();
            mCity = aMapLocation.getCity();
            mDistrict = aMapLocation.getDistrict();
        }

        RxView.clicks(mAddAddressLocation).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> showAddressDialog());
        RxView.clicks(mAddressSave).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> AddNewAddress());
    }

    private void AddNewAddress() {
        String name = mAddAddressName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showShortToast("收货人不能为空");
            return;
        }

        String phone = mAddAddressTel.getText().toString().trim();
        if (ValueUtil.isMobilePhone(phone)) {
            ToastUtils.showShortToast("请输入正确的手机号");
            return;
        }

        String address = mAddAddressLocationText.getText().toString().trim();
        if (TextUtils.isEmpty(address)) {
            ToastUtils.showShortToast("所在区域不能为空");
            return;
        }

        String street = mAddAddressLocationStreet.getText().toString().trim();
        if (TextUtils.isEmpty(street)) {
            ToastUtils.showShortToast("所在街道不能为空");
            return;
        }

        String addressDetail = mAddAddressLocationDetail.getText().toString().trim();
        if (TextUtils.isEmpty(addressDetail)) {
            ToastUtils.showShortToast("详细地址不能为空");
            return;
        }
        request.name = name;
//        request.mobile = mBuProcessor.getUserPhone() == null ? mSharePreferenceUtil.getStringValue(SpConstant.USER_NAME) : mBuProcessor.getUserPhone();
        request.mobile = phone;
        request.province = mProvince;
        request.city = mCity;
        request.area = mDistrict;
        request.street = street;//街区  修改？？？？
        request.address = addressDetail;
        request.is_default = mAddAddressDefault.isChecked() ? "2" : "1";

        if (bean == null) {
            mPresenter.requestAddressAdd(request,token);
        } else {
            mPresenter.requestAddressUpdate(bean.getId(), request, token);
        }
    }

    @Override
    public void addAddressSuccess() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void updateAddressSuccess() {
        setResult(RESULT_OK);
        finish();
    }

    private void showAddressDialog() {
        hideSoftInput(mAddAddressLocation);
        CityPicker cityPicker = new CityPicker.Builder(AddAddressActivity.this).textSize(18)
                .title(" ")
                .cancelTextColor("#35BBc6")
                .confirTextColor("#35BBc6")
                .backgroundPop(0xa0000000)
                .province(TextUtils.isEmpty(mProvince) ? "北京市" : mProvince)
                .city(TextUtils.isEmpty(mCity) ? "北京市" : mCity)
                .district(TextUtils.isEmpty(mDistrict) ? "朝阳区" : mDistrict)
                .textColor(Color.parseColor("#35BBc6"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(6)
                .itemPadding(10)
                .build();
        cityPicker.show();
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                mProvince = citySelected[0];
                mCity = citySelected[1];
                mDistrict = citySelected[2];

                if (citySelected[0].equals(citySelected[1])) {
                    mAddAddressLocationText.setText(citySelected[0] + citySelected[2]+"");
                } else {
                    mAddAddressLocationText.setText(citySelected[0] + citySelected[1]
                            + citySelected[2]);
                }
            }

            @Override
            public void onCancel() {
            }
        });
    }

    private void initData() {
        token = mBuProcessor.getUserToken();
        if (getIntent() != null) {
            bean = getIntent().getParcelableExtra(IntentConstant.ADDRESS_DETAIL);
            if (bean == null) return;
            mAddAddressName.setText(bean.getName());
            mAddAddressTel.setText(bean.getMobile());
            if (bean.getProvince().equals(bean.getCity())) {
                mAddAddressLocationText.setText(bean.getProvince() + bean.getArea());
            } else {
                mAddAddressLocationText.setText(bean.getProvince() + bean.getCity() + bean.getArea());
            }
            mAddAddressLocationStreet.setText(bean.getStreet());
            mAddAddressLocationDetail.setText(bean.getAddress());

            mAddAddressDefault.setChecked(bean.getIs_default() == 2);

            mAddAddressDefault.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (!mAddAddressDefault.isChecked() && bean.getIs_default() == 2) {
                    mAddAddressDefault.setChecked(true);
                }
            });
        }
    }

    private void initializeInjector() {
        DaggerAddAddressActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .addAddressActivityModule(new AddAddressActivityModule(AddAddressActivity.this, this))
                .build().inject(this);
    }
}
