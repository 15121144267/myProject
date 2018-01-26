package com.banshengyuan.feima.help.ShareHelp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.widget.Toast;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.R;
import com.banshengyuan.feima.utils.WXUtil;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.net.URL;

import javax.inject.Singleton;

/**
 * Created by lei.he on 2018/1/26.
 * 微信分享辅助类
 */

@Singleton
class WXShareHelper {
    static final int SHARE_WX = SendMessageToWX.Req.WXSceneSession;//微信
    static final int SHARE_WX_FRIENDS = SendMessageToWX.Req.WXSceneTimeline;//朋友圈
    private static IWXAPI api;

    static IWXAPI registerWxApi(Context context) {
        api = WXAPIFactory.createWXAPI(context, BuildConfig.WX_APP_ID, false);
        api.registerApp(BuildConfig.WX_APP_ID);
        return api;
    }

    static void shareWx(final Context context, final String url, final String title, final String content, final String imgUrl, final int shareTag) {

        if (!api.isWXAppInstalled()) {
            Toast.makeText(context, "当前没有安装微信", Toast.LENGTH_SHORT).show();
            return;
        }
        if (shareTag == SHARE_WX_FRIENDS && !api.isWXAppSupportAPI()) {
            Toast.makeText(context, "当前微信版本过低不支持分享朋友圈", Toast.LENGTH_SHORT).show();
            return;
        }
        new Thread() {
            @Override
            public void run() {
                try {
                    Bitmap thumbBmp;
                    Bitmap bitmap = null;
                    if (!TextUtils.isEmpty(imgUrl)) {
                        thumbBmp = BitmapFactory.decodeStream(new URL(imgUrl).openStream());
                        bitmap = Bitmap.createScaledBitmap(thumbBmp, 100, 100, true);
                        thumbBmp.recycle();
                        if (bitmap == null) {
                            Toast.makeText(context, "分享失败", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    WXWebpageObject webPage = new WXWebpageObject();
                    webPage.webpageUrl = url;
                    WXMediaMessage msg = new WXMediaMessage(webPage);
                    msg.title = title;
                    msg.description = content;
                    if (bitmap == null) {
                        msg.thumbData = WXUtil.bmpToByteArray(BitmapFactory.decodeResource(context.getResources(),
                                R.mipmap.freemud_logo_new), true);
                    } else {
                        msg.thumbData = WXUtil.bmpToByteArray(bitmap, true);
                    }
                    SendMessageToWX.Req req = new SendMessageToWX.Req();
                    req.transaction = System.currentTimeMillis() + "";
                    req.message = msg;
                    req.scene = shareTag;
                    api.sendReq(req);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
