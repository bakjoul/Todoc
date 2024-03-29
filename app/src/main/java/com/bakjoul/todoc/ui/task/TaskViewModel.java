package com.bakjoul.todoc.ui.task;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bakjoul.todoc.data.TaskRepository;
import com.bakjoul.todoc.data.entity.Project;
import com.bakjoul.todoc.data.entity.Task;
import com.bakjoul.todoc.di.DatabaseModule;
import com.bakjoul.todoc.ui.ViewEvent;
import com.bakjoul.todoc.utils.SingleLiveEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class TaskViewModel extends ViewModel {

    @NonNull
    private final TaskRepository taskRepository;
    @NonNull
    private final Executor ioExecutor;

    private final MutableLiveData<TaskSortingType> taskSortingTypeMutableLiveData = new MutableLiveData<>();

    private final MediatorLiveData<TaskViewState> taskViewStateMediatorLiveData = new MediatorLiveData<>();

    private final SingleLiveEvent<ViewEvent> taskViewEvent = new SingleLiveEvent<>();

    @Inject
    public TaskViewModel(@NonNull TaskRepository taskRepository, @DatabaseModule.IoExecutor @NonNull Executor ioExecutor) {
        this.taskRepository = taskRepository;
        this.ioExecutor = ioExecutor;

        LiveData<List<Task>> taskLiveData = taskRepository.getAllTasks();
        LiveData<List<Project>> projectLiveData = taskRepository.getAllProjects();

        taskViewStateMediatorLiveData.addSource(taskLiveData, tasks ->
            combine(tasks, taskSortingTypeMutableLiveData.getValue(), projectLiveData.getValue())
        );
        taskViewStateMediatorLiveData.addSource(taskSortingTypeMutableLiveData, taskSortingType ->
            combine(taskLiveData.getValue(), taskSortingType, projectLiveData.getValue())
        );
        taskViewStateMediatorLiveData.addSource(projectLiveData, projects ->
            combine(taskLiveData.getValue(), taskSortingTypeMutableLiveData.getValue(), projects)
        );

    }

    private void combine(@Nullable List<Task> tasks, @Nullable TaskSortingType taskSortingType, @Nullable List<Project> projects) {
        if (tasks == null || projects == null) {
            return;
        }

        List<TaskViewStateItem> taskViewStateItemList = new ArrayList<>();

        if (taskSortingType != null) {
            Collections.sort(tasks, taskSortingType.getComparator());
        }

        for (Task task : tasks) {
            for (Project project : projects) {
                if (task.getProjectId() == project.getId()) {
                    taskViewStateItemList.add(
                        new TaskViewStateItem(
                            task.getId(),
                            project.getColor(),
                            task.getTaskDescription(),
                            project.getName()
                        )
                    );
                    break;
                }
            }
        }
        taskViewStateMediatorLiveData.setValue(
            new TaskViewState(
                taskViewStateItemList,
                taskViewStateItemList.isEmpty()
            )
        );
    }

    public void onSortingTypeChanged(TaskSortingType taskSortingType) {
        taskSortingTypeMutableLiveData.setValue(taskSortingType);
    }

    public LiveData<TaskViewState> getTaskViewStateLiveData() {
        return taskViewStateMediatorLiveData;
    }

    public SingleLiveEvent<ViewEvent> getTaskViewEvent() {
        return taskViewEvent;
    }

    public void onAddButtonClicked() {
        taskViewEvent.setValue(ViewEvent.DISPLAY_ADD_TASK_DIALOG);
    }

    public void onDeleteTaskButtonClicked(long taskId) {
        ioExecutor.execute(() -> taskRepository.deleteTask(taskId));
    }
}
