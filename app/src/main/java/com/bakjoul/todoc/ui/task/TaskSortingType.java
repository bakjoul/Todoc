package com.bakjoul.todoc.ui.task;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.bakjoul.todoc.R;
import com.bakjoul.todoc.data.entity.Task;

import java.util.Comparator;

public enum TaskSortingType {

    AZ(
            R.string.sorting_alphabetic,
            (t1, t2) -> {
                return t1.getTaskDescription().compareTo(t2.getTaskDescription());
            }
    ),
    ZA(
            R.string.sorting_alphabetic_inverted,
            (t1, t2) -> {
                return t2.getTaskDescription().compareTo(t1.getTaskDescription());
            }
    ),
    OLDEST_FIRST(
            R.string.sorting_oldest_first,
            (t1, t2) -> (int) (t1.getId() - t2.getId())
    ),
    NEWEST_FIRST(
            R.string.sorting_newest_first,
            (t1, t2) -> (int) (t2.getId() - t1.getId())
    );

    @StringRes
    private final int sortingTypeStringRes;

    @Nullable
    private final Comparator<Task> comparator;

    TaskSortingType(@StringRes int sortingTypeStringRes, @Nullable Comparator<Task> comparator) {
        this.sortingTypeStringRes = sortingTypeStringRes;
        this.comparator = comparator;
    }

    public int getSortingTypeStringRes() {
        return sortingTypeStringRes;
    }

    @Nullable
    public Comparator<Task> getComparator() {
        return comparator;
    }
}
