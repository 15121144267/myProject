package com.dispatching.feima.superscoket;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

/**
 * Created by lei.he on 2017/6/9.
 */

public class SendMsgThread extends Thread implements Runnable {
    private final Handler handler;
    private OutputStream serverOutput;
    private Object msg = null;


    public SendMsgThread(Socket Client, Handler handler, Object msg) {
        try {
            if(Client.isConnected()&&!Client.isClosed()){
                serverOutput = Client.getOutputStream();
                this.msg = msg;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.handler = handler;
    }


    @Override
    public void run() {
        super.run();
        Message message = new Message();
        try {
            byte[] data = toByteArray(msg);
            serverOutput.write(data);
            serverOutput.flush();
            message.what = SocketClient.SENDSUCCESS;
        } catch (Exception ste) {
            message.what = SocketClient.SENDFAILURE;
            message.obj = ste;
        }
        handler.sendMessage(message);
    }


    public byte[] toByteArray(Object obj) {
        try {
            return obj.toString().getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
            return null;
        }
    }
}
