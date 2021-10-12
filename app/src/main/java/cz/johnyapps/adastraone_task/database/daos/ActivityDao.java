package cz.johnyapps.adastraone_task.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cz.johnyapps.adastraone_task.entities.Activity;

@Dao
public interface ActivityDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Activity activity);

    @Query("SELECT * FROM Activity WHERE `key` = :key")
    Activity get(String key);

    @Query("SELECT * FROM Activity")
    LiveData<List<Activity>> getAll();

    @Update
    void update(Activity activity);

    @Query("SELECT * FROM Activity WHERE `key` != :key ORDER BY RANDOM() LIMIT 1")
    Activity getRandom(String key);

    @Query("SELECT * FROM Activity ORDER BY RANDOM() LIMIT 1")
    Activity getRandom();
}
