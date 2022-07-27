package com.bakjoul.todoc.ui.task;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.bakjoul.todoc.data.TaskRepository;
import com.bakjoul.todoc.data.entity.Project;
import com.bakjoul.todoc.data.entity.Task;
import com.bakjoul.todoc.utils.TestExecutor;

import org.junit.Before;
import org.junit.Rule;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModelTest {

    public static final Project PROJECT_1 = new Project(1, "Projet Tartampion", 0xFFEADAD1);
    public static final Project PROJECT_2 = new Project(2, "Projet Lucidia", 0xFFB4CDBA);
    public static final Project PROJECT_3 = new Project(3, "Projet Circus", 0xFFA3CED2);

    public static final Task TASK_PROJECT_1 = new Task(1, PROJECT_1.getId(), "Tâche pour le projet Tartampion");
    public static final Task TASK_PROJECT_2 = new Task(2, PROJECT_2.getId(), "Tâche pour le projet Lucidia");
    public static final Task TASK_PROJECT_3 = new Task(3, PROJECT_3.getId(), "Tâche pour le projet Circus");

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final TaskRepository taskRepository = Mockito.mock(TaskRepository.class);
    private final Executor ioExecutor = Mockito.spy(new TestExecutor());

    private final MutableLiveData<List<Project>> projectsLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Task>> tasksLiveData = new MutableLiveData<>();

    private TaskViewModel viewModel;

    @Before
    public void setUp() {
        Mockito.doReturn(projectsLiveData).when(taskRepository).getAllProjects();
        Mockito.doReturn(tasksLiveData).when(taskRepository).getAllTasks();

        List<Project> projects = new ArrayList<>();
        projects.add(PROJECT_1);
        projects.add(PROJECT_2);
        projects.add(PROJECT_3);
        projectsLiveData.setValue(projects);

        List<Task> tasks = new ArrayList<>();
        tasks.add(TASK_PROJECT_1);
        tasks.add(TASK_PROJECT_2);
        tasks.add(TASK_PROJECT_3);
        tasksLiveData.setValue(tasks);

        viewModel = new TaskViewModel(taskRepository, ioExecutor);

        Mockito.verify(taskRepository).getAllProjects();
        Mockito.verify(taskRepository).getAllTasks();
    }
}