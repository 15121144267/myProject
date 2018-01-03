package com.banshengyuan.feima.help;

import android.content.Context;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by lei.he on 2017/7/3.
 * GlideLoader
 */

public class GlideLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(true).dontAnimate().error(R.mipmap.freemud_logo).into(imageView);
    }

}
