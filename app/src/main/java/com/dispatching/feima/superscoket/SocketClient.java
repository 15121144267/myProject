package com.dispatching.feima.superscoket;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Created by lei.he on 2017/6/9.
 */

public class SocketClient {
    private Socket Client;
    private OutputStream serverOutput = null;
    private String host;
    private int port = -1;
    private int timeout = 3;
    private ICoallBack CoallBack = null;
    private ISocketPacket _packet = null;
    private ISendResult _Result = null;

    public SocketClient(String host, int port, int timeOut) {
        this.host = host;
        this.port = port;
        this.timeout = timeOut;
    }

    public void setOnConnectListener(ICoallBack CoallBack) {
        this.CoallBack = CoallBack;
    }


    /**
     * 连接成功
     **/
    public static final int CONNECTSUCCESS = 1;
    /**
     * 连接失败
     **/
    public static final int CONNECTFAILURE = -1;
    /**
     * 接收消息
     **/
    public static final int RECEIVEMESSAGE = 2;
    /**
     * 发送消息
     **/
    public static final int SENDMESSAGE = 3;
    /**
     * 发送成功
     **/
    public static final int SENDSUCCESS = 4;
    /**
     * 发送失败
     **/
    public static final int SENDFAILURE = -2;

    public Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == CONNECTSUCCESS) {
                new MsgReceiveThread(Client, handler).start();
            } else if (msg.what == CONNECTFAILURE) {
                Exception e1 = (Exception) msg.obj;
                if (CoallBack != null) {
                    CoallBack.OnFailure(e1);
                }
            } else if (msg.what == RECEIVEMESSAGE) {
                if (_packet != null) {
                    _packet.SocketPacket(msg.obj.toString());
                }
            } else if (msg.what == SENDMESSAGE) {
                new SendMsgThread(Client, handler, msg.obj).start();
            } else if (msg.what == SENDSUCCESS) {
                if (_Result != null) {
                    _Result.OnSendSuccess();
                }
            } else if (msg.what == SENDFAILURE) {
                if (_Result != null) {
                    Exception e = (Exception) msg.obj;
                    _Result.OnSendFailure(e);
                }
            }
            return false;
        }
    });

    public void setOnReceiveListener(ISocketPacket _packet) {
        this._packet = _packet;
    }


    /**
     * 打开连接
     *
     * @return boolean
     */
    public void Connection() {
       new Thread(socketConnection).start();
    }

   private Runnable socketConnection =  new Runnable() {
        @Override
        public void run() {
            Message message = new Message();
            try {
                Client = new Socket(host, port);
                if (CoallBack != null) {
                    CoallBack.OnSuccess(Client);
                }
                Log.d("Client", "Socket链接成功");
                message.what = CONNECTSUCCESS;
                handler.sendMessage(message);
            } catch (Exception e1) {
                message.what = CONNECTFAILURE;
                message.obj = e1;
                e1.printStackTrace();
                Log.d("Client", "连接失败");
            }
        }
    };

    /**
     * 关闭连接的输入输出流
     *
     */
    public void closeConnection() {
        try {
            if (Client != null) {
                Client.close();// 关闭socket
            }
        } catch (Exception e) {

        }
    }

    public boolean judgeClient() {
       return Client!=null;
    }

    public void SenddData(Object msg, ISendResult _Result) {
        this._Result = _Result;
        Message message = new Message();
        message.obj = msg;
        message.what = SENDMESSAGE;
        handler.sendMessage(message);
    }


    /**
     * 发送数据
     *
     * @param sndStr
     * @return boolean
     */
    public boolean SenddData(String sndStr) {
        try {
            serverOutput.write(sndStr.getBytes());
            serverOutput.flush();
            return true;
        } catch (SocketTimeoutException ste) {
            closeConnection();
            return false;
        } catch (Exception e) {
            closeConnection();
            return false;
        }
    }
}
