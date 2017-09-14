package com.banshengyuan.feima.help.GlideHelper;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.banshengyuan.feima.R;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by lei.he on 2017/7/10.
 * ImageLoaderHelper
 */

public class ImageLoaderHelper extends GlideLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path)
                .placeholder(R.mipmap.freemud_logo)
                .error(R.mipmap.freemud_logo).into(imageView);
    }

    @Override
    public void displayCircularImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path)
                .bitmapTransform(new CropCircleTransformation(context))
                .placeholder(R.mipmap.freemud_logo)
                .error(R.mipmap.freemud_logo).into(imageView);
    }

    @Override
    public void displayRoundedCornerImage(Context context, Object path, ImageView imageView,Integer size) {
        Glide.with(context).load(path)
                .bitmapTransform(new RoundedCornersTransformation(context,size,0))
                .placeholder(R.mipmap.freemud_logo)
                .error(R.mipmap.freemud_logo).into(imageView);
    }
}
