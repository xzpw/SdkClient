package com.prinum.sdkclient;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.annotation.WorkerThread;

import com.prinum.kmmsdksample.sdk.PublicApi;
import com.prinum.kmmsdksample.sdk.SdkData;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SdkJavaExecutor {

    private final PublicApi api;
    private final Executor executor;

    public SdkJavaExecutor(PublicApi api) {
        this.api = api;
        this.executor = Executors.newSingleThreadExecutor();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @WorkerThread
    public SdkData getSdkDataWithCoroutinesExecutor(String version) {
        CompletableFuture<SdkData> suspendResult = new CompletableFuture<>();
        api.getSdkDataSuspend(version, new SdkContinuation<SdkData>(suspendResult));
        try {
            return suspendResult.get();
        } catch (ExecutionException e) {
            Log.e("SdkJavaFacade", "Execution error: ", e);
            return null;
        } catch (InterruptedException e) {
            Log.e("SdkJavaFacade", "Interruption error: ", e);
            return null;
        }
    }
}
