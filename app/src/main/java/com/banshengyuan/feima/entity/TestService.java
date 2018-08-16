package com.banshengyuan.feima.entity;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;

/**
 * Created by lei.he on 2018/5/15.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class TestService extends JobService {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        //执行任务
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
