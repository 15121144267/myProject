package com.dispatching.feima.superscoket;

import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by lei.he on 2017/6/9.
 */

public class MsgReceiveThread extends Thread implements Runnable {
    Socket _Client;
    Handler handler;
    BufferedReader in;


    public MsgReceiveThread(Socket _Client, Handler handler) {
        this._Client = _Client;
        this.handler = handler;
    }


    @Override
    public void run() {
        super.run();
        try {
            String line = "";
            if (_Client != null) {
                in = new BufferedReader(new InputStreamReader(_Client.getInputStream()));
                while ((line = in.readLine()) != null) {
                    System.out.print("收到服务端消息" + line);
                    Message message = new Message();
                    message.what = SocketClient.RECEIVEMESSAGE;
                    message.obj = line;
                    handler.sendMessage(message);
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
