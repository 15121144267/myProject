package com.banshengyuan.feima.view.adapter;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.MagicMusicResponse;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class MagicMusicAdapter extends BaseQuickAdapter<MagicMusicResponse, BaseViewHolder> {
    private final Context mContext;

    public MagicMusicAdapter(List<MagicMusicResponse> mList, Context context) {
        super(R.layout.adapter_magic_music, mList);
        mContext =  context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MagicMusicResponse item) {
        if (item == null) return;
        helper.setText(R.id.adapter_magic_music_text, item.name);
    }

}
