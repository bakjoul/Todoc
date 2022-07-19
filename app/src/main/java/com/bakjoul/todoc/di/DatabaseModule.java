package com.bakjoul.todoc.di;

import android.app.Application;

import androidx.annotation.NonNull;

import com.bakjoul.todoc.data.AppDatabase;
import com.bakjoul.todoc.data.dao.ProjectDao;
import com.bakjoul.todoc.data.dao.TaskDao;
import com.bakjoul.todoc.utils.MainThreadExecutor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Qualifier;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@InstallIn(SingletonComponent.class)
@Module
public class DatabaseModule {

    @Provides
    @Singleton
    @IoExecutor
    public Executor provideIoExecutor() {
        return Executors.newFixedThreadPool(4);
    }

    @Provides
    @Singleton
    @MainExecutor
    public Executor provideMainExecutor() {
        return new MainThreadExecutor();
    }

    @Provides
    @Singleton
    public AppDatabase provideDatabase(Application application, @IoExecutor Executor ioExecutor) {
        return AppDatabase.getInstance(application, ioExecutor);
    }

    @Provides
    @Singleton
    public ProjectDao provideProjectDao(@NonNull AppDatabase appDatabase) {
        return appDatabase.projectDao();
    }

    @Provides
    @Singleton
    public TaskDao provideTaskDao(@NonNull AppDatabase appDatabase) {
        return appDatabase.taskDao();
    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface IoExecutor {
    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface MainExecutor {
    }
}
