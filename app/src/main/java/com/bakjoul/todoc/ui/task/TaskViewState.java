package com.bakjoul.todoc.ui.task;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import java.util.Objects;

public class TaskViewState {

    private final long taskId;

    @ColorInt
    private final int projectColor;

    @NonNull
    private final String taskDescription;

    public TaskViewState(long taskId, int projectColor, @NonNull String taskDescription) {
        this.taskId = taskId;
        this.projectColor = projectColor;
        this.taskDescription = taskDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskViewState that = (TaskViewState) o;
        return taskId == that.taskId && projectColor == that.projectColor && taskDescription.equals(that.taskDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, projectColor, taskDescription);
    }

    @NonNull
    @Override
    public String toString() {
        return "TaskViewState{" +
                "taskId=" + taskId +
                ", projectColor=" + projectColor +
                ", taskDescription='" + taskDescription + '\'' +
                '}';
    }
}
