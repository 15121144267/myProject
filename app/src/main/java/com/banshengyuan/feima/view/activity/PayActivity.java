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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerPayActivityComponent;
import com.banshengyuan.feima.dagger.module.PayActivityModule;
import com.banshengyuan.feima.entity.ShoppingCardListResponse;
import com.banshengyuan.feima.entity.AddressResponse;
import com.banshengyuan.feima.entity.IntentConstant;
import com.banshengyuan.feima.entity.MyCoupleResponse;
import com.banshengyuan.feima.entity.OrderConfirmItem;
import com.banshengyuan.feima.entity.OrderConfirmedResponse;
import com.banshengyuan.feima.listener.TabCheckListener;
import com.banshengyuan.feima.utils.ValueUtil;
import com.banshengyuan.feima.view.PresenterControl.PayControl;
import com.banshengyuan.feima.view.adapter.PayGoodsListAdapter;
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

public class PayActivity extends BaseActivity implements PayControl.PayView {

    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
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
    @BindView(R.id.pay_tab_layout)
    TabLayout mPayTabLayout;


    public static Intent getIntent(Context context, ShoppingCardListResponse response, Integer flag) {
        Intent intent = new Intent(context, PayActivity.class);
        intent.putExtra("ShoppingCardListResponse", response);
        intent.putExtra("flag", flag);
        return intent;
    }

