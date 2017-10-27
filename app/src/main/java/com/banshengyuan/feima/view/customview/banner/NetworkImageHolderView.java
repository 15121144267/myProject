package com.banshengyuan.feima.view.customview.banner;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.view.adapter.MainProductsAdapter;

import java.util.List;

/**
 * Created by Sai on 15/8/4.
 * 网络图片加载例子
 */
public class NetworkImageHolderView implements Holder<List> {
    private RecyclerView mRecyclerView;
    private MainProductsAdapter mAdapter;
    @Override
    public View createView(final Context context) {
        View view  = LayoutInflater.from(context).inflate(R.layout.banner_recycleview,null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.demo_recycle);
        mRecyclerView.setLayoutManager(new GridLayoutManager(context,4));
        mAdapter = new MainProductsAdapter(null, context);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((adapter, view1, position) ->
                Toast.makeText(context,""+position,Toast.LENGTH_SHORT).show());
        return view;
    }

    @Override
    public void UpdateUI(Context context,int position, List data) {
        mAdapter.setNewData(data);
    }
}
