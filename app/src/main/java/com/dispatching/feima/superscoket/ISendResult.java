package com.dispatching.feima.superscoket;

/**
 * Created by lei.he on 2017/6/9.
 */

public interface ISendResult {
    void OnSendSuccess();


    void OnSendFailure(Exception e);
}
