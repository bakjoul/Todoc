package com.bakjoul.todoc.ui.add;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import java.util.Objects;

public class AddTaskProjectItemViewState {

    private final long projectId;

    @ColorInt
    private final int projectColor;

    @NonNull
    private final String projectName;

    public AddTaskProjectItemViewState(long projectId, @ColorInt int projectColor, @NonNull String projectName) {
        this.projectId = projectId;
        this.projectColor = projectColor;
        this.projectName = projectName;
    }

    public long getProjectId() {
        return projectId;
    }

    public int getProjectColor() {
        return projectColor;
    }

    @NonNull
    public String getProjectName() {
        return projectName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddTaskProjectItemViewState that = (AddTaskProjectItemViewState) o;
        return projectId == that.projectId && projectColor == that.projectColor && projectName.equals(that.projectName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, projectColor, projectName);
    }

    @NonNull
    @Override
    public String toString() {
        return "AddTaskProjectItemViewState{" +
            "projectId=" + projectId +
            ", projectColor=" + projectColor +
            ", projectName='" + projectName + '\'' +
            '}';
    }
}
