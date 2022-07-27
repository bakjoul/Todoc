package com.bakjoul.todoc.ui.add;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.bakjoul.todoc.data.TaskRepository;
import com.bakjoul.todoc.data.entity.Project;
import com.bakjoul.todoc.utils.TestExecutor;

import org.junit.Before;
import org.junit.Rule;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class AddTaskViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final Application application = Mockito.mock(Application.class);
    private final TaskRepository taskRepository = Mockito.mock(TaskRepository.class);
    private final Executor ioExecutor = Mockito.spy(new TestExecutor());
    private final Executor mainExecutor = Mockito.spy(new TestExecutor());

    private final MutableLiveData<List<Project>> projectsLiveData = new MutableLiveData<>();

    private AddTaskViewModel viewModel;

    @Before
    public void setUp() {
        Mockito.doReturn(projectsLiveData).when(taskRepository).getAllProjects();
        projectsLiveData.setValue(getDefaultProjects());

        viewModel = new AddTaskViewModel(application, taskRepository, ioExecutor, mainExecutor);

        Mockito.verify(taskRepository).getAllProjects();
    }

    // region IN
    @NonNull
    private List<Project> getDefaultProjects() {
        List<Project> projects = new ArrayList<>();
        projects.add(new Project(1, "Projet Tartampion", 0xFFEADAD1));
        projects.add(new Project(2, "Projet Lucidia", 0xFFB4CDBA));
        projects.add(new Project(3, "Projet Circus", 0xFFA3CED2));
        return projects;
    }
    // endregion
}