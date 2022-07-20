package com.bakjoul.todoc.ui.task;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.bakjoul.todoc.R;

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

    public int getSortingTypeStringRes() {
        return sortingTypeStringRes;
    }

    @NonNull
    @Override
    public String toString() {
        return "TaskSortingType{" +
                "sortingTypeStringRes=" + sortingTypeStringRes +
                '}';
    }
}
