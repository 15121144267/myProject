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
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.mipmap.freemud_logo)
                .placeholder(R.mipmap.freemud_logo)
                .into(imageView);
    }

    @Override
    public void displayImage(Context context, Object path, ImageView imageView, int res) {
        Glide.with(context).load(path)
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(res)
                .placeholder(res)
                .into(imageView);
    }

    @Override
    public void displayCircularImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path)
                .transform(new GlideCircleTransform(context))
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.mipmap.freemud_logo)
                .placeholder(R.mipmap.freemud_logo)
                .into(imageView);
    }

    @Override
    public void displayRoundedCornerImage(Context context, Object path, ImageView imageView, Integer size) {
        Glide.with(context).load(path)
                .transform(new CenterCrop(context), new GlideRoundTransform(context, size))
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.mipmap.freemud_logo)
                .placeholder(R.mipmap.freemud_logo)
                .into(imageView);
    }
}
