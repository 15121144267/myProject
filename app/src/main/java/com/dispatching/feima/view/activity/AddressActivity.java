package com.dispatching.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.dispatching.feima.R;
import com.dispatching.feima.dagger.component.DaggerAddressActivityComponent;
import com.dispatching.feima.dagger.module.AddressActivityModule;
import com.dispatching.feima.entity.AddressResponse;
import com.dispatching.feima.entity.IntentConstant;
import com.dispatching.feima.view.PresenterControl.AddressControl;
import com.dispatching.feima.view.adapter.AddressAdapter;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/28.
 * AddressActivity
 */

public class AddressActivity extends BaseActivity implements AddressControl.AddressView {

    public static Intent getIntent(Context context) {
        return new Intent(context, AddressActivity.class);
    }


    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.address_list)
    RecyclerView mAddressList;
    @BindView(R.id.address_add)
    Button mAddressAdd;
    private CheckBox mCheckBox;

    private AddressAdapter mAdapter;
    private List<AddressResponse> mList;
    @Inject
    AddressControl.PresenterAddress mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);
        initializeInjector();
        supportActionBar(mToolbar, true);
        mMiddleName.setText("收货地址");
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

    private void initView() {
        mList = new ArrayList<>();
        mAdapter = new AddressAdapter(null, this);
        mAddressList.setLayoutManager(new LinearLayoutManager(this));
        mAddressList.setAdapter(mAdapter);
        RxView.clicks(mAddressAdd).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> requestAddAddress());
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                    mCheckBox = (CheckBox) view.findViewById(R.id.address_default);
                    switch (view.getId()) {
                        case R.id.address_edit:
                            showToast("我被编辑了");
                            break;
                        case R.id.address_delete:
                            showToast("我被删除了");

                        case R.id.address_default:
                            if (!mCheckBox.isChecked()) {
                                mCheckBox.setChecked(true);
                                return;
                            }
                            mAdapter.setChecked(position, true);
                            break;
                    }
                }
        );
    }

    private void requestAddAddress() {
        startActivityForResult(AddAddressActivity.getIntent(this), IntentConstant.ORDER_POSITION_ONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentConstant.ORDER_POSITION_ONE && resultCode == RESULT_OK) {
            if (data != null){
                AddressResponse newAddress = (AddressResponse) data.getSerializableExtra("newAddress");
                if(newAddress.checkedAddress){
                    for (AddressResponse addressResponse : mList) {
                        addressResponse.checkedAddress = false;
                    }
                    mList.add(0,newAddress);
                }else {
                    mList.add(newAddress);
                }
                mAdapter.setNewData(mList);
            }
        }
    }

    private void initData() {
        for (int i = 0; i < 2; i++) {
            AddressResponse response = new AddressResponse();
            if (i == 0) {
                response.checkedAddress = true;
            }
            response.address = "上海市祁连山耀光国际1803";
            response.name = "何先生";
            response.phone = "15121144267";
            mList.add(response);
        }
        mAdapter.setNewData(mList);
    }

    private void initializeInjector() {
        DaggerAddressActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .addressActivityModule(new AddressActivityModule(AddressActivity.this, this))
                .build().inject(this);
    }
}
