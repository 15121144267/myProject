package com.banshengyuan.feima.help.GlideHelper;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.utils.AppDeviceUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

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
                .skipMemoryCache(true)
                .placeholder(R.mipmap.freemud_logo)
                .dontAnimate()
                .into(imageView);
    }


    @Override
    public void displayImage(Context context, Object path, ImageView imageView, int res) {
        Glide.with(context).load(path)
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(res)
                .skipMemoryCache(true)
                .placeholder(res)
                .dontAnimate()
                .into(imageView);
    }

    @Override
    public void displayCircularImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path)
                .transform(new GlideCircleTransform(context))
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
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
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.mipmap.freemud_logo)
                .placeholder(R.mipmap.freemud_logo)
                .dontAnimate()
                .into(imageView);
    }

    @Override
    public void displayMatchImage(Context context, Object path, ImageView imageView) {
        SimpleTarget<GlideDrawable> simpleTarget = new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation glideAnimation) {
                int width = AppDeviceUtil.getScreenWidth(context);//屏幕的宽度
                imageView.setImageDrawable(resource);
                ViewGroup.LayoutParams linearParams = imageView.getLayoutParams();
                linearParams.height = (int) ((float) resource.getMinimumHeight() / (float) resource.getMinimumWidth() * width);// 控件的宽强制设成适应
                imageView.setLayoutParams(linearParams);
            }
        };
        Glide.with(context).load(path).placeholder(R.mipmap.freemud_logo)
                .error(R.mipmap.freemud_logo).dontAnimate().
                into(simpleTarget);
    }
}
