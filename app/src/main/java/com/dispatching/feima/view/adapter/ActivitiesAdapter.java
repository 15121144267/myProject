package com.dispatching.feima.view.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.dispatching.feima.R;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class ActivitiesAdapter extends BaseQuickAdapter<Drawable, BaseViewHolder> {
    private final Context mContext;

    public ActivitiesAdapter(List<Drawable> mList, Context context) {
        super(R.layout.adapter_activities, mList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Drawable item) {
        helper.setImageDrawable(R.id.adapter_activity_pic, item);
    }

}
