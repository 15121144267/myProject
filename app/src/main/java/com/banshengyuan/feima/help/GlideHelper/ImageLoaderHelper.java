package com.banshengyuan.feima.help.GlideHelper;

import android.content.Context;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import javax.inject.Inject;

/**
 * Created by lei.he on 2017/7/10.
 * ImageLoaderHelper
 */

public class ImageLoaderHelper extends GlideLoader {

    @Inject
    public ImageLoaderHelper(final Context ctx) {

    }

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path)
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .error(R.mipmap.freemud_logo)
                .skipMemoryCache(true)
                .placeholder(R.mipmap.freemud_logo)
                .dontAnimate()
                .into(imageView);
    }


    @Override
    public void displayImage(Context context, Object path, ImageView imageView, int res) {
        Glide.with(context).load(path)
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .error(res)
                .skipMemoryCache(true)
                .placeholder(res)
                .dontAnimate()
                .into(imageView);
    }

    @Override
    public void displayCircularImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path)
                .thumbnail(0.5f)
                .transform(new GlideCircleTransform(context))
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .skipMemoryCache(true)
                .error(R.mipmap.freemud_logo)
                .placeholder(R.mipmap.freemud_logo)
                .dontAnimate()
                .into(imageView);
    }

    @Override
    public void displayRoundedCornerImage(Context context, Object path, ImageView imageView, Integer size) {
        Glide.with(context).load(path)
                .transform(new CenterCrop(context), new GlideRoundTransform(context, size))
                .thumbnail(0.5f)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .error(R.mipmap.freemud_logo)
                .placeholder(R.mipmap.freemud_logo)
                .dontAnimate()
                .into(imageView);
    }

    @Override
    public void displayMatchImage(Context context, Object path, ImageView imageView) {

    }
}
