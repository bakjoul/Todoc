package com.bakjoul.todoc.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.bakjoul.todoc.data.entity.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void insert(Task task);

    @Query("DELETE FROM task WHERE id=:taskId")
    int delete(long taskId);

    @Query("SELECT * FROM task")
    LiveData<List<Task>> getAllTasks();
}
