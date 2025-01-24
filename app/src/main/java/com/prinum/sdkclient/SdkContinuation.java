package com.prinum.sdkclient;


import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;

public class SdkContinuation<T> implements Continuation<T> {
    private final CompletableFuture<T> future;

    public SdkContinuation(CompletableFuture<T> future) {
        this.future = future;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void resumeWith(@NotNull Object o) {
        if (o instanceof Result.Failure) {
            future.completeExceptionally(((Result.Failure) o).exception);
        } else
            future.complete((T) o);
    }

    @NonNull
    @Override
    public CoroutineContext getContext() {
        return EmptyCoroutineContext.INSTANCE;
    }
}
