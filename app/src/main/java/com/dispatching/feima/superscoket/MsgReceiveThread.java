package com.dispatching.feima.superscoket;

import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.Socket;

/**
 * Created by lei.he on 2017/6/9.
 */

public class MsgReceiveThread extends Thread implements Runnable {
    Socket _Client;
    Handler handler;
    BufferedReader in;
    InputStream mInputStream;

    public MsgReceiveThread(Socket _Client, Handler handler) {
        this._Client = _Client;
        this.handler = handler;
        try {
            mInputStream = _Client.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        super.run();
        try {
            String line = "";
            Reader reader;
            if (_Client != null && mInputStream != null) {
                in = new BufferedReader(new InputStreamReader(mInputStream));
                while ((line = in.readLine()) != null) {
                    line = line+"\n";
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
