package com.dispatching.feima.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.dispatching.feima.DaggerApplication;
import com.dispatching.feima.R;
import com.dispatching.feima.database.OrderNotice;
import com.dispatching.feima.entity.IntentConstant;
import com.dispatching.feima.entity.RabbitRely;
import com.dispatching.feima.entity.SpConstant;
import com.dispatching.feima.gen.DaoSession;
import com.dispatching.feima.gen.OrderNoticeDao;
import com.dispatching.feima.utils.AppDeviceUtil;
import com.dispatching.feima.utils.SharePreferenceUtil;
import com.dispatching.feima.utils.TimeUtil;
import com.dispatching.feima.view.activity.LoginActivity;
import com.dispatching.feima.view.activity.NoticeCenterActivity;
import com.dispatching.feima.view.model.ModelTransform;
import com.dispatching.feima.view.model.ResponseData;
import com.google.gson.Gson;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

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
    private Channel mChannel;
    private ConnectionFactory factory;
    private String TASK_QUEUE_NAME;
    private OrderNoticeDao mOrderNoticeDao;
    private String mUId;
    @Override
    public void onCreate() {
        super.onCreate();
        ((DaggerApplication) getApplication()).getApplicationComponent().inject(this);
        mOrderNoticeDao = mDaoSession.getOrderNoticeDao();
        mUId = mSharePreferenceUtil.getStringValue(SpConstant.USER_ID);
        TASK_QUEUE_NAME = "delivery.postman." + mUId;
        initData();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("onStartCommand","1");
        if(mChannel!=null){

            double longitude = intent.getDoubleExtra(IntentConstant.LONGITUDE,0.0);
            double latitude =intent.getDoubleExtra(IntentConstant.LATITUDE,0.0);
            RabbitRely rely = new RabbitRely();
            rely.latitude = latitude;
            rely.longitude = longitude;
            rely.uId = mUId;
            String relyJson = mGson.toJson(rely);
            try {
                mChannel.exchangeDeclare("delivery.postman.coordinate", "fanout" );
                mChannel.basicPublish("delivery.postman.coordinate", "" , null , relyJson.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void initData() {
        factory = new ConnectionFactory();
        factory.setHost("115.159.73.217");
        factory.setPort(5673);
        factory.setUsername("erle.li@freemud");
        factory.setPassword("A2PH8YkkQB");
        factory.setVirtualHost("vhost-waimai");
        new Thread(networkTask).start();
    }

    Runnable networkTask = new Runnable() {
        @Override
        public void run() {
            try {
                Connection connection = factory.newConnection();
                mChannel = connection.createChannel();
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
                            showNotification(message);
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
    };

    private void showNotification(String msg) {
        Resources resources = getResources();
        Intent i;
        if (AppDeviceUtil.isRunningApp(this)) {
            if (AppDeviceUtil.getTopActivityName(this).equals(LoginActivity.class.getName())) {
                i = LoginActivity.getLoginIntent(this);
            } else {
                i = NoticeCenterActivity.getNoticeIntent(this);
            }

        } else {
            i = LoginActivity.getLoginIntent(this);
        }
        PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);
        Notification notification = new NotificationCompat.Builder(this)
                .setTicker(resources.getString(R.string.app_name))
                .setSmallIcon(R.drawable.ic_takeaway)
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

    private void insertNotice(ResponseData responseData){
        OrderNotice notice = new OrderNotice();
        notice.setOrderTime(TimeUtil.formatDate(responseData.time));
        notice.setOrderId(responseData.businessId);
        notice.setOrderFlag(0);
        mOrderNoticeDao.insert(notice);
    }
}
