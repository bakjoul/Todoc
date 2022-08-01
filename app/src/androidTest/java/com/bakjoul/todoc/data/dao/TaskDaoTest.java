package com.bakjoul.todoc.data.dao;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.bakjoul.todoc.data.AppDatabase;
import com.bakjoul.todoc.data.entity.Project;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

public class TaskDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private AppDatabase appDatabase;
    private TaskDao taskDao;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        appDatabase = Room
                .inMemoryDatabaseBuilder(context, AppDatabase.class)
                .build();
        taskDao = appDatabase.getTaskDao();

        appDatabase.getProjectDao().insert(
                new Project(1, "Project 1", 1)
        );
    }

    @After
    public void closeDb() {
        appDatabase.close();
    }


}
