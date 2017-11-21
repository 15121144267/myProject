package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.FairUnderLineResponse;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.banshengyuan.feima.view.customview.recycleviewgallery.CardAdapterHelper;

import java.util.ArrayList;
import java.util.List;


public class GallerySellerAdapter extends RecyclerView.Adapter<GallerySellerAdapter.ViewHolder> {
    private List<FairUnderLineResponse.ListBean> mList;
    private CardAdapterHelper mCardAdapterHelper;
    private ImageLoaderHelper mImageLoaderHelper;
    private Context mContext;
    private SellerClickListener mSellerClickListener;

    public void setOnItemListener(SellerClickListener sellerClickListener) {
        mSellerClickListener = sellerClickListener;
    }

    public GallerySellerAdapter(Context context, List<FairUnderLineResponse.ListBean> list, ImageLoaderHelper imageLoaderHelper) {
        if (list == null) {
            mList = new ArrayList<>();
        } else {
            mList = list;
        }
        mImageLoaderHelper = imageLoaderHelper;
        mContext = context;
        mCardAdapterHelper = new CardAdapterHelper();
        mCardAdapterHelper.setShowLeftCardWidth(15);
        mCardAdapterHelper.setPagePadding(3);
    }

    public void setNewData(List<FairUnderLineResponse.ListBean> list) {
        mList = list;
        notifyDataSetChanged();
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
        holder.mTextView.setText(mList.get(position).name);
        mImageLoaderHelper.displayRoundedCornerImage(mContext, mList.get(position).cover_img, holder.mImageView, 6);
        holder.mImageView.setOnClickListener(v -> {
            if (mSellerClickListener != null) {
                mSellerClickListener.sellerClickItemListener(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mImageView;
        public final TextView mTextView;

        public ViewHolder(final View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.adapter_seller_icon);
            mTextView = (TextView) itemView.findViewById(R.id.adapter_seller_name);
        }

    }

    public interface SellerClickListener {
        void sellerClickItemListener(int position);
    }
}
