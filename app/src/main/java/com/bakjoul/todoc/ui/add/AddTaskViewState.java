package com.bakjoul.todoc.ui.add;

import androidx.annotation.NonNull;

import java.util.Objects;

public class AddTaskViewState {

    @NonNull
    private final String taskDescriptionError;

    @NonNull
    private final String projectError;

    public AddTaskViewState(@NonNull String taskDescriptionError, @NonNull String projectError) {
        this.taskDescriptionError = taskDescriptionError;
        this.projectError = projectError;
    }

    @NonNull
    public String getTaskDescriptionError() {
        return taskDescriptionError;
    }

    @NonNull
    public String getProjectError() {
        return projectError;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddTaskViewState that = (AddTaskViewState) o;
        return taskDescriptionError.equals(that.taskDescriptionError) && projectError.equals(that.projectError);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskDescriptionError, projectError);
    }

    @NonNull
    @Override
    public String toString() {
        return "AddTaskViewState{" +
                "taskDescriptionError='" + taskDescriptionError + '\'' +
                ", projectError='" + projectError + '\'' +
                '}';
    }
}
