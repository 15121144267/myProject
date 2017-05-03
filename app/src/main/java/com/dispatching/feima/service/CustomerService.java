package com.dispatching.feima.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;


/**
 * Created by helei on 2017/5/2.
 */

public class CustomerService extends IntentService {
    private static final String TAG = CustomerService.class.getSimpleName();
    private static final String ACTION = "com.dispatching.customerservice";
    private final static String TASK_QUEUE_NAME = "rabbitMQ.test";
    private Channel mChannel;
    public CustomerService(){
        super(TAG);
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, CustomerService.class);
        intent.setAction(ACTION);
        return intent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            //初始化rabbitMq
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            factory.setUsername("Test");
            factory.setPassword("Test");
            factory.setVirtualHost("TestV1");
            Connection connection = factory.newConnection();
            mChannel = connection.createChannel();
            mChannel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
            mChannel.basicQos(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Consumer consumer = new DefaultConsumer(mChannel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Customer Received '" + message + "'");
                try {
                    //doWork(message);
                }catch (Exception e){
                    mChannel.abort();
                }finally {
                    mChannel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };
        try {
            mChannel.basicConsume(TASK_QUEUE_NAME, false, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
