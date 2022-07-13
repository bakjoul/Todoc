package com.bakjoul.todoc.ui.add;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bakjoul.todoc.data.TaskRepository;
import com.bakjoul.todoc.data.entity.Project;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AddTaskViewModel extends ViewModel {

    @NonNull
    private final Application application;
    @NonNull
    private final TaskRepository taskRepository;

    private final MutableLiveData<AddTaskViewState> addTaskViewStateMutableLiveData = new MutableLiveData<>();

    @NonNull
    private String taskDescription;

    @NonNull
    private Project project;

    @Inject
    public AddTaskViewModel(@NonNull Application application, @NonNull TaskRepository taskRepository) {
        this.application = application;
        this.taskRepository = taskRepository;

    }

    public void onProjectSelected() {

    }
}
