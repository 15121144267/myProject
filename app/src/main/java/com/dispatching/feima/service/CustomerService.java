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

import com.dispatching.feima.DaggerApplication;
import com.dispatching.feima.R;
import com.dispatching.feima.database.OrderNotice;
import com.dispatching.feima.entity.IntentConstant;
import com.dispatching.feima.entity.SpConstant;
import com.dispatching.feima.gen.DaoSession;
import com.dispatching.feima.gen.OrderNoticeDao;
import com.dispatching.feima.superscoket.ICoallBack;
import com.dispatching.feima.superscoket.SocketClient;
import com.dispatching.feima.utils.SharePreferenceUtil;
import com.dispatching.feima.utils.TimeUtil;
import com.dispatching.feima.view.activity.LoginActivity;
import com.dispatching.feima.view.activity.MainActivity;
import com.dispatching.feima.view.model.ModelTransform;
import com.dispatching.feima.view.model.ResponseData;
import com.google.gson.Gson;
import com.rabbitmq.client.ConnectionFactory;

import java.net.Socket;

import javax.inject.Inject;


/**
 * Created by helei on 2017/5/2.
 * CustomerService
 */

public class CustomerService extends Service implements ICoallBack{

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
    private double mLongitude;
    private double mLatitude;

    @Override
    public void onCreate() {
        super.onCreate();
        ((DaggerApplication) getApplication()).getApplicationComponent().inject(this);
        mSocketClient.Connection();
        mSocketClient.setOnConnectListener(this);
        mOrderNoticeDao = mDaoSession.getOrderNoticeDao();
        mUId = mSharePreferenceUtil.getStringValue(SpConstant.USER_ID);

    }

    @Override
    public void OnSuccess(Socket client) {

    }

    @Override
    public void OnFailure(Exception e) {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String uId = mSharePreferenceUtil.getStringValue(SpConstant.USER_ID);
        if (!uId.equals(mUId)) {
           /* try {
                mUId = uId;
                TASK_QUEUE_NAME = "delivery.postman." + mUId;
                mChannel.close();
                mConnection.close();
                new Thread(networkTask).start();
            } catch (Exception e) {
                e.printStackTrace();
            }*/
        }
        if (intent != null) {
            mLongitude = intent.getDoubleExtra(IntentConstant.LONGITUDE, 0.0);
            mLatitude = intent.getDoubleExtra(IntentConstant.LATITUDE, 0.0);
//            new Thread(mSendRunnable).start();
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

  /*  private final Runnable networkTask = new Runnable() {
        @Override
        public void run() {
            try {
                mConnection = factory.newConnection();
                mChannel = mConnection.createChannel();
                mChannel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
                Consumer consumer = new DefaultConsumer(mChannel) {
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope,
                                               AMQP.BasicProperties properties, byte[] body)
                            throws IOException {
                        String message = new String(body, "UTF-8");
                        ResponseData responseData = mTransform.transformNotice(message);
                        insertNotice(responseData);
                        try {
                            String noticeMessage = "单号：" + responseData.businessId;
                            showNotification(noticeMessage);
                        } catch (Exception e) {
                            mChannel.abort();
                        } finally {
                            try {
                                mChannel.basicAck(envelope.getDeliveryTag(), false);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
                try {
                    mChannel.basicConsume(TASK_QUEUE_NAME, false, consumer);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };*/

  /*  private final Runnable mSendRunnable = new Runnable() {
        @Override
        public void run() {
            if (factory != null) {
                RabbitRely rely = new RabbitRely();
                rely.latitude = mLatitude;
                rely.longitude = mLongitude;
                rely.uId = mUId;
                String relyJson = mGson.toJson(rely);
                try {
                    if (mConnection2 == null) {
                        mConnection2 = factory.newConnection();
                    }
                    if (mChannel2 == null) {
                        mChannel2 = mConnection2.createChannel();
                    }

                    mChannel2.queueDeclare("delivery.postman.coordinate", false, false, false, null);
                    mChannel2.basicPublish("", "delivery.postman.coordinate", null, relyJson.getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };*/

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

    private void insertNotice(ResponseData responseData) {
        OrderNotice notice = new OrderNotice();
        notice.setOrderTime(TimeUtil.formatDate(responseData.time));
        notice.setOrderId(responseData.businessId);
        notice.setOrderFlag(0);
        notice.setOrderChannel(responseData.channel);
        mOrderNoticeDao.insert(notice);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /*try {
            mChannel.close();
            mConnection.close();
            mChannel2.close();
            mConnection2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
