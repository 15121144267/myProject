package com.banshengyuan.feima.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

import com.banshengyuan.feima.DaggerApplication;
import com.banshengyuan.feima.R;
import com.banshengyuan.feima.database.OrderNotice;
import com.banshengyuan.feima.entity.BroConstant;
import com.banshengyuan.feima.entity.IntentConstant;
import com.banshengyuan.feima.entity.NoticeInfo;
import com.banshengyuan.feima.entity.NoticeMessage;
import com.banshengyuan.feima.entity.PushMessageInfo;
import com.banshengyuan.feima.entity.SpConstant;
import com.banshengyuan.feima.gen.DaoSession;
import com.banshengyuan.feima.gen.OrderNoticeDao;
import com.banshengyuan.feima.utils.SharePreferenceUtil;
import com.banshengyuan.feima.utils.TimeUtil;
import com.banshengyuan.feima.view.activity.LoginActivity;
import com.banshengyuan.feima.view.activity.MainActivity;
import com.banshengyuan.feima.view.model.ModelTransform;
import com.banshengyuan.feima.view.model.ResponseData;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;


/**
 * Created by helei on 2017/5/2.
 * CustomerService
 */

public class CustomerService extends Service {

    public static final String ACTION = "com.dispatching.customerservice";

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, CustomerService.class);
        intent.setAction(ACTION);
        return intent;
    }

    @Inject
    SharePreferenceUtil mSharePreferenceUtil;
    @Inject
    Gson mGson;
    @Inject
    DaoSession mDaoSession;
    @Inject
    ModelTransform mTransform;


    private OrderNoticeDao mOrderNoticeDao;
    private String mUId;

    @Override
    public void onCreate() {
        super.onCreate();
        ((DaggerApplication) getApplication()).getApplicationComponent().inject(this);

        mOrderNoticeDao = mDaoSession.getOrderNoticeDao();
        mUId = mSharePreferenceUtil.getStringValue(SpConstant.USER_ID);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            double mLongitude = intent.getDoubleExtra(IntentConstant.LONGITUDE, 0.0);
            double mLatitude = intent.getDoubleExtra(IntentConstant.LATITUDE, 0.0);

        }
        return START_STICKY;
    }

    /**
     * 连接成功：1
     * 短信发送成功:99
     * 订单推送成功:100
     * 只处理订单推送
     *
     */
    public void transformSuperSocketInfo(String msg) {
        if (!msg.trim().equals("OK")) {
            ResponseData responseData = mTransform.transformCommon(msg);
            if (responseData.resultCode == 100) {
                responseData.parseData(NoticeMessage.class);
                NoticeMessage message = (NoticeMessage) responseData.parsedData;
                List<NoticeInfo> list = message.orderslist;
                if (list != null && list.size() > 0) {
                    LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(BroConstant.PENDING_DELIVERY));
                    for (NoticeInfo noticeInfo : list) {
                        PushMessageInfo info = noticeInfo.PushOrderInfo;
                        insertNotice(info);
                        String noticeMessage = "单号：" + noticeInfo.PushOrderInfo.businessId;
                        showNotification(noticeMessage);
                    }
                }
            }
        }

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private void showNotification(String msg) {
        Intent i;
        if (TextUtils.isEmpty(mUId)) {
            i = LoginActivity.getLoginIntent(this);
        } else {
            i = MainActivity.getMainIntent(this);
        }
        PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);
        Notification notification = new NotificationCompat.Builder(this)
                .setTicker(getResources().getString(R.string.app_name))
                .setSmallIcon(R.mipmap.freemud_logo)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText(msg)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .setVibrate(new long[]{0, 100, 1000, 300})
                .setDefaults(Notification.DEFAULT_SOUND)
                .setFullScreenIntent(pi, true)
                .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .build();
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(0, notification);
    }

    private void insertNotice(PushMessageInfo info) {
        OrderNotice notice = new OrderNotice();
        notice.setOrderTime(TimeUtil.formatDate(info.distributeTime));
        notice.setOrderId(info.businessId);
        notice.setOrderFlag(0);
        notice.setOrderChannel(info.channel);
        mOrderNoticeDao.insertOrReplace(notice);
    }

}
