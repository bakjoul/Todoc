package com.bakjoul.todoc.data.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.database.sqlite.SQLiteException;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.bakjoul.todoc.data.AppDatabase;
import com.bakjoul.todoc.data.entity.Project;
import com.bakjoul.todoc.utils.LiveDataTestUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ProjectDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private AppDatabase appDatabase;
    private ProjectDao projectDao;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        projectDao = appDatabase.getProjectDao();
    }

    @After
    public void closeDb() {
        appDatabase.close();
    }

    @Test
    public void insert_project() {
        // Given
        String projectName = "projectName";
        int projectColor = 1;
        Project project = new Project(1, projectName, projectColor);

        // When
        projectDao.insert(project);
        List<Project> results = LiveDataTestUtil.getValueForTesting(projectDao.getAllProjects());

        // Then
        assertEquals(Collections.singletonList(new Project(1, projectName, projectColor)), results);
    }

    @Test(expected = SQLiteException.class)
    public void insert_failed_duplicate_id() {
        // Given
        String projectName = "projectName";
        int projectColor = 1;
        Project project = new Project(1, projectName, projectColor);

        // When
        projectDao.insert(project);
        projectDao.insert(project);
    }

    @Test
    public void getAllProjects_should_be_empty() {
        // When
        List<Project> results = LiveDataTestUtil.getValueForTesting(projectDao.getAllProjects());

        // Then
        assertTrue(results.isEmpty());
    }
}
