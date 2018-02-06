package com.banshengyuan.feima.view.customview.banner;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.banshengyuan.feima.view.PresenterControl.ProductControl;
import com.banshengyuan.feima.view.adapter.MainProductsSecondAdapter;

import java.util.List;

/**
 * Created by Sai on 15/8/4.
 * 网络图片加载例子
 */
public class NetworkImageHolderView implements Holder<List> {
    private RecyclerView mRecyclerView;
    private MainProductsSecondAdapter mAdapter;
    private ImageLoaderHelper mImageLoaderHelper;
    private ProductControl.ProductView mView;

    public NetworkImageHolderView(ImageLoaderHelper imageLoaderHelper,ProductControl.ProductView view) {
        mImageLoaderHelper = imageLoaderHelper;
        mView = view;
    }

    @Override
    public View createView(final Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.banner_recycleview, null);
        mRecyclerView = view.findViewById(R.id.demo_recycle);
        mRecyclerView.setLayoutManager(new GridLayoutManager(context, 4));
        mAdapter = new MainProductsSecondAdapter(null, context, mImageLoaderHelper);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener( (adapter, view1, position) ->    mView.switchToProductList(adapter, position));
        return view;
    }

    @Override
    public void UpdateUI(Context context, int position, List data) {
        mAdapter.setNewData(data);
    }
}
