package com.banshengyuan.feima.view.adapter;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.MusicListResponse;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class MagicMusicHotAdapter extends BaseQuickAdapter<MusicListResponse.HotBean, BaseViewHolder> {
    private final Context mContext;

    public MagicMusicHotAdapter(List<MusicListResponse.HotBean> mList, Context context) {
        super(R.layout.adapter_magic_music, mList);
        mContext =  context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MusicListResponse.HotBean item) {
        if (item == null) return;
        helper.setText(R.id.adapter_magic_music_text, item.name);
    }

}
