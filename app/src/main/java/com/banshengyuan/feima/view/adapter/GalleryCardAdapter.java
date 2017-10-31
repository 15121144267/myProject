package com.banshengyuan.feima.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.view.customview.recycleviewgallery.CardAdapterHelper;

import java.util.ArrayList;
import java.util.List;


public class GalleryCardAdapter extends  RecyclerView.Adapter<GalleryCardAdapter.ViewHolder> {
    private List<Integer> mList = new ArrayList<>();
    private CardAdapterHelper mCardAdapterHelper = new CardAdapterHelper();

    public GalleryCardAdapter(List<Integer> mList) {
        this.mList = mList;
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
            mImageView = (ImageView) itemView.findViewById(R.id.imageView);
        }

    }
   /* private final Context mContext;
    private CardAdapterHelper mCardAdapterHelper ;
    public GalleryCardAdapter(List<Integer> mList, Context context) {
        super(R.layout.adapter_gallery_card, mList);
        mCardAdapterHelper= new CardAdapterHelper();
//        mCardAdapterHelper.onCreateViewHolder(p, itemView);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper,Integer item) {
        if (item == null) return;

        mCardAdapterHelper.onCreateViewHolder(helper.get);
        ImageView imageView = helper.getView(R.id.imageView);
        mCardAdapterHelper.onBindViewHolder(imageView, helper.getLayoutPosition(), getItemCount());
    }*/

}
