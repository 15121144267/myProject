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

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerPayActivityComponent;
import com.banshengyuan.feima.dagger.module.PayActivityModule;
import com.banshengyuan.feima.dagger.module.ShoppingCardListResponse;
import com.banshengyuan.feima.entity.AddressResponse;
import com.banshengyuan.feima.entity.IntentConstant;
import com.banshengyuan.feima.entity.MyCoupleResponse;
import com.banshengyuan.feima.entity.OrderConfirmItem;
import com.banshengyuan.feima.entity.OrderConfirmedResponse;
import com.banshengyuan.feima.entity.PayConstant;
import com.banshengyuan.feima.entity.PayResponse;
import com.banshengyuan.feima.help.DialogFactory;
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
    @BindView(R.id.pay_layout)
    LinearLayout payLayout;


    public static Intent getIntent(Context context, ShoppingCardListResponse response, Integer flag) {
        Intent intent = new Intent(context, PayActivity.class);
        intent.putExtra("ShoppingCardListResponse", response);
        intent.putExtra("flag", flag);
        return intent;
    }

    public static Intent getIntent(Context context, String order_sn) {
        Intent intent = new Intent(context, PayActivity.class);
        intent.putExtra("order_sn", order_sn);
        return intent;
    }


    @Inject
    PayControl.PresenterPay mPresenter;
    private final String[] modules = {"物流到家", "门店自提"};
    private PayGoodsListAdapter mAdapter;
    private ShoppingCardListResponse mOrderConfirm;
    private AddressResponse.ListBean mDataBean;
    private String mAddressId;
    private OrderConfirmedResponse mResponse;
    private Integer mFlag;
    private String mOrderSn;
    private int mChannel = 1;

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
        showToast("订单确定成功");
        mResponse = response;
        PayMethodDialog payMethodDialog = PayMethodDialog.newInstance();
        payMethodDialog.setListener(this);
        DialogFactory.showDialogFragment(getSupportFragmentManager(), payMethodDialog, PayMethodDialog.TAG);
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
    public void clickRechargeBtn(Integer payType) {
        if (mResponse != null) {
            switch (payType) {
                case 1:
                    mPresenter.requestPayInfo(mResponse, payType, mChannel);
                    break;
                case 2:
                    mPresenter.requestPayInfo(mResponse, payType, mChannel);
                    break;
            }
        }
    }

    @Override
    public void orderPayInfoSuccess(PayResponse response) {
        if (PayConstant.PAY_TYPE_WX.equals(response.pay_ebcode + "")) {
            PayWXHelper.getInstance().pay(response.pay_order, this);
        } else {
            PayZFBHelper.getInstance().pay(response.biz_content, this);
        }
    }

    @Override
    public void orderPaySuccess() {

    }

    @Override
    public void getCouponListRequestSuccess(MyCoupleResponse response) {
        List<ShoppingCardListResponse.ListBeanX.UserTicketBean> list = new ArrayList<>();
        if (response.getList() != null && response.getList().size() > 0) {
            for (MyCoupleResponse.ListBean listBean : response.getList()) {
                ShoppingCardListResponse.ListBeanX.UserTicketBean bean = new ShoppingCardListResponse.ListBeanX.UserTicketBean();
                bean.id = listBean.getId();
                bean.name = listBean.getName();
                bean.status = listBean.getStatus();
                bean.store_name = listBean.getStore_name();
                bean.store_id = listBean.getStore_id();
                bean.type = listBean.getType();
                bean.value = listBean.getValue();
                bean.expire_end_time = listBean.getExpire_end_time();
                bean.expire_start_time = listBean.getExpire_start_time();
                list.add(bean);
            }
            mOrderConfirm.list.get(0).user_ticket = list;
        }

        mAdapter.setNewData(mOrderConfirm.list);
    }

    @Override
    public void getCouponListRequestFail(String des) {
        showToast(des);
        mAdapter.setNewData(mOrderConfirm.list);
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
        mPresenter.requestAddressList(mBuProcessor.getUserToken());
    }

    private void initView() {
        mOrderSn = getIntent().getStringExtra("order_sn");
        if (!TextUtils.isEmpty(mOrderSn)) {
            mChannel = 2;
            payLayout.setVisibility(View.GONE);
            mResponse = new OrderConfirmedResponse();
            mResponse.order_sn = mOrderSn;
            showDialog();
            return;
        }

        for (String module : modules) {
            mPayTabLayout.addTab(mPayTabLayout.newTab().setText(module));
        }
        ValueUtil.setIndicator(mPayTabLayout, 60, 60);
        mOrderConfirm = (ShoppingCardListResponse) getIntent().getSerializableExtra("ShoppingCardListResponse");

        mFlag = getIntent().getIntExtra("flag", 0);

        mPayOrderList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new PayGoodsListAdapter(null, PayActivity.this, mImageLoaderHelper);
        mPayOrderList.setAdapter(mAdapter);
        if (mFlag == 2) {
            if (mOrderConfirm != null) {
                mAdapter.setNewData(mOrderConfirm.list);
            }
        } else if (mFlag == 1) {
            mPresenter.requestCouponList(mOrderConfirm.list.get(0).store_id + "", "1");
        }


        RxView.clicks(mPayOrder).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> requestPay());
        RxView.clicks(mPayOrderAddressLayout).throttleFirst(1, TimeUnit.SECONDS).subscribe(v -> requestAddress());
        countPrice();
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

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.adapter_pay_coupon:
                    showToast("显示优惠券");
                    break;
            }
        });
    }

    private void requestPay() {
        if (TextUtils.isEmpty(mAddressId)) {
            showToast("请选择收获地址");
            return;
        }


        if (mResponse == null) {
            if (mOrderConfirm.list != null && mOrderConfirm.list.size() > 0) {
                List<OrderConfirmItem> list = new ArrayList<>();
                for (int i = 0; i < mOrderConfirm.list.size(); i++) {
                    OrderConfirmItem confirmItem = new OrderConfirmItem();
                    confirmItem.store_id = mOrderConfirm.list.get(i).store_id;
                    confirmItem.store_name = mOrderConfirm.list.get(i).stoer_name;
                    EditText edit = (EditText) mAdapter.getViewByPosition(mPayOrderList, i, R.id.adapter_pay_suggestion);
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
                mPresenter.requestOrderConfirmed(mAddressId, list);
            }
        } else {
            showDialog();
        }

    }

    private void showDialog() {
        PayMethodDialog payMethodDialog = PayMethodDialog.newInstance();
        payMethodDialog.setListener(this);
        DialogFactory.showDialogFragment(getSupportFragmentManager(), payMethodDialog, PayMethodDialog.TAG);
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
                Integer price = 0;
                for (ShoppingCardListResponse.ListBeanX.ListBean listBean : listBeanX.list) {
                    price += listBean.goods_price * listBean.number;
                }
                allPrice += price;
                if (listBeanX.shop_freight_config != null) {
                    if (listBeanX.shop_freight_config.freight == 1) {
                        if (price >= listBeanX.shop_freight_config.free_shipping_price) {
                            dispatchingPrice += 0;
                        } else {
                            dispatchingPrice += listBeanX.shop_freight_config.shipping_price;
                        }

                    }

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
