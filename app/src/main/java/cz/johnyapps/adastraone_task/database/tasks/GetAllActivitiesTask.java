package cz.johnyapps.adastraone_task.database.tasks;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import java.util.List;

import cz.johnyapps.adastraone_task.entities.Activity;

public class GetAllActivitiesTask extends BaseDatabaseTask<Void, Void, LiveData<List<Activity>>> {
    public GetAllActivitiesTask(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    protected LiveData<List<Activity>> doInBackground(@Nullable Void unused) throws Exception {
        return getDatabase().activityDao().getAll();
    }
}
