package com.bakjoul.todoc.ui.add;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.bakjoul.todoc.data.TaskRepository;
import com.bakjoul.todoc.data.entity.Project;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AddTaskViewModel extends ViewModel {

    @NonNull
    private final Application application;
    @NonNull
    private final TaskRepository taskRepository;

    private final MutableLiveData<AddTaskViewState> addTaskViewStateLiveData = new MutableLiveData<>();

    private LiveData<List<AddTaskProjectItemViewState>> projectItemsViewState;

    @Nullable
    private String taskDescription;

    @Nullable
    private Long projectId;

    @Inject
    public AddTaskViewModel(@NonNull Application application, @NonNull TaskRepository taskRepository) {
        this.application = application;
        this.taskRepository = taskRepository;

        projectItemsViewState = Transformations.map(
                taskRepository.getAllProjects(), projects -> {
                    List<AddTaskProjectItemViewState> projectItemViewStateList = new ArrayList<>();
                    for (Project project : projects) {
                        projectItemViewStateList.add(
                                new AddTaskProjectItemViewState(
                                        project.getId(),
                                        project.getColor(),
                                        project.getName()
                                )
                        );
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

    public void onTaskDescriptionChanged(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public void onProjectSelected(long projectId) {
        this.projectId = projectId;
    }

}
