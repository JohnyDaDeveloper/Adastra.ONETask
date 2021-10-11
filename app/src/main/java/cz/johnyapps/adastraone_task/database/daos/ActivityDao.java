package cz.johnyapps.adastraone_task.database.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import cz.johnyapps.adastraone_task.entities.Activity;

@Dao
public interface ActivityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Activity activity);

    @Query("SELECT * FROM Activity WHERE `key` = :key")
    Activity get(String key);
}
