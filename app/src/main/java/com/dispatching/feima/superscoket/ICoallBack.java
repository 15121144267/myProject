package com.dispatching.feima.superscoket;

import java.net.Socket;

/**
 * Created by lei.he on 2017/6/9.
 */

public interface ICoallBack {
    /**
     * 连接成功事件
     * **/
    public void OnSuccess(Socket client);


    /**
     * 连接失败事件
     * **/

    public void OnFailure(Exception e);
}
