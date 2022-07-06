package com.bakjoul.todoc.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

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

    public abstract ProjectDao projectDao();

    public abstract TaskDao taskDao();

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
    private static AppDatabase  create(
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
                    ProjectDao projectDao = AppDatabase.getInstance(application, ioExecutor).projectDao();

                    projectDao.insert(
                            new Project(
                                    "Projet Tartampion",
                                    0xFFEADAD1
                            )
                    );
                    projectDao.insert(
                            new Project(
                                    "Projet Lucidia",
                                    0xFFB4CDBA
                            )
                    );
                    projectDao.insert(
                            new Project(
                                    "Projet Circus",
                                    0xFFA3CED2
                            )
                    );
                });
            }
        });
        return builder.build();
    }

}
