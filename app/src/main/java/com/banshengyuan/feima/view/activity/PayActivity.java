package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerPayActivityComponent;
import com.banshengyuan.feima.dagger.module.PayActivityModule;
import com.banshengyuan.feima.dagger.module.ShoppingCardListResponse;
import com.banshengyuan.feima.entity.AddressResponse;
import com.banshengyuan.feima.entity.IntentConstant;
import com.banshengyuan.feima.entity.OrderConfirmItem;
import com.banshengyuan.feima.entity.OrderConfirmedResponse;
import com.banshengyuan.feima.entity.PayConstant;
import com.banshengyuan.feima.entity.PayResponse;
import com.banshengyuan.feima.help.PayZFBHelper;
import com.banshengyuan.feima.help.WXPayHelp.PayWXHelper;
import com.banshengyuan.feima.listener.TabCheckListener;
import com.banshengyuan.feima.utils.ValueUtil;
import com.banshengyuan.feima.view.PresenterControl.PayControl;
import com.banshengyuan.feima.view.adapter.PayGoodsListAdapter;
import com.banshengyuan.feima.view.fragment.PayMethodDialog;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/27.
 * MyOrderActivity
 */

public class PayActivity extends BaseActivity implements PayControl.PayView, PayMethodDialog.PayMethodClickListener {

    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.pay_tab_layout)
    TabLayout mPayTabLayout;
    @BindView(R.id.pay_order_list)
    RecyclerView mPayOrderList;
    @BindView(R.id.pay_price)
    TextView mPayPrice;
    @BindView(R.id.pay_order)
    TextView mPayOrder;
    @BindView(R.id.pay_address_name)
    TextView mPayAddressName;
    @BindView(R.id.pay_address_phone)
    TextView mPayAddressPhone;
    @BindView(R.id.pay_address_detail)
    TextView mPayAddressDetail;
    @BindView(R.id.pay_choose_address)
    LinearLayout mPayChooseAddress;
    @BindView(R.id.pay_order_address_layout)
    LinearLayout mPayOrderAddressLayout;

    public static Intent getIntent(Context context, ShoppingCardListResponse response) {
        Intent intent = new Intent(context, PayActivity.class);
        intent.putExtra("ShoppingCardListResponse", response);
        return intent;
    }


    @Inject
    PayControl.PresenterPay mPresenter;
    private final String[] modules = {"物流到家", "门店自提"};
    private PayGoodsListAdapter mAdapter;
    private ShoppingCardListResponse mOrderConfirm;
    private AddressResponse.ListBean mDataBean;
    private String mAddressId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        initializeInjector();
        supportActionBar(mToolbar, true);
        mMiddleName.setText("提交订单");
        initView();
        initData();
    }

    @Override
    public void listAddressSuccess(AddressResponse addressResponse) {
        if (addressResponse.getList() != null && addressResponse.getList().size() > 0) {
            List<AddressResponse.ListBean> addressList = addressResponse.getList();
            for (AddressResponse.ListBean listBean : addressList) {
                if (listBean.getIs_default() == 2) {
                    mAddressId = listBean.getId() + "";
                    mPayAddressName.setText(TextUtils.isEmpty(listBean.getName()) ? "未知" : listBean.getName());
                    mPayAddressDetail.setText(TextUtils.isEmpty(listBean.getAddress()) ? "未知" : listBean.getAddress());
                    mPayAddressPhone.setText(TextUtils.isEmpty(listBean.getMobile()) ? "未知" : listBean.getMobile());
                }
            }
        }
    }

    @Override
    public void orderConfirmedSuccess(OrderConfirmedResponse response) {
       /* mResponse = response;
        PayMethodDialog payMethodDialog = PayMethodDialog.newInstance();
        payMethodDialog.setListener(this);
        DialogFactory.showDialogFragment(getSupportFragmentManager(), payMethodDialog, PayMethodDialog.TAG);*/
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

    @Override
    public void clickRechargeBtn(String payType) {
        /*if (mResponse != null) {
            switch (payType) {
                case PayConstant.PAY_TYPE_WX:
                    mPresenter.requestPayInfo(mResponse, PayConstant.PAY_TYPE_WX);
                    break;
                case PayConstant.PAY_TYPE_ZFB:
                    mPresenter.requestPayInfo(mResponse, PayConstant.PAY_TYPE_ZFB);
                    break;
            }
        }*/
    }

    @Override
    public void orderPayInfoSuccess(PayResponse response) {
        if (PayConstant.PAY_TYPE_WX.equals(String.valueOf(response.pay_ebcode))) {
            PayWXHelper.getInstance().pay(response.pay_order, this);
        } else {
            PayZFBHelper.getInstance().pay(response.biz_content, this);
        }
    }

    @Override
    public void orderPaySuccess() {
        /*PayAccessRequest request = new PayAccessRequest();
        List<PayAccessRequest.OrdersBean> list = new ArrayList<>();
        for (String s : mResponse.data) {
            PayAccessRequest.OrdersBean order = new PayAccessRequest.OrdersBean();
            order.orderId = s;
            list.add(order);
        }
        request.orders = list;
        mPresenter.updateOrderStatus(request);*/
    }

    @Override
    public void updateOrderStatusSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        this.finish();
    }

    private void initData() {
        mPresenter.requestAddressList(BuildConfig.USER_TOKEN);
    }

    private void initView() {

        for (String module : modules) {
            mPayTabLayout.addTab(mPayTabLayout.newTab().setText(module));
        }
        ValueUtil.setIndicator(mPayTabLayout, 60, 60);
        mOrderConfirm = (ShoppingCardListResponse) getIntent().getSerializableExtra("ShoppingCardListResponse");
        mPayOrderList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new PayGoodsListAdapter(null, this, mImageLoaderHelper);
        mPayOrderList.setAdapter(mAdapter);
        if (mOrderConfirm != null) {
            mAdapter.setNewData(mOrderConfirm.list);
        }

        mAdapter.setOnItemClickListener((adapter, view, position) ->
                showToast(position + "")
        );
        countPrice();
        RxView.clicks(mPayOrder).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> requestPay());
        RxView.clicks(mPayOrderAddressLayout).throttleFirst(1, TimeUnit.SECONDS).subscribe(v -> requestAddress());

        mPayTabLayout.addOnTabSelectedListener(new TabCheckListener() {
            @Override
            public void onMyTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        mPayOrderAddressLayout.setVisibility(View.VISIBLE);
                        for (ShoppingCardListResponse.ListBeanX listBeanX : mOrderConfirm.list) {
                            listBeanX.freightWay = 0;
                        }

                        break;
                    case 1:
                        mPayOrderAddressLayout.setVisibility(View.GONE);
                        for (ShoppingCardListResponse.ListBeanX listBeanX : mOrderConfirm.list) {
                            listBeanX.freightWay = 1;
                        }
                        break;
                }
                mAdapter.setNewData(mOrderConfirm.list);
            }
        });
    }

    private void requestPay() {
        if (TextUtils.isEmpty(mAddressId)) {
            showToast("请选择收获地址");
            return;
        }
        if (mOrderConfirm.list != null && mOrderConfirm.list.size() > 0) {
            List<OrderConfirmItem> list = new ArrayList<>();
            for (int i = 0; i < mOrderConfirm.list.size(); i++) {
                OrderConfirmItem confirmItem = new OrderConfirmItem();
                confirmItem.store_id = mOrderConfirm.list.get(i).store_id;
                confirmItem.store_name = mOrderConfirm.list.get(i).stoer_name;
                confirmItem.freight = mOrderConfirm.list.get(i).freight;
                EditText edit = (EditText) mAdapter.getViewByPosition(i, R.id.adapter_pay_suggestion);
                if (edit != null) {
                    confirmItem.remark = edit.getText().toString();
                }
                confirmItem.ticket_id = "";
                List<OrderConfirmItem.ProductBean> product = new ArrayList<>();
                for (ShoppingCardListResponse.ListBeanX.ListBean listBean : mOrderConfirm.list.get(i).list) {
                    OrderConfirmItem.ProductBean productItem = new OrderConfirmItem.ProductBean();
                    productItem.goods_id = listBean.goods_id;
                    productItem.goods_sku = listBean.goods_sku;
                    productItem.number = listBean.number;
                    product.add(productItem);
                }

                confirmItem.product = product;
                list.add(confirmItem);
            }
            mPresenter.requestOrderConfirmed(mAddressId,list);
        }

       /* if (mResponse == null) {
            for (OrderConfirmedRequest request : mProductSpecification.orders) {
                request.address = mDataBean.getAddress() + mDataBean.getArea();
                request.phone = mDataBean.getMobile();
                request.userName = (String) mDataBean.getName();
            }

            mPresenter.requestOrderConfirmed(mProductSpecification);
        } else {
            PayMethodDialog payMethodDialog = PayMethodDialog.newInstance();
            payMethodDialog.setListener(this);
            DialogFactory.showDialogFragment(getSupportFragmentManager(), payMethodDialog, PayMethodDialog.TAG);
        }*/

    }

    private void requestAddress() {
        startActivityForResult(AddressActivity.getIntent(this, "payActivity"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentConstant.ORDER_POSITION_TWO && resultCode == RESULT_OK) {
            if (data != null) {
                mDataBean = data.getParcelableExtra("addressDataBean");
                if (mDataBean != null) {
                    mAddressId = mDataBean.getId() + "";
                    mPayAddressName.setText(TextUtils.isEmpty(mDataBean.getName()) ? "未知" : mDataBean.getName());
                    mPayAddressDetail.setText(TextUtils.isEmpty(mDataBean.getAddress()) ? "未知" : mDataBean.getAddress());
                    mPayAddressPhone.setText(TextUtils.isEmpty(mDataBean.getMobile()) ? "未知" : mDataBean.getMobile());
                }
            }
        }

    }


    private void countPrice() {
        if (mOrderConfirm.list != null && mOrderConfirm.list.size() > 0) {
            Integer allPrice = 0;
            Integer dispatchingPrice = 0;
            for (ShoppingCardListResponse.ListBeanX listBeanX : mOrderConfirm.list) {
                dispatchingPrice += listBeanX.freight;
                for (ShoppingCardListResponse.ListBeanX.ListBean listBean : listBeanX.list) {
                    allPrice += listBean.goods_price * listBean.number;
                }
            }
            mPayPrice.setText(ValueUtil.setAllPriceText(allPrice + dispatchingPrice, this));
        }
    }

    private void initializeInjector() {
        DaggerPayActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .payActivityModule(new PayActivityModule(PayActivity.this, this))
                .build().inject(this);
    }
}
