package com.bakjoul.todoc.ui.task;

import androidx.annotation.NonNull;

import com.bakjoul.todoc.data.entity.Task;

import org.jetbrains.annotations.Contract;

import java.util.Comparator;

public enum TaskSortingType {

    AZ(new TaskAZComparator()),
    ZA(new TaskZAComparator()),
    OLDEST_FIRST(new TaskOldComparator()),
    NEWEST_FIRST(new TaskRecentComparator());

    private final Comparator<Task> comparator;

    TaskSortingType(Comparator<Task> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Task> getComparator() {
        return comparator;
    }

    @NonNull
    @Contract(pure = true)
    @Override
    public String toString() {
        return "TaskSortingType{" +
            "comparator=" + comparator +
            '}';
    }

    private static class TaskAZComparator implements Comparator<Task> {
        @Override
        public int compare(@NonNull Task left, @NonNull Task right) {
            return Long.compare(right.getProjectId(), left.getProjectId());
        }
    }

    private static class TaskZAComparator implements Comparator<Task> {
        @Override
        public int compare(@NonNull Task left, @NonNull Task right) {
            return Long.compare(left.getProjectId(), right.getProjectId());
        }
    }

    private static class TaskOldComparator implements Comparator<Task> {
        @Override
        public int compare(@NonNull Task left, @NonNull Task right) {
            return (int) (left.getId() - right.getId());
        }
    }

    private static class TaskRecentComparator implements Comparator<Task> {
        @Override
        public int compare(@NonNull Task left, @NonNull Task right) {
            return (int) (right.getId() - left.getId());
        }
    }
}
