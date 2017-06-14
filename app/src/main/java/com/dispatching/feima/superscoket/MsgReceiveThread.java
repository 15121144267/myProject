package com.dispatching.feima.superscoket;

import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by lei.he on 2017/6/9.
 * MsgReceiveThread
 */

public class MsgReceiveThread extends Thread implements Runnable {
    private final Socket _Client;
    private final Handler handler;
    private InputStream mInputStream;

    public MsgReceiveThread(Socket _Client, Handler handler) {
        this._Client = _Client;
        this.handler = handler;
        try {
            if(_Client.isConnected()&&!_Client.isClosed()){
                mInputStream = _Client.getInputStream();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        super.run();
        try {
            String line;
            if (_Client != null && mInputStream != null) {
                BufferedReader in = new BufferedReader(new InputStreamReader(mInputStream));
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
