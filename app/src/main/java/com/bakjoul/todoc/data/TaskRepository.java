package com.bakjoul.todoc.data;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;

import com.bakjoul.todoc.data.dao.ProjectDao;
import com.bakjoul.todoc.data.dao.TaskDao;
import com.bakjoul.todoc.data.entity.Project;
import com.bakjoul.todoc.data.entity.Task;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TaskRepository {

    @NonNull
    private final ProjectDao projectDao;

    @NonNull
    private final TaskDao taskDao;

    @Inject
    public TaskRepository(@NonNull ProjectDao projectDao, @NonNull TaskDao taskDao) {
        this.projectDao = projectDao;
        this.taskDao = taskDao;
    }

    @MainThread
    public LiveData<List<Project>> getAllProjects() {
        return projectDao.getAllProjects();
    }

    @MainThread
    public LiveData<List<Task>> getAllTasks() {
        return taskDao.getAllTasks();
    }

    @WorkerThread
    public void addTask(Task task) {
        taskDao.insert(task);
    }

    @WorkerThread
    public void deleteTask(long taskId) {
        taskDao.delete(taskId);
    }
}
