package com.banshengyuan.feima.help.GlideHelper;

import android.content.Context;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

/**
 * Created by lei.he on 2017/7/10.
 * ImageLoaderHelper
 */

public class ImageLoaderHelper extends GlideLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path)
                .diskCacheStrategy( DiskCacheStrategy.NONE )
                .skipMemoryCache( true )
                .error(R.mipmap.freemud_logo).into(imageView);
    }

    @Override
    public void displayCircularImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path)
                .transform(new GlideCircleTransform(context))
                .diskCacheStrategy( DiskCacheStrategy.NONE )
                .skipMemoryCache( true )
                .error(R.mipmap.freemud_logo).into(imageView);
    }

    @Override
    public void displayRoundedCornerImage(Context context, Object path, ImageView imageView, Integer size) {
        Glide.with(context).load(path)
                .transform(new CenterCrop(context), new GlideRoundTransform(context, size))
                .diskCacheStrategy( DiskCacheStrategy.NONE )
                .skipMemoryCache( true )
                .error(R.mipmap.freemud_logo).into(imageView);
    }
}
