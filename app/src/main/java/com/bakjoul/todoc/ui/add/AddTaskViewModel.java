package com.bakjoul.todoc.ui.add;

import android.app.Application;
import android.database.sqlite.SQLiteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.bakjoul.todoc.R;
import com.bakjoul.todoc.data.TaskRepository;
import com.bakjoul.todoc.data.entity.Project;
import com.bakjoul.todoc.data.entity.Task;
import com.bakjoul.todoc.di.DatabaseModule;
import com.bakjoul.todoc.ui.ViewEvent;
import com.bakjoul.todoc.utils.SingleLiveEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AddTaskViewModel extends ViewModel {

    @NonNull
    private final Application application;
    @NonNull
    private final TaskRepository taskRepository;
    @NonNull
    private final Executor ioExecutor;
    @NonNull
    private final Executor mainExecutor;

    private final MutableLiveData<AddTaskViewState> addTaskViewStateLiveData = new MutableLiveData<>();

    private final LiveData<List<AddTaskProjectItemViewState>> projectItemsViewState;

    private final SingleLiveEvent<ViewEvent> addTaskViewEvent = new SingleLiveEvent<>();

    @Nullable
    private String taskDescription;

    @Nullable
    private Long projectId;

    @Inject
    public AddTaskViewModel(@NonNull Application application,
                            @NonNull TaskRepository taskRepository,
                            @NonNull @DatabaseModule.IoExecutor Executor ioExecutor,
                            @NonNull @DatabaseModule.MainExecutor Executor mainExecutor) {
        this.application = application;
        this.taskRepository = taskRepository;
        this.ioExecutor = ioExecutor;
        this.mainExecutor = mainExecutor;

        projectItemsViewState = Transformations.map(
                taskRepository.getAllProjects(), projects -> {
                    List<AddTaskProjectItemViewState> projectItemViewStateList = new ArrayList<>();
                    if (projects != null) {
                        for (Project project : projects) {
                            projectItemViewStateList.add(
                                    new AddTaskProjectItemViewState(
                                            project.getId(),
                                            project.getColor(),
                                            project.getName()
                                    )
                            );
                        }
                    }

                    return projectItemViewStateList;
                }
        );
    }

    public LiveData<AddTaskViewState> getAddTaskViewStateLiveData() {
        return addTaskViewStateLiveData;
    }

    public LiveData<List<AddTaskProjectItemViewState>> getProjectItemsViewState() {
        return projectItemsViewState;
    }

    public SingleLiveEvent<ViewEvent> getAddTaskViewEvent() {
        return addTaskViewEvent;
    }

    public void onTaskDescriptionChanged(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public void onProjectSelected(long projectId) {
        this.projectId = projectId;
    }

    public void onAddButtonClicked() {
        Form form = getFormattedInputs();
        if (form != null) {
            ioExecutor.execute(() -> {
                try {
                    taskRepository.addTask(new Task(form.projectId, form.taskDescription));
                    mainExecutor.execute(() -> addTaskViewEvent.setValue(ViewEvent.DISMISS_ADD_TASK_DIALOG));
                } catch (SQLiteException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Nullable
    private Form getFormattedInputs() {
        boolean areInputsOk = true;

        String taskDescriptionError;
        if (taskDescription == null || taskDescription.isEmpty()) {
            taskDescriptionError = application.getString(R.string.error_task_description);
            areInputsOk = false;
        } else {
            taskDescriptionError = null;
        }

        String projectError;
        if (projectId == null) {
            projectError = application.getString(R.string.error_project);
            areInputsOk = false;
        } else {
            projectError = null;
        }

        addTaskViewStateLiveData.setValue(
                new AddTaskViewState(
                        taskDescriptionError,
                        projectError
                )
        );

        Form result = null;

        if (areInputsOk) {
            result = new Form(
                    taskDescription,
                    projectId
            );
        }
        return result;
    }

    private static class Form {
        @NonNull
        private final String taskDescription;
        private final long projectId;

        public Form(@NonNull String taskDescription, long projectId) {
            this.taskDescription = taskDescription;
            this.projectId = projectId;
        }
    }

}
