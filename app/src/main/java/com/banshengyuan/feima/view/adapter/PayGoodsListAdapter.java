package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.EditText;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.module.ShoppingCardListResponse;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.banshengyuan.feima.listener.MyTextWatchListener;
import com.banshengyuan.feima.utils.ValueUtil;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class PayGoodsListAdapter extends BaseQuickAdapter<ShoppingCardListResponse.ListBeanX, BaseViewHolder> {
    private final Context mContext;
    private final ImageLoaderHelper mImageLoaderHelper;

    public PayGoodsListAdapter(List<ShoppingCardListResponse.ListBeanX> notices, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_pay_goods_list, notices);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, ShoppingCardListResponse.ListBeanX item) {
        helper.addOnClickListener(R.id.adapter_pay_coupon);
        EditText editText = helper.getView(R.id.adapter_pay_suggestion);
        editText.addTextChangedListener(new MyTextWatchListener() {
            @Override
            public void onMyTextChanged(CharSequence s) {
                if (!TextUtils.isEmpty(s)) {
                    item.remark = s.toString();
                }
            }
        });

        double productPrice = 0;
        double finalPrice;
        for (ShoppingCardListResponse.ListBeanX.ListBean product : item.list) {
            productPrice += product.goods_price * product.number;
        }
        if (item.freightWay == 0) {
            if (item.shop_freight_config != null) {
                if (item.shop_freight_config.freight == 1) {
                    if (productPrice >= item.shop_freight_config.free_shipping_price) {
                        helper.setText(R.id.adapter_pay_dispatching_way, "快递 免邮");
                        if (item.reduceWay == 1) {
                            //满减
                            finalPrice = productPrice - item.reduceValue * 100;
                        } else {
                            //折扣
                            finalPrice = productPrice * item.reduceValue;
                        }
                    } else {
                        helper.setText(R.id.adapter_pay_dispatching_way, "快递" + ValueUtil.formatAmount2(item.shop_freight_config.shipping_price) + "");
                        if (item.reduceWay == 1) {
                            //满减
                            finalPrice = productPrice - item.reduceValue * 100 + item.shop_freight_config.shipping_price;
                        } else {
                            //折扣
                            finalPrice = productPrice * item.reduceValue + item.shop_freight_config.shipping_price;
                        }
                    }
                } else {
                    helper.setText(R.id.adapter_pay_dispatching_way, "快递 免邮");
                    if (item.reduceWay == 1) {
                        //满减
                        finalPrice = productPrice - item.reduceValue * 100;
                    } else {
                        //折扣
                        finalPrice = productPrice * item.reduceValue;
                    }
                }
            } else {
                helper.setText(R.id.adapter_pay_dispatching_way, "快递 免邮");
                if (item.reduceWay == 1) {
                    //满减
                    finalPrice = productPrice - item.reduceValue * 100;
                } else {
                    //折扣
                    finalPrice = productPrice * item.reduceValue;
                }
            }
        } else {
            helper.setText(R.id.adapter_pay_dispatching_way, "门店自提");
            if (item.reduceWay == 1) {
                //满减
                finalPrice = productPrice - item.reduceValue * 100;
            } else {
                //折扣
                finalPrice = productPrice * item.reduceValue;
            }
        }
        helper.setText(R.id.adapter_shopping_card_price_all, ValueUtil.formatAmount2(finalPrice));
        if (TextUtils.isEmpty(item.couponDes)) {
            if (item.user_ticket != null && item.user_ticket.size() > 0) {
                helper.setText(R.id.adapter_pay_coupon, "优惠券数量(" + item.user_ticket.size() + ")");
                helper.setEnable(R.id.adapter_pay_coupon, true);
            } else {
                helper.setText(R.id.adapter_pay_coupon, "无可用");
                helper.setEnable(R.id.adapter_pay_coupon, false);
            }
        } else {
            helper.setText(R.id.adapter_pay_coupon, item.couponDes);
        }


        RecyclerView recyclerView = helper.getView(R.id.adapter_shopping_card_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        PayGoodsItemListAdapter adapter = new PayGoodsItemListAdapter(item.list, mContext, mImageLoaderHelper);
        recyclerView.setAdapter(adapter);


        helper.setText(R.id.adapter_shopping_card_product_count, "共计" + item.list.size() + "件商品");
        helper.setText(R.id.adapter_shopping_card_shop_name, TextUtils.isEmpty(item.stoer_name) ? "未知店铺" : item.stoer_name);
    }

}
