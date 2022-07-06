package com.bakjoul.todoc.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.bakjoul.todoc.data.entity.Project;

import java.util.List;

@Dao
public interface ProjectDao {

    @Insert
    void insert(Project project);

    @Query("SELECT * FROM project")
    LiveData<List<Project>> getAllProjects();
}
