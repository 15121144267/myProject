package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerAddressActivityComponent;
import com.banshengyuan.feima.dagger.module.AddressActivityModule;
import com.banshengyuan.feima.entity.AddAddressRequest;
import com.banshengyuan.feima.entity.AddressResponse;
import com.banshengyuan.feima.entity.Constant;
import com.banshengyuan.feima.entity.IntentConstant;
import com.banshengyuan.feima.entity.SpConstant;
import com.banshengyuan.feima.help.DialogFactory;
import com.banshengyuan.feima.view.PresenterControl.AddressControl;
import com.banshengyuan.feima.view.adapter.AddressAdapter;
import com.banshengyuan.feima.view.fragment.CommonDialog;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/28.
 * AddressActivity
 */

public class AddressActivity extends BaseActivity implements AddressControl.AddressView, CommonDialog.CommonDialogListener {

    public static Intent getIntent(Context context, String fromFlag) {
        Intent intent = new Intent(context, AddressActivity.class);
        intent.putExtra("fromFlag", fromFlag);
        return intent;
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
    private AddressResponse.ListBean mBean;
    private AddressAdapter mAdapter;
    private String mUserPhone;
    private Integer mPosition;
    private String token;

    private List<AddressResponse.ListBean> mList = new ArrayList<>();
    private int deleteId = -1;//记录要删除的id
    private View mEmptyView = null;

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
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public Context getContext() {
        return this;
    }


    private void initView() {
        String fromFlag = getIntent().getStringExtra("fromFlag");
        mUserPhone = mSharePreferenceUtil.getStringValue(SpConstant.USER_NAME);
        mAdapter = new AddressAdapter(null, this);
        mAddressList.setLayoutManager(new LinearLayoutManager(this));
        mAddressList.setAdapter(mAdapter);

        mEmptyView = LayoutInflater.from(this).inflate(R.layout.empty_view, (ViewGroup) mAddressList.getParent(), false);
        ImageView imageView = (ImageView) mEmptyView.findViewById(R.id.empty_icon);
        mImageLoaderHelper.displayImage(this,R.mipmap.empty_address_view,imageView);
        TextView emptyContent = (TextView) mEmptyView.findViewById(R.id.empty_content);
        emptyContent.setVisibility(View.VISIBLE);
        emptyContent.setText(R.string.mime_address_empty_view);
        Button emptyButton = (Button) mEmptyView.findViewById(R.id.empty_text);
        emptyButton.setVisibility(View.GONE);


        RxView.clicks(mAddressAdd).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> requestAddAddress());
        if (fromFlag.equals("payActivity")) {
            mAdapter.setOnItemClickListener((adapter, view, position) -> {
                        mBean = (AddressResponse.ListBean) adapter.getItem(position);
                        Intent intent = new Intent();
                        intent.putExtra("addressDataBean", mBean);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
            );
        }

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                    mPosition = position;
                    mBean = (AddressResponse.ListBean) adapter.getItem(position);
                    mCheckBox = (CheckBox) view.findViewById(R.id.address_default);
                    switch (view.getId()) {
                        case R.id.address_edit:
                            startActivityForResult(AddAddressActivity.getIntent(this, mBean), IntentConstant.ORDER_POSITION_ONE);
                            break;
                        case R.id.address_delete:
                            deleteId = mBean.getId();
                            showDialog();
                            break;
                        case R.id.address_default:
                            if (!mCheckBox.isChecked()) {
                                mCheckBox.setChecked(true);
                                return;
                            }
                            AddressResponse.ListBean listBean = (AddressResponse.ListBean) adapter.getItem(position);
                            if (listBean != null) {
                                if (listBean.getIs_default() == 1) {
                                    //设为默认地址
                                    AddAddressRequest addAddressRequest = new AddAddressRequest();
                                    addAddressRequest.setName(listBean.getName());
                                    addAddressRequest.setAddress(listBean.getAddress());
                                    addAddressRequest.setArea(listBean.getArea());
                                    addAddressRequest.setCity(listBean.getCity());
                                    addAddressRequest.setMobile(listBean.getMobile());
                                    addAddressRequest.setProvince(listBean.getProvince());
                                    addAddressRequest.setStreet(listBean.getStreet());
                                    addAddressRequest.setIs_default("2");
                                    mPresenter.requestUpdateAddress(listBean.getId() + "", addAddressRequest, token);
                                }
                            }
                            break;
                    }
                }
        );
    }


    private void requestAddAddress() {
        startActivityForResult(AddAddressActivity.getIntent(this, null), IntentConstant.ORDER_POSITION_ONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentConstant.ORDER_POSITION_ONE && resultCode == RESULT_OK) {//添加地址  更新地址
            mPresenter.requestAddressList(token);
        }

    }

    @Override
    public void commonDialogBtnOkListener(int type, int position) {
        if (deleteId != -1) {
            mPresenter.requestDeleteAddress(deleteId + "", token);
        }

    }

    @Override
    public void listAddressSuccess(AddressResponse addressResponse) {
        mList = addressResponse.getList();
        if (mList.size() > 0) {
            mAdapter.setNewData(mList);
            mAdapter.loadMoreComplete();
        } else {
            mAdapter.setEmptyView(mEmptyView);
        }
    }

    @Override
    public void deleteAddressSuccess() {
        mAdapter.remove(mPosition);
        mPresenter.requestAddressList(token);
    }

    @Override
    public void updateAddressSuccess() {
        showToast("更新成功");
        for (int i = 0; i < mList.size(); i++) {
            if (i == mPosition) {
                mList.get(i).setIs_default(2);
            } else {
                mList.get(i).setIs_default(1);
            }
        }
        Collections.sort(mList, (o1, o2) -> {
            if (o1.getIs_default() < (o2.getIs_default())) {
                return 1;
            }
            return -1;
        });
        mAdapter.setNewData(mList);
    }

    @Override
    public void updateAddressFail() {
        mCheckBox.setChecked(false);
    }

    @Override
    public void updateAddressError(Throwable throwable) {
        showErrMessage(throwable);
        mCheckBox.setChecked(false);
    }

    private void initData() {
        token = mBuProcessor.getUserToken();
        mPresenter.requestAddressList(token);
    }

    private void showDialog() {
        CommonDialog commonDialog = CommonDialog.newInstance();
        commonDialog.setContent(getString(R.string.delete_address));
        commonDialog.setListener(this);
        DialogFactory.showDialogFragment(getSupportFragmentManager(), commonDialog, CommonDialog.TAG);
    }


    private void initializeInjector() {
        DaggerAddressActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .addressActivityModule(new AddressActivityModule(AddressActivity.this, this))
                .build().inject(this);
    }
}
