package com.bakjoul.todoc.di;

import android.app.Application;

import androidx.annotation.NonNull;

import com.bakjoul.todoc.data.AppDatabase;
import com.bakjoul.todoc.data.dao.ProjectDao;

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

@Module
@InstallIn(SingletonComponent.class)
public class HiltModule {

    @Provides
    @Singleton
    @IoExecutor
    public Executor provideIoExecutor() {
        return Executors.newFixedThreadPool(4);
    }

    @Provides
    @Singleton
    public AppDatabase provideAppDatabase(
            Application application,
            @IoExecutor Executor ioExecutor
    ) {
        return AppDatabase.getInstance(application, ioExecutor);
    }

    @Provides
    @Singleton
    public ProjectDao provideProjectDao(@NonNull AppDatabase appDatabase) {
        return appDatabase.projectDao();
    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    private @interface IoExecutor {}

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    private @interface MainExecutor {}
}
