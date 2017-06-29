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
import android.widget.TextView;

import com.dispatching.feima.R;
import com.dispatching.feima.entity.AddressResponse;
import com.dispatching.feima.utils.ToastUtils;
import com.dispatching.feima.utils.ValueUtil;
import com.dispatching.feima.view.customview.citypickerview.widget.CityPicker;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/28.
 */

public class AddAddressActivity extends BaseActivity {

    public static Intent getIntent(Context context) {
        return new Intent(context, AddAddressActivity.class);
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
    TextView mAddAddressLocation;
    @BindView(R.id.add_address_location_detail)
    EditText mAddAddressLocationDetail;
    @BindView(R.id.add_address_time)
    TextView mAddAddressTime;
    @BindView(R.id.add_address_default)
    CheckBox mAddAddressDefault;
    @BindView(R.id.address_save)
    Button mAddressSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);
        supportActionBar(mToolbar, true);
        mMiddleName.setText("新增收货地址");
        initView();
        initData();
    }

    private void initView() {
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

        String address = mAddAddressLocation.getText().toString().trim();
        if (TextUtils.isEmpty(address)) {
            ToastUtils.showShortToast("所在区域不能为空");
            return;
        }

        String addressDetail = mAddAddressLocationDetail.getText().toString().trim();
        if (TextUtils.isEmpty(addressDetail)) {
            ToastUtils.showShortToast("详细地址不能为空");
            return;
        }
        AddressResponse response = new AddressResponse();
        response.name = name;
        response.phone = phone;
        response.address = address + addressDetail;
        response.checkedAddress = mAddAddressDefault.isChecked();
        Intent intent = new Intent();
        intent.putExtra("newAddress",response);
        setResult(RESULT_OK,intent);
        finish();
    }

    private void showAddressDialog() {
        CityPicker cityPicker = new CityPicker.Builder(AddAddressActivity.this).textSize(20)
                .titleTextColor("#000000")
                .backgroundPop(0xa0000000)
                .province("上海市")
                .city("上海市")
                .district("普陀区")
                .textColor(Color.parseColor("#000000"))
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
                    mAddAddressLocation.setText(citySelected[0] + citySelected[2]);
                } else {
                    mAddAddressLocation.setText(citySelected[0] + citySelected[1]
                            + citySelected[2]);
                }

            }

            @Override
            public void onCancel() {
            }
        });
    }

    private void initData() {

    }
}
