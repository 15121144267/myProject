package com.dispatching.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.dispatching.feima.R;

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
    }

    private void initData() {
    }
}
