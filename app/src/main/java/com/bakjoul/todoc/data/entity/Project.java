package com.bakjoul.todoc.data.entity;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "project")
public class Project {

    @PrimaryKey(autoGenerate = true)
    private final long id;

    @NonNull
    private final String name;

    @ColorInt
    private final int color;

    @Ignore
    public Project(@NonNull String name, @ColorInt int color) {
        this(0, name, color);
    }

    public Project(long id, @NonNull String name, int color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public long getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return id == project.id && color == project.color && name.equals(project.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, color);
    }

    @NonNull
    @Override
    public String toString() {
        return "Project{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", color=" + color +
            '}';
    }
}
