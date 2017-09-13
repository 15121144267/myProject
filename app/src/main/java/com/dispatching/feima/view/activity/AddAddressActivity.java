package com.dispatching.feima.view.activity;

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
import com.dispatching.feima.DaggerApplication;
import com.dispatching.feima.R;
import com.dispatching.feima.dagger.component.DaggerAddAddressActivityComponent;
import com.dispatching.feima.dagger.module.AddAddressActivityModule;
import com.dispatching.feima.entity.AddAddressRequest;
import com.dispatching.feima.entity.AddressResponse;
import com.dispatching.feima.entity.IntentConstant;
import com.dispatching.feima.entity.SpConstant;
import com.dispatching.feima.utils.ToastUtils;
import com.dispatching.feima.utils.ValueUtil;
import com.dispatching.feima.view.PresenterControl.AddAddressControl;
import com.dispatching.feima.view.customview.citypickerview.widget.CityPicker;
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

    public static Intent getIntent(Context context, AddressResponse.DataBean bean) {
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
        if(aMapLocation!=null){
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

        String addressDetail = mAddAddressLocationDetail.getText().toString().trim();
        if (TextUtils.isEmpty(addressDetail)) {
            ToastUtils.showShortToast("详细地址不能为空");
            return;
        }
        request.receiverName = name;
        request.receiverPhone = phone;
        request.address = address;
        request.area = addressDetail;
        request.phone = mBuProcessor.getUserPhone() == null ? mSharePreferenceUtil.getStringValue(SpConstant.USER_NAME) : mBuProcessor.getUserPhone();
        request.isDefault = mAddAddressDefault.isChecked() ? 1 : 0;
        mPresenter.requestAddAddress(request);

    }

    @Override
    public void addAddressSuccess() {
        showToast("操作成功");
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
                .city(TextUtils.isEmpty(mCity)? "北京市" : mCity)
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
                if (citySelected[0].equals(citySelected[1])) {
                    mAddAddressLocationText.setText(citySelected[0] + citySelected[2]);
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
        if (getIntent().getSerializableExtra(IntentConstant.ADDRESS_DETAIL) != null) {
            AddressResponse.DataBean bean = (AddressResponse.DataBean) getIntent().getSerializableExtra(IntentConstant.ADDRESS_DETAIL);
            mAddAddressName.setText((String)bean.receiverName);
            mAddAddressTel.setText(bean.receiverPhone);
            mAddAddressLocationText.setText(bean.address);
            mAddAddressLocationDetail.setText(bean.area);
            mAddAddressDefault.setChecked(bean.isDefault == 1);
            request.id = bean.id;
        }
    }

    private void initializeInjector() {
        DaggerAddAddressActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .addAddressActivityModule(new AddAddressActivityModule(AddAddressActivity.this, this))
                .build().inject(this);
    }
}
