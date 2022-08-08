package com.bakjoul.todoc.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.bakjoul.todoc.R;
import com.bakjoul.todoc.data.dao.ProjectDao;
import com.bakjoul.todoc.data.dao.TaskDao;
import com.bakjoul.todoc.data.entity.Project;
import com.bakjoul.todoc.data.entity.Task;

import java.util.concurrent.Executor;

@Database(
        entities = {
                Project.class,
                Task.class
        },
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "app_database";

    private static volatile AppDatabase instance;

    public abstract ProjectDao getProjectDao();

    public abstract TaskDao getTaskDao();

    public static AppDatabase getInstance(
            @NonNull Application application,
            @NonNull Executor ioExecutor
    ) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = create(application, ioExecutor);
                }
            }
        }
        return instance;
    }

    @NonNull
    private static AppDatabase create(
            @NonNull Application application,
            @NonNull Executor ioExecutor
    ) {
        Builder<AppDatabase> builder = Room.databaseBuilder(
                application,
                AppDatabase.class,
                DATABASE_NAME
        );

        builder.addCallback(new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                ioExecutor.execute(() -> {
                    ProjectDao projectDao = AppDatabase.getInstance(application, ioExecutor).getProjectDao();

                    projectDao.insert(
                            new Project(
                                    application.getString(R.string.project_tartampion),
                                    ResourcesCompat.getColor(application.getResources(), R.color.project_color_tartampion, null)
                            )
                    );
                    projectDao.insert(
                            new Project(
                                    application.getString(R.string.project_lucidia),
                                    ResourcesCompat.getColor(application.getResources(), R.color.project_color_lucidia, null)
                            )
                    );
                    projectDao.insert(
                            new Project(
                                    application.getString(R.string.project_circus),
                                    ResourcesCompat.getColor(application.getResources(), R.color.project_color_circus, null)
                            )
                    );

/*                    TaskDao taskDao = AppDatabase.getInstance(application, ioExecutor).taskDao();

                    for (int i = 0; i < 10; i++) {
                        Task task = new Task(
                                (i % 3) + 1,
                                "Task description #" + i
                        );
                        taskDao.insert(task);
                    }*/
                });
            }
        });
        return builder.build();
    }

}
