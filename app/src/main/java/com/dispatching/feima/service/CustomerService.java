package com.dispatching.feima.service;

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
import android.text.TextUtils;
import android.util.Log;

import com.dispatching.feima.DaggerApplication;
import com.dispatching.feima.R;
import com.dispatching.feima.database.OrderNotice;
import com.dispatching.feima.entity.IntentConstant;
import com.dispatching.feima.entity.NoticeInfo;
import com.dispatching.feima.entity.NoticeMessage;
import com.dispatching.feima.entity.PostManLocation;
import com.dispatching.feima.entity.PushMessageInfo;
import com.dispatching.feima.entity.SpConstant;
import com.dispatching.feima.gen.DaoSession;
import com.dispatching.feima.gen.OrderNoticeDao;
import com.dispatching.feima.superscoket.ISendResult;
import com.dispatching.feima.superscoket.SocketClient;
import com.dispatching.feima.superscoket.SuperSocketCallBack;
import com.dispatching.feima.utils.SharePreferenceUtil;
import com.dispatching.feima.utils.TimeUtil;
import com.dispatching.feima.view.activity.LoginActivity;
import com.dispatching.feima.view.activity.MainActivity;
import com.dispatching.feima.view.model.ModelTransform;
import com.dispatching.feima.view.model.ResponseData;
import com.google.gson.Gson;
import com.rabbitmq.client.ConnectionFactory;

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
    @Inject
    ConnectionFactory factory;
    @Inject
    SocketClient mSocketClient;

    private OrderNoticeDao mOrderNoticeDao;
    private String mUId;

    @Override
    public void onCreate() {
        super.onCreate();
        ((DaggerApplication) getApplication()).getApplicationComponent().inject(this);
        if (!mSocketClient.judgeClient()) {
            mSocketClient.Connection();
        }
        mOrderNoticeDao = mDaoSession.getOrderNoticeDao();
        mUId = mSharePreferenceUtil.getStringValue(SpConstant.USER_ID);
        mSocketClient.setOnReceiveListener(new SuperSocketCallBack(this));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            double mLongitude = intent.getDoubleExtra(IntentConstant.LONGITUDE, 0.0);
            double mLatitude = intent.getDoubleExtra(IntentConstant.LATITUDE, 0.0);
            if (mLongitude != 0.0 && mLatitude != 0.0) {
                PostManLocation postManLocation = new PostManLocation();
                postManLocation.Longitude = mLongitude;
                postManLocation.Latitude = mLatitude;
                postManLocation.uId = mUId;
                String locationJson = mGson.toJson(postManLocation);
                String locationInfo = "PostManSend:" + locationJson + "\r\n";
                if (mSocketClient.judgeClient()) {
                    mSocketClient.SenddData(locationInfo, new ISendResult() {
                        @Override
                        public void OnSendSuccess() {

                        }

                        @Override
                        public void OnSendFailure(Exception e) {

                        }
                    });

                } else {
                    mSocketClient.Connection();
                }
            }
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
        Log.d("myConnection",msg);
        if (!msg.trim().equals("OK")) {
            ResponseData responseData = mTransform.transformCommon(msg);
            if (responseData.resultCode == 100) {
                responseData.parseData(NoticeMessage.class);
                NoticeMessage message = (NoticeMessage) responseData.parsedData;
                List<NoticeInfo> list = message.orderslist;
                if (list != null && list.size() > 0) {
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSocketClient.closeConnection();
    }
}