    @Inject
    PayControl.PresenterPay mPresenter;
    private final String[] modules = {"物流到家", "门店自提"};
    private PayGoodsListAdapter mAdapter;
    private ShoppingCardListResponse mOrderConfirm;
    private AddressResponse.ListBean mDataBean;
    private String mAddressId = "";
    private Integer mIsSelfFetch = 0;
    private Integer mCouponPosition;

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
                    mDataBean = listBean;
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
        if (!TextUtils.isEmpty(response.order_sn)) {
            if (response.total_fee < 0) {
                showToast("数据出错,请稍后重试");
                return;
            }
            setResult(RESULT_OK);
            startActivity(FinalPayActivity.getIntent(this, response.order_sn, 1));
        }
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
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
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
                bean.start_val = listBean.getStart_val();
                bean.end_val = listBean.getEnd_val();
                bean.expire_end_time = listBean.getExpire_end_time();
                bean.expire_start_time = listBean.getExpire_start_time();
                list.add(bean);
            }
            mOrderConfirm.list.get(0).user_ticket = list;
        }

        mAdapter.setNewData(mOrderConfirm.list);
        initCountPrice();
    }

    @Override
    public void getCouponListRequestFail(String des) {
        showToast(des);
        mAdapter.setNewData(mOrderConfirm.list);
        initCountPrice();
    }

    private void initData() {
        mPresenter.requestAddressList(mBuProcessor.getUserToken());
    }

    private void initView() {
        for (String module : modules) {
            mPayTabLayout.addTab(mPayTabLayout.newTab().setText(module));
        }
        ValueUtil.setIndicator(mPayTabLayout, 60, 60);
        mOrderConfirm = (ShoppingCardListResponse) getIntent().getSerializableExtra("ShoppingCardListResponse");

        Integer mFlag = getIntent().getIntExtra("flag", 0);

        mPayOrderList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new PayGoodsListAdapter(null, PayActivity.this, mImageLoaderHelper);
        mPayOrderList.setAdapter(mAdapter);
        if (mFlag == 2) {
            if (mOrderConfirm != null) {
                mAdapter.setNewData(mOrderConfirm.list);
                initCountPrice();
            }
        } else if (mFlag == 1) {
            mPresenter.requestCouponList(mOrderConfirm.list.get(0).store_id + "", "1");
        }


        RxView.clicks(mPayOrder).throttleFirst(2, TimeUnit.SECONDS).subscribe(v -> requestPay());
        RxView.clicks(mPayOrderAddressLayout).throttleFirst(1, TimeUnit.SECONDS).subscribe(v -> requestAddress());

        mPayTabLayout.addOnTabSelectedListener(new TabCheckListener() {
            @Override
            public void onMyTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        mIsSelfFetch = 0;
                        if (mDataBean != null) {
                            mAddressId = mDataBean.getId() + "";
                        }
                        mPayOrderAddressLayout.setVisibility(View.VISIBLE);
                        for (ShoppingCardListResponse.ListBeanX listBeanX : mOrderConfirm.list) {
                            listBeanX.freightWay = 0;
                        }

                        break;
                    case 1:
                        mIsSelfFetch = 1;
                        mAddressId = "";
                        mPayOrderAddressLayout.setVisibility(View.GONE);
                        for (ShoppingCardListResponse.ListBeanX listBeanX : mOrderConfirm.list) {
                            listBeanX.freightWay = 1;
                        }
                        break;
                }
                mAdapter.setNewData(mOrderConfirm.list);
                initCountPrice();
            }
        });

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            ShoppingCardListResponse.ListBeanX bean = (ShoppingCardListResponse.ListBeanX) adapter.getItem(position);
            TextView textView = (TextView) adapter.getViewByPosition(mPayOrderList, position, R.id.adapter_shopping_card_price_all);
            double price = 0;
            if (bean != null) {
                switch (view.getId()) {
                    case R.id.adapter_pay_coupon:
                        mCouponPosition = position;
                        if (textView != null) {
                            price = Double.parseDouble(textView.getText().toString());
                        }
                        if (bean.user_ticket != null && bean.user_ticket.size() > 0) {
                            MyCoupleResponse response = new MyCoupleResponse();
                            List<MyCoupleResponse.ListBean> listBean = new ArrayList<>();
                            for (ShoppingCardListResponse.ListBeanX.UserTicketBean userTicketBean : bean.user_ticket) {
                                MyCoupleResponse.ListBean beanItem = new MyCoupleResponse.ListBean();
                                beanItem.setEnd_val(userTicketBean.end_val);
                                beanItem.setStart_val(userTicketBean.start_val);
                                beanItem.setExpire_end_time(userTicketBean.expire_end_time);
                                beanItem.setExpire_start_time(userTicketBean.expire_start_time);
                                beanItem.setId(userTicketBean.id);
                                beanItem.setStore_id(userTicketBean.store_id);
                                beanItem.setStore_name(userTicketBean.store_name);
                                beanItem.setName(userTicketBean.name);
                                beanItem.setType(userTicketBean.type);
                                beanItem.setStatus(userTicketBean.status);
                                listBean.add(beanItem);
                            }
                            response.setList(listBean);
                            startActivityForResult(CouponActivity.getIntent(this, response, price), 12);
                        }
                        break;
                }
            }

        });
    }

    private void requestPay() {
        if (mIsSelfFetch == 0 && TextUtils.isEmpty(mAddressId)) {
            showToast("请选择收获地址");
            return;
        }

        if (mAdapter.getData().size() > 0) {
            List<OrderConfirmItem> list = new ArrayList<>();
            for (ShoppingCardListResponse.ListBeanX listBeanX : mAdapter.getData()) {
                OrderConfirmItem confirmItem = new OrderConfirmItem();
                confirmItem.store_id = listBeanX.store_id;
                confirmItem.store_name = listBeanX.stoer_name;
                confirmItem.ticket_id = listBeanX.couponId;
                confirmItem.remark = listBeanX.remark;
                List<OrderConfirmItem.ProductBean> product = new ArrayList<>();
                for (ShoppingCardListResponse.ListBeanX.ListBean listBean : listBeanX.list) {
                    OrderConfirmItem.ProductBean productItem = new OrderConfirmItem.ProductBean();
                    productItem.goods_id = listBean.goods_id;
                    productItem.goods_sku = listBean.goods_sku;
                    productItem.number = listBean.number;
                    product.add(productItem);
                }
                confirmItem.product = product;
                list.add(confirmItem);
            }
            mPresenter.requestOrderConfirmed(mAddressId, list, mIsSelfFetch);
        }

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
        } else if (requestCode == 12 && resultCode == RESULT_OK) {
            MyCoupleResponse.ListBean mCheckData = (MyCoupleResponse.ListBean) data.getSerializableExtra("mCheckData");
            if (mCheckData != null) {
                ShoppingCardListResponse.ListBeanX bean = mAdapter.getItem(mCouponPosition);
                if (bean != null) {
                    bean.reduceWay = mCheckData.getType();
                    bean.reduceValue = mCheckData.getEnd_val();
                    bean.couponDes = mCheckData.getName();
                    bean.couponId = mCheckData.getId();
                    mAdapter.setData(mCouponPosition, bean);
                    initCountPrice();
                }
            }
        }
    }

    private void initCountPrice() {
        if (mOrderConfirm.list != null && mOrderConfirm.list.size() > 0) {
            double allPrice = 0;
            double cutPrice = 0;
            Integer dispatchingPrice = 0;
            for (ShoppingCardListResponse.ListBeanX listBeanX : mAdapter.getData()) {
                double price = 0;
                for (ShoppingCardListResponse.ListBeanX.ListBean listBean : listBeanX.list) {
                    price += listBean.goods_price * listBean.number;
                }
                allPrice += price;
                if (mIsSelfFetch != 1) {
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

                if (listBeanX.reduceWay == 1) {
                    cutPrice += listBeanX.reduceValue * 100;
                } else {
                    cutPrice += price * (1 - listBeanX.reduceValue);
                }
            }
            mPayPrice.setText(ValueUtil.setAllPriceText(allPrice + dispatchingPrice - cutPrice, this));
        }

    }

    private void initializeInjector() {
        DaggerPayActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .payActivityModule(new PayActivityModule(PayActivity.this, this))
                .build().inject(this);
    }
}
