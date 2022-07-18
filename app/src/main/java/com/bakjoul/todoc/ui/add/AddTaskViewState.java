package com.bakjoul.todoc.ui.add;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class AddTaskViewState {

    @Nullable
    private final String taskDescriptionError;

    @Nullable
    private final String projectError;

    public AddTaskViewState(@Nullable String taskDescriptionError, @Nullable String projectError) {
        this.taskDescriptionError = taskDescriptionError;
        this.projectError = projectError;
    }

    @Nullable
    public String getTaskDescriptionError() {
        return taskDescriptionError;
    }

    @Nullable
    public String getProjectError() {
        return projectError;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddTaskViewState that = (AddTaskViewState) o;
        return Objects.equals(taskDescriptionError, that.taskDescriptionError) && Objects.equals(projectError, that.projectError);
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
