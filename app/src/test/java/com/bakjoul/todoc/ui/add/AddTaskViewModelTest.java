package com.bakjoul.todoc.ui.add;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.bakjoul.todoc.data.TaskRepository;
import com.bakjoul.todoc.data.entity.Project;
import com.bakjoul.todoc.data.entity.Task;
import com.bakjoul.todoc.ui.ViewEvent;
import com.bakjoul.todoc.utils.LiveDataTestUtil;
import com.bakjoul.todoc.utils.TestExecutor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
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

    @Test
    public void nominal_case() {
        // When
        AddTaskViewState addTaskViewState = LiveDataTestUtil.getValueForTesting(viewModel.getAddTaskViewStateLiveData());
        List<AddTaskProjectItemViewState> addTaskProjectItemViewStateList = LiveDataTestUtil.getValueForTesting(viewModel.getProjectItemsViewState());
        ViewEvent viewEvent = LiveDataTestUtil.getValueForTesting(viewModel.getAddTaskViewEvent());

        // Then
        assertNull(addTaskViewState);
        assertEquals(getDefaultProjectItemViewStates(), addTaskProjectItemViewStateList);
        assertNull(viewEvent);
        Mockito.verifyNoMoreInteractions(application, taskRepository, ioExecutor, mainExecutor);
    }

    @Test
    public void empty_projects() {
        // Given
        projectsLiveData.setValue(new ArrayList<>());

        // When
        AddTaskViewState addTaskViewState = LiveDataTestUtil.getValueForTesting(viewModel.getAddTaskViewStateLiveData());
        List<AddTaskProjectItemViewState> addTaskProjectItemViewStateList = LiveDataTestUtil.getValueForTesting(viewModel.getProjectItemsViewState());
        ViewEvent viewEvent = LiveDataTestUtil.getValueForTesting(viewModel.getAddTaskViewEvent());

        // Then
        assertNull(addTaskViewState);
        assertEquals(new ArrayList<>(), addTaskProjectItemViewStateList);
        assertNull(viewEvent);
    }

    @Test
    public void null_projects() {
        // Given
        projectsLiveData.setValue(null);

        // When
        AddTaskViewState addTaskViewState = LiveDataTestUtil.getValueForTesting(viewModel.getAddTaskViewStateLiveData());
        List<AddTaskProjectItemViewState> addTaskProjectItemViewStateList = LiveDataTestUtil.getValueForTesting(viewModel.getProjectItemsViewState());
        ViewEvent viewEvent = LiveDataTestUtil.getValueForTesting(viewModel.getAddTaskViewEvent());

        // Then
        assertNull(addTaskViewState);
        assertEquals(new ArrayList<>(), addTaskProjectItemViewStateList);
        assertNull(viewEvent);
    }

    @Test
    public void add_task_with_success() {
        // Given
        String taskDescription = "taskDescription";
        long projectId = 2;

        // When
        viewModel.onTaskDescriptionChanged(taskDescription);
        viewModel.onProjectSelected(projectId);
        viewModel.onAddButtonClicked();

        AddTaskViewState addTaskViewState = LiveDataTestUtil.getValueForTesting(viewModel.getAddTaskViewStateLiveData());
        ViewEvent viewEvent = LiveDataTestUtil.getValueForTesting(viewModel.getAddTaskViewEvent());

        // Then
        assertEquals(new AddTaskViewState(null, null), addTaskViewState);
        assertEquals(ViewEvent.DISMISS_ADD_TASK_DIALOG, viewEvent);
        Mockito.verify(ioExecutor).execute(any());
        Mockito.verify(taskRepository).addTask(new Task(projectId, taskDescription));
        Mockito.verify(mainExecutor).execute(any());
        Mockito.verifyNoMoreInteractions(taskRepository, ioExecutor, mainExecutor);
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

    @NonNull
    private List<AddTaskProjectItemViewState> getDefaultProjectItemViewStates() {
        List<Project> projects = getDefaultProjects();
        List<AddTaskProjectItemViewState> addTaskProjectItemViewStateList = new ArrayList<>();
        for (Project p : projects) {
            addTaskProjectItemViewStateList.add(
                    new AddTaskProjectItemViewState(
                            p.getId(),
                            p.getColor(),
                            p.getName()
                    )
            );
        }
        return addTaskProjectItemViewStateList;
    }
    // endregion
}