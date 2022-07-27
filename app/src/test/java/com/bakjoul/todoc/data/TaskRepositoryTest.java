package com.bakjoul.todoc.data;

import static org.junit.Assert.assertEquals;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bakjoul.todoc.data.dao.ProjectDao;
import com.bakjoul.todoc.data.dao.TaskDao;
import com.bakjoul.todoc.data.entity.Project;
import com.bakjoul.todoc.data.entity.Task;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

public class TaskRepositoryTest {

    private final ProjectDao projectDao = Mockito.mock(ProjectDao.class);
    private final TaskDao taskDao = Mockito.mock(TaskDao.class);
    private TaskRepository taskRepository;

    @Before
    public void setUp() {
        taskRepository = new TaskRepository(projectDao, taskDao);
    }

    @Test
    public void verify_getAllProjects() {
        // Given
        LiveData<List<Project>> projectsLiveData = new MutableLiveData<>();
        Mockito.doReturn(projectsLiveData).when(projectDao).getAllProjects();   // La méthode de l'interface retournera projectsLiveData

        // When
        LiveData<List<Project>> result = projectDao.getAllProjects();

        // Then
        assertEquals(projectsLiveData, result);
        Mockito.verify(projectDao).getAllProjects();    // Vérifie que la méthode a bien été appelée une fois
        Mockito.verifyNoMoreInteractions(projectDao, taskDao);  // Vérifie que les mocks ont aucune autre intéraction non vérifiée
    }

    @Test
    public void verify_addProject() {
        // Given
        Project project = Mockito.mock(Project.class);

        // When
        projectDao.insert(project);

        // Then
        Mockito.verify(projectDao).insert(project);
        Mockito.verifyNoMoreInteractions(projectDao);
    }

    @Test
    public void verify_getAllTasks() {
        // Given
        LiveData<List<Task>> tasksLiveData = new MutableLiveData<>();
        Mockito.doReturn(tasksLiveData).when(taskDao).getAllTasks();

        // When
        LiveData<List<Task>> result = taskDao.getAllTasks();

        // Then
        assertEquals(tasksLiveData, result);
        Mockito.verify(taskDao).getAllTasks();
        Mockito.verifyNoMoreInteractions(projectDao, taskDao);
    }

    @Test
    public void verify_insertTask() {
        // Given
        Task task = Mockito.mock(Task.class);

        // When
        taskRepository.addTask(task);

        // Then
        Mockito.verify(taskDao).insert(task);
        Mockito.verifyNoMoreInteractions(projectDao, taskDao);
    }

    @Test
    public void verify_deleteTask() {
        // Given
        long taskId = 99;

        // When
        taskRepository.deleteTask(taskId);

        // Then
        Mockito.verify(taskDao).delete(taskId);
        Mockito.verifyNoMoreInteractions(projectDao, taskDao);
    }
}