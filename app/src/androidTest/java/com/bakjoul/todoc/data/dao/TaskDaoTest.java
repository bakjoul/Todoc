package com.bakjoul.todoc.data.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.bakjoul.todoc.data.AppDatabase;
import com.bakjoul.todoc.data.entity.Project;
import com.bakjoul.todoc.data.entity.Task;
import com.bakjoul.todoc.utils.LiveDataTestUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

public class TaskDaoTest {

    private static final int EXPECTED_PROJECT_ID = 1;
    private static final String EXPECTED_TASK_DESCRIPTION = "taskDescription";

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

    @Test
    public void insert_task() {
        // Given
        Task task = new Task(EXPECTED_PROJECT_ID, EXPECTED_TASK_DESCRIPTION);

        // When
        taskDao.insert(task);
        List<Task> results = LiveDataTestUtil.getValueForTesting(taskDao.getAllTasks());

        // Then
        assertEquals(Collections.singletonList(new Task(1, EXPECTED_PROJECT_ID, EXPECTED_TASK_DESCRIPTION)), results);
    }

    @Test
    public void delete_task() {
        // Given
        long taskId = 1;
        Task task = new Task(taskId, EXPECTED_PROJECT_ID, EXPECTED_TASK_DESCRIPTION);
        taskDao.insert(task);

        // When
        taskDao.delete(taskId);
        List<Task> results = LiveDataTestUtil.getValueForTesting(taskDao.getAllTasks());

        // Then
        assertTrue(results.isEmpty());
    }
}
