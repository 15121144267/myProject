package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.MyCoupleResponse;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.banshengyuan.feima.utils.TimeUtil;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class CouponAdapter extends BaseQuickAdapter<MyCoupleResponse.ListBean, BaseViewHolder> {
    private final Context mContext;
    private ImageLoaderHelper mImageLoaderHelper;

    public CouponAdapter(List<MyCoupleResponse.ListBean> mList, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_coupon_item, mList);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, MyCoupleResponse.ListBean item) {
        ImageView imageView = helper.getView(R.id.adapter_coupon_image);
        switch (item.getStatus()) {
            case 1:
                mImageLoaderHelper.displayImage(mContext,R.mipmap.nouse_couple_bg,imageView);
                break;
            case 2:
                mImageLoaderHelper.displayImage(mContext,R.mipmap.used_couple_bg,imageView);
                break;
            case 3:
                mImageLoaderHelper.displayImage(mContext,R.mipmap.expired_couple_bg,imageView);
                break;
            default:
                mImageLoaderHelper.displayImage(mContext,R.mipmap.expired_couple_bg,imageView);
                break;
        }

        helper.setText(R.id.adapter_couple_name, item.getName());
        helper.setText(R.id.adapter_couple_usetime, "使用期限：" + TimeUtil.transferLongToDate(TimeUtil.TIME_MMDD_CH, (long) item.getExpire_start_time()) +
                "-" + TimeUtil.transferLongToDate(TimeUtil.TIME_MMDD_CH, (long) item.getExpire_end_time()));
        helper.setText(R.id.adapter_couple_limit, "仅可在店铺" + item.getStore_name() + "中使用");

        if (item.getType() == 1) {
            //券类型 1满减 2折扣
            helper.setText(R.id.adapter_couple_price, "￥" + item.getEnd_val());
        } else {
            helper.setText(R.id.adapter_couple_price, item.getEnd_val()*10 + "折");
        }
    }
}
