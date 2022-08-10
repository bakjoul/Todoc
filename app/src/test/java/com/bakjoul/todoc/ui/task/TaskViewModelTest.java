package com.bakjoul.todoc.ui.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;

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
import java.util.Collections;
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

        projectsLiveData.setValue(getDefaultProjects());
        tasksLiveData.setValue(getDefaultTasks());

        viewModel = new TaskViewModel(taskRepository, ioExecutor);

        Mockito.verify(taskRepository).getAllProjects();
        Mockito.verify(taskRepository).getAllTasks();
    }


    @Test
    public void nominal_case() {
        // When
        TaskViewState taskViewState = LiveDataTestUtil.getValueForTesting(viewModel.getTaskViewStateLiveData());

        // Then
        assertEquals(taskViewState, getDefaultTaskViewState());

        Mockito.verify(ioExecutor, Mockito.never()).execute(any());
        Mockito.verifyNoMoreInteractions(taskRepository, ioExecutor);
    }

    @Test
    public void initial_case() {
        // Given
        projectsLiveData.setValue(new ArrayList<>());
        tasksLiveData.setValue(new ArrayList<>());

        // When
        TaskViewState taskViewState = LiveDataTestUtil.getValueForTesting(viewModel.getTaskViewStateLiveData());

        // Then
        assertEquals(getEmptyTaskViewState(), taskViewState);
    }

    @Test
    public void sortByProjectAZ() {
        // Given
        viewModel.onSortingTypeChanged(TaskSortingType.AZ);

        // When
        TaskViewState taskViewState = LiveDataTestUtil.getValueForTesting(viewModel.getTaskViewStateLiveData());

        // Then
        assertEquals(getDefaultTaskViewStateSortedByProjectAZ(), taskViewState);
    }

    @Test
    public void sortByProjectZA() {
        // Given
        viewModel.onSortingTypeChanged(TaskSortingType.AZ);
        viewModel.onSortingTypeChanged(TaskSortingType.ZA);

        // When
        TaskViewState taskViewState = LiveDataTestUtil.getValueForTesting(viewModel.getTaskViewStateLiveData());

        // Then
        assertEquals(getDefaultTaskViewStateSortedByProjectZA(), taskViewState);
    }

    @Test
    public void sortByOldestFirst() {
        // Given
        viewModel.onSortingTypeChanged(TaskSortingType.AZ);
        viewModel.onSortingTypeChanged(TaskSortingType.OLDEST_FIRST);

        // When
        TaskViewState taskViewState = LiveDataTestUtil.getValueForTesting(viewModel.getTaskViewStateLiveData());

        // Then
        assertEquals(getDefaultTaskViewState(), taskViewState);
    }

    @Test
    public void sortByNewestFirst() {
        // Given
        viewModel.onSortingTypeChanged(TaskSortingType.OLDEST_FIRST);
        viewModel.onSortingTypeChanged(TaskSortingType.NEWEST_FIRST);

        // When
        TaskViewState taskViewState = LiveDataTestUtil.getValueForTesting(viewModel.getTaskViewStateLiveData());

        // Then
        assertEquals(getDefaultTaskViewStateSortedByNewestFirst(), taskViewState);
    }

    @Test
    public void verify_onDeleteTaskButtonClicked() {
        // Given
        long taskId = 3;

        // When
        viewModel.onDeleteTaskButtonClicked(taskId);

        // Then
        Mockito.verify(taskRepository).deleteTask(taskId);
        Mockito.verify(ioExecutor).execute(any());
        Mockito.verifyNoMoreInteractions(taskRepository, ioExecutor);
    }

    @Test
    public void viewEvent_should_be_null() {
        // When
        ViewEvent result = LiveDataTestUtil.getValueForTesting(viewModel.getTaskViewEvent());

        // Then
        assertNull(result);
    }

    @Test
    public void onAddButtonClicked_viewEvent_should_expose_display_add_task_dialog() {
        // When
        viewModel.onAddButtonClicked();
        ViewEvent result = LiveDataTestUtil.getValueForTesting(viewModel.getTaskViewEvent());

        // Then
        assertEquals(ViewEvent.DISPLAY_ADD_TASK_DIALOG, result);
    }

    // region IN
    @NonNull
    private List<Project> getDefaultProjects() {
        List<Project> projects = new ArrayList<>();
        projects.add(PROJECT_1);
        projects.add(PROJECT_2);
        projects.add(PROJECT_3);
        return projects;
    }

    @NonNull
    private List<Task> getDefaultTasks() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(TASK_PROJECT_1);
        tasks.add(TASK_PROJECT_2);
        tasks.add(TASK_PROJECT_3);
        return tasks;
    }
    // endregion

    // region OUT
    @NonNull
    private TaskViewState getDefaultTaskViewState() {
        List<Project> projects = getDefaultProjects();
        List<Task> tasks = getDefaultTasks();
        List<TaskViewStateItem> taskViewStateItemList = new ArrayList<>();

        for (Project p : projects) {
            for (Task t : tasks) {
                if (t.getProjectId() == p.getId()) {
                    taskViewStateItemList.add(
                        new TaskViewStateItem(
                            t.getId(),
                            p.getColor(),
                            t.getTaskDescription(),
                            p.getName()
                        )
                    );
                }
            }
        }
        return new TaskViewState(taskViewStateItemList, taskViewStateItemList.isEmpty());
    }

    @NonNull
    private TaskViewState getEmptyTaskViewState() {
        return new TaskViewState(new ArrayList<>(), true);
    }

    @NonNull
    private TaskViewState getDefaultTaskViewStateSortedByProjectAZ() {
        List<TaskViewStateItem> taskViewStateItemList = new ArrayList<>();
        taskViewStateItemList.add(new TaskViewStateItem(TASK_PROJECT_3.getId(), PROJECT_3.getColor(), TASK_PROJECT_3.getTaskDescription(), PROJECT_3.getName()));
        taskViewStateItemList.add(new TaskViewStateItem(TASK_PROJECT_2.getId(), PROJECT_2.getColor(), TASK_PROJECT_2.getTaskDescription(), PROJECT_2.getName()));
        taskViewStateItemList.add(new TaskViewStateItem(TASK_PROJECT_1.getId(), PROJECT_1.getColor(), TASK_PROJECT_1.getTaskDescription(), PROJECT_1.getName()));

        return new TaskViewState(taskViewStateItemList, false);
    }

    @NonNull
    private TaskViewState getDefaultTaskViewStateSortedByProjectZA() {
        List<TaskViewStateItem> taskViewStateItemList = new ArrayList<>();
        taskViewStateItemList.add(new TaskViewStateItem(TASK_PROJECT_1.getId(), PROJECT_1.getColor(), TASK_PROJECT_1.getTaskDescription(), PROJECT_1.getName()));
        taskViewStateItemList.add(new TaskViewStateItem(TASK_PROJECT_2.getId(), PROJECT_2.getColor(), TASK_PROJECT_2.getTaskDescription(), PROJECT_2.getName()));
        taskViewStateItemList.add(new TaskViewStateItem(TASK_PROJECT_3.getId(), PROJECT_3.getColor(), TASK_PROJECT_3.getTaskDescription(), PROJECT_3.getName()));

        return new TaskViewState(taskViewStateItemList, false);
    }

    @NonNull
    private TaskViewState getDefaultTaskViewStateSortedByNewestFirst() {
        List<TaskViewStateItem> taskViewStateItemList = getDefaultTaskViewState().getTaskViewStateItems();
        Collections.reverse(taskViewStateItemList);
        return new TaskViewState(taskViewStateItemList, false);
    }
    // endregion
}