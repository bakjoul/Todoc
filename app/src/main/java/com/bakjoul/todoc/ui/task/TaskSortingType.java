package com.bakjoul.todoc.ui.task;

import androidx.annotation.NonNull;

import com.bakjoul.todoc.data.entity.Task;

import org.jetbrains.annotations.Contract;

import java.util.Comparator;

public enum TaskSortingType {

    AZ(R.string.sorting_alphabetic),
    ZA(R.string.sorting_alphabetic_inverted),
    OLDEST_FIRST(R.string.sorting_oldest_first),
    NEWEST_FIRST(R.string.sorting_newest_first);

    @StringRes
    private final int sortingTypeStringRes;

    TaskSortingType(int sortingTypeStringRes) {
        this.sortingTypeStringRes = sortingTypeStringRes;
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

    @NonNull
    @Override
    public String toString() {
        return "TaskSortingType{" +
                "sortingTypeStringRes=" + sortingTypeStringRes +
                '}';
    }
}
