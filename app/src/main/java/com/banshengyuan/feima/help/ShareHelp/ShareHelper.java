package com.banshengyuan.feima.help.ShareHelp;

import android.app.Activity;
import android.widget.Toast;

import com.banshengyuan.feima.entity.ShareInfo;


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
            Toast.makeText(context, "分享失败", Toast.LENGTH_SHORT).show();
        } else {
            if (type == 1) {
                WXShareHelper.registerWxApi(context);
                WXShareHelper.shareWx(context, shareInfo.linkUrl, shareInfo.title, shareInfo.content, shareInfo.imageUrl, WXShareHelper.SHARE_WX);
            } else if (type == 2) {
                WXShareHelper.registerWxApi(context);
                WXShareHelper.shareWx(context, shareInfo.linkUrl, shareInfo.title, shareInfo.content, shareInfo.imageUrl, WXShareHelper.SHARE_WX_FRIENDS);
            } else {
                Toast.makeText(context, "分享失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
