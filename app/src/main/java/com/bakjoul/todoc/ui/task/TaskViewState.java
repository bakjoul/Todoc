package com.bakjoul.todoc.ui.task;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Objects;

public class TaskViewState {

    private final List<TaskViewStateItem> taskViewStateItems;
    private final boolean isEmptyStateVisible;

    public TaskViewState(List<TaskViewStateItem> taskViewStateItems, boolean isEmptyStateVisible) {
        this.taskViewStateItems = taskViewStateItems;
        this.isEmptyStateVisible = isEmptyStateVisible;
    }

    public List<TaskViewStateItem> getTaskViewStateItems() {
        return taskViewStateItems;
    }

    public boolean isEmptyStateVisible() {
        return isEmptyStateVisible;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskViewState that = (TaskViewState) o;
        return isEmptyStateVisible == that.isEmptyStateVisible && Objects.equals(taskViewStateItems, that.taskViewStateItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskViewStateItems, isEmptyStateVisible);
    }

    @NonNull
    @Override
    public String toString() {
        return "TaskViewState{" +
            "taskViewStateItems=" + taskViewStateItems +
            ", isEmptyStateVisible=" + isEmptyStateVisible +
            '}';
    }
}
