package com.banshengyuan.feima.help.GlideHelper;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

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
        Glide.with(context)
                .load((String) path)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (imageView == null) {
                            return false;
                        }
                        if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        }
                        ViewGroup.LayoutParams params = imageView.getLayoutParams();
                        int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
                        float scale = (float) vw / (float) resource.getIntrinsicWidth();
                        int vh = Math.round(resource.getIntrinsicHeight() * scale);
                        params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
                        imageView.setLayoutParams(params);
                        return false;
                    }
                })
                .placeholder(R.mipmap.freemud_logo)
                .error(R.mipmap.freemud_logo)
                .into(imageView);
    }
}
