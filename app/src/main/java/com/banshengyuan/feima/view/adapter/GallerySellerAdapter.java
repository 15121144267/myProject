package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.banshengyuan.feima.view.customview.recycleviewgallery.CardAdapterHelper;

import java.util.ArrayList;
import java.util.List;


public class GallerySellerAdapter extends  RecyclerView.Adapter<GallerySellerAdapter.ViewHolder> {
    private List<Integer> mList = new ArrayList<>();
    private CardAdapterHelper mCardAdapterHelper ;
    private ImageLoaderHelper mImageLoaderHelper;
    private Context mContext;
    public GallerySellerAdapter(Context context,List<Integer> mList, ImageLoaderHelper imageLoaderHelper) {
        this.mList = mList;
        mImageLoaderHelper = imageLoaderHelper;
        mContext = context;
        mCardAdapterHelper = new CardAdapterHelper();
        mCardAdapterHelper.setShowLeftCardWidth(15);
        mCardAdapterHelper.setPagePadding(3);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_seller_top, parent, false);
        mCardAdapterHelper.onCreateViewHolder(parent, itemView);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        mCardAdapterHelper.onBindViewHolder(holder.itemView, position, getItemCount());
        holder.mImageView.setImageResource(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mImageView;

        public ViewHolder(final View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.adapter_seller_icon);
        }

    }

}
