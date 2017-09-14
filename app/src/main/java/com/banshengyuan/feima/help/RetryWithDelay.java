package com.banshengyuan.feima.help;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by helei on 2017/4/13.
 * RetryWithDelay
 */

public class RetryWithDelay implements Function<Observable<? extends Throwable>, Observable<?>>{
    private final int maxRetries;
    private final int retryDelayMillis;
    private int retryCount;

    public RetryWithDelay(int maxRetries, int retryDelayMillis) {
        this.maxRetries = maxRetries;
        this.retryDelayMillis = retryDelayMillis;
    }

    @Override
    public Observable<?> apply(Observable<? extends Throwable> throwable) {
        return throwable.flatMap(e ->{
            if (++retryCount <= maxRetries) {
                return Observable.timer(retryDelayMillis,
                        TimeUnit.MILLISECONDS);
            }
            return Observable.error(e);
        });
    }

}
