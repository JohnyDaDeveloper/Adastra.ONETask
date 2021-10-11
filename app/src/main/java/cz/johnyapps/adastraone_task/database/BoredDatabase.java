package cz.johnyapps.adastraone_task.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import cz.johnyapps.adastraone_task.database.daos.ActivityDao;
import cz.johnyapps.adastraone_task.entities.Activity;

@Database(entities = {Activity.class}, version = 1)
public abstract class BoredDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "bored_database";

    public abstract ActivityDao activityDao();

    @Nullable
    private static volatile BoredDatabase instance;

    public static BoredDatabase getDatabase(@NonNull Context context) {
        if (instance == null) {
            synchronized (BoredDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context, BoredDatabase.class, DATABASE_NAME).build();
                }
            }
        }

        return instance;
    }
}
