package com.bakjoul.todoc.data;

import com.bakjoul.todoc.data.dao.ProjectDao;
import com.bakjoul.todoc.data.dao.TaskDao;

import org.junit.Before;
import org.mockito.Mockito;

public class TaskRepositoryTest {

    private final ProjectDao projectDao = Mockito.mock(ProjectDao.class);
    private final TaskDao taskDao = Mockito.mock(TaskDao.class);
    private TaskRepository taskRepository;

    @Before
    public void setUp() {
        taskRepository = new TaskRepository(projectDao, taskDao);
    }



}