package com.example.samsungfinal;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorkerRequest extends Worker {
    public final String TAG = "GET_REQUEST";

    public MyWorkerRequest(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String s = getInputData().getString("keyA");
        int i = getInputData().getInt("keyB", 0);
        Log.v(TAG, "Work is in progress "+ s + i);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return Result.failure();
        }
        Data output = new Data.Builder()
                .putString("keyC", "Hello")
                .putInt("keyD", 10)
                .build();
        Log.v(TAG, "Work finished");
        return Result.success(output);
    }
}
