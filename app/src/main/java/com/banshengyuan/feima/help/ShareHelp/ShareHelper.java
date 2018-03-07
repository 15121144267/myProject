package com.banshengyuan.feima.help.ShareHelp;

import android.app.Activity;

import com.banshengyuan.feima.entity.ShareInfo;
import com.banshengyuan.feima.utils.ToastUtils;


/**
 * Created by lei.he on 2018/1/26.
 * 微信分享辅助类
 * 1：微信好友
 * 2：微信朋友圈
 */

public class ShareHelper {

    private static ShareHelper instance;

    public static ShareHelper getInstance() {
        if (instance == null) {
            synchronized (ShareHelper.class) {
                if (instance == null) {
                    instance = new ShareHelper();
                }
            }
        }
        return instance;
    }


    public void shareInfo(Activity context, ShareInfo shareInfo, Integer type) {
        if (shareInfo == null) {
            ToastUtils.showLongToast("分享失败");
        } else {
            if (type == 1) {
                WXShareHelper.registerWxApi(context);
                WXShareHelper.shareWx(context, shareInfo.linkUrl, shareInfo.title, shareInfo.content, shareInfo.imageUrl, WXShareHelper.SHARE_WX);
            } else if (type == 2) {
                WXShareHelper.registerWxApi(context);
                WXShareHelper.shareWx(context, shareInfo.linkUrl, shareInfo.title, shareInfo.content, shareInfo.imageUrl, WXShareHelper.SHARE_WX_FRIENDS);
            } else {
                ToastUtils.showLongToast("分享失败");
            }
        }
    }

}
