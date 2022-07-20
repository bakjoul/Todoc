package com.bakjoul.todoc.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Comparator;
import java.util.Objects;

@Entity(tableName = "task")
public class Task {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(index = true)
    private long projectId;

    @NonNull
    private String taskDescription;

    @Ignore
    public Task(long projectId, @NonNull String taskDescription) {
        this(0, projectId, taskDescription);
    }

    public Task(long id, long projectId, @NonNull String taskDescription) {
        this.id = id;
        this.projectId = projectId;
        this.taskDescription = taskDescription;
    }

    public long getId() {
        return id;
    }

    public long getProjectId() {
        return projectId;
    }

    @NonNull
    public String getTaskDescription() {
        return taskDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && projectId == task.projectId && taskDescription.equals(task.taskDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, projectId, taskDescription);
    }

    @NonNull
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", taskDescription='" + taskDescription + '\'' +
                '}';
    }

    public static class TaskAZComparator implements Comparator<Task> {
        @Override
        public int compare(@NonNull Task left, @NonNull Task right) {
            return Long.compare(right.getProjectId(), left.getProjectId());
        }
    }

    public static class TaskZAComparator implements Comparator<Task> {
        @Override
        public int compare(@NonNull Task left, @NonNull Task right) {
            return Long.compare(left.getProjectId(), right.getProjectId());
        }
    }

    public static class TaskRecentComparator implements Comparator<Task> {
        @Override
        public int compare(@NonNull Task left, @NonNull Task right) {
            return (int) (right.getId() - left.getId());
        }
    }

    public static class TaskOldComparator implements Comparator<Task> {
        @Override
        public int compare(@NonNull Task left, @NonNull Task right) {
            return (int) (left.getId() - right.getId());
        }
    }
}
