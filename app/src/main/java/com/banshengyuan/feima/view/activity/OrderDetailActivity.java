package com.banshengyuan.feima.view.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aries.ui.view.radius.RadiusTextView;
import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerOrderDetailActivityComponent;
import com.banshengyuan.feima.dagger.module.OrderDetailActivityModule;
import com.banshengyuan.feima.entity.BroConstant;
import com.banshengyuan.feima.entity.MyOrdersResponse;
import com.banshengyuan.feima.entity.OrderDetailResponse;
import com.banshengyuan.feima.utils.TimeUtil;
import com.banshengyuan.feima.utils.ToolUtils;
import com.banshengyuan.feima.utils.ValueUtil;
import com.banshengyuan.feima.view.PresenterControl.OrderDetailControl;
import com.banshengyuan.feima.view.adapter.OrdersDetailAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by helei on 2017/4/27.
 * OrderDetailActivity
 */

public class OrderDetailActivity extends BaseActivity implements OrderDetailControl.OrderDetailView {
    @Inject
    OrderDetailControl.PresenterOrderDetail mPresenter;
    @BindView(R.id.middle_name)
    TextView middleName;
    @BindView(R.id.toolbar_right_icon)
    ImageView toolbarRightIcon;
    @BindView(R.id.toolbar_right_text)
    TextView toolbarRightText;
    @BindView(R.id.order_detail_shop_name)
    TextView orderDetailShopName;
    @BindView(R.id.order_detail_product_list)
    RecyclerView orderDetailProductList;
    @BindView(R.id.order_detail_total_price)
    TextView orderDetailTotalPrice;
    @BindView(R.id.order_detail_dispatch_price)
    TextView orderDetailDispatchPrice;
    @BindView(R.id.order_detail_should_pay)
    TextView orderDetailShouldPay;
    @BindView(R.id.order_detail_order_id)
    TextView orderDetailOrderId;
    @BindView(R.id.order_detail_copy_orderid)
    RadiusTextView orderDetailCopyOrderid;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.address_map_icon)
    ImageView addressMapIcon;
    @BindView(R.id.order_right_btn)
    RadiusTextView orderRightBtn;
    @BindView(R.id.order_left_btn)
    RadiusTextView orderLeftBtn;

    @BindView(R.id.receipt_user)
    TextView receiptUser;
    @BindView(R.id.receipt_phone)
    TextView receiptPhone;
    @BindView(R.id.receipt_address)
    TextView receiptAddress;
    @BindView(R.id.order_detail_create_date)
    TextView createDate;
    @BindView(R.id.order_detail_pay_date)
    TextView payDate;
    @BindView(R.id.order_detail_state_tv)
    TextView stateTv;
    @BindView(R.id.order_detail_state_iv)
    ImageView stateIv;
    @BindView(R.id.order_address_layout)
    LinearLayout orderAddressLayout;
    @BindView(R.id.order_zt_layout)
    RelativeLayout orderZtLayout;
    @BindView(R.id.order_unlinepay_layout)
    LinearLayout orderUnlinepayLayout;
    @BindView(R.id.order_detail_linepay_center_layout)
    LinearLayout linepayCenterLayout;
    //自提
    @BindView(R.id.order_detail_zt_image)
    ImageView orderDetailZtImage;
    @BindView(R.id.order_detail_zt_code)
    TextView orderDetailZtCode;
    //线下支付
    @BindView(R.id.unlinepay_image)
    ImageView unlinepayImage;
    @BindView(R.id.unlinepay_storename)
    TextView unlinepayStorename;
    @BindView(R.id.order_detail_unlinepay_phone)
    ImageView orderSetailUnlinepayPhone;
    @BindView(R.id.unlinepay_total_price)
    TextView unlinepayTotalPrice;
    @BindView(R.id.unlinepay_discount_price)
    TextView unlinepayDiscountPrice;
    @BindView(R.id.unlinepay_final_price)
    TextView unlinepayFinalPrice;


    private String mOrderSn;
    private String mToken;
    List<OrderDetailResponse.GoodsListBean.ProductBean> mList = new ArrayList<>();
    ArrayList<MyOrdersResponse.ListBean.ProductBean> mList1 = new ArrayList<>();//跳转评论需要订单entity
    private OrdersDetailAdapter adapter = null;
    private String storePhone = "";//商家电话
    private OrderDetailResponse.InfoBean infoBean;
    private OrderDetailResponse.GoodsListBean goodsListBean;
    private boolean isFreshOrder = false;


    public static Intent getOrderDetailIntent(Context context, String order_sn) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra("order_sn", order_sn);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetail);
        ButterKnife.bind(this);
        supportActionBar(mToolbar, true);
        initializeInjector();
        middleName.setText("订单详情");
        initView();
        initData();
    }

    @Override
    void onReceivePro(Context context, Intent intent) {
        if (intent.getAction().equals(BroConstant.ORDER_TO_PAY_OrderDetailActivity) || intent.getAction().equals(BroConstant.ORDER_TO_ORDERDETAIL)) {
            isFreshOrder = true;
            mPresenter.requestOrderDetailInfo(mOrderSn, mToken);
        }
        super.onReceivePro(context, intent);
    }

    @Override
    void addFilter() {
        super.addFilter();
        mFilter.addAction(BroConstant.ORDER_TO_PAY_OrderDetailActivity);
        mFilter.addAction(BroConstant.ORDER_TO_ORDERDETAIL);
    }

    private void initData() {
        mToken = mBuProcessor.getUserToken();
        mPresenter.requestOrderDetailInfo(mOrderSn, mToken);
    }

    private void parseIntent() {
        mOrderSn = getIntent().getStringExtra("order_sn");
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
        parseIntent();
        orderDetailProductList.setLayoutManager(new LinearLayoutManager(this));
        orderDetailProductList.setNestedScrollingEnabled(false);
        adapter = new OrdersDetailAdapter(null, this, mImageLoaderHelper);
        orderDetailProductList.setAdapter(adapter);
    }

    private void initializeInjector() {
        DaggerOrderDetailActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .orderDetailActivityModule(new OrderDetailActivityModule(OrderDetailActivity.this, this))
                .build().inject(this);
    }

    @OnClick({R.id.call_business_iv, R.id.order_detail_copy_orderid, R.id.order_right_btn, R.id.order_left_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.call_business_iv:
                try {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + storePhone));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (Exception e) {
                    showToast("该设备暂无通话功能");
                }
                break;
            case R.id.order_detail_copy_orderid:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                String orderSnTv = orderDetailOrderId.getText().toString();
                if (!TextUtils.isEmpty(orderSnTv)) {
                    cm.setPrimaryClip(new ClipData(ClipData.newPlainText(null, orderSnTv)));
                    orderDetailCopyOrderid.setText("已复制");
                    showToast("已复制到粘贴板");
                }
                break;
            case R.id.order_right_btn:
                if (infoBean.getOrder_type() == 1) {
                    //线上
                    if (infoBean.getPay_status() == 1) {//立即付款
                        startActivity(FinalPayActivity.getIntent(OrderDetailActivity.this, mOrderSn, infoBean.getOrder_type(), "OrderDetailActivity"));
                    } else if (infoBean.getPay_status() == 2) {//确认收货
                        mPresenter.requestConfirmOrder(mOrderSn, mToken);
                    } else if (infoBean.getPay_status() == 3) {//提醒发货
                        mPresenter.requestRemindSendGoods(mOrderSn, mToken);
                    } else if (infoBean.getPay_status() == 4) {//去评价
                        if (infoBean.getIs_evaluate() == 0) {
                            for (OrderDetailResponse.GoodsListBean.ProductBean bean : mList) {
                                MyOrdersResponse.ListBean.ProductBean productBean = new MyOrdersResponse.ListBean.ProductBean();
                                productBean.setId(bean.getId());
                                productBean.setName(bean.getGoods_name());
                                productBean.setGoods_id(bean.getGoods_id());
                                productBean.setPrice(bean.getGoods_price());
                                productBean.setNumber(bean.getNumber());
                                productBean.setCover_img(bean.getGoods_img());
                                mList1.add(productBean);
                            }
                            startActivity(CommentActivity.getIntent(OrderDetailActivity.this, mList1, infoBean.getSn()));
                        } else {//删除
                            mPresenter.requestDeleteOrder(mOrderSn, mToken);
                        }
                    } else if (infoBean.getPay_status() == 5) {//删除
                        mPresenter.requestDeleteOrder(mOrderSn, mToken);
                    }
                } else if (infoBean.getOrder_type() == 2) {
                    //2自提订单
                    if (infoBean.getPay_status() == 1) {//立即付款
                        startActivity(FinalPayActivity.getIntent(OrderDetailActivity.this, mOrderSn, infoBean.getOrder_type(), "OrderDetailActivity"));
                    } else if (infoBean.getPay_status() == 2) {//确认收货
                        mPresenter.requestConfirmOrder(mOrderSn, mToken);
                    } else if (infoBean.getPay_status() == 3) {//确认收货
                        mPresenter.requestConfirmOrder(mOrderSn, mToken);
                    } else if (infoBean.getPay_status() == 4) {//去评价
                        if (infoBean.getIs_evaluate() == 0) {
                            for (OrderDetailResponse.GoodsListBean.ProductBean bean : mList) {
                                MyOrdersResponse.ListBean.ProductBean productBean = new MyOrdersResponse.ListBean.ProductBean();
                                productBean.setId(bean.getId());
                                productBean.setName(bean.getGoods_name());
                                productBean.setGoods_id(bean.getGoods_id());
                                productBean.setPrice(bean.getGoods_price());
                                productBean.setNumber(bean.getNumber());
                                productBean.setCover_img(bean.getGoods_img());
                                mList1.add(productBean);
                            }
                            startActivity(CommentActivity.getIntent(OrderDetailActivity.this, mList1, infoBean.getSn()));
                        } else {//删除
                            mPresenter.requestDeleteOrder(mOrderSn, mToken);
                        }
                    } else if (infoBean.getPay_status() == 5) {//删除
                        mPresenter.requestDeleteOrder(mOrderSn, mToken);
                    }
                } else if (infoBean.getOrder_type() == 3) {
                    //3线下收款订单
                    //删除
                    mPresenter.requestDeleteOrder(mOrderSn, mToken);
                }
                break;
            case R.id.order_left_btn:
                if (infoBean.getOrder_type() == 1) {
                    //线上
                    if (infoBean.getPay_status() == 1) {//取消订单
                        mPresenter.requestCancelOrder(mOrderSn, mToken);
                    } else if (infoBean.getPay_status() == 2) {//false
                    } else if (infoBean.getPay_status() == 3) {//false
                    } else if (infoBean.getPay_status() == 4) {//删除
                        mPresenter.requestDeleteOrder(mOrderSn, mToken);
                    } else if (infoBean.getPay_status() == 5) {//删除
                        mPresenter.requestDeleteOrder(mOrderSn, mToken);
                    }
                } else if (infoBean.getOrder_type() == 2) {
                    //2自提订单
                    if (infoBean.getPay_status() == 1) {//取消订单
                        mPresenter.requestCancelOrder(mOrderSn, mToken);
                    } else if (infoBean.getPay_status() == 2) {//false
                    } else if (infoBean.getPay_status() == 3) {//false
                    } else if (infoBean.getPay_status() == 4) {//删除
                        mPresenter.requestDeleteOrder(mOrderSn, mToken);
                    } else if (infoBean.getPay_status() == 5) {//删除
                        mPresenter.requestDeleteOrder(mOrderSn, mToken);
                    }
                } else if (infoBean.getOrder_type() == 3) {
                    //3线下收款订单
                    // false
                }
                break;
        }
    }

    @Override
    public void getCancelOrderSuccess() {
        showToast("取消成功");
        isFreshOrder = true;
        mPresenter.requestOrderDetailInfo(mOrderSn, mToken);
    }

    @Override
    public void getComfirmOrderSuccess() {
        //确认收货
        isFreshOrder = true;
        mPresenter.requestOrderDetailInfo(mOrderSn, mToken);
    }

    @Override
    public void getDeleteOrderSuccess() {
        isFreshOrder = true;
        showLoading("删除成功");
        refreshOrder();
        finish();
    }

    @Override
    public void loadFail(Throwable throwable) {
    }

    @Override
    public void getOrderDetailInfoSuccess(OrderDetailResponse orderDetailResponse) {
        if (orderDetailResponse != null) {
            mList = orderDetailResponse.getGoods_list().getProduct();
            infoBean = orderDetailResponse.getInfo();
            goodsListBean = orderDetailResponse.getGoods_list();

            if (infoBean.getOrder_type() == 1) {//线上订单
                orderAddressLayout.setVisibility(View.VISIBLE);
                orderZtLayout.setVisibility(View.GONE);
                orderUnlinepayLayout.setVisibility(View.GONE);

            } else if (infoBean.getOrder_type() == 2) {//门店自提
                orderAddressLayout.setVisibility(View.GONE);
                orderZtLayout.setVisibility(View.VISIBLE);
                orderUnlinepayLayout.setVisibility(View.GONE);
                String code = infoBean.getSelffetch_code();
                if (!TextUtils.isEmpty(code)) {
                    orderDetailZtCode.setText(code);
                    //生成二维码
                    getCodeBitmap(code);
                }

            } else if (infoBean.getOrder_type() == 3) {//线下付款
                orderAddressLayout.setVisibility(View.GONE);
                orderZtLayout.setVisibility(View.GONE);
                linepayCenterLayout.setVisibility(View.GONE);
                orderUnlinepayLayout.setVisibility(View.VISIBLE);

                if (!TextUtils.isEmpty(goodsListBean.getCover_image())) {
                    mImageLoaderHelper.displayCircularImage(this, goodsListBean.getCover_image(), unlinepayImage);
                }
                unlinepayStorename.setText(goodsListBean.getStore_name());

                Integer totalPrice = infoBean.getTotal_fee();
                Integer transPrice = infoBean.getFreight();
                Integer shouldPrice = infoBean.getPayed();

                unlinepayTotalPrice.setText("￥" + ValueUtil.formatAmount4(totalPrice));

                unlinepayDiscountPrice.setText("-￥" + ValueUtil.formatAmount4(transPrice));

                unlinepayFinalPrice.setText("￥" + ValueUtil.formatAmount4(shouldPrice));
            }
            adapter.setNewData(mList);

            receiptUser.setText(infoBean.getMember_name());
            receiptPhone.setText(infoBean.getMember_mobile());
            String address = infoBean.getMember_province() + infoBean.getMember_city() + infoBean.getMember_area() +
                    infoBean.getMember_street() + infoBean.getMember_address();


            orderDetailShopName.setText(goodsListBean.getStore_name());
            storePhone = goodsListBean.getStore_mobile();
            orderDetailOrderId.setText(infoBean.getSn());
            createDate.setText("创建时间：" + TimeUtil.transferLongToDate(TimeUtil.TIME_YYMMDD_HHMMSS, (long) infoBean.getCreate_time()));
            payDate.setText("成交时间：" + TimeUtil.transferLongToDate(TimeUtil.TIME_YYMMDD_HHMMSS, (long) infoBean.getDeal_time()));
            receiptAddress.setText(address);
            stateTv.setText(infoBean.getStatus());
            /**
             *  /**
             * 1.等待买家付款（待付款）
             2.等待买家收货（已发货或待收货、待自提）
             3.等待卖家发货（待发货或已付款）自提订单无此状态
             4.交易成功（待评价或已完成）
             5.交易关闭（已取消）
             * pay_status        1 待付款 2已付款
             * 	deliver_status   1 待发货 2已发货
             */
            if (infoBean.getOrder_type() == 1) {
                if (infoBean.getPay_status() == 1) {
                    stateIv.setImageResource(R.mipmap.order_detail_no_pay);
                    orderLeftBtn.setText("取消订单");
                    orderRightBtn.setText("立即付款");
                } else if (infoBean.getPay_status() == 2) {
                    stateIv.setImageResource(R.mipmap.order_detail_no_receipt);
                    orderLeftBtn.setVisibility(View.GONE);
                    orderRightBtn.setText("确认收货");
                } else if (infoBean.getPay_status() == 3) {
                    stateIv.setImageResource(R.mipmap.order_detail_no_deliver);
                    orderLeftBtn.setVisibility(View.GONE);
                    orderRightBtn.setText("提醒发货");
                } else if (infoBean.getPay_status() == 4) {
                    stateIv.setImageResource(R.mipmap.order_detail_success);
                    if (infoBean.getIs_evaluate() == 0) {
                        orderLeftBtn.setText("删除订单");
                        orderRightBtn.setText("去评价");
                    } else {
                        orderLeftBtn.setVisibility(View.GONE);
                        orderRightBtn.setText("删除订单");
                    }
                } else if (infoBean.getPay_status() == 5) {
                    stateIv.setImageResource(R.mipmap.order_detail_close);
                    orderLeftBtn.setVisibility(View.GONE);
                    orderRightBtn.setText("删除订单");
                }
            } else if (infoBean.getOrder_type() == 2) {//2自提订单
                if (infoBean.getPay_status() == 1) {
                    stateIv.setImageResource(R.mipmap.order_detail_no_pay);
                    orderLeftBtn.setText("取消订单");
                    orderRightBtn.setText("立即付款");
                } else if (infoBean.getPay_status() == 2) {
                    stateIv.setImageResource(R.mipmap.order_detail_no_receipt);
                    orderLeftBtn.setVisibility(View.GONE);
                    orderRightBtn.setText("确认收货");
                } else if (infoBean.getPay_status() == 3) {
                    stateIv.setImageResource(R.mipmap.order_detail_no_deliver);
                    orderLeftBtn.setVisibility(View.GONE);
                    orderRightBtn.setText("确认收货");
                } else if (infoBean.getPay_status() == 4) {
                    stateIv.setImageResource(R.mipmap.order_detail_success);
                    if (infoBean.getIs_evaluate() == 0) {
                        orderLeftBtn.setText("删除订单");
                        orderRightBtn.setText("去评价");
                    } else {
                        orderLeftBtn.setVisibility(View.GONE);
                        orderRightBtn.setText("删除订单");
                    }
                } else if (infoBean.getPay_status() == 5) {
                    stateIv.setImageResource(R.mipmap.order_detail_close);
                    orderLeftBtn.setVisibility(View.GONE);
                    orderRightBtn.setText("删除订单");
                }
            } else if (infoBean.getOrder_type() == 3) {//3线下收款订单
                stateIv.setImageResource(R.mipmap.order_detail_no_receipt);
                orderLeftBtn.setVisibility(View.GONE);
                orderRightBtn.setText("删除订单");
            }

            Integer totalPrice = infoBean.getTotal_fee();
            Integer transPrice = infoBean.getFreight();
            Integer shouldPrice = infoBean.getPayed();

            String priceTotal = "￥" + ValueUtil.formatAmount4(totalPrice);
            orderDetailTotalPrice.setText(priceTotal);

            orderDetailDispatchPrice.setText("￥" + ValueUtil.formatAmount4(transPrice));

            orderDetailShouldPay.setText("￥" + ValueUtil.formatAmount4(shouldPrice));

        }
    }

    private void getCodeBitmap(String code) {
        Bitmap qrBitmap = ToolUtils.generateBitmap(code, 400, 400);
        orderDetailZtImage.setImageBitmap(qrBitmap);
    }

    @Override
    public void onBackPressed() {
        refreshOrder();
        super.onBackPressed();
    }

    private void refreshOrder() {
        if (isFreshOrder) {//刷新我的订单界面
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(BroConstant.ORDER_TO_ORDERDETAIL));
        }
    }
}
