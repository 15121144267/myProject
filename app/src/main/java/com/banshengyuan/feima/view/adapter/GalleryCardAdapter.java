package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.RecommendBrandResponse;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.banshengyuan.feima.view.customview.recycleviewgallery.CardAdapterHelper;

import java.util.ArrayList;
import java.util.List;


public class GalleryCardAdapter extends RecyclerView.Adapter<GalleryCardAdapter.ViewHolder> {
    private List<RecommendBrandResponse.ListBean> mList;
    private CardAdapterHelper mCardAdapterHelper = new CardAdapterHelper();
    private ImageLoaderHelper mImageLoaderHelper;
    private Context mContext;

    public GalleryCardAdapter(List<RecommendBrandResponse.ListBean> list, Context context, ImageLoaderHelper imageLoaderHelper) {
        if (mList == null) {
            mList = new ArrayList<>();
        } else {
            mList = list;
        }
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
        mCardAdapterHelper.setPagePadding(5);
        mCardAdapterHelper.setShowLeftCardWidth(25);
    }

    public void setNewData(List<RecommendBrandResponse.ListBean> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_gallery_card, parent, false);
        mCardAdapterHelper.onCreateViewHolder(parent, itemView);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        mCardAdapterHelper.onBindViewHolder(holder.itemView, position, getItemCount());
        RecommendBrandResponse.ListBean bean = mList.get(position);
        mImageLoaderHelper.displayImage(mContext,bean.cover_img,holder.mImageView);
        holder.mNameTextView.setText(bean.name);
        holder.mSummaryTextView.setText(bean.summary);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mImageView;
        private final TextView mNameTextView;
        private final TextView mSummaryTextView;
        private ViewHolder(final View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.imageView);
            mNameTextView = (TextView) itemView.findViewById(R.id.adapter_brand_fair_name);
            mSummaryTextView = (TextView) itemView.findViewById(R.id.adapter_brand_fair_summary);
        }

    }

}
