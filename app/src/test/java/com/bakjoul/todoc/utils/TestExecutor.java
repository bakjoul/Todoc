package com.bakjoul.todoc.utils;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;

public class TestExecutor implements Executor {
    @Override
    public void execute(@NonNull Runnable runnable) {
        runnable.run();
    }
}
